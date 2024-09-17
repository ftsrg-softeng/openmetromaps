package de.topobyte.lina;

public class Dimension {
   int width;
   int height;

   public Dimension(int width, int height) {
      this.width = width;
      this.height = height;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof Dimension)) {
         return false;
      } else {
         Dimension other = (Dimension)o;
         return other.height == this.height && other.width == this.width;
      }
   }

   @Override
   public String toString() {
      return this.height + "x" + this.width;
   }
}
