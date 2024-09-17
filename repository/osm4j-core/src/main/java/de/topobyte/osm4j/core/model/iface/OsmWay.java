package de.topobyte.osm4j.core.model.iface;

public interface OsmWay extends OsmEntity {
   int getNumberOfNodes();

   long getNodeId(int var1);
}
