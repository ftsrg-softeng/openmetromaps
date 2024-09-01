package de.topobyte.osm4j.core.resolve;

import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;

public interface OsmEntityProvider {
   OsmNode getNode(long var1) throws EntityNotFoundException;

   OsmWay getWay(long var1) throws EntityNotFoundException;

   OsmRelation getRelation(long var1) throws EntityNotFoundException;
}
