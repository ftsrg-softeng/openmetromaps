package de.topobyte.osm4j.extra.relations;

import de.topobyte.osm4j.core.access.OsmIteratorInputFactory;
import de.topobyte.osm4j.extra.relations.split.ComplexRelationSplitter;
import de.topobyte.osm4j.extra.relations.split.SimpleRelationSplitter;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RelationsSplitterAndMemberCollector {
   private OsmIteratorInputFactory inputSimpleRelations;
   private OsmIteratorInputFactory inputComplexRelations;
   private OsmIteratorInputFactory inputWays;
   private OsmIteratorInputFactory inputNodes;
   private Path pathOutputSimpleRelations;
   private Path pathOutputComplexRelations;
   private String fileNamesRelations;
   private OsmOutputConfig outputConfig;

   public RelationsSplitterAndMemberCollector(
      OsmIteratorInputFactory inputSimpleRelations,
      OsmIteratorInputFactory inputComplexRelations,
      Path pathOutputSimpleRelations,
      Path pathOutputComplexRelations,
      String fileNamesRelations,
      OsmIteratorInputFactory inputWays,
      OsmIteratorInputFactory inputNodes,
      OsmOutputConfig outputConfig
   ) {
      this.inputSimpleRelations = inputSimpleRelations;
      this.inputComplexRelations = inputComplexRelations;
      this.pathOutputSimpleRelations = pathOutputSimpleRelations;
      this.pathOutputComplexRelations = pathOutputComplexRelations;
      this.fileNamesRelations = fileNamesRelations;
      this.inputWays = inputWays;
      this.inputNodes = inputNodes;
      this.outputConfig = outputConfig;
   }

   public void execute() throws IOException {
      Files.createDirectories(this.pathOutputSimpleRelations);
      Files.createDirectories(this.pathOutputComplexRelations);
      SimpleRelationSplitter simpleRelationSplitter = new SimpleRelationSplitter(
         this.pathOutputSimpleRelations, this.fileNamesRelations, this.inputSimpleRelations, this.outputConfig
      );
      simpleRelationSplitter.execute();
      ComplexRelationSplitter complexRelationSplitter = new ComplexRelationSplitter(
         this.pathOutputComplexRelations, this.fileNamesRelations, this.inputComplexRelations, this.outputConfig
      );
      complexRelationSplitter.execute();
      List<Path> pathsRelations = new ArrayList<>();
      pathsRelations.add(this.pathOutputSimpleRelations);
      pathsRelations.add(this.pathOutputComplexRelations);
      RelationsMemberCollector memberCollector = new RelationsMemberCollector(
         pathsRelations, this.fileNamesRelations, this.inputWays, this.inputNodes, this.outputConfig
      );
      memberCollector.execute();
   }
}
