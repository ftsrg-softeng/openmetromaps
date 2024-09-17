package de.topobyte.osm4j.utils.areafilter;

import de.topobyte.adt.geo.BBox;
import de.topobyte.jts.utils.predicate.PredicateEvaluatorRectangle;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmOutputStream;

public class BboxFilter extends AbstractAreaFilter {
   public BboxFilter(OsmOutputStream output, OsmIterator input, BBox bbox, boolean onlyNodes) {
      super(output, input, onlyNodes);
      this.test = new PredicateEvaluatorRectangle(bbox.getLon1(), bbox.getLat2(), bbox.getLon2(), bbox.getLat1());
   }
}
