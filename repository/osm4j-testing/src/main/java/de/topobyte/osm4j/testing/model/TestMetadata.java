package de.topobyte.osm4j.testing.model;

import de.topobyte.osm4j.core.model.iface.OsmMetadata;

public class TestMetadata implements OsmMetadata {
   private int version;
   private long timestamp;
   private long uid;
   private String user;
   private long changeset;
   private boolean visible = true;

   public TestMetadata(int version, long timestamp, long uid, String user, long changeset) {
      this.version = version;
      this.timestamp = timestamp;
      this.uid = uid;
      this.user = user;
      this.changeset = changeset;
   }

   public TestMetadata(int version, long timestamp, long uid, String user, long changeset, boolean visible) {
      this(version, timestamp, uid, user, changeset);
      this.visible = visible;
   }

   public int getVersion() {
      return this.version;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public long getUid() {
      return this.uid;
   }

   public String getUser() {
      return this.user;
   }

   public long getChangeset() {
      return this.changeset;
   }

   public boolean isVisible() {
      return this.visible;
   }
}
