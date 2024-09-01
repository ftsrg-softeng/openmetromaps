package de.topobyte.osm4j.core.dataset;

import de.topobyte.osm4j.core.dataset.sort.IdComparator;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import de.topobyte.osm4j.core.resolve.OsmEntityProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InMemoryListDataSet implements OsmEntityProvider {
   private OsmBounds bounds = null;
   private List<OsmNode> nodes = new ArrayList<>();
   private List<OsmWay> ways = new ArrayList<>();
   private List<OsmRelation> relations = new ArrayList<>();

   public boolean hasBounds() {
      return this.bounds != null;
   }

   public OsmBounds getBounds() {
      return this.bounds;
   }

   public void setBounds(OsmBounds bounds) {
      this.bounds = bounds;
   }

   public List<OsmNode> getNodes() {
      return this.nodes;
   }

   public void setNodes(List<OsmNode> nodes) {
      this.nodes = nodes;
   }

   public List<OsmWay> getWays() {
      return this.ways;
   }

   public void setWays(List<OsmWay> ways) {
      this.ways = ways;
   }

   public List<OsmRelation> getRelations() {
      return this.relations;
   }

   public void setRelations(List<OsmRelation> relations) {
      this.relations = relations;
   }

   public void sort() {
      this.sort(new IdComparator());
   }

   public void sort(Comparator<? super OsmEntity> comparator) {
      Collections.sort(this.nodes, comparator);
      Collections.sort(this.ways, comparator);
      Collections.sort(this.relations, comparator);
   }

   public void sort(Comparator<? super OsmNode> nodeComparator, Comparator<? super OsmWay> wayComparator, Comparator<? super OsmRelation> relationComparator) {
      Collections.sort(this.nodes, nodeComparator);
      Collections.sort(this.ways, wayComparator);
      Collections.sort(this.relations, relationComparator);
   }

   @Override
   public OsmNode getNode(long id) throws EntityNotFoundException {
      return this.find(this.nodes, id);
   }

   @Override
   public OsmWay getWay(long id) throws EntityNotFoundException {
      return this.find(this.ways, id);
   }

   @Override
   public OsmRelation getRelation(long id) throws EntityNotFoundException {
      return this.find(this.relations, id);
   }

   private <T extends OsmEntity> T find(List<T> list, long nodeId) throws EntityNotFoundException {
      int low = 0;
      int high = list.size() - 1;

      while (low <= high) {
         int mid = low + high >>> 1;
         T v = (T)list.get(mid);
         int cmp = Long.compare(v.getId(), nodeId);
         if (cmp < 0) {
            low = mid + 1;
         } else {
            if (cmp <= 0) {
               return v;
            }

            high = mid - 1;
         }
      }

      throw new EntityNotFoundException("element not available in data set");
   }
}
