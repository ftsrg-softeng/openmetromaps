package de.topobyte.jts.utils.transform;

import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.CoordinateSequenceFactory;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.util.GeometryTransformer;
import de.topobyte.jgs.transform.CoordinateTransformer;

public class CoordinateGeometryTransformer extends GeometryTransformer {
   private CoordinateTransformer ct;

   public CoordinateGeometryTransformer(CoordinateTransformer ct) {
      this.ct = ct;
   }

   protected CoordinateSequence transformCoordinates(CoordinateSequence coords, Geometry parent) {
      CoordinateSequenceFactory csf = this.factory.getCoordinateSequenceFactory();
      CoordinateSequence cs = csf.create(coords.size(), coords.getDimension());

      for (int i = 0; i < coords.size(); i++) {
         cs.setOrdinate(i, 0, this.ct.getX(coords.getX(i)));
         cs.setOrdinate(i, 1, this.ct.getY(coords.getY(i)));
      }

      return cs;
   }
}
