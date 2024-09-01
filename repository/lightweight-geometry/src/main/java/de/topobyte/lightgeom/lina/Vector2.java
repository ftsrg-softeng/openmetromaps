package de.topobyte.lightgeom.lina;

public class Vector2 {
   public double x;
   public double y;

   public Vector2(double x, double y) {
      this.x = x;
      this.y = y;
   }

   public Vector2(Point c) {
      this(c.x, c.y);
   }

   public Vector2(Vector2 v) {
      this(v.x, v.y);
   }

   public Vector2(double fromX, double fromY, double toX, double toY) {
      this(toX - fromX, toY - fromY);
   }

   public Vector2(Point from, Point to) {
      this(to.x - from.x, to.y - from.y);
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public Vector2 setX(double x) {
      this.x = x;
      return this;
   }

   public Vector2 setY(double y) {
      this.y = y;
      return this;
   }

   public Vector2 set(double x, double y) {
      this.x = x;
      this.y = y;
      return this;
   }

   public Vector2 set(Point c) {
      return this.set(c.x, c.y);
   }

   public Vector2 set(Vector2 v) {
      return this.set(v.x, v.y);
   }

   public Vector2 set(double fromX, double fromY, double toX, double toY) {
      return this.set(toX - fromX, toY - fromY);
   }

   public Vector2 set(Point from, Point to) {
      return this.set(to.x - from.x, to.y - from.y);
   }

   @Override
   public String toString() {
      return String.format("%f,%f", this.x, this.y);
   }

   public Vector2 copy() {
      return new Vector2(this.x, this.y);
   }

   public Vector2 add(Vector2 other) {
      this.x = this.x + other.x;
      this.y = this.y + other.y;
      return this;
   }

   public Vector2 add(double ox, double oy) {
      this.x += ox;
      this.y += oy;
      return this;
   }

   public Vector2 sub(Vector2 other) {
      this.x = this.x - other.x;
      this.y = this.y - other.y;
      return this;
   }

   public Vector2 sub(double ox, double oy) {
      this.x -= ox;
      this.y -= oy;
      return this;
   }

   public Vector2 mult(double lambda) {
      this.x *= lambda;
      this.y *= lambda;
      return this;
   }

   public Vector2 divide(double lambda) {
      this.x /= lambda;
      this.y /= lambda;
      return this;
   }

   public double dotProduct(Vector2 other) {
      return this.x * other.x + this.y * other.y;
   }

   public double norm() {
      return Math.sqrt(this.x * this.x + this.y * this.y);
   }

   public double length() {
      return Math.sqrt(this.x * this.x + this.y * this.y);
   }

   public double length2() {
      return this.x * this.x + this.y * this.y;
   }

   public Vector2 normalize() {
      double norm = this.norm();
      this.divide(norm);
      return this;
   }

   public double fastInverseNorm() {
      return (double)fastInverseSquareRoot((float)(this.x * this.x + this.y * this.y));
   }

   public static float fastInverseSquareRoot(float x) {
      float xHalf = 0.5F * x;
      int temp = Float.floatToRawIntBits(x);
      temp = 1597463007 - (temp >> 1);
      float newX = Float.intBitsToFloat(temp);
      return newX * (1.5F - xHalf * newX * newX);
   }

   public Vector2 normalizeFast() {
      double inverseNorm = this.fastInverseNorm();
      this.mult(inverseNorm);
      return this;
   }

   public Vector2 perpendicularLeft() {
      double ox = this.x;
      this.x = -this.y;
      this.y = ox;
      return this;
   }

   public Vector2 perpendicularRight() {
      double ox = this.x;
      this.x = this.y;
      this.y = -ox;
      return this;
   }
}
