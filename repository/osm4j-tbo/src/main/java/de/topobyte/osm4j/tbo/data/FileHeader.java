package de.topobyte.osm4j.tbo.data;

import de.topobyte.compactio.CompactWriter;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.tbo.writerhelper.Blockable;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class FileHeader implements Blockable {
   public static final byte[] MAGIC = "tbo!".getBytes();
   public static final int FLAG_HAS_METADATA = 1;
   public static final int FLAG_HAS_BOUNDS = 2;
   private int version;
   private Map<String, String> tags = new TreeMap<>();
   private boolean hasMetadata;
   private OsmBounds bounds;

   public FileHeader(int version, Map<String, String> tags, boolean hasMetadata, OsmBounds bounds) {
      this.version = version;
      this.tags = tags;
      this.hasMetadata = hasMetadata;
      this.bounds = bounds;
   }

   public int getVersion() {
      return this.version;
   }

   public Map<String, String> getTags() {
      return this.tags;
   }

   public boolean hasTag(String key) {
      return this.tags.containsKey(key);
   }

   public String get(String key) {
      return this.tags.get(key);
   }

   public boolean hasMetadata() {
      return this.hasMetadata;
   }

   public boolean hasBounds() {
      return this.bounds != null;
   }

   public OsmBounds getBounds() {
      return this.bounds;
   }

   public void setBounds(OsmBounds bounds) {
      this.bounds = bounds;
   }

   @Override
   public void write(CompactWriter writer) throws IOException {
      writer.write(MAGIC);
      writer.writeVariableLengthUnsignedInteger((long)this.version);
      writer.writeVariableLengthUnsignedInteger((long)this.tags.size());

      for (Entry<String, String> entry : this.tags.entrySet()) {
         writer.writeString(entry.getKey());
         writer.writeString(entry.getValue());
      }

      int flags = 0;
      if (this.hasMetadata) {
         flags |= 1;
      }

      if (this.hasBounds()) {
         flags |= 2;
      }

      writer.writeByte(flags);
      if (this.hasBounds()) {
         writer.writeLong(Double.doubleToLongBits(this.bounds.getLeft()));
         writer.writeLong(Double.doubleToLongBits(this.bounds.getRight()));
         writer.writeLong(Double.doubleToLongBits(this.bounds.getBottom()));
         writer.writeLong(Double.doubleToLongBits(this.bounds.getTop()));
      }
   }
}
