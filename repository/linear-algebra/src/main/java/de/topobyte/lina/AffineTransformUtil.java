package de.topobyte.lina;

public class AffineTransformUtil {
   public static Matrix translate(double x, double y) {
      Matrix matrix = new Matrix(3, 3);
      matrix.setValue(2, 0, x);
      matrix.setValue(2, 1, y);
      matrix.setValue(0, 0, 1.0);
      matrix.setValue(1, 1, 1.0);
      matrix.setValue(2, 2, 1.0);
      return matrix;
   }

   public static Matrix scale(double x, double y) {
      Matrix matrix = new Matrix(3, 3);
      matrix.setValue(0, 0, x);
      matrix.setValue(1, 1, y);
      matrix.setValue(2, 2, 1.0);
      return matrix;
   }
}
