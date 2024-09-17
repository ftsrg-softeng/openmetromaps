package de.topobyte.osm4j.tbo.writerhelper;

import de.topobyte.compactio.CompactWriter;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmMetadata;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import de.topobyte.osm4j.tbo.ByteArrayOutputStream;
import de.topobyte.osm4j.tbo.data.StringPool;
import de.topobyte.osm4j.tbo.data.StringPoolBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityBatch<T extends OsmEntity> implements Blockable {
   private boolean writeMetadata;
   protected List<T> elements;
   private long idOffset = 0L;
   protected StringPool stringPoolTags;
   protected StringPool stringPoolUsernames;

   public EntityBatch(boolean writeMetadata) {
      this.writeMetadata = writeMetadata;
      this.elements = new ArrayList<>();
   }

   public void put(T node) {
      this.elements.add(node);
   }

   public void clear() {
      this.idOffset = 0L;
      this.elements.clear();
   }

   public int size() {
      return this.elements.size();
   }

   public void writeAndReset(CompactWriter writer, ByteArrayOutputStream baos) throws IOException {
      byte[] bytes = baos.toByteArray();
      writer.writeVariableLengthUnsignedInteger((long)bytes.length);
      writer.write(bytes);
      baos.reset();
   }

   public void writeIds(CompactWriter writer) throws IOException {
      for (OsmEntity entity : this.elements) {
         long id = entity.getId();
         writer.writeVariableLengthSignedInteger(id - this.idOffset);
         this.idOffset = id;
      }
   }

   public void writeTagStringPool(CompactWriter writer) throws IOException {
      StringPoolBuilder poolBuilder = new StringPoolBuilder();

      for (OsmEntity object : this.elements) {
         int nTags = object.getNumberOfTags();

         for (int i = 0; i < nTags; i++) {
            OsmTag tag = object.getTag(i);
            String key = tag.getKey();
            String value = tag.getValue();
            poolBuilder.add(key);
            poolBuilder.add(value);
         }
      }

      this.stringPoolTags = poolBuilder.buildStringPool();
      this.writePool(writer, this.stringPoolTags);
   }

   public void writeUsernameStringPool(CompactWriter writer) throws IOException {
      StringPoolBuilder poolBuilder = new StringPoolBuilder();

      for (OsmEntity object : this.elements) {
         OsmMetadata metadata = object.getMetadata();
         if (metadata != null) {
            poolBuilder.add(metadata.getUser());
         }
      }

      this.stringPoolUsernames = poolBuilder.buildStringPool();
      this.writePool(writer, this.stringPoolUsernames);
   }

   protected void writePool(CompactWriter writer, StringPool stringPool) throws IOException {
      int size = stringPool.size();
      writer.writeVariableLengthUnsignedInteger((long)size);

      for (int i = 0; i < size; i++) {
         String string = stringPool.getString(i);
         writer.writeString(string);
      }
   }

   protected void writeTags(CompactWriter writer) throws IOException {
      for (OsmEntity entity : this.elements) {
         int nTags = entity.getNumberOfTags();
         writer.writeVariableLengthUnsignedInteger((long)nTags);

         for (int i = 0; i < nTags; i++) {
            OsmTag tag = entity.getTag(i);
            String key = tag.getKey();
            String value = tag.getValue();
            int k = this.stringPoolTags.getId(key);
            int v = this.stringPoolTags.getId(value);
            writer.writeVariableLengthUnsignedInteger((long)k);
            writer.writeVariableLengthUnsignedInteger((long)v);
         }
      }
   }

   protected void writeMetadata(CompactWriter writer) throws IOException {
      if (this.writeMetadata) {
         boolean none = true;
         boolean all = true;

         for (OsmEntity element : this.elements) {
            if (element.getMetadata() == null) {
               all = false;
            } else {
               none = false;
            }

            if (!all && !none) {
               break;
            }
         }

         int situation;
         if (all) {
            situation = 2;
         } else if (none) {
            situation = 1;
         } else {
            situation = 3;
         }

         writer.writeByte(situation);
         if (!none) {
            this.writeUsernameStringPool(writer);
            if (!all) {
               this.writeFlags(writer);
            }

            this.writeVersions(writer);
            this.writeTimestamps(writer);
            this.writeChangesets(writer);
            this.writerUserIds(writer);
            this.writeUsernames(writer);
         }
      }
   }

   private void writeFlags(CompactWriter writer) throws IOException {
      for (OsmEntity element : this.elements) {
         if (element.getMetadata() == null) {
            writer.writeByte(0);
         } else {
            writer.writeByte(1);
         }
      }
   }

   private void writeVersions(CompactWriter writer) throws IOException {
      int offset = 0;

      for (OsmEntity element : this.elements) {
         OsmMetadata metadata = element.getMetadata();
         if (metadata != null) {
            int version = metadata.getVersion();
            writer.writeVariableLengthSignedInteger((long)(version - offset));
            offset = version;
         }
      }
   }

   private void writeTimestamps(CompactWriter writer) throws IOException {
      long offset = 0L;

      for (OsmEntity element : this.elements) {
         OsmMetadata metadata = element.getMetadata();
         if (metadata != null) {
            long timestamp = metadata.getTimestamp();
            writer.writeVariableLengthSignedInteger(timestamp - offset);
            offset = timestamp;
         }
      }
   }

   private void writeChangesets(CompactWriter writer) throws IOException {
      long offset = 0L;

      for (OsmEntity element : this.elements) {
         OsmMetadata metadata = element.getMetadata();
         if (metadata != null) {
            long changeset = metadata.getChangeset();
            writer.writeVariableLengthSignedInteger(changeset - offset);
            offset = changeset;
         }
      }
   }

   private void writerUserIds(CompactWriter writer) throws IOException {
      long offset = 0L;

      for (OsmEntity element : this.elements) {
         OsmMetadata metadata = element.getMetadata();
         if (metadata != null) {
            long userId = metadata.getUid();
            writer.writeVariableLengthSignedInteger(userId - offset);
            offset = userId;
         }
      }
   }

   private void writeUsernames(CompactWriter writer) throws IOException {
      for (OsmEntity element : this.elements) {
         OsmMetadata metadata = element.getMetadata();
         if (metadata != null) {
            String user = metadata.getUser();
            int index = this.stringPoolUsernames.getId(user);
            writer.writeVariableLengthUnsignedInteger((long)index);
         }
      }
   }
}
