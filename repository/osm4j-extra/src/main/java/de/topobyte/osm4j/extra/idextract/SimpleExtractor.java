package de.topobyte.osm4j.extra.idextract;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.extra.entitywriter.EntityWriter;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.util.List;

public class SimpleExtractor extends AbstractExtractor {
   public SimpleExtractor(EntityType type, List<ExtractionItem> extractionItems, OsmOutputConfig outputConfig, boolean lowMemory, OsmIterator iterator) {
      super(type, extractionItems, outputConfig, lowMemory, iterator);
   }

   @Override
   public void execute() throws IOException {
      this.executeExtraction();
      this.finish();
   }

   @Override
   protected void output(EntityWriter writer, OsmEntity entity) throws IOException {
      writer.write(entity);
   }
}
