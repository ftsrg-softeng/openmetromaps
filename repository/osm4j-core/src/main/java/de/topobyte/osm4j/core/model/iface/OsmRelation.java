package de.topobyte.osm4j.core.model.iface;

public interface OsmRelation extends OsmEntity {
   int getNumberOfMembers();

   OsmRelationMember getMember(int var1);
}
