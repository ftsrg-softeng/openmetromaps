package de.topobyte.osm4j.core.resolve;

import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.util.List;

public class UnionOsmEntityProvider implements OsmEntityProvider {
   private List<OsmEntityProvider> providers;

   public UnionOsmEntityProvider(List<OsmEntityProvider> providers) {
      this.providers = providers;
   }

   @Override
   public OsmNode getNode(long id) throws EntityNotFoundException {
      for (int i = 0; i < this.providers.size(); i++) {
         OsmEntityProvider provider = this.providers.get(i);

         try {
            return provider.getNode(id);
         } catch (EntityNotFoundException var6) {
         }
      }

      throw new EntityNotFoundException("unable to find node with id: " + id);
   }

   @Override
   public OsmWay getWay(long id) throws EntityNotFoundException {
      for (int i = 0; i < this.providers.size(); i++) {
         OsmEntityProvider provider = this.providers.get(i);

         try {
            return provider.getWay(id);
         } catch (EntityNotFoundException var6) {
         }
      }

      throw new EntityNotFoundException("unable to find way with id: " + id);
   }

   @Override
   public OsmRelation getRelation(long id) throws EntityNotFoundException {
      for (int i = 0; i < this.providers.size(); i++) {
         OsmEntityProvider provider = this.providers.get(i);

         try {
            return provider.getRelation(id);
         } catch (EntityNotFoundException var6) {
         }
      }

      throw new EntityNotFoundException("unable to find relation with id: " + id);
   }
}
