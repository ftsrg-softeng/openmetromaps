package de.topobyte.osm4j.extra.idextract;

import de.topobyte.largescalefileio.ClosingFileInputStreamFactory;
import de.topobyte.largescalefileio.ClosingFileOutputStreamFactory;
import de.topobyte.largescalefileio.SimpleClosingFileInputStreamFactory;
import de.topobyte.largescalefileio.SimpleClosingFileOutputStreamFactory;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.extra.entitywriter.EntityWriter;
import de.topobyte.osm4j.extra.entitywriter.EntityWriters;
import de.topobyte.osm4j.extra.idlist.IdInput;
import de.topobyte.osm4j.extra.idlist.IdListInputStream;
import de.topobyte.osm4j.extra.idlist.merge.MergedIdInput;
import de.topobyte.osm4j.extra.progress.NodeProgress;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public abstract class AbstractExtractor implements Extractor {
   private final EntityType type;
   private List<ExtractionItem> extractionItems;
   private PriorityQueue<Item> queue;
   private List<Item> items;
   private OsmOutputConfig outputConfig;
   private boolean lowMemory;
   private OsmIterator iterator;

   public AbstractExtractor(EntityType type, List<ExtractionItem> extractionItems, OsmOutputConfig outputConfig, boolean lowMemory, OsmIterator iterator) {
      this.type = type;
      this.extractionItems = extractionItems;
      this.outputConfig = outputConfig;
      this.lowMemory = lowMemory;
      this.iterator = iterator;
   }

   protected abstract void output(EntityWriter var1, OsmEntity var2) throws IOException;

   public void executeExtraction() throws IOException {
      this.queue = new PriorityQueue<>(this.extractionItems.size(), new ItemComparator());
      this.items = new ArrayList<>(this.extractionItems.size());
      ClosingFileInputStreamFactory factoryIn = new SimpleClosingFileInputStreamFactory();
      ClosingFileOutputStreamFactory factoryOut = new SimpleClosingFileOutputStreamFactory();

      for (ExtractionItem configItem : this.extractionItems) {
         List<Path> pathsIds = configItem.getPathsIds();
         IdInput idInput;
         if (pathsIds.size() == 1) {
            File fileIds = pathsIds.get(0).toFile();
            InputStream inputIds = factoryIn.create(fileIds);
            InputStream var20 = new BufferedInputStream(inputIds);
            idInput = new IdListInputStream(var20);
         } else {
            List<IdInput> idInputs = new ArrayList<>();

            for (Path path : pathsIds) {
               InputStream inputIds = factoryIn.create(path.toFile());
               InputStream var26 = new BufferedInputStream(inputIds);
               idInputs.add(new IdListInputStream(var26));
            }

            idInput = new MergedIdInput(idInputs);
         }

         File fileOutput = configItem.getPathOutput().toFile();
         OutputStream output = factoryOut.create(fileOutput);
         OutputStream var22 = new BufferedOutputStream(output);
         OsmOutputStream osmOutput = OsmIoUtils.setupOsmOutput(var22, this.outputConfig, this.lowMemory);
         EntityWriter writer = EntityWriters.create(this.type, osmOutput);

         try {
            Item item = new Item(idInput, var22, osmOutput, writer);
            this.queue.add(item);
            this.items.add(item);
         } catch (EOFException var12) {
            osmOutput.complete();
            var22.close();
         }
      }

      NodeProgress progress = new NodeProgress();
      progress.printTimed(1000L);

      while (this.iterator.hasNext()) {
         EntityContainer container = (EntityContainer)this.iterator.next();
         if (container.getType() != this.type) {
            break;
         }

         OsmEntity entity = container.getEntity();
         progress.increment();
         if (this.queue.isEmpty()) {
            break;
         }

         long id = entity.getId();
         Item input = this.queue.peek();
         long next = input.getNext();
         if (next <= id) {
            if (next == id) {
               this.write(this.queue.poll(), entity);

               while (!this.queue.isEmpty() && this.queue.peek().getNext() == id) {
                  this.write(this.queue.poll(), entity);
               }
            } else {
               this.skip(this.queue.poll());

               while (!this.queue.isEmpty() && this.queue.peek().getNext() < id) {
                  this.skip(this.queue.poll());
               }
            }
         }
      }

      progress.stop();
   }

   public void finish() throws IOException {
      for (Item item : this.items) {
         item.getOsmOutput().complete();
         item.getOutput().close();
      }
   }

   private void write(Item item, OsmEntity entity) throws IOException {
      this.output(item.getWriter(), entity);

      try {
         item.next();
         this.queue.add(item);
      } catch (EOFException var4) {
         item.close();
      }
   }

   private void skip(Item input) throws IOException {
      try {
         input.next();
         this.queue.add(input);
      } catch (EOFException var3) {
         input.close();
      }
   }
}
