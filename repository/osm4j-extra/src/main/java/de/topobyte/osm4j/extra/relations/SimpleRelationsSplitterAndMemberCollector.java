package de.topobyte.osm4j.extra.relations;

import de.topobyte.osm4j.core.access.OsmIteratorInputFactory;
import de.topobyte.osm4j.extra.relations.split.SimpleRelationSplitter;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SimpleRelationsSplitterAndMemberCollector {
   private OsmIteratorInputFactory inputSimpleRelations;
   private OsmIteratorInputFactory inputWays;
   private OsmIteratorInputFactory inputNodes;
   private Path pathOutputSimpleRelations;
   private String fileNamesRelations;
   private OsmOutputConfig outputConfig;

   public SimpleRelationsSplitterAndMemberCollector(
      OsmIteratorInputFactory inputSimpleRelations,
      Path pathOutputSimpleRelations,
      String fileNamesRelations,
      OsmIteratorInputFactory inputWays,
      OsmIteratorInputFactory inputNodes,
      OsmOutputConfig outputConfig
   ) {
      this.inputSimpleRelations = inputSimpleRelations;
      this.pathOutputSimpleRelations = pathOutputSimpleRelations;
      this.fileNamesRelations = fileNamesRelations;
      this.inputWays = inputWays;
      this.inputNodes = inputNodes;
      this.outputConfig = outputConfig;
   }

   public void execute() throws IOException {
      Files.createDirectories(this.pathOutputSimpleRelations);
      SimpleRelationSplitter simpleRelationSplitter = new SimpleRelationSplitter(
         this.pathOutputSimpleRelations, this.fileNamesRelations, this.inputSimpleRelations, this.outputConfig
      );
      simpleRelationSplitter.execute();
      List<Path> pathsRelations = new ArrayList<>();
      pathsRelations.add(this.pathOutputSimpleRelations);
      RelationsMemberCollector memberCollector = new RelationsMemberCollector(
         pathsRelations, this.fileNamesRelations, this.inputWays, this.inputNodes, this.outputConfig
      );
      memberCollector.execute();
   }
}
