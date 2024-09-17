package de.topobyte.jts.utils;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineSegment;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.operation.valid.IsValidOp;
import de.topobyte.jsi.GenericRTree;
import de.topobyte.jsi.GenericSpatialIndex;
import de.topobyte.jsijts.JsiAndJts;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelfIntersectionUtil {
   static final Logger logger = LoggerFactory.getLogger(SelfIntersectionUtil.class);

   public static boolean hasSelfIntersections(LineString string) {
      List<LineSegment> segments = new ArrayList<>();
      CoordinateSequence seq = string.getCoordinateSequence();

      for (int i = 0; i < string.getNumPoints() - 1; i++) {
         Coordinate a = seq.getCoordinate(i);
         Coordinate b = seq.getCoordinate(i + 1);
         segments.add(new LineSegment(a.x, a.y, b.x, b.y));
      }

      GenericSpatialIndex<LineSegment> si = new GenericRTree(1, 10);

      for (LineSegment line : segments) {
         si.add(JsiAndJts.toRectangle(line), line);
      }

      for (int i = 0; i < segments.size() - 1; i++) {
         LineSegment a = segments.get(i);

         for (LineSegment b : si.intersects(JsiAndJts.toRectangle(a))) {
            if (!a.equals(b) && !Segments.connected(a, b)) {
               Coordinate intersection = a.intersection(b);
               if (intersection != null) {
                  logger.debug("intersection!!!");
                  logger.debug(intersection + ": " + a + " " + b);
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static Set<LinearRing> repair(LinearRing input) {
      Set<LinearRing> rings = new HashSet<>();
      List<LineSegment> ringSegs = new ArrayList<>();
      CoordinateSequence seq = input.getCoordinateSequence();

      for (int i = 0; i < input.getNumPoints() - 1; i++) {
         Coordinate a = seq.getCoordinate(i);
         Coordinate b = seq.getCoordinate(i + 1);
         ringSegs.add(new LineSegment(a.x, a.y, b.x, b.y));
      }

      List<List<LineSegment>> done = new ArrayList<>();
      List<List<LineSegment>> todo = new ArrayList<>();
      todo.add(ringSegs);
      boolean repairedSomething = false;

      while (!todo.isEmpty()) {
         List<LineSegment> segs = todo.remove(todo.size() - 1);
         RepairResult result = repairSegmentRing(segs);
         repairedSomething |= result.isIntersectionFound();
         Set<List<LineSegment>> results = result.getResults();
         if (!result.isIntersectionFound()) {
            done.addAll(results);
         } else {
            todo.addAll(results);
         }
      }

      if (!repairedSomething) {
         rings.add(input);
         return rings;
      } else {
         logger.debug("number of segment rings: " + done.size());

         for (int i = 0; i < done.size(); i++) {
            logger.debug("converting ring # " + i);
            List<LineSegment> segments = done.get(i);
            LinearRing ring = null;

            try {
               ring = ringFromSegments(segments);
               if (ring == null) {
                  logger.debug("no ring could be constructed for " + segments.size() + " segments");
                  continue;
               }
            } catch (IllegalArgumentException var12) {
               logger.debug("a broken ring has been constructed for " + segments.size() + " segments");
               continue;
            }

            try {
               if (ring.isValid()) {
                  rings.add(ring);
               } else {
                  logger.debug("invalid ring with: " + ring.getNumPoints() + " points");
                  IsValidOp isValidOp = new IsValidOp(ring);
                  logger.debug("validation error: " + isValidOp.getValidationError());
               }
            } catch (Exception var11) {
               logger.debug("catched an unchecked Exception");
            }
         }

         return rings;
      }
   }

   private static RepairResult repairSegmentRing(List<LineSegment> ringSegs) {
      GenericSpatialIndex<LineSegment> si = new GenericRTree(1, 10);

      for (LineSegment line : ringSegs) {
         si.add(JsiAndJts.toRectangle(line), line);
      }

      List<LineSegment> segments = new ArrayList<>(ringSegs.size());
      segments.addAll(ringSegs);

      for (int i = 0; i < segments.size() - 1; i++) {
         LineSegment a = segments.get(i);

         for (LineSegment b : si.intersects(JsiAndJts.toRectangle(a))) {
            if (!a.equals(b) && !Segments.connected(a, b)) {
               Coordinate intersection = a.intersection(b);
               if (intersection != null) {
                  logger.debug("found intersection...");
                  Set<List<LineSegment>> rings = ringify(ringSegs, i, a, b);
                  return new RepairResult(true, rings);
               }
            }
         }
      }

      Set<List<LineSegment>> rings = new HashSet<>();
      rings.add(ringSegs);
      return new RepairResult(false, rings);
   }

   private static LinearRing ringFromSegments(List<LineSegment> segments) {
      GeometryFactory factory = new GeometryFactory();
      int nSegs = segments.size();
      if (nSegs < 3) {
         return null;
      } else {
         int len = segments.size() + 1;
         CoordinateSequence seq = factory.getCoordinateSequenceFactory().create(len, 2);
         int i = 0;

         for (LineSegment line : segments) {
            seq.setOrdinate(i, 0, line.p0.x);
            seq.setOrdinate(i, 1, line.p0.y);
            i++;
         }

         seq.setOrdinate(i, 0, segments.get(0).p0.x);
         seq.setOrdinate(i, 1, segments.get(0).p0.y);
         return factory.createLinearRing(seq);
      }
   }

   private static Set<List<LineSegment>> ringify(List<LineSegment> segments, int k, LineSegment a, LineSegment b) {
      List<LineSegment> one = new ArrayList<>();
      List<LineSegment> two = new ArrayList<>();
      List<LineSegment> lines = new ArrayList<>(segments.size());

      for (int i = k; i < segments.size(); i++) {
         lines.add(segments.get(i));
      }

      for (int i = 0; i < k; i++) {
         lines.add(segments.get(i));
      }

      Coordinate intersection = a.intersection(b);
      LineSegment start = new LineSegment(intersection, a.p1);
      one.add(start);

      int i;
      for (i = 1; i < lines.size(); i++) {
         LineSegment line = lines.get(i);
         if (line == b) {
            break;
         }

         one.add(line);
      }

      LineSegment end = new LineSegment(b.p0, intersection);
      one.add(end);
      LineSegment start2 = new LineSegment(intersection, b.p1);
      two.add(start2);
      i++;

      while (i < lines.size()) {
         LineSegment line = lines.get(i);
         two.add(line);
         i++;
      }

      LineSegment end2 = new LineSegment(a.p0, intersection);
      two.add(end2);
      Set<List<LineSegment>> rings = new HashSet<>();
      rings.add(one);
      rings.add(two);
      return rings;
   }
}
