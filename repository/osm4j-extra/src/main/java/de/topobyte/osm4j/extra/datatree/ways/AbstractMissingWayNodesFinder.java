package de.topobyte.osm4j.extra.datatree.ways;

import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.DataTreeFiles;
import de.topobyte.osm4j.extra.datatree.DataTreeOpener;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public abstract class AbstractMissingWayNodesFinder implements MissingWayNodesFinder {
   private Path pathNodeTree;
   private Path pathWayTree;
   private Path pathOutputTree;
   private String fileNamesNodes;
   private String fileNamesWays;
   private String fileNamesOutput;
   protected FileFormat inputFormatNodes;
   protected FileFormat inputFormatWays;
   protected DataTreeFiles filesNodes;
   protected DataTreeFiles filesWays;
   protected DataTreeFiles filesOutput;
   protected List<Node> leafs;
   private int leafsDone = 0;
   private long counter = 0L;
   private long found = 0L;
   private long notFound = 0L;
   private long start = System.currentTimeMillis();
   private NumberFormat format = NumberFormat.getNumberInstance(Locale.US);

   public AbstractMissingWayNodesFinder(
      Path pathNodeTree,
      Path pathWayTree,
      Path pathOutputTree,
      String fileNamesNodes,
      String fileNamesWays,
      String fileNamesOutput,
      FileFormat inputFormatNodes,
      FileFormat inputFormatWays
   ) {
      this.pathNodeTree = pathNodeTree;
      this.pathWayTree = pathWayTree;
      this.pathOutputTree = pathOutputTree;
      this.fileNamesNodes = fileNamesNodes;
      this.fileNamesWays = fileNamesWays;
      this.fileNamesOutput = fileNamesOutput;
      this.inputFormatNodes = inputFormatNodes;
      this.inputFormatWays = inputFormatWays;
   }

   protected void prepare() throws IOException {
      DataTree tree = DataTreeOpener.open(this.pathNodeTree.toFile());
      this.filesNodes = new DataTreeFiles(this.pathNodeTree, this.fileNamesNodes);
      this.filesWays = new DataTreeFiles(this.pathWayTree, this.fileNamesWays);
      this.filesOutput = new DataTreeFiles(this.pathOutputTree, this.fileNamesOutput);
      this.leafs = tree.getLeafs();
   }

   protected MissingWayNodesFinderTask creatTask(Node leaf) {
      Path fileNodes = this.filesNodes.getPath(leaf);
      Path fileWays = this.filesWays.getPath(leaf);
      File fileOutput = this.filesOutput.getFile(leaf);
      return new MissingWayNodesFinderTask(new OsmFile(fileNodes, this.inputFormatNodes), new OsmFile(fileWays, this.inputFormatWays), fileOutput, false);
   }

   protected void stats(MissingWayNodesFinderTask t) {
      this.leafsDone++;
      this.counter = this.counter + t.getCounter();
      this.found = this.found + t.getFound();
      this.notFound = this.notFound + t.getNotFound();
      double ratio = (double)this.notFound / (double)(this.found + this.notFound);
      System.out
         .println(
            String.format(
               "ways: %s, found ids: %s, missing ids: %s, ratio: %f",
               this.format.format(this.counter),
               this.format.format(this.found),
               this.format.format(this.notFound),
               ratio
            )
         );
      long now = System.currentTimeMillis();
      long past = now - this.start;
      long estimate = Math.round((double)past / (double)this.leafsDone * (double)this.leafs.size());
      System.out.println(String.format("Past: %.2f", (double)(past / 1000L) / 60.0));
      System.out.println(String.format("Estimate: %.2f", (double)(estimate / 1000L) / 60.0));
   }
}
