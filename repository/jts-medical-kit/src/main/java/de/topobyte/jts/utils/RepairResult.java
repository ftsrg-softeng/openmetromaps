package de.topobyte.jts.utils;

import com.vividsolutions.jts.geom.LineSegment;
import java.util.List;
import java.util.Set;

class RepairResult {
   private boolean intersectionFound;
   private Set<List<LineSegment>> results;

   public RepairResult(boolean intersectionFound, Set<List<LineSegment>> results) {
      this.intersectionFound = intersectionFound;
      this.results = results;
   }

   public boolean isIntersectionFound() {
      return this.intersectionFound;
   }

   public Set<List<LineSegment>> getResults() {
      return this.results;
   }
}
