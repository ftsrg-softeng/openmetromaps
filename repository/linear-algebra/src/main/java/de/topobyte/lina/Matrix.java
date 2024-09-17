package de.topobyte.lina;

import de.topobyte.formatting.DoubleFormatter;
import java.util.Random;

public class Matrix {
   private final int height;
   private final int width;
   private double[][] values;

   public Matrix(int height, int width) {
      if (height >= 1 && width >= 1) {
         this.height = height;
         this.width = width;
         this.values = new double[height][width];

         for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
               this.values[y][x] = 0.0;
            }
         }
      } else {
         throw new IllegalArgumentException("width and height must be positive");
      }
   }

   private Matrix(double[][] values) {
      this.width = values[0].length;
      this.height = values.length;
      this.values = values;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public void setValue(int x, int y, double v) {
      if (x < 0 || x > this.width - 1) {
         throw new IllegalArgumentException("matrix out of bounds: x");
      } else if (y >= 0 && y <= this.height - 1) {
         this.values[y][x] = v;
      } else {
         throw new IllegalArgumentException("matrix out of bounds: y");
      }
   }

   public double getValue(int x, int y) {
      if (x < 0 || x > this.width - 1) {
         throw new IllegalArgumentException("matrix out of bounds: x");
      } else if (y >= 0 && y <= this.height - 1) {
         return this.values[y][x];
      } else {
         throw new IllegalArgumentException("matrix out of bounds: y");
      }
   }

   public Matrix transponate() {
      Matrix transponated = new Matrix(this.width, this.height);

      for (int y = 0; y < this.height; y++) {
         for (int x = 0; x < this.width; x++) {
            transponated.setValue(y, x, this.getValue(x, y));
         }
      }

      return transponated;
   }

   public Matrix multiplyFromRight(Matrix other) {
      if (this.width != other.height) {
         throw new IllegalArgumentException("sizes don't fit");
      } else {
         Matrix result = new Matrix(this.height, other.width);

         for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
               double value = 0.0;

               for (int k = 0; k < this.width; k++) {
                  value += this.getValue(k, y) * other.getValue(x, k);
               }

               result.setValue(x, y, value);
            }
         }

         return result;
      }
   }

   public Matrix add(Matrix other) {
      if (!this.getDimension().equals(other.getDimension())) {
         throw new IllegalArgumentException("dimensions don't match");
      } else {
         Matrix result = new Matrix(this.height, this.width);

         for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
               result.setValue(x, y, this.getValue(x, y) + other.getValue(x, y));
            }
         }

         return result;
      }
   }

   public Matrix subtract(Matrix other) {
      if (!this.getDimension().equals(other.getDimension())) {
         throw new IllegalArgumentException("dimensions don't match");
      } else {
         Matrix result = new Matrix(this.height, this.width);

         for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
               result.setValue(x, y, this.getValue(x, y) - other.getValue(x, y));
            }
         }

         return result;
      }
   }

   public Matrix multiply(double lambda) {
      Matrix result = new Matrix(this.height, this.width);

      for (int y = 0; y < result.getHeight(); y++) {
         for (int x = 0; x < result.getWidth(); x++) {
            result.setValue(x, y, this.getValue(x, y) * lambda);
         }
      }

      return result;
   }

   public boolean isScalar() {
      return this.width == 1 && this.height == 1;
   }

   public boolean isVector() {
      return this.width == 1 || this.height == 1;
   }

   public double toScalar() {
      if (!this.isScalar()) {
         throw new IllegalArgumentException("matrix is not a scalar");
      } else {
         return this.getValue(0, 0);
      }
   }

   public Vector toVector() {
      if (!this.isVector()) {
         throw new IllegalArgumentException("matrix is not a vector");
      } else if (this.height == 1) {
         Vector vector = new Vector(this.width, VectorType.Row);

         for (int x = 0; x < this.width; x++) {
            vector.setValue(x, 0, this.getValue(x, 0));
         }

         return vector;
      } else {
         Vector vector = new Vector(this.height, VectorType.Column);

         for (int y = 0; y < this.height; y++) {
            vector.setValue(0, y, this.getValue(0, y));
         }

         return vector;
      }
   }

   public Dimension getDimension() {
      return new Dimension(this.width, this.height);
   }

   @Override
   public String toString() {
      DoubleFormatter formatter = new DoubleFormatter();
      formatter.setFractionDigits(4);
      formatter.setMinWidth(10);
      StringBuilder strb = new StringBuilder();
      String newline = System.getProperty("line.separator");

      for (int y = 0; y < this.getHeight(); y++) {
         for (int x = 0; x < this.getWidth(); x++) {
            formatter.format(strb, this.getValue(x, y));
            if (x < this.getWidth() - 1) {
               strb.append(" ");
            }
         }

         if (y < this.getHeight() - 1) {
            strb.append(newline);
         }
      }

      return strb.toString();
   }

   public void initRandom(Random random, int maxValue) {
      for (int y = 0; y < this.getHeight(); y++) {
         for (int x = 0; x < this.getWidth(); x++) {
            int v = random.nextInt(maxValue + 1);
            this.setValue(x, y, (double)v);
         }
      }
   }

   public static Matrix getIdentity(int size) {
      Matrix matrix = new Matrix(size, size);

      for (int i = 0; i < size; i++) {
         matrix.values[i][i] = 1.0;
      }

      return matrix;
   }

   public Matrix invert() {
      jama.Matrix jama = new jama.Matrix(this.values);
      jama.Matrix jamaInverse = jama.inverse();
      return new Matrix(jamaInverse.getArray());
   }

   public double determinant() {
      jama.Matrix jama = new jama.Matrix(this.values);
      return jama.det();
   }

   double[][] getValues() {
      return this.values;
   }

   public Matrix solve(Vector vector) {
      jama.Matrix jama = new jama.Matrix(this.values);
      jama.Matrix b = new jama.Matrix(vector.getValues());
      jama.Matrix solution = jama.solve(b);
      return new Matrix(solution.getArray());
   }
}
