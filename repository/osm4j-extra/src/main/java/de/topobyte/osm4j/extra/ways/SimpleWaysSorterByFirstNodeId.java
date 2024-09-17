package de.topobyte.osm4j.extra.ways;

import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SimpleWaysSorterByFirstNodeId implements WaysSorterByFirstNodeId {
   private OsmIterator input;
   private Path dirOutput;
   private OsmOutputConfig outputConfig;
   private int batchCount = 0;
   private int wayCount = 0;
   private long start = System.currentTimeMillis();
   private NumberFormat format = NumberFormat.getNumberInstance(Locale.US);

   public SimpleWaysSorterByFirstNodeId(OsmIterator input, Path dirOutput, OsmOutputConfig outputConfig) {
      this.input = input;
      this.dirOutput = dirOutput;
      this.outputConfig = outputConfig;
   }

   @Override
   public void execute() throws IOException {
      this.init();
      this.run();
   }

   private void init() throws IOException {
      if (!Files.exists(this.dirOutput)) {
         System.out.println("Creating output directory");
         Files.createDirectories(this.dirOutput);
      }

      if (!Files.isDirectory(this.dirOutput)) {
         System.out.println("Output path is not a directory");
         System.exit(1);
      }

      if (this.dirOutput.toFile().list().length != 0) {
         System.out.println("Output directory is not empty");
         System.exit(1);
      }
   }

   private void run() throws IOException {
      WayBatch batch = new WayBatch(800000, 10000000);

      for (EntityContainer container : this.input) {
         if (container.getType() == EntityType.Way) {
            OsmWay way = (OsmWay)container.getEntity();
            if (way.getNumberOfNodes() != 0) {
               if (batch.fits(way)) {
                  batch.add(way);
               } else {
                  this.process(batch);
                  this.status();
                  batch.clear();
                  batch.add(way);
               }
            }
         }
      }

      if (!batch.getElements().isEmpty()) {
         this.process(batch);
         this.status();
         batch.clear();
      }
   }

   private void status() {
      long now = System.currentTimeMillis();
      long past = now - this.start;
      double seconds = (double)(past / 1000L);
      long perSecond = Math.round((double)this.wayCount / seconds);
      System.out
         .println(
            String.format(
               "Processed: %s ways, time passed: %.2f per second: %s",
               this.format.format((long)this.wayCount),
               (double)(past / 1000L) / 60.0,
               this.format.format(perSecond)
            )
         );
   }

   private void process(WayBatch batch) throws IOException {
      List<OsmWay> ways = batch.getElements();
      Collections.sort(ways, new WayNodeIdComparator());
      this.batchCount++;
      String filename = String.format("%d%s", this.batchCount, OsmIoUtils.extension(this.outputConfig.getFileFormat()));
      Path path = this.dirOutput.resolve(filename);
      File file = path.toFile();
      OutputStream output = StreamUtil.bufferedOutputStream(file);
      OsmOutputStream osmOutput = OsmIoUtils.setupOsmOutput(output, this.outputConfig);

      for (OsmWay way : ways) {
         osmOutput.write(way);
      }

      osmOutput.complete();
      output.close();
      this.wayCount = this.wayCount + ways.size();
   }
}
