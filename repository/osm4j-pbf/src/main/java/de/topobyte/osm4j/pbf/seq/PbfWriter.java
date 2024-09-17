package de.topobyte.osm4j.pbf.seq;

import com.google.protobuf.ByteString;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmMetadata;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.model.impl.Metadata;
import de.topobyte.osm4j.pbf.Compression;
import de.topobyte.osm4j.pbf.protobuf.Osmformat;
import de.topobyte.osm4j.pbf.util.PbfUtil;
import de.topobyte.osm4j.pbf.util.StringTable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PbfWriter extends BlockWriter implements OsmOutputStream {
   private boolean writeMetadata;
   private Compression compression = Compression.DEFLATE;
   private boolean useDense = true;
   private int granularity = 100;
   private int dateGranularity = 1000;
   private StringTable stringTable = new StringTable();
   private int batchLimit = 4000;
   private int counter = 0;
   private List<OsmNode> bufNodes = new ArrayList<>();
   private List<OsmWay> bufWays = new ArrayList<>();
   private List<OsmRelation> bufRelations = new ArrayList<>();
   private boolean headerWritten = false;

   public PbfWriter(OutputStream output, boolean writeMetadata) {
      super(output);
      this.writeMetadata = writeMetadata;
   }

   public Compression getCompression() {
      return this.compression;
   }

   public void setCompression(Compression compression) {
      this.compression = compression;
   }

   public boolean isUseDense() {
      return this.useDense;
   }

   public void setUseDense(boolean useDense) {
      this.useDense = useDense;
   }

   public int getGranularity() {
      return this.granularity;
   }

   public void setGranularity(int granularity) {
      this.granularity = granularity;
   }

   public int getDateGranularity() {
      return this.dateGranularity;
   }

   public void setDateGranularity(int dateGranularity) {
      this.dateGranularity = dateGranularity;
   }

   public int getBatchLimit() {
      return this.batchLimit;
   }

   public void setBatchLimit(int batchLimit) {
      this.batchLimit = batchLimit;
   }

   public void write(OsmBounds bounds) throws IOException {
      if (!this.headerWritten) {
         this.writeHeader(bounds);
      }
   }

   private void ensureHeader() throws IOException {
      if (!this.headerWritten) {
         this.writeHeader(null);
      }
   }

   public void write(OsmNode node) throws IOException {
      this.bufNodes.add(node);
      this.incrementCounter();
   }

   public void write(OsmWay way) throws IOException {
      this.bufWays.add(way);
      this.incrementCounter();
   }

   public void write(OsmRelation relation) throws IOException {
      this.bufRelations.add(relation);
      this.incrementCounter();
   }

   private void incrementCounter() throws IOException {
      if (++this.counter >= this.batchLimit) {
         this.writeBatch();
      }
   }

   public void complete() throws IOException {
      this.ensureHeader();
      if (this.counter > 0) {
         this.writeBatch();
      }
   }

   private void writeHeader(OsmBounds bounds) throws IOException {
      Osmformat.HeaderBlock header = PbfUtil.createHeader("osm4j-pbf-0.0.12", true, bounds);
      ByteString headerData = header.toByteString();
      this.write("OSMHeader", null, this.compression, headerData);
      this.headerWritten = true;
   }

   private void writeBatch() throws IOException {
      this.ensureHeader();
      Osmformat.PrimitiveBlock.Builder builder = Osmformat.PrimitiveBlock.newBuilder();
      this.addTagsToStringTable(this.bufNodes);
      this.addTagsToStringTable(this.bufWays);
      this.addTagsToStringTable(this.bufRelations);
      this.addMemberRolesToStringTable(this.bufRelations);
      if (this.writeMetadata) {
         this.addUsersToStringTable(this.bufNodes);
         this.addUsersToStringTable(this.bufWays);
         this.addUsersToStringTable(this.bufRelations);
      }

      this.stringTable.finish();
      if (this.bufNodes.size() > 0) {
         if (this.useDense) {
            Osmformat.PrimitiveGroup group = this.serializeDense(this.bufNodes);
            builder.addPrimitivegroup(group);
         } else {
            Osmformat.PrimitiveGroup group = this.serializeNonDense(this.bufNodes);
            builder.addPrimitivegroup(group);
         }

         this.bufNodes.clear();
      }

      if (this.bufWays.size() > 0) {
         Osmformat.PrimitiveGroup group = this.serializeWays(this.bufWays);
         builder.addPrimitivegroup(group);
         this.bufWays.clear();
      }

      if (this.bufRelations.size() > 0) {
         Osmformat.PrimitiveGroup group = this.serializeRelations(this.bufRelations);
         builder.addPrimitivegroup(group);
         this.bufRelations.clear();
      }

      builder.setDateGranularity(this.dateGranularity);
      builder.setGranularity(this.granularity);
      builder.setStringtable(this.stringTable.serialize());
      Osmformat.PrimitiveBlock block = builder.build();
      ByteString data = block.toByteString();
      this.counter = 0;
      this.stringTable.clear();
      this.write("OSMData", null, this.compression, data);
   }

   private void addTagsToStringTable(Collection<? extends OsmEntity> entities) {
      for (OsmEntity entity : entities) {
         for (int k = 0; k < entity.getNumberOfTags(); k++) {
            OsmTag tag = entity.getTag(k);
            this.stringTable.incr(tag.getKey());
            this.stringTable.incr(tag.getValue());
         }
      }
   }

   private void addUsersToStringTable(Collection<? extends OsmEntity> entities) {
      for (OsmEntity entity : entities) {
         OsmMetadata metadata = entity.getMetadata();
         if (metadata != null) {
            String user = metadata.getUser();
            if (user != null) {
               this.stringTable.incr(user);
            }
         }
      }
   }

   private Osmformat.Info.Builder serializeMetadata(OsmEntity entity) {
      Osmformat.Info.Builder b = Osmformat.Info.newBuilder();
      if (this.writeMetadata) {
         OsmMetadata metadata = entity.getMetadata();
         if (metadata == null) {
            return b;
         }

         if (metadata.getUid() >= 0L) {
            b.setUid((int)metadata.getUid());
            b.setUserSid(this.stringTable.getIndex(metadata.getUser()));
         }

         b.setTimestamp((long)((int)(metadata.getTimestamp() / (long)this.dateGranularity)));
         b.setVersion(metadata.getVersion());
         b.setChangeset(metadata.getChangeset());
      }

      return b;
   }

   private void serializeMetadataDense(Osmformat.DenseInfo.Builder b, Collection<? extends OsmEntity> entities) {
      long lasttimestamp = 0L;
      long lastchangeset = 0L;
      int lastuserSid = 0;
      int lastuid = 0;

      for (OsmEntity e : entities) {
         OsmMetadata metadata = e.getMetadata();
         if (metadata == null) {
            metadata = new Metadata(-1, -1L, -1L, "", -1L);
         }

         int uid = (int)metadata.getUid();
         int userSid = this.stringTable.getIndex(metadata.getUser());
         int timestamp = (int)(metadata.getTimestamp() / (long)this.dateGranularity);
         int version = metadata.getVersion();
         long changeset = metadata.getChangeset();
         b.addVersion(version);
         b.addTimestamp((long)timestamp - lasttimestamp);
         lasttimestamp = (long)timestamp;
         b.addChangeset(changeset - lastchangeset);
         lastchangeset = changeset;
         b.addUid(uid - lastuid);
         lastuid = uid;
         b.addUserSid(userSid - lastuserSid);
         lastuserSid = userSid;
      }
   }

   private int mapDegrees(double degrees) {
      return (int)(degrees / 1.0E-7 / (double)(this.granularity / 100));
   }

   private Osmformat.PrimitiveGroup serializeDense(Collection<OsmNode> nodes) {
      Osmformat.PrimitiveGroup.Builder builder = Osmformat.PrimitiveGroup.newBuilder();
      long lastlat = 0L;
      long lastlon = 0L;
      long lastid = 0L;
      Osmformat.DenseNodes.Builder bi = Osmformat.DenseNodes.newBuilder();
      boolean doesBlockHaveTags = false;

      for (OsmNode node : nodes) {
         if (node.getNumberOfTags() != 0) {
            doesBlockHaveTags = true;
            break;
         }
      }

      boolean hasMetadata = false;

      for (OsmNode nodex : nodes) {
         if (nodex.getMetadata() != null) {
            hasMetadata = true;
         }
      }

      if (this.writeMetadata && hasMetadata) {
         Osmformat.DenseInfo.Builder bdi = Osmformat.DenseInfo.newBuilder();
         this.serializeMetadataDense(bdi, nodes);
         bi.setDenseinfo(bdi);
      }

      for (OsmNode nodexx : nodes) {
         long id = nodexx.getId();
         int lat = this.mapDegrees(nodexx.getLatitude());
         int lon = this.mapDegrees(nodexx.getLongitude());
         bi.addId(id - lastid);
         lastid = id;
         bi.addLon((long)lon - lastlon);
         lastlon = (long)lon;
         bi.addLat((long)lat - lastlat);
         lastlat = (long)lat;
         if (doesBlockHaveTags) {
            for (int k = 0; k < nodexx.getNumberOfTags(); k++) {
               OsmTag t = nodexx.getTag(k);
               bi.addKeysVals(this.stringTable.getIndex(t.getKey()));
               bi.addKeysVals(this.stringTable.getIndex(t.getValue()));
            }

            bi.addKeysVals(0);
         }
      }

      builder.setDense(bi);
      return builder.build();
   }

   private Osmformat.PrimitiveGroup serializeNonDense(Collection<OsmNode> nodes) {
      Osmformat.PrimitiveGroup.Builder builder = Osmformat.PrimitiveGroup.newBuilder();

      for (OsmNode node : nodes) {
         Osmformat.Node.Builder bi = Osmformat.Node.newBuilder();
         bi.setId(node.getId());
         bi.setLon((long)this.mapDegrees(node.getLongitude()));
         bi.setLat((long)this.mapDegrees(node.getLatitude()));

         for (int k = 0; k < node.getNumberOfTags(); k++) {
            OsmTag t = node.getTag(k);
            bi.addKeys(this.stringTable.getIndex(t.getKey()));
            bi.addVals(this.stringTable.getIndex(t.getValue()));
         }

         if (this.writeMetadata && node.getMetadata() != null) {
            bi.setInfo(this.serializeMetadata(node));
         }

         builder.addNodes(bi);
      }

      return builder.build();
   }

   private Osmformat.PrimitiveGroup serializeWays(Collection<OsmWay> ways) {
      Osmformat.PrimitiveGroup.Builder builder = Osmformat.PrimitiveGroup.newBuilder();

      for (OsmWay way : ways) {
         Osmformat.Way.Builder bi = Osmformat.Way.newBuilder();
         bi.setId(way.getId());
         long lastid = 0L;

         for (int k = 0; k < way.getNumberOfNodes(); k++) {
            long id = way.getNodeId(k);
            bi.addRefs(id - lastid);
            lastid = id;
         }

         for (int k = 0; k < way.getNumberOfTags(); k++) {
            OsmTag t = way.getTag(k);
            bi.addKeys(this.stringTable.getIndex(t.getKey()));
            bi.addVals(this.stringTable.getIndex(t.getValue()));
         }

         if (this.writeMetadata && way.getMetadata() != null) {
            bi.setInfo(this.serializeMetadata(way));
         }

         builder.addWays(bi);
      }

      return builder.build();
   }

   private void addMemberRolesToStringTable(Collection<OsmRelation> relations) {
      for (OsmRelation relation : relations) {
         for (int k = 0; k < relation.getNumberOfMembers(); k++) {
            OsmRelationMember j = relation.getMember(k);
            this.stringTable.incr(j.getRole());
         }
      }
   }

   private Osmformat.PrimitiveGroup serializeRelations(Collection<OsmRelation> relations) {
      Osmformat.PrimitiveGroup.Builder builder = Osmformat.PrimitiveGroup.newBuilder();

      for (OsmRelation relation : relations) {
         Osmformat.Relation.Builder bi = Osmformat.Relation.newBuilder();
         bi.setId(relation.getId());
         long lastid = 0L;

         for (int k = 0; k < relation.getNumberOfMembers(); k++) {
            OsmRelationMember j = relation.getMember(k);
            long id = j.getId();
            bi.addMemids(id - lastid);
            lastid = id;
            EntityType t = j.getType();
            Osmformat.Relation.MemberType type = this.getType(t);
            bi.addTypes(type);
            bi.addRolesSid(this.stringTable.getIndex(j.getRole()));
         }

         for (int k = 0; k < relation.getNumberOfTags(); k++) {
            OsmTag t = relation.getTag(k);
            bi.addKeys(this.stringTable.getIndex(t.getKey()));
            bi.addVals(this.stringTable.getIndex(t.getValue()));
         }

         if (this.writeMetadata && relation.getMetadata() != null) {
            bi.setInfo(this.serializeMetadata(relation));
         }

         builder.addRelations(bi);
      }

      return builder.build();
   }

   private Osmformat.Relation.MemberType getType(EntityType t) {
      switch (t) {
         case Node:
         default:
            return Osmformat.Relation.MemberType.NODE;
         case Way:
            return Osmformat.Relation.MemberType.WAY;
         case Relation:
            return Osmformat.Relation.MemberType.RELATION;
      }
   }
}
