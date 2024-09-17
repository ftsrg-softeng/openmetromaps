package de.topobyte.lina;

import de.topobyte.formatting.DoubleFormatter;

public class Vector extends Matrix {
   private final VectorType type;

   public Vector(int size, VectorType type) {
      super(type == VectorType.Row ? 1 : size, type == VectorType.Column ? 1 : size);
      this.type = type;
   }

   public void setValue(int p, double v) {
      if (this.type == VectorType.Column) {
         this.setValue(0, p, v);
      } else {
         this.setValue(p, 0, v);
      }
   }

   public double getValue(int p) {
      return this.type == VectorType.Column ? this.getValue(0, p) : this.getValue(p, 0);
   }

   public int getSize() {
      return this.type == VectorType.Column ? this.getHeight() : this.getWidth();
   }

   @Override
   public String toString() {
      StringBuilder strb = new StringBuilder();

      for (int i = 0; i < this.getSize(); i++) {
         strb.append(this.getValue(i));
         if (i < this.getSize() - 1) {
            strb.append(",");
         }
      }

      return strb.toString();
   }

   public String toString(int k) {
      DoubleFormatter formatter = new DoubleFormatter();
      formatter.setFractionDigits(k);
      StringBuilder strb = new StringBuilder();

      for (int i = 0; i < this.getSize(); i++) {
         formatter.format(strb, this.getValue(i));
         if (i < this.getSize() - 1) {
            strb.append(",");
         }
      }

      return strb.toString();
   }

   public double distance(Vector prev) {
      double sum = 0.0;

      for (int i = 0; i < this.getSize(); i++) {
         sum += Math.pow(prev.getValue(i) - this.getValue(i), 2.0);
      }

      return Math.sqrt(sum);
   }

   public double norm() {
      return Math.sqrt(this.transponate().multiplyFromRight(this).toScalar());
   }

   public Vector normalized() {
      return this.multiply(1.0 / this.norm()).toVector();
   }

   public Vector transponate() {
      Vector v = new Vector(this.getSize(), this.type == VectorType.Column ? VectorType.Row : VectorType.Column);

      for (int i = 0; i < this.getSize(); i++) {
         v.setValue(i, this.getValue(i));
      }

      return v;
   }
}
