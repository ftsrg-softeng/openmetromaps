package de.topobyte.osm4j.extra.relations.split;

import com.slimjars.dist.gnu.trove.map.TLongIntMap;
import com.slimjars.dist.gnu.trove.map.hash.TLongIntHashMap;
import com.vividsolutions.jts.geom.Envelope;
import de.topobyte.largescalefileio.ClosingFileOutputStreamFactory;
import de.topobyte.largescalefileio.SimpleClosingFileOutputStreamFactory;
import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmIteratorInputFactory;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.access.OsmOutputStreamStreamOutput;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.model.impl.Bounds;
import de.topobyte.osm4j.extra.idbboxlist.IdBboxEntry;
import de.topobyte.osm4j.extra.idbboxlist.IdBboxListOutputStream;
import de.topobyte.osm4j.extra.idbboxlist.IdBboxUtil;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RelationSorterBase {
   private int maxMembers;
   private Path pathInputBboxes;
   private Path pathOutput;
   protected String fileNamesRelations;
   protected OsmIteratorInputFactory iteratorFactory;
   protected OsmOutputConfig outputConfig;
   private Path pathOutputBboxList;
   protected NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
   protected TLongIntMap idToBatch;
   protected List<List<IdBboxEntry>> batches;
   protected List<OsmStreamOutput> outputs;
   private IdBboxListOutputStream bboxOutput;

   public RelationSorterBase(
      Path pathInputBboxes,
      Path pathOutput,
      String fileNamesRelations,
      OsmIteratorInputFactory iteratorFactory,
      OsmOutputConfig outputConfig,
      Path pathOutputBboxList,
      int maxMembers
   ) {
      this.pathInputBboxes = pathInputBboxes;
      this.pathOutput = pathOutput;
      this.fileNamesRelations = fileNamesRelations;
      this.iteratorFactory = iteratorFactory;
      this.outputConfig = outputConfig;
      this.pathOutputBboxList = pathOutputBboxList;
      this.maxMembers = maxMembers;
   }

   protected void ensureOutputDirectory() throws IOException {
      if (!Files.exists(this.pathOutput)) {
         System.out.println("Creating output directory");
         Files.createDirectories(this.pathOutput);
      }

      if (!Files.isDirectory(this.pathOutput)) {
         System.out.println("Output path is not a directory");
         System.exit(1);
      }

      if (this.pathOutput.toFile().list().length != 0) {
         System.out.println("Output directory is not empty");
         System.exit(1);
      }
   }

   protected void createBboxOutput() throws IOException {
      OutputStream output = StreamUtil.bufferedOutputStream(this.pathOutputBboxList);
      this.bboxOutput = new IdBboxListOutputStream(output);
   }

   protected void createBatchOutputs() throws IOException {
      InputStream inputBboxes = StreamUtil.bufferedInputStream(this.pathInputBboxes);
      List<IdBboxEntry> bboxes = IdBboxUtil.read(inputBboxes);
      inputBboxes.close();
      this.batches = BatchSorting.sort(bboxes, this.maxMembers);
      System.out.println("number of batches: " + this.batches.size());
      this.idToBatch = new TLongIntHashMap();
      int batchCounter = 0;

      for (List<IdBboxEntry> batch : this.batches) {
         for (IdBboxEntry entry : batch) {
            this.idToBatch.put(entry.getId(), batchCounter);
         }

         batchCounter++;
      }

      ClosingFileOutputStreamFactory factory = new SimpleClosingFileOutputStreamFactory();
      this.outputs = new ArrayList<>();

      for (int i = 0; i < this.batches.size(); i++) {
         int id = i + 1;
         List<IdBboxEntry> batch = this.batches.get(i);
         IdBboxEntry batchEntry = this.sum(id, batch);
         Path subdir = this.batchDir(id);
         Files.createDirectory(subdir);
         Path path = this.batchFile(id, this.fileNamesRelations);
         OutputStream output = new BufferedOutputStream(factory.create(path.toFile()));
         OsmOutputStream osmOutput = OsmIoUtils.setupOsmOutput(output, this.outputConfig);
         this.outputs.add(new OsmOutputStreamStreamOutput(output, osmOutput));
         Envelope e = batchEntry.getEnvelope();
         Bounds bounds = new Bounds(e.getMinX(), e.getMaxX(), e.getMaxY(), e.getMinY());
         osmOutput.write(bounds);
         this.bboxOutput.write(batchEntry);
      }
   }

   protected Path batchDir(int id) {
      String subdirName = String.format("%d", id);
      return this.pathOutput.resolve(subdirName);
   }

   protected Path batchFile(int id, String fileName) {
      Path subdir = this.batchDir(id);
      return subdir.resolve(fileName);
   }

   protected void closeOutputs() throws IOException {
      this.bboxOutput.close();

      for (OsmStreamOutput output : this.outputs) {
         output.getOsmOutput().complete();
         output.close();
      }
   }

   private IdBboxEntry sum(int id, List<IdBboxEntry> batch) {
      Envelope envelope = new Envelope();
      int size = 0;

      for (IdBboxEntry entry : batch) {
         envelope.expandToInclude(entry.getEnvelope());
         size += entry.getSize();
      }

      return new IdBboxEntry((long)id, envelope, size);
   }
}
