package de.topobyte.jts.utils;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import java.util.Iterator;

public class GeometryCollectionIterator implements Iterable<Geometry>, Iterator<Geometry> {
   private GeometryCollection gc = null;
   private int index = 0;
   private int n = 0;

   public GeometryCollectionIterator(GeometryCollection gc) {
      this.gc = gc;
      this.n = gc.getNumGeometries();
   }

   @Override
   public Iterator<Geometry> iterator() {
      return this;
   }

   @Override
   public boolean hasNext() {
      return this.index < this.n;
   }

   public Geometry next() {
      Geometry g = this.gc.getGeometryN(this.index);
      this.index++;
      return g;
   }

   @Override
   public void remove() {
      System.out.println("not implemented");
   }
}
