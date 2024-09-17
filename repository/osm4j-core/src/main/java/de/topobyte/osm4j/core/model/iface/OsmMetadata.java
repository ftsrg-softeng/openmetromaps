package de.topobyte.osm4j.core.model.iface;

public interface OsmMetadata {
   int getVersion();

   long getTimestamp();

   long getUid();

   String getUser();

   long getChangeset();

   boolean isVisible();
}
