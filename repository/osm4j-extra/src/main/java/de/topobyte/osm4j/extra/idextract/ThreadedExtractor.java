package de.topobyte.osm4j.extra.idextract;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.extra.entitywriter.EntityWriter;
import de.topobyte.osm4j.extra.threading.ObjectBuffer;
import de.topobyte.osm4j.extra.threading.write.EntityWriterWriteRequest;
import de.topobyte.osm4j.extra.threading.write.WriteRequest;
import de.topobyte.osm4j.extra.threading.write.WriterRunner;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import de.topobyte.osm4j.utils.buffer.ParallelExecutor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThreadedExtractor extends AbstractExtractor {
   private ObjectBuffer<WriteRequest> buffer = new ObjectBuffer<>(1000, 100);

   public ThreadedExtractor(EntityType type, List<ExtractionItem> extractionItems, OsmOutputConfig outputConfig, boolean lowMemory, OsmIterator iterator) {
      super(type, extractionItems, outputConfig, lowMemory, iterator);
   }

   @Override
   protected void output(EntityWriter writer, OsmEntity entity) throws IOException {
      this.buffer.write(new EntityWriterWriteRequest(writer, entity));
   }

   @Override
   public void execute() throws IOException {
      Runnable extractor = new Runnable() {
         @Override
         public void run() {
            try {
               ThreadedExtractor.this.executeExtraction();
               ThreadedExtractor.this.buffer.close();
            } catch (IOException var2) {
               throw new RuntimeException(var2);
            }
         }
      };
      Runnable writer = new WriterRunner(this.buffer);
      List<Runnable> tasks = new ArrayList<>();
      tasks.add(extractor);
      tasks.add(writer);
      ParallelExecutor executor = new ParallelExecutor(tasks);
      executor.execute();
      this.finish();
   }
}
