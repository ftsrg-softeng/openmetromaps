package de.topobyte.osm4j.pbf.seq;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.pbf.protobuf.Fileformat;
import de.topobyte.osm4j.pbf.protobuf.Osmformat;
import de.topobyte.osm4j.pbf.util.BlobHeader;
import de.topobyte.osm4j.pbf.util.BlockData;
import de.topobyte.osm4j.pbf.util.PbfUtil;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class PbfIterator implements OsmIterator {
   private DataInputStream input;
   private boolean fetchMetadata;
   private OsmBounds bounds = null;
   private boolean beyondBounds = false;
   private List<OsmNode> nodes = new LinkedList<>();
   private List<OsmWay> ways = new LinkedList<>();
   private List<OsmRelation> relations = new LinkedList<>();
   private int available = 0;
   private boolean finished = false;

   public PbfIterator(InputStream input, boolean fetchMetadata) {
      this.input = new DataInputStream(input);
      this.fetchMetadata = fetchMetadata;
   }

   public boolean hasNext() {
      if (this.available > 0) {
         return true;
      } else {
         while (!this.finished && this.available == 0) {
            try {
               this.tryAdvanceBlock();
            } catch (IOException var2) {
               throw new RuntimeException("error while reading block", var2);
            }
         }

         return this.available > 0;
      }
   }

   public EntityContainer next() {
      while (this.available == 0) {
         if (this.finished) {
            throw new NoSuchElementException();
         }

         try {
            this.tryAdvanceBlock();
         } catch (IOException var2) {
            throw new RuntimeException("error while reading block", var2);
         }
      }

      this.available--;
      if (this.nodes.size() > 0) {
         OsmNode node = this.nodes.remove(0);
         return new EntityContainer(EntityType.Node, node);
      } else if (this.ways.size() > 0) {
         OsmWay way = this.ways.remove(0);
         return new EntityContainer(EntityType.Way, way);
      } else {
         OsmRelation relation = this.relations.remove(0);
         return new EntityContainer(EntityType.Relation, relation);
      }
   }

   public void remove() {
      throw new UnsupportedOperationException("read only");
   }

   private void tryAdvanceBlock() throws IOException {
      try {
         this.advanceBlock();
      } catch (EOFException var2) {
         this.finished = true;
         this.beyondBounds = true;
      }
   }

   private void advanceBlock() throws IOException {
      BlobHeader header = PbfUtil.parseHeader(this.input);
      Fileformat.Blob blob = PbfUtil.parseBlock(this.input, header.getDataLength());
      BlockData blockData = PbfUtil.getBlockData(blob);
      String type = header.getType();
      if (type.equals("OSMData")) {
         this.beyondBounds = true;
         Osmformat.PrimitiveBlock block = Osmformat.PrimitiveBlock.parseFrom(blockData.getBlobData());
         PrimParser primParser = new PrimParser(block, this.fetchMetadata);

         for (Osmformat.PrimitiveGroup group : block.getPrimitivegroupList()) {
            if (group.getNodesCount() > 0) {
               this.pushNodes(primParser, group.getNodesList());
            }

            if (group.hasDense()) {
               this.pushNodes(primParser, group.getDense());
            }

            if (group.getWaysCount() > 0) {
               this.pushWays(primParser, group.getWaysList());
            }

            if (group.getRelationsCount() > 0) {
               this.pushRelations(primParser, group.getRelationsList());
            }
         }
      } else {
         if (!type.equals("OSMHeader")) {
            throw new IOException("invalid PBF block");
         }

         Osmformat.HeaderBlock block = Osmformat.HeaderBlock.parseFrom(blockData.getBlobData());
         Osmformat.HeaderBBox bbox = block.getBbox();
         if (bbox != null && !this.beyondBounds) {
            this.bounds = PbfUtil.bounds(bbox);
         }

         this.beyondBounds = true;
      }
   }

   private void pushNodes(PrimParser primParser, List<Osmformat.Node> nodes) {
      this.available = this.available + nodes.size();

      for (Osmformat.Node node : nodes) {
         this.nodes.add(primParser.convert(node));
      }
   }

   private void pushNodes(PrimParser primParser, Osmformat.DenseNodes dense) {
      List<OsmNode> nodes = primParser.convert(dense);
      this.available = this.available + nodes.size();
      this.nodes.addAll(nodes);
   }

   private void pushWays(PrimParser primParser, List<Osmformat.Way> ways) {
      this.available = this.available + ways.size();

      for (Osmformat.Way way : ways) {
         this.ways.add(primParser.convert(way));
      }
   }

   private void pushRelations(PrimParser primParser, List<Osmformat.Relation> relations) {
      this.available = this.available + relations.size();

      for (Osmformat.Relation relation : relations) {
         this.relations.add(primParser.convert(relation));
      }
   }

   public Iterator<EntityContainer> iterator() {
      return this;
   }

   public boolean hasBounds() {
      this.ensureBeyondBounds();
      return this.bounds != null;
   }

   public OsmBounds getBounds() {
      this.ensureBeyondBounds();
      return this.bounds;
   }

   private void ensureBeyondBounds() {
      while (!this.beyondBounds) {
         try {
            this.tryAdvanceBlock();
         } catch (IOException var2) {
            throw new RuntimeException("error while reading block", var2);
         }
      }
   }
}
