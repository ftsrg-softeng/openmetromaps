package de.topobyte.osm4j.core.access;

import de.topobyte.osm4j.core.model.iface.EntityType;

public interface OsmElementCounter {
   void count() throws OsmInputException;

   long getNumberOfNodes();

   long getNumberOfWays();

   long getNumberOfRelations();

   long getTotalNumberOfElements();

   long getNumberOfElements(EntityType var1);
}
