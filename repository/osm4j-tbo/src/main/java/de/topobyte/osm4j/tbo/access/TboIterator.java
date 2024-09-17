package de.topobyte.osm4j.tbo.access;

import de.topobyte.compactio.CompactReader;
import de.topobyte.compactio.InputStreamCompactReader;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.tbo.data.FileBlock;
import de.topobyte.osm4j.tbo.data.FileHeader;
import de.topobyte.osm4j.tbo.io.Decompression;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public class TboIterator extends BlockReader implements OsmIterator {
   private FileHeader header;
   private boolean hasMetadata;
   private boolean fetchTags;
   private boolean fetchMetadata;
   private int available = 0;
   private int pointer = 0;
   private boolean valid = true;
   private FileBlock block = null;
   private EntityType entityType = EntityType.Node;
   private List<? extends OsmEntity> entities = null;

   public TboIterator(InputStream input, boolean fetchTags, boolean fetchMetadata) throws IOException {
      this(new InputStreamCompactReader(input), fetchTags, fetchMetadata);
   }

   public TboIterator(CompactReader reader, boolean fetchTags, boolean fetchMetadata) throws IOException {
      super(reader);
      this.fetchTags = fetchTags;
      this.fetchMetadata = fetchMetadata;
      this.header = ReaderUtil.parseHeader(reader);
      this.hasMetadata = this.header.hasMetadata();
   }

   public boolean hasNext() {
      if (this.available == 0) {
         try {
            this.advanceBlock();
         } catch (IOException var2) {
            return false;
         }
      }

      return this.valid && this.available > 0;
   }

   public EntityContainer next() {
      OsmEntity entity = this.entities.get(this.pointer);
      this.pointer++;
      this.available--;
      return new EntityContainer(this.entityType, entity);
   }

   public void remove() {
   }

   private void advanceBlock() throws IOException {
      this.block = this.readBlock();
      if (this.block == null) {
         this.valid = false;
      } else {
         this.pointer = 0;
         this.available = this.block.getNumObjects();
         byte[] uncompressed = Decompression.decompress(this.block);
         ByteArrayInputStream bais = new ByteArrayInputStream(uncompressed);
         CompactReader reader = new InputStreamCompactReader(bais);
         switch (this.block.getType()) {
            case 1:
               this.entityType = EntityType.Node;
               this.entities = ReaderUtil.parseNodes(reader, this.block, this.fetchTags, this.hasMetadata, this.fetchMetadata);
               break;
            case 2:
               this.entityType = EntityType.Way;
               this.entities = ReaderUtil.parseWays(reader, this.block, this.fetchTags, this.hasMetadata, this.fetchMetadata);
               break;
            case 3:
               this.entityType = EntityType.Relation;
               this.entities = ReaderUtil.parseRelations(reader, this.block, this.fetchTags, this.hasMetadata, this.fetchMetadata);
         }
      }
   }

   public Iterator<EntityContainer> iterator() {
      return this;
   }

   public boolean hasBounds() {
      return this.header.hasBounds();
   }

   public OsmBounds getBounds() {
      return this.header.getBounds();
   }
}
