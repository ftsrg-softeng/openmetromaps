package de.topobyte.viewports.geometry;

import de.topobyte.lina.Matrix;
import de.topobyte.lina.Vector;
import de.topobyte.lina.VectorType;

public class CoordinateTransformer {
   protected Matrix matrix;

   public CoordinateTransformer(Matrix matrix) {
      this.matrix = matrix;
   }

   public Coordinate transform(Coordinate c) {
      Vector v = new Vector(3, VectorType.Column);
      v.setValue(0, c.getX());
      v.setValue(1, c.getY());
      v.setValue(2, 1.0);
      Matrix r = this.matrix.multiplyFromRight(v);
      double x = r.getValue(0, 0);
      double y = r.getValue(0, 1);
      return new Coordinate(x, y);
   }
}
