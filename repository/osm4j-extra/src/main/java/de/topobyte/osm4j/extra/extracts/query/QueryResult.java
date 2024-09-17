package de.topobyte.osm4j.extra.extracts.query;

public class QueryResult {
   private int nNodes;
   private int nWays;
   private int nSimpleRelations;
   private int nComplexRelations;

   public QueryResult(int nNodes, int nWays, int nSimpleRelations, int nComplexRelations) {
      this.nNodes = nNodes;
      this.nWays = nWays;
      this.nSimpleRelations = nSimpleRelations;
      this.nComplexRelations = nComplexRelations;
   }

   public int getNumNodes() {
      return this.nNodes;
   }

   public int getNumWays() {
      return this.nWays;
   }

   public int getNumSimpleRelations() {
      return this.nSimpleRelations;
   }

   public int getNumComplexRelations() {
      return this.nComplexRelations;
   }
}
