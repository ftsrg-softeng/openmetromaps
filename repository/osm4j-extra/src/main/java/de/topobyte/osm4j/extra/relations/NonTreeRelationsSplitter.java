package de.topobyte.osm4j.extra.relations;

import de.topobyte.osm4j.extra.batch.BatchFilesUtil;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmFile;
import de.topobyte.osm4j.utils.OsmFileInput;
import de.topobyte.osm4j.utils.OsmFileSetInput;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NonTreeRelationsSplitter {
   private int maxMembersSimple;
   private int maxMembersComplex;
   private Path pathInputSimple;
   private Path pathInputComplex;
   private Path pathInputSimpleBboxes;
   private Path pathInputComplexBboxes;
   private Path pathInputSimpleOld;
   private Path pathInputComplexOld;
   private Path pathOutputSimple;
   private Path pathOutputComplex;
   private FileFormat inputFormat;
   private OsmOutputConfig outputConfig;
   private Path pathOutputSimpleBboxList;
   private Path pathOutputComplexBboxList;
   private boolean keepUnsortedRelations;

   public NonTreeRelationsSplitter(
      Path pathInputSimple,
      Path pathInputComplex,
      Path pathInputSimpleBboxes,
      Path pathInputComplexBboxes,
      Path pathInputSimpleOld,
      Path pathInputComplexOld,
      Path pathOutputSimple,
      Path pathOutputComplex,
      FileFormat inputFormat,
      OsmOutputConfig outputConfig,
      Path pathOutputSimpleBboxList,
      Path pathOutputComplexBboxList,
      int maxMembersSimple,
      int maxMembersComplex,
      boolean keepUnsortedRelations
   ) {
      this.pathInputSimple = pathInputSimple;
      this.pathInputComplex = pathInputComplex;
      this.pathInputSimpleBboxes = pathInputSimpleBboxes;
      this.pathInputComplexBboxes = pathInputComplexBboxes;
      this.pathInputSimpleOld = pathInputSimpleOld;
      this.pathInputComplexOld = pathInputComplexOld;
      this.pathOutputSimple = pathOutputSimple;
      this.pathOutputComplex = pathOutputComplex;
      this.inputFormat = inputFormat;
      this.outputConfig = outputConfig;
      this.pathOutputSimpleBboxList = pathOutputSimpleBboxList;
      this.pathOutputComplexBboxList = pathOutputComplexBboxList;
      this.maxMembersSimple = maxMembersSimple;
      this.maxMembersComplex = maxMembersComplex;
      this.keepUnsortedRelations = keepUnsortedRelations;
   }

   public void execute() throws IOException {
      List<Path> nodePathsSimple = BatchFilesUtil.getPaths(this.pathInputSimpleOld, "nodes" + OsmIoUtils.extension(this.inputFormat));
      Collection<OsmFile> nodeFilesSimple = this.createOsmFiles(nodePathsSimple);
      List<Path> wayPathsSimple = BatchFilesUtil.getPaths(this.pathInputSimpleOld, "ways" + OsmIoUtils.extension(this.inputFormat));
      Collection<OsmFile> wayFilesSimple = this.createOsmFiles(wayPathsSimple);
      List<Path> nodePathsComplex = BatchFilesUtil.getPaths(this.pathInputComplexOld, "nodes" + OsmIoUtils.extension(this.inputFormat));
      Collection<OsmFile> nodeFilesComplex = this.createOsmFiles(nodePathsComplex);
      List<Path> wayPathsComplex = BatchFilesUtil.getPaths(this.pathInputComplexOld, "ways" + OsmIoUtils.extension(this.inputFormat));
      Collection<OsmFile> wayFilesComplex = this.createOsmFiles(wayPathsComplex);
      OsmFileSetInput inputNodesSimple = new OsmFileSetInput(nodeFilesSimple);
      OsmFileSetInput inputWaysSimple = new OsmFileSetInput(wayFilesSimple);
      OsmFileSetInput inputNodesComplex = new OsmFileSetInput(nodeFilesComplex);
      OsmFileSetInput inputWaysComplex = new OsmFileSetInput(wayFilesComplex);
      OsmFileInput inputSimpleRelations = new OsmFileInput(this.pathInputSimple, this.inputFormat);
      OsmFileInput inputComplexRelations = new OsmFileInput(this.pathInputComplex, this.inputFormat);
      Path pathOutputSimpleRelations = this.pathOutputSimple;
      Path pathOutputComplexRelations = this.pathOutputComplex;
      String fileNamesRelations = "relations" + OsmIoUtils.extension(this.outputConfig.getFileFormat());
      String fileNamesRelationsUnsorted = "relations-unsorted" + OsmIoUtils.extension(this.outputConfig.getFileFormat());
      SimpleRelationsSorterAndMemberCollector task1 = new SimpleRelationsSorterAndMemberCollector(
         inputSimpleRelations,
         this.pathInputSimpleBboxes,
         pathOutputSimpleRelations,
         fileNamesRelations,
         inputWaysSimple,
         inputNodesSimple,
         this.outputConfig,
         this.pathOutputSimpleBboxList,
         this.maxMembersSimple
      );
      task1.execute();
      ComplexRelationsSorterAndMemberCollector task2 = new ComplexRelationsSorterAndMemberCollector(
         inputComplexRelations,
         this.pathInputComplexBboxes,
         pathOutputComplexRelations,
         fileNamesRelationsUnsorted,
         fileNamesRelations,
         inputWaysComplex,
         inputNodesComplex,
         this.outputConfig,
         this.pathOutputComplexBboxList,
         this.maxMembersComplex,
         this.keepUnsortedRelations
      );
      task2.execute();
   }

   private Collection<OsmFile> createOsmFiles(List<Path> paths) {
      List<OsmFile> files = new ArrayList<>();

      for (Path path : paths) {
         files.add(new OsmFile(path, this.inputFormat));
      }

      return files;
   }
}
