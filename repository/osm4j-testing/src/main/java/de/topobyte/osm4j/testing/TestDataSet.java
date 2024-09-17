package de.topobyte.osm4j.testing;

import de.topobyte.osm4j.core.dataset.InMemoryListDataSet;
import de.topobyte.osm4j.core.dataset.InMemoryMapDataSet;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.testing.model.TestBounds;
import de.topobyte.osm4j.testing.model.TestNode;
import de.topobyte.osm4j.testing.model.TestRelation;
import de.topobyte.osm4j.testing.model.TestWay;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDataSet {
   private TestBounds bounds = null;
   private List<TestNode> nodes = new ArrayList<>();
   private List<TestWay> ways = new ArrayList<>();
   private List<TestRelation> relations = new ArrayList<>();

   public TestDataSet() {
   }

   public TestDataSet(TestDataSet data) {
      for (OsmNode node : data.getNodes()) {
         this.nodes.add(EntityHelper.clone(node));
      }

      for (OsmWay way : data.getWays()) {
         this.ways.add(EntityHelper.clone(way));
      }

      for (OsmRelation relation : data.getRelations()) {
         this.relations.add(EntityHelper.clone(relation));
      }
   }

   public TestDataSet(InMemoryListDataSet data) {
      for (OsmNode node : data.getNodes()) {
         this.nodes.add(EntityHelper.clone(node));
      }

      for (OsmWay way : data.getWays()) {
         this.ways.add(EntityHelper.clone(way));
      }

      for (OsmRelation relation : data.getRelations()) {
         this.relations.add(EntityHelper.clone(relation));
      }
   }

   public TestDataSet(InMemoryMapDataSet data) {
      long[] nodeIds = data.getNodes().keys();
      Arrays.sort(nodeIds);

      for (int i = 0; i < nodeIds.length; i++) {
         this.nodes.add(EntityHelper.clone((OsmNode)data.getNodes().get(nodeIds[i])));
      }

      long[] wayIds = data.getWays().keys();
      Arrays.sort(wayIds);

      for (int i = 0; i < wayIds.length; i++) {
         this.ways.add(EntityHelper.clone((OsmWay)data.getWays().get(wayIds[i])));
      }

      long[] relationIds = data.getRelations().keys();
      Arrays.sort(relationIds);

      for (int i = 0; i < relationIds.length; i++) {
         this.relations.add(EntityHelper.clone((OsmRelation)data.getRelations().get(relationIds[i])));
      }
   }

   public boolean hasBounds() {
      return this.bounds != null;
   }

   public TestBounds getBounds() {
      return this.bounds;
   }

   public void setBounds(TestBounds bounds) {
      this.bounds = bounds;
   }

   public List<TestNode> getNodes() {
      return this.nodes;
   }

   public void setNodes(List<TestNode> nodes) {
      this.nodes = nodes;
   }

   public List<TestWay> getWays() {
      return this.ways;
   }

   public void setWays(List<TestWay> ways) {
      this.ways = ways;
   }

   public List<TestRelation> getRelations() {
      return this.relations;
   }

   public void setRelations(List<TestRelation> relations) {
      this.relations = relations;
   }
}
