package de.topobyte.osm4j.core.model.impl;

import de.topobyte.osm4j.core.model.iface.OsmMetadata;

public class Metadata implements OsmMetadata {
   private int version;
   private long timestamp;
   private long uid;
   private String user;
   private long changeset;
   private boolean visible = true;

   public Metadata(int version, long timestamp, long uid, String user, long changeset) {
      this.version = version;
      this.timestamp = timestamp;
      this.uid = uid;
      this.user = user;
      this.changeset = changeset;
   }

   public Metadata(int version, long timestamp, long uid, String user, long changeset, boolean visible) {
      this(version, timestamp, uid, user, changeset);
      this.visible = visible;
   }

   @Override
   public int getVersion() {
      return this.version;
   }

   @Override
   public long getTimestamp() {
      return this.timestamp;
   }

   @Override
   public long getUid() {
      return this.uid;
   }

   @Override
   public String getUser() {
      return this.user;
   }

   @Override
   public long getChangeset() {
      return this.changeset;
   }

   public void setVisible(boolean visible) {
      this.visible = visible;
   }

   @Override
   public boolean isVisible() {
      return this.visible;
   }
}
