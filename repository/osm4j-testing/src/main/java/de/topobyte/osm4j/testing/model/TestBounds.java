package de.topobyte.osm4j.testing.model;

import de.topobyte.osm4j.core.model.iface.OsmBounds;

public class TestBounds implements OsmBounds {
   private final double left;
   private final double right;
   private final double top;
   private final double bottom;

   public TestBounds(double left, double right, double top, double bottom) {
      this.left = left;
      this.right = right;
      this.top = top;
      this.bottom = bottom;
   }

   public double getLeft() {
      return this.left;
   }

   public double getRight() {
      return this.right;
   }

   public double getTop() {
      return this.top;
   }

   public double getBottom() {
      return this.bottom;
   }

   @Override
   public String toString() {
      return String.format("%f:%f,%f:%f", this.left, this.right, this.bottom, this.top);
   }
}
