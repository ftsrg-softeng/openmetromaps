package de.topobyte.osm4j.extra.idextract;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.util.List;

public class Extractors {
   public static Extractor create(
      EntityType type, List<ExtractionItem> extractionItems, OsmOutputConfig outputConfig, boolean lowMemory, OsmIterator iterator, boolean threaded
   ) {
      return (Extractor)(!threaded
         ? new SimpleExtractor(type, extractionItems, outputConfig, lowMemory, iterator)
         : new ThreadedExtractor(type, extractionItems, outputConfig, lowMemory, iterator));
   }
}
