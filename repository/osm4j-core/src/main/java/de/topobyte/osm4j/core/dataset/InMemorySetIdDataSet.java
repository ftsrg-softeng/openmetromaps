package de.topobyte.osm4j.core.dataset;

import com.slimjars.dist.gnu.trove.set.TLongSet;
import com.slimjars.dist.gnu.trove.set.hash.TLongHashSet;
import de.topobyte.osm4j.core.model.iface.OsmBounds;

public class InMemorySetIdDataSet {
   private OsmBounds bounds = null;
   private TLongSet nodeIds = new TLongHashSet();
   private TLongSet wayIds = new TLongHashSet();
   private TLongSet relationIds = new TLongHashSet();

   public boolean hasBounds() {
      return this.bounds != null;
   }

   public OsmBounds getBounds() {
      return this.bounds;
   }

   public void setBounds(OsmBounds bounds) {
      this.bounds = bounds;
   }

   public TLongSet getNodeIds() {
      return this.nodeIds;
   }

   public TLongSet getWayIds() {
      return this.wayIds;
   }

   public TLongSet getRelationIds() {
      return this.relationIds;
   }

   public void setNodeIds(TLongSet nodeIds) {
      this.nodeIds = nodeIds;
   }

   public void setWayIds(TLongSet wayIds) {
      this.wayIds = wayIds;
   }

   public void setRelationIds(TLongSet relationIds) {
      this.relationIds = relationIds;
   }
}
