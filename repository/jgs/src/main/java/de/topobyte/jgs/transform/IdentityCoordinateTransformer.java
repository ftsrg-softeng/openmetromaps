package de.topobyte.jgs.transform;

public class IdentityCoordinateTransformer implements CoordinateTransformer {
   @Override
   public double getX(double x) {
      return x;
   }

   @Override
   public double getY(double y) {
      return y;
   }
}
