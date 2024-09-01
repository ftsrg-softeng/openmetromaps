package de.topobyte.osm4j.core.model.impl;

import de.topobyte.osm4j.core.model.iface.OsmBounds;

public class Bounds implements OsmBounds {
   private final double left;
   private final double right;
   private final double top;
   private final double bottom;

   public Bounds(double left, double right, double top, double bottom) {
      this.left = left;
      this.right = right;
      this.top = top;
      this.bottom = bottom;
   }

   @Override
   public double getLeft() {
      return this.left;
   }

   @Override
   public double getRight() {
      return this.right;
   }

   @Override
   public double getTop() {
      return this.top;
   }

   @Override
   public double getBottom() {
      return this.bottom;
   }

   @Override
   public String toString() {
      return String.format("%f:%f,%f:%f", this.left, this.right, this.bottom, this.top);
   }
}
