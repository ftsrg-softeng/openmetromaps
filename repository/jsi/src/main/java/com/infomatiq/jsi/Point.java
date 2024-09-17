package com.infomatiq.jsi;

public class Point {
   public float x;
   public float y;

   public Point(float x, float y) {
      this.x = x;
      this.y = y;
   }

   public void set(Point other) {
      this.x = other.x;
      this.y = other.y;
   }

   @Override
   public String toString() {
      return "(" + this.x + ", " + this.y + ")";
   }

   public int xInt() {
      return Math.round(this.x);
   }

   public int yInt() {
      return Math.round(this.y);
   }
}
