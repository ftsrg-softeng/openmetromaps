package de.topobyte.osm4j.testing;

import de.topobyte.osm4j.testing.model.TestNode;
import de.topobyte.osm4j.testing.model.TestRelation;
import de.topobyte.osm4j.testing.model.TestWay;

public class DataSetGenerator {
   private EntityGenerator entityGenerator;

   public DataSetGenerator(EntityGenerator entityGenerator) {
      this.entityGenerator = entityGenerator;
   }

   public TestDataSet generate(int numNodes, int numWays, int numRelations) {
      TestDataSet dataSet = new TestDataSet();

      for (int i = 0; i < numNodes; i++) {
         TestNode node = this.entityGenerator.generateNode();
         dataSet.getNodes().add(node);
      }

      for (int i = 0; i < numWays; i++) {
         TestWay way = this.entityGenerator.generateWay();
         dataSet.getWays().add(way);
      }

      for (int i = 0; i < numRelations; i++) {
         TestRelation relation = this.entityGenerator.generateRelation();
         dataSet.getRelations().add(relation);
      }

      return dataSet;
   }
}
