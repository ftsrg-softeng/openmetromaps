package de.topobyte.osm4j.core.resolve;

import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;

public class CompositeOsmEntityProvider implements OsmEntityProvider {
   private OsmEntityProvider nodeProvider;
   private OsmEntityProvider wayProvider;
   private OsmEntityProvider relationProvider;

   public CompositeOsmEntityProvider(OsmEntityProvider nodeProvider, OsmEntityProvider wayProvider, OsmEntityProvider relationProvider) {
      this.nodeProvider = nodeProvider;
      this.wayProvider = wayProvider;
      this.relationProvider = relationProvider;
   }

   @Override
   public OsmNode getNode(long id) throws EntityNotFoundException {
      if (this.nodeProvider == null) {
         throw new EntityNotFoundException("No node-provider supplied");
      } else {
         return this.nodeProvider.getNode(id);
      }
   }

   @Override
   public OsmWay getWay(long id) throws EntityNotFoundException {
      if (this.wayProvider == null) {
         throw new EntityNotFoundException("No way-provider supplied");
      } else {
         return this.wayProvider.getWay(id);
      }
   }

   @Override
   public OsmRelation getRelation(long id) throws EntityNotFoundException {
      if (this.relationProvider == null) {
         throw new EntityNotFoundException("No relation-provider supplied");
      } else {
         return this.relationProvider.getRelation(id);
      }
   }
}
