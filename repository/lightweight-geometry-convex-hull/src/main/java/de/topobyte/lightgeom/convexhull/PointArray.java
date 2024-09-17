package de.topobyte.lightgeom.convexhull;

public class PointArray extends FloatArray {
   public void add(float x, float y) {
      this.add(x);
      this.add(y);
   }

   public int numPoints() {
      return this.size / 2;
   }

   public float getX(int i) {
      return this.get(i * 2);
   }

   public float getY(int i) {
      return this.get(i * 2 + 1);
   }
}
