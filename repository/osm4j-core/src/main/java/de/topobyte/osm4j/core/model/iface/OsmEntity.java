package de.topobyte.osm4j.core.model.iface;

public interface OsmEntity {
   long getId();

   int getNumberOfTags();

   OsmTag getTag(int var1);

   OsmMetadata getMetadata();
}
