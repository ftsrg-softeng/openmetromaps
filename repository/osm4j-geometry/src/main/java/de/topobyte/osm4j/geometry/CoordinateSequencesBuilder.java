package de.topobyte.osm4j.geometry;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.GeometryFactory;
import java.util.ArrayList;
import java.util.List;

class CoordinateSequencesBuilder {
   private List<List<Coordinate>> results = new ArrayList<>();
   private List<Coordinate> current = null;

   public void beginNewSequence() {
      this.finish(true);
   }

   public void finishSequence() {
      this.finish(false);
   }

   private void finish(boolean initNewList) {
      if (this.current != null && !this.current.isEmpty()) {
         this.results.add(this.current);
      }

      if (initNewList) {
         this.current = new ArrayList<>();
      } else {
         this.current = null;
      }
   }

   public void add(Coordinate c) {
      this.current.add(c);
   }

   public WayBuilderResult createWayBuilderResult(GeometryFactory factory, boolean includePuntal, boolean closed, boolean firstMissing) {
      WayBuilderResult result = new WayBuilderResult();
      int numPoints = 0;
      int numLines = 0;

      for (int i = 0; i < this.results.size(); i++) {
         List<Coordinate> coords = this.results.get(i);
         if (coords.size() == 1) {
            numPoints++;
         } else {
            numLines++;
         }
      }

      if (closed && !firstMissing && numPoints == 0 && numLines == 1) {
         List<Coordinate> coords = this.results.get(0);
         if (coords.size() > 3) {
            result.setLinearRing(factory.createLinearRing(coords.toArray(new Coordinate[0])));
            return result;
         }
      }

      int first = 0;
      int last = this.results.size() - 1;
      if (closed && this.results.size() > 1 && !firstMissing) {
         List<Coordinate> coords = new ArrayList<>();
         List<Coordinate> c1 = this.results.get(first);
         List<Coordinate> c2 = this.results.get(last);
         coords.addAll(c2);
         coords.addAll(c1.subList(1, c1.size()));
         this.addToResult(factory, result, coords);
         first++;
         last--;
         if (c1.size() == 1) {
            numPoints--;
         } else {
            numLines--;
         }

         if (c2.size() == 1) {
            numPoints--;
         } else {
            numLines--;
         }
      }

      for (int ix = first; ix <= last; ix++) {
         List<Coordinate> coordsx = this.results.get(ix);
         this.addToResult(factory, result, coordsx);
      }

      return result;
   }

   private void addToResult(GeometryFactory factory, WayBuilderResult result, List<Coordinate> coords) {
      if (coords.size() == 1) {
         result.getCoordinates().add(coords.get(0));
      } else {
         CoordinateSequence cs = factory.getCoordinateSequenceFactory().create(coords.toArray(new Coordinate[0]));
         result.getLineStrings().add(factory.createLineString(cs));
      }
   }
}
