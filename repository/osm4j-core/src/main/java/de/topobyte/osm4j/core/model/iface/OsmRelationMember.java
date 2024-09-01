package de.topobyte.osm4j.core.model.iface;

public interface OsmRelationMember {
   long getId();

   EntityType getType();

   String getRole();
}
