package de.topobyte.osm4j.extra.datatree.ways;

import de.topobyte.largescalefileio.ClosingFileInputStreamFactory;
import de.topobyte.largescalefileio.SimpleClosingFileInputStreamFactory;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.dataset.sort.IdComparator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.DataTreeOpener;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.extra.progress.NodeProgress;
import de.topobyte.osm4j.extra.ways.WayNodeIdComparator;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.merge.sorted.SortedMergeIterator;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWaysToTreeMapper implements WaysToTreeMapper {
   private OsmIterator nodeIterator;
   protected Path pathTree;
   private Path pathWays;
   private FileFormat inputFormatWays;
   private boolean readMetadata;
   protected DataTree tree;
   private SortedMergeIterator wayIterator;
   private List<InputStream> wayInputStreams = new ArrayList<>();
   private NodeProgress progress = new NodeProgress();
   private OsmWay way = null;
   private long next = -1L;

   public AbstractWaysToTreeMapper(OsmIterator nodeIterator, Path pathTree, Path pathWays, FileFormat inputFormatWays, boolean readMetadata) {
      this.nodeIterator = nodeIterator;
      this.pathTree = pathTree;
      this.pathWays = pathWays;
      this.inputFormatWays = inputFormatWays;
      this.readMetadata = readMetadata;
   }

   @Override
   public void execute() throws IOException {
      this.prepare();
      this.map();
      this.finish();
   }

   public void prepare() throws IOException {
      this.tree = DataTreeOpener.open(this.pathTree.toFile());
      ClosingFileInputStreamFactory factoryIn = new SimpleClosingFileInputStreamFactory();
      List<OsmIterator> wayIterators = new ArrayList<>();
      File[] wayFiles = this.pathWays.toFile().listFiles();

      for (File file : wayFiles) {
         InputStream inputWays = factoryIn.create(file);
         InputStream var10 = new BufferedInputStream(inputWays);
         this.wayInputStreams.add(var10);
         OsmIterator osmIterator = OsmIoUtils.setupOsmIterator(var10, this.inputFormatWays, this.readMetadata);
         wayIterators.add(osmIterator);
      }

      this.wayIterator = new SortedMergeIterator(wayIterators, new IdComparator(), new WayNodeIdComparator(), new IdComparator());
   }

   private boolean advanceWay() {
      while (this.wayIterator.hasNext()) {
         EntityContainer c = this.wayIterator.next();
         if (c.getType() == EntityType.Way) {
            this.way = (OsmWay)c.getEntity();
            this.next = this.way.getNodeId(0);
            return true;
         }
      }

      this.way = null;
      this.next = -1L;
      return false;
   }

   public void map() throws IOException {
      this.progress.printTimed(1000L);
      this.advanceWay();

      while (this.nodeIterator.hasNext()) {
         EntityContainer container = (EntityContainer)this.nodeIterator.next();
         if (container.getType() != EntityType.Node) {
            break;
         }

         OsmNode node = (OsmNode)container.getEntity();
         long id = node.getId();
         this.progress.increment();
         if (this.next <= id) {
            if (this.next == id) {
               this.query(node);

               while (this.advanceWay() && this.next == id) {
                  this.query(node);
               }
            } else {
               while (this.advanceWay() && this.next < id) {
                  this.query(node);
               }
            }

            if (this.way == null) {
               break;
            }
         }
      }

      this.progress.stop();
   }

   private void query(OsmNode node) throws IOException {
      for (Node leaf : this.tree.query(node.getLongitude(), node.getLatitude())) {
         this.process(this.way, leaf);
      }
   }

   protected void finish() throws IOException {
      for (InputStream input : this.wayInputStreams) {
         input.close();
      }
   }

   protected abstract void process(OsmWay var1, Node var2) throws IOException;
}
