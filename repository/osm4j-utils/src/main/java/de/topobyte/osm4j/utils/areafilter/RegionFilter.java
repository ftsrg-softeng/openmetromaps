package de.topobyte.osm4j.utils.areafilter;

import com.vividsolutions.jts.geom.Geometry;
import de.topobyte.jts.utils.predicate.PredicateEvaluatorPrepared;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmOutputStream;

public class RegionFilter extends AbstractAreaFilter {
   public RegionFilter(OsmOutputStream output, OsmIterator input, Geometry region, boolean onlyNodes) {
      super(output, input, onlyNodes);
      this.test = new PredicateEvaluatorPrepared(region);
   }
}
