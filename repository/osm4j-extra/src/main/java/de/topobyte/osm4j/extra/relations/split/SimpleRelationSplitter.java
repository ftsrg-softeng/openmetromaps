package de.topobyte.osm4j.extra.relations.split;

import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmIteratorInput;
import de.topobyte.osm4j.core.access.OsmIteratorInputFactory;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.util.RelationIterator;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SimpleRelationSplitter {
   private int maxMembers = 100000;
   private Path pathOutput;
   private String fileNamesRelations;
   private OsmIteratorInputFactory iteratorFactory;
   private OsmOutputConfig outputConfig;
   private int batchCount = 0;
   private int relationCount = 0;
   private long start = System.currentTimeMillis();
   private NumberFormat format = NumberFormat.getNumberInstance(Locale.US);

   public SimpleRelationSplitter(Path pathOutput, String fileNamesRelations, OsmIteratorInputFactory iteratorFactory, OsmOutputConfig outputConfig) {
      this.pathOutput = pathOutput;
      this.fileNamesRelations = fileNamesRelations;
      this.iteratorFactory = iteratorFactory;
      this.outputConfig = outputConfig;
   }

   public void execute() throws IOException {
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

      RelationBatch batch = new RelationBatch(this.maxMembers);
      OsmIteratorInput iteratorInput = this.iteratorFactory.createIterator(true, this.outputConfig.isWriteMetadata());

      for (OsmRelation relation : new RelationIterator(iteratorInput.getIterator())) {
         if (relation.getNumberOfMembers() != 0) {
            if (batch.fits(relation)) {
               batch.add(relation);
            } else {
               this.process(batch);
               this.status();
               batch.clear();
               batch.add(relation);
            }
         }
      }

      if (!batch.getElements().isEmpty()) {
         this.process(batch);
         this.status();
         batch.clear();
      }

      System.out.println(String.format("Wrote %s relations in %d batches", this.format.format((long)this.relationCount), this.batchCount));
   }

   private void status() {
      long now = System.currentTimeMillis();
      long past = now - this.start;
      double seconds = (double)(past / 1000L);
      long perSecond = Math.round((double)this.relationCount / seconds);
      System.out
         .println(
            String.format(
               "Processed: %s relations, time passed: %.2f per second: %s",
               this.format.format((long)this.relationCount),
               (double)(past / 1000L) / 60.0,
               this.format.format(perSecond)
            )
         );
   }

   private void process(RelationBatch batch) throws IOException {
      List<OsmRelation> relations = batch.getElements();
      this.batchCount++;
      String subdirName = String.format("%d", this.batchCount);
      Path subdir = this.pathOutput.resolve(subdirName);
      Path path = subdir.resolve(this.fileNamesRelations);
      Files.createDirectory(subdir);
      OutputStream output = StreamUtil.bufferedOutputStream(path.toFile());
      OsmOutputStream osmOutput = OsmIoUtils.setupOsmOutput(output, this.outputConfig);

      for (OsmRelation relation : relations) {
         osmOutput.write(relation);
      }

      osmOutput.complete();
      output.close();
      this.relationCount = this.relationCount + relations.size();
   }
}
