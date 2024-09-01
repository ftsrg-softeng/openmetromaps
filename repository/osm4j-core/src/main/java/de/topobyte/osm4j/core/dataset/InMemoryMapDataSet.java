package de.topobyte.osm4j.core.dataset;

import com.slimjars.dist.gnu.trove.map.TLongObjectMap;
import com.slimjars.dist.gnu.trove.map.hash.TLongObjectHashMap;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import de.topobyte.osm4j.core.resolve.OsmEntityProvider;

public class InMemoryMapDataSet implements OsmEntityProvider {
   private OsmBounds bounds = null;
   private TLongObjectMap<OsmNode> nodes = new TLongObjectHashMap();
   private TLongObjectMap<OsmWay> ways = new TLongObjectHashMap();
   private TLongObjectMap<OsmRelation> relations = new TLongObjectHashMap();

   public boolean hasBounds() {
      return this.bounds != null;
   }

   public OsmBounds getBounds() {
      return this.bounds;
   }

   public void setBounds(OsmBounds bounds) {
      this.bounds = bounds;
   }

   public TLongObjectMap<OsmNode> getNodes() {
      return this.nodes;
   }

   public TLongObjectMap<OsmWay> getWays() {
      return this.ways;
   }

   public TLongObjectMap<OsmRelation> getRelations() {
      return this.relations;
   }

   public void setNodes(TLongObjectMap<OsmNode> nodes) {
      this.nodes = nodes;
   }

   public void setWays(TLongObjectMap<OsmWay> ways) {
      this.ways = ways;
   }

   public void setRelations(TLongObjectMap<OsmRelation> relations) {
      this.relations = relations;
   }

   @Override
   public OsmNode getNode(long id) throws EntityNotFoundException {
      OsmNode node = (OsmNode)this.nodes.get(id);
      if (node == null) {
         throw new EntityNotFoundException("unable to find node with id: " + id);
      } else {
         return node;
      }
   }

   @Override
   public OsmWay getWay(long id) throws EntityNotFoundException {
      OsmWay way = (OsmWay)this.ways.get(id);
      if (way == null) {
         throw new EntityNotFoundException("unable to find way with id: " + id);
      } else {
         return way;
      }
   }

   @Override
   public OsmRelation getRelation(long id) throws EntityNotFoundException {
      OsmRelation relation = (OsmRelation)this.relations.get(id);
      if (relation == null) {
         throw new EntityNotFoundException("unable to find relation with id: " + id);
      } else {
         return relation;
      }
   }
}
