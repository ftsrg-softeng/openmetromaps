package de.topobyte.jts.utils;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import de.topobyte.adt.graph.Graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolygonHelper {
   static final Logger logger = LoggerFactory.getLogger(PolygonHelper.class);

   public static Polygon polygonFromLinearRing(LinearRing shell, GeometryFactory factory) {
      return new Polygon(shell, null, factory);
   }

   public static Geometry hull(Geometry geometry) {
      GeometryFactory factory = geometry.getFactory();
      if (geometry instanceof Polygon) {
         Polygon polygon = (Polygon)geometry;
         LinearRing ring = (LinearRing)polygon.getExteriorRing();
         return factory.createPolygon(ring, null);
      } else if (geometry instanceof MultiPolygon) {
         MultiPolygon mp = (MultiPolygon)geometry;
         Geometry all = null;

         for (int i = 0; i < mp.getNumGeometries(); i++) {
            Geometry child = mp.getGeometryN(i);
            Geometry childHull = hull(child);
            if (all == null) {
               all = childHull;
            } else {
               all = all.union(childHull);
            }
         }

         return all;
      } else {
         return null;
      }
   }

   public static MultiPolygon multipolygonFromRings(Set<LinearRing> rings, boolean checkValid) {
      if (checkValid) {
         Iterator<LinearRing> iter = rings.iterator();

         while (iter.hasNext()) {
            LinearRing ring = iter.next();
            if (!ring.isValid()) {
               iter.remove();
               logger.debug("remove......");
            }
         }
      }

      GeometryFactory factory = new GeometryFactory();
      List<Polygon> polygons = new ArrayList<>();
      Graph<LinearRing> graph = new Graph();
      graph.addNodes(rings);
      Map<LinearRing, Polygon> ringToPolygon = new HashMap<>();

      for (LinearRing r : rings) {
         Polygon p = polygonFromLinearRing(r, factory);
         ringToPolygon.put(r, p);
      }

      for (LinearRing r : rings) {
         Set<LinearRing> candidates = new HashSet<>();
         candidates.addAll(rings);
         candidates.remove(r);
         Polygon p1 = ringToPolygon.get(r);

         for (LinearRing c : candidates) {
            Polygon p2 = ringToPolygon.get(c);
            if (p1.contains(p2)) {
               graph.addEdge(r, c);
            }
         }
      }

      for (LinearRing r : rings) {
         int d = graph.degreeIn(r);
         if (d % 2 == 0) {
            Set<LinearRing> possiblyInner = graph.getEdgesOut(r);
            Set<LinearRing> inner = new HashSet<>();

            for (LinearRing q : possiblyInner) {
               if (graph.degreeIn(q) == d + 1) {
                  inner.add(q);
               }
            }

            LinearRing[] holes = inner.toArray(new LinearRing[0]);
            polygons.add(new Polygon(r, holes, factory));
         }
      }

      Polygon[] ps = polygons.toArray(new Polygon[0]);
      return new MultiPolygon(ps, factory);
   }
}
