package de.topobyte.viewports.scrolling;

import de.topobyte.lina.AffineTransformUtil;
import de.topobyte.lina.Matrix;
import de.topobyte.viewports.geometry.Rectangle;

public class TransformHelper {
   public static Matrix createMatrix(Rectangle scene, Viewport viewport) {
      Matrix shift = AffineTransformUtil.translate(-scene.getX1(), -scene.getY1());
      Matrix translate = AffineTransformUtil.translate(viewport.getPositionX(), viewport.getPositionY());
      Matrix scale = AffineTransformUtil.scale(viewport.getZoom(), viewport.getZoom());
      return scale.multiplyFromRight(translate).multiplyFromRight(shift);
   }

   public static Matrix createInverseMatrix(Rectangle scene, Viewport viewport) {
      return createMatrix(scene, viewport).invert();
   }
}
