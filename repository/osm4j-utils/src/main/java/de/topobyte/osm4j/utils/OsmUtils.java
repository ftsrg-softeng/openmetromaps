package de.topobyte.osm4j.utils;

import de.topobyte.adt.geo.BBox;
import de.topobyte.osm4j.core.access.OsmIteratorInput;
import de.topobyte.osm4j.core.access.OsmIteratorInputFactory;
import de.topobyte.osm4j.utils.bbox.BBoxCalculator;
import java.io.IOException;

public class OsmUtils {
   public static BBox computeBBox(OsmIteratorInputFactory iteratorFactory) throws IOException {
      OsmIteratorInput iterator = iteratorFactory.createIterator(false, false);
      BBoxCalculator calculator = new BBoxCalculator(iterator.getIterator());
      BBox bbox = calculator.execute();
      iterator.close();
      return bbox;
   }
}
