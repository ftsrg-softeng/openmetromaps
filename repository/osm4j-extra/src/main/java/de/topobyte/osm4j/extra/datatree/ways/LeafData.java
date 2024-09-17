package de.topobyte.osm4j.extra.datatree.ways;

import de.topobyte.osm4j.core.dataset.InMemoryListDataSet;
import de.topobyte.osm4j.core.resolve.OsmEntityProvider;
import de.topobyte.osm4j.core.resolve.UnionOsmEntityProvider;
import de.topobyte.osm4j.extra.datatree.Node;
import java.util.ArrayList;
import java.util.List;

public class LeafData {
   private Node leaf;
   private InMemoryListDataSet dataWays;
   private InMemoryListDataSet dataNodes1;
   private InMemoryListDataSet dataNodes2;

   public LeafData(Node leaf, InMemoryListDataSet dataWays, InMemoryListDataSet dataNodes1, InMemoryListDataSet dataNodes2) {
      this.leaf = leaf;
      this.dataWays = dataWays;
      this.dataNodes1 = dataNodes1;
      this.dataNodes2 = dataNodes2;
   }

   public Node getLeaf() {
      return this.leaf;
   }

   public InMemoryListDataSet getDataWays() {
      return this.dataWays;
   }

   public InMemoryListDataSet getDataNodes1() {
      return this.dataNodes1;
   }

   public InMemoryListDataSet getDataNodes2() {
      return this.dataNodes2;
   }

   public OsmEntityProvider getNodeProvider() {
      List<OsmEntityProvider> providers = new ArrayList<>();
      providers.add(this.dataNodes1);
      providers.add(this.dataNodes2);
      return new UnionOsmEntityProvider(providers);
   }
}
