package de.topobyte.osm4j.extra.relations;

import de.topobyte.osm4j.core.access.OsmIteratorInputFactory;
import de.topobyte.osm4j.extra.relations.split.SimpleRelationSorter;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SimpleRelationsSorterAndMemberCollector {
   private int maxMembers;
   private OsmIteratorInputFactory inputSimpleRelations;
   private Path pathInputSimpleRelationsBboxes;
   private OsmIteratorInputFactory inputWays;
   private OsmIteratorInputFactory inputNodes;
   private Path pathOutputSimpleRelations;
   private String fileNamesRelations;
   private OsmOutputConfig outputConfig;
   private Path pathOutputBboxList;

   public SimpleRelationsSorterAndMemberCollector(
      OsmIteratorInputFactory inputSimpleRelations,
      Path pathInputSimpleRelationsBboxes,
      Path pathOutputSimpleRelations,
      String fileNamesRelations,
      OsmIteratorInputFactory inputWays,
      OsmIteratorInputFactory inputNodes,
      OsmOutputConfig outputConfig,
      Path pathOutputBboxList,
      int maxMembers
   ) {
      this.inputSimpleRelations = inputSimpleRelations;
      this.pathInputSimpleRelationsBboxes = pathInputSimpleRelationsBboxes;
      this.pathOutputSimpleRelations = pathOutputSimpleRelations;
      this.fileNamesRelations = fileNamesRelations;
      this.inputWays = inputWays;
      this.inputNodes = inputNodes;
      this.outputConfig = outputConfig;
      this.pathOutputBboxList = pathOutputBboxList;
      this.maxMembers = maxMembers;
   }

   public void execute() throws IOException {
      Files.createDirectories(this.pathOutputSimpleRelations);
      SimpleRelationSorter simpleRelationSorter = new SimpleRelationSorter(
         this.pathInputSimpleRelationsBboxes,
         this.pathOutputSimpleRelations,
         this.fileNamesRelations,
         this.inputSimpleRelations,
         this.outputConfig,
         this.pathOutputBboxList,
         this.maxMembers
      );
      simpleRelationSorter.execute();
      List<Path> pathsRelations = new ArrayList<>();
      pathsRelations.add(this.pathOutputSimpleRelations);
      RelationsMemberCollector memberCollector = new RelationsMemberCollector(
         pathsRelations, this.fileNamesRelations, this.inputWays, this.inputNodes, this.outputConfig
      );
      memberCollector.execute();
   }
}
