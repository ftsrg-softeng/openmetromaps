package de.topobyte.jts.utils.predicate;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;

public abstract class AbstractPredicateEvaluator implements PredicateEvaluator {
   @Override
   public boolean covers(Geometry geometry) {
      return geometry instanceof GeometryCollection ? this.coversCollection(geometry) : this.coversNonCollection(geometry);
   }

   @Override
   public boolean contains(Geometry geometry) {
      return geometry instanceof GeometryCollection ? this.containsCollection(geometry) : this.containsNonCollection(geometry);
   }

   @Override
   public boolean intersects(Geometry geometry) {
      return geometry instanceof GeometryCollection ? this.intersectsCollection(geometry) : this.intersectsNonCollection(geometry);
   }

   public abstract boolean coversNonCollection(Geometry var1);

   public boolean coversCollection(Geometry b) {
      for (int i = 0; i < b.getNumGeometries(); i++) {
         Geometry g = b.getGeometryN(i);
         if (!this.covers(g)) {
            return false;
         }
      }

      return true;
   }

   public abstract boolean containsNonCollection(Geometry var1);

   public boolean containsCollection(Geometry b) {
      for (int i = 0; i < b.getNumGeometries(); i++) {
         Geometry g = b.getGeometryN(i);
         if (!this.contains(g)) {
            return false;
         }
      }

      return true;
   }

   public abstract boolean intersectsNonCollection(Geometry var1);

   public boolean intersectsCollection(Geometry b) {
      for (int i = 0; i < b.getNumGeometries(); i++) {
         Geometry g = b.getGeometryN(i);
         if (this.intersects(g)) {
            return true;
         }
      }

      return false;
   }
}
