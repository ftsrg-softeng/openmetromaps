package de.topobyte.osm4j.utils.split;

import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

public class AbstractEntitySplitter {
   protected OsmIterator iterator;
   private Path pathNodes;
   private Path pathWays;
   private Path pathRelations;
   protected boolean passNodes = false;
   protected boolean passWays = false;
   protected boolean passRelations = false;
   private OutputStream osNodes = null;
   private OutputStream osWays = null;
   private OutputStream osRelations = null;
   protected OsmOutputStream oosNodes = null;
   protected OsmOutputStream oosWays = null;
   protected OsmOutputStream oosRelations = null;
   private OsmOutputConfig outputConfig;

   public AbstractEntitySplitter(OsmIterator iterator, Path pathNodes, Path pathWays, Path pathRelations, OsmOutputConfig outputConfig) {
      this.iterator = iterator;
      this.pathNodes = pathNodes;
      this.pathWays = pathWays;
      this.pathRelations = pathRelations;
      this.outputConfig = outputConfig;
      this.passNodes = pathNodes != null;
      this.passWays = pathWays != null;
      this.passRelations = pathRelations != null;
   }

   protected void init() throws IOException {
      if (this.passNodes) {
         this.osNodes = StreamUtil.bufferedOutputStream(this.pathNodes);
         this.oosNodes = OsmIoUtils.setupOsmOutput(this.osNodes, this.outputConfig);
      }

      if (this.passWays) {
         this.osWays = StreamUtil.bufferedOutputStream(this.pathWays);
         this.oosWays = OsmIoUtils.setupOsmOutput(this.osWays, this.outputConfig);
      }

      if (this.passRelations) {
         this.osRelations = StreamUtil.bufferedOutputStream(this.pathRelations);
         this.oosRelations = OsmIoUtils.setupOsmOutput(this.osRelations, this.outputConfig);
      }
   }

   protected void passBounds() throws IOException {
      if (this.iterator.hasBounds()) {
         OsmBounds bounds = this.iterator.getBounds();
         if (this.passNodes) {
            this.oosNodes.write(bounds);
         }

         if (this.passWays) {
            this.oosWays.write(bounds);
         }

         if (this.passRelations) {
            this.oosRelations.write(bounds);
         }
      }
   }

   protected void finish() throws IOException {
      if (this.passNodes) {
         this.oosNodes.complete();
         this.osNodes.close();
      }

      if (this.passWays) {
         this.oosWays.complete();
         this.osWays.close();
      }

      if (this.passRelations) {
         this.oosRelations.complete();
         this.osRelations.close();
      }
   }
}
