package com.infomatiq.jsi;

public class Rectangle {
   public float minX;
   public float minY;
   public float maxX;
   public float maxY;

   public Rectangle() {
      this.minX = Float.MAX_VALUE;
      this.minY = Float.MAX_VALUE;
      this.maxX = -Float.MAX_VALUE;
      this.maxY = -Float.MAX_VALUE;
   }

   public Rectangle(float x1, float y1, float x2, float y2) {
      this.set(x1, y1, x2, y2);
   }

   public void set(float x1, float y1, float x2, float y2) {
      this.minX = Math.min(x1, x2);
      this.maxX = Math.max(x1, x2);
      this.minY = Math.min(y1, y2);
      this.maxY = Math.max(y1, y2);
   }

   public void set(Rectangle r) {
      this.minX = r.minX;
      this.minY = r.minY;
      this.maxX = r.maxX;
      this.maxY = r.maxY;
   }

   public Rectangle copy() {
      return new Rectangle(this.minX, this.minY, this.maxX, this.maxY);
   }

   public boolean edgeOverlaps(Rectangle r) {
      return this.minX == r.minX || this.maxX == r.maxX || this.minY == r.minY || this.maxY == r.maxY;
   }

   public boolean intersects(Rectangle r) {
      return this.maxX >= r.minX && this.minX <= r.maxX && this.maxY >= r.minY && this.minY <= r.maxY;
   }

   public static boolean intersects(float r1MinX, float r1MinY, float r1MaxX, float r1MaxY, float r2MinX, float r2MinY, float r2MaxX, float r2MaxY) {
      return r1MaxX >= r2MinX && r1MinX <= r2MaxX && r1MaxY >= r2MinY && r1MinY <= r2MaxY;
   }

   public boolean contains(Rectangle r) {
      return this.maxX >= r.maxX && this.minX <= r.minX && this.maxY >= r.maxY && this.minY <= r.minY;
   }

   public static boolean contains(float r1MinX, float r1MinY, float r1MaxX, float r1MaxY, float r2MinX, float r2MinY, float r2MaxX, float r2MaxY) {
      return r1MaxX >= r2MaxX && r1MinX <= r2MinX && r1MaxY >= r2MaxY && r1MinY <= r2MinY;
   }

   public boolean containedBy(Rectangle r) {
      return r.maxX >= this.maxX && r.minX <= this.minX && r.maxY >= this.maxY && r.minY <= this.minY;
   }

   public float distance(Point p) {
      float distanceSquared = 0.0F;
      float temp = this.minX - p.x;
      if (temp < 0.0F) {
         temp = p.x - this.maxX;
      }

      if (temp > 0.0F) {
         distanceSquared += temp * temp;
      }

      temp = this.minY - p.y;
      if (temp < 0.0F) {
         temp = p.y - this.maxY;
      }

      if (temp > 0.0F) {
         distanceSquared += temp * temp;
      }

      return (float)Math.sqrt((double)distanceSquared);
   }

   public static float distance(float minX, float minY, float maxX, float maxY, float pX, float pY) {
      return (float)Math.sqrt((double)distanceSq(minX, minY, maxX, maxY, pX, pY));
   }

   public static float distanceSq(float minX, float minY, float maxX, float maxY, float pX, float pY) {
      float distanceSqX = 0.0F;
      float distanceSqY = 0.0F;
      if (minX > pX) {
         distanceSqX = minX - pX;
         distanceSqX *= distanceSqX;
      } else if (pX > maxX) {
         distanceSqX = pX - maxX;
         distanceSqX *= distanceSqX;
      }

      if (minY > pY) {
         distanceSqY = minY - pY;
         distanceSqY *= distanceSqY;
      } else if (pY > maxY) {
         distanceSqY = pY - maxY;
         distanceSqY *= distanceSqY;
      }

      return distanceSqX + distanceSqY;
   }

   public float distance(Rectangle r) {
      float distanceSquared = 0.0F;
      float greatestMin = Math.max(this.minX, r.minX);
      float leastMax = Math.min(this.maxX, r.maxX);
      if (greatestMin > leastMax) {
         distanceSquared += (greatestMin - leastMax) * (greatestMin - leastMax);
      }

      greatestMin = Math.max(this.minY, r.minY);
      leastMax = Math.min(this.maxY, r.maxY);
      if (greatestMin > leastMax) {
         distanceSquared += (greatestMin - leastMax) * (greatestMin - leastMax);
      }

      return (float)Math.sqrt((double)distanceSquared);
   }

   public float enlargement(Rectangle r) {
      float enlargedArea = (Math.max(this.maxX, r.maxX) - Math.min(this.minX, r.minX)) * (Math.max(this.maxY, r.maxY) - Math.min(this.minY, r.minY));
      return enlargedArea - this.area();
   }

   public static float enlargement(float r1MinX, float r1MinY, float r1MaxX, float r1MaxY, float r2MinX, float r2MinY, float r2MaxX, float r2MaxY) {
      float r1Area = (r1MaxX - r1MinX) * (r1MaxY - r1MinY);
      if (r1Area == Float.POSITIVE_INFINITY) {
         return 0.0F;
      } else {
         if (r2MinX < r1MinX) {
            r1MinX = r2MinX;
         }

         if (r2MinY < r1MinY) {
            r1MinY = r2MinY;
         }

         if (r2MaxX > r1MaxX) {
            r1MaxX = r2MaxX;
         }

         if (r2MaxY > r1MaxY) {
            r1MaxY = r2MaxY;
         }

         float r1r2UnionArea = (r1MaxX - r1MinX) * (r1MaxY - r1MinY);
         return r1r2UnionArea == Float.POSITIVE_INFINITY ? Float.POSITIVE_INFINITY : r1r2UnionArea - r1Area;
      }
   }

   public float area() {
      return (this.maxX - this.minX) * (this.maxY - this.minY);
   }

   public static float area(float minX, float minY, float maxX, float maxY) {
      return (maxX - minX) * (maxY - minY);
   }

   public void add(Rectangle r) {
      if (r.minX < this.minX) {
         this.minX = r.minX;
      }

      if (r.maxX > this.maxX) {
         this.maxX = r.maxX;
      }

      if (r.minY < this.minY) {
         this.minY = r.minY;
      }

      if (r.maxY > this.maxY) {
         this.maxY = r.maxY;
      }
   }

   public void add(Point p) {
      if (p.x < this.minX) {
         this.minX = p.x;
      }

      if (p.x > this.maxX) {
         this.maxX = p.x;
      }

      if (p.y < this.minY) {
         this.minY = p.y;
      }

      if (p.y > this.maxY) {
         this.maxY = p.y;
      }
   }

   public Rectangle union(Rectangle r) {
      Rectangle union = this.copy();
      union.add(r);
      return union;
   }

   @Override
   public int hashCode() {
      int prime = 31;
      int result = 1;
      result = 31 * result + Float.floatToIntBits(this.maxX);
      result = 31 * result + Float.floatToIntBits(this.maxY);
      result = 31 * result + Float.floatToIntBits(this.minX);
      return 31 * result + Float.floatToIntBits(this.minY);
   }

   @Override
   public boolean equals(Object o) {
      boolean equals = false;
      if (o instanceof Rectangle) {
         Rectangle r = (Rectangle)o;
         if (this.minX == r.minX && this.minY == r.minY && this.maxX == r.maxX && this.maxY == r.maxY) {
            equals = true;
         }
      }

      return equals;
   }

   public boolean sameObject(Object o) {
      return super.equals(o);
   }

   @Override
   public String toString() {
      return "(" + this.minX + ", " + this.minY + "), (" + this.maxX + ", " + this.maxY + ")";
   }

   public float width() {
      return this.maxX - this.minX;
   }

   public float height() {
      return this.maxY - this.minY;
   }

   public float aspectRatio() {
      return this.width() / this.height();
   }

   public Point centre() {
      return new Point((this.minX + this.maxX) / 2.0F, (this.minY + this.maxY) / 2.0F);
   }
}
