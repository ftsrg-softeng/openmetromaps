package de.topobyte.osm4j.core.resolve;

import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;

public class NullOsmEntityProvider implements OsmEntityProvider {
   private static String MESSAGE = "The null provider does not contain any data";

   @Override
   public OsmNode getNode(long id) throws EntityNotFoundException {
      throw new EntityNotFoundException(MESSAGE);
   }

   @Override
   public OsmWay getWay(long id) throws EntityNotFoundException {
      throw new EntityNotFoundException(MESSAGE);
   }

   @Override
   public OsmRelation getRelation(long id) throws EntityNotFoundException {
      throw new EntityNotFoundException(MESSAGE);
   }
}
