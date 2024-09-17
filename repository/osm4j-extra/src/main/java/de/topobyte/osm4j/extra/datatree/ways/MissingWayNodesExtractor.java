package de.topobyte.osm4j.extra.datatree.ways;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.DataTreeFiles;
import de.topobyte.osm4j.extra.datatree.DataTreeOpener;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.extra.idextract.ExtractionItem;
import de.topobyte.osm4j.extra.idextract.Extractor;
import de.topobyte.osm4j.extra.idextract.Extractors;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MissingWayNodesExtractor {
   private OsmIterator iterator;
   private Path pathIdTree;
   private Path pathOutputTree;
   private String fileNamesIds;
   private String fileNamesOutput;
   private OsmOutputConfig outputConfig;
   private boolean threaded;
   private List<ExtractionItem> extractionItems = new ArrayList<>();

   public MissingWayNodesExtractor(
      OsmIterator iterator, Path pathIdTree, String fileNamesIds, Path pathOutputTree, String fileNamesOutput, OsmOutputConfig outputConfig, boolean threaded
   ) {
      this.iterator = iterator;
      this.pathIdTree = pathIdTree;
      this.fileNamesIds = fileNamesIds;
      this.pathOutputTree = pathOutputTree;
      this.fileNamesOutput = fileNamesOutput;
      this.outputConfig = outputConfig;
      this.threaded = threaded;
   }

   public void execute() throws IOException {
      this.prepare();
      this.run();
   }

   private void prepare() throws IOException {
      DataTree tree = DataTreeOpener.open(this.pathIdTree.toFile());
      DataTreeFiles filesIds = new DataTreeFiles(this.pathIdTree, this.fileNamesIds);
      DataTreeFiles filesOutput = new DataTreeFiles(this.pathOutputTree, this.fileNamesOutput);

      for (Node leaf : tree.getLeafs()) {
         File fileIds = filesIds.getFile(leaf);
         File fileOutput = filesOutput.getFile(leaf);
         ExtractionItem item = new ExtractionItem(fileIds.toPath(), fileOutput.toPath());
         this.extractionItems.add(item);
      }
   }

   private void run() throws IOException {
      Extractor extractor = Extractors.create(EntityType.Node, this.extractionItems, this.outputConfig, true, this.iterator, this.threaded);
      extractor.execute();
   }
}
