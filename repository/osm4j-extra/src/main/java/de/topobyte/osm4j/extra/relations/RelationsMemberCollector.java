package de.topobyte.osm4j.extra.relations;

import de.topobyte.osm4j.core.access.OsmIteratorInput;
import de.topobyte.osm4j.core.access.OsmIteratorInputFactory;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.extra.batch.BatchFilesUtil;
import de.topobyte.osm4j.extra.idextract.ExtractionItem;
import de.topobyte.osm4j.extra.idextract.ExtractionUtil;
import de.topobyte.osm4j.extra.idextract.Extractor;
import de.topobyte.osm4j.extra.idextract.Extractors;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RelationsMemberCollector {
   public static final String FILE_NAMES_NODE_IDS = "nodes.ids";
   public static final String FILE_NAMES_WAY_IDS = "ways.ids";
   public static final String FILE_NAMES_WAY_NODE_IDS = "waynodes.ids";
   public static final String FILE_NAMES_NODE_BASENAME = "nodes";
   public static final String FILE_NAMES_WAY_BASENAME = "ways";
   private OsmIteratorInputFactory inputWays;
   private OsmIteratorInputFactory inputNodes;
   private List<Path> pathsRelations;
   private String fileNamesRelations;
   private OsmOutputConfig outputConfig;

   public RelationsMemberCollector(
      List<Path> pathsRelations, String fileNamesRelations, OsmIteratorInputFactory inputWays, OsmIteratorInputFactory inputNodes, OsmOutputConfig outputConfig
   ) {
      this.pathsRelations = pathsRelations;
      this.fileNamesRelations = fileNamesRelations;
      this.inputWays = inputWays;
      this.inputNodes = inputNodes;
      this.outputConfig = outputConfig;
   }

   public void execute() throws IOException {
      String fileNamesRelationNodeIds = "nodes.ids";
      String fileNamesRelationWayIds = "ways.ids";
      String fileNamesWayNodeIds = "waynodes.ids";
      String fileNamesWays = "ways" + OsmIoUtils.extension(this.outputConfig.getFileFormat());
      String fileNamesNodes = "nodes" + OsmIoUtils.extension(this.outputConfig.getFileFormat());
      Path[] dirsData = this.pathsRelations.toArray(new Path[0]);
      MemberIdsExtractor memberIdsExtractor = new MemberIdsExtractor(
         dirsData, this.fileNamesRelations, fileNamesRelationNodeIds, fileNamesRelationWayIds, this.outputConfig.getFileFormat()
      );
      memberIdsExtractor.execute();
      List<ExtractionItem> wayExtractionItems = new ArrayList<>();

      for (Path path : this.pathsRelations) {
         wayExtractionItems.addAll(ExtractionUtil.createExtractionItems(path, fileNamesRelationWayIds, fileNamesWays));
      }

      boolean threaded = true;
      OsmIteratorInput wayInput = this.inputWays.createIterator(true, this.outputConfig.isWriteMetadata());
      Extractor wayExtractor = Extractors.create(EntityType.Way, wayExtractionItems, this.outputConfig, true, wayInput.getIterator(), threaded);
      wayExtractor.execute();
      wayInput.close();
      WayMemberNodeIdsExtractor wayMemberNodeIdsExtractor = new WayMemberNodeIdsExtractor(
         dirsData, fileNamesWays, fileNamesWayNodeIds, this.outputConfig.getFileFormat()
      );
      wayMemberNodeIdsExtractor.execute();
      String[] fileNamesNodeIds = new String[]{fileNamesRelationNodeIds, fileNamesWayNodeIds};
      List<ExtractionItem> nodeExtractionItems = new ArrayList<>();

      for (Path path : this.pathsRelations) {
         nodeExtractionItems.addAll(ExtractionUtil.createExtractionItems(path, fileNamesNodeIds, fileNamesNodes));
      }

      OsmIteratorInput nodeInput = this.inputNodes.createIterator(true, this.outputConfig.isWriteMetadata());
      Extractor nodeExtractor = Extractors.create(EntityType.Node, nodeExtractionItems, this.outputConfig, true, nodeInput.getIterator(), threaded);
      nodeExtractor.execute();
      nodeInput.close();

      for (Path path : this.pathsRelations) {
         this.delete(BatchFilesUtil.getPaths(path, fileNamesRelationNodeIds));
         this.delete(BatchFilesUtil.getPaths(path, fileNamesRelationWayIds));
         this.delete(BatchFilesUtil.getPaths(path, fileNamesWayNodeIds));
      }
   }

   private void delete(List<Path> paths) throws IOException {
      for (Path path : paths) {
         Files.delete(path);
      }
   }
}
