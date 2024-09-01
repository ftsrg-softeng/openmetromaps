package de.topobyte.osm4j.extra.relations;

import de.topobyte.osm4j.core.access.OsmIteratorInputFactory;
import de.topobyte.osm4j.extra.relations.split.ComplexRelationSplitter;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ComplexRelationsSplitterAndMemberCollector {
   private OsmIteratorInputFactory inputComplexRelations;
   private OsmIteratorInputFactory inputWays;
   private OsmIteratorInputFactory inputNodes;
   private Path pathOutputComplexRelations;
   private String fileNamesRelations;
   private OsmOutputConfig outputConfig;

   public ComplexRelationsSplitterAndMemberCollector(
      OsmIteratorInputFactory inputComplexRelations,
      Path pathOutputComplexRelations,
      String fileNamesRelations,
      OsmIteratorInputFactory inputWays,
      OsmIteratorInputFactory inputNodes,
      OsmOutputConfig outputConfig
   ) {
      this.inputComplexRelations = inputComplexRelations;
      this.pathOutputComplexRelations = pathOutputComplexRelations;
      this.fileNamesRelations = fileNamesRelations;
      this.inputWays = inputWays;
      this.inputNodes = inputNodes;
      this.outputConfig = outputConfig;
   }

   public void execute() throws IOException {
      Files.createDirectories(this.pathOutputComplexRelations);
      ComplexRelationSplitter complexRelationSplitter = new ComplexRelationSplitter(
         this.pathOutputComplexRelations, this.fileNamesRelations, this.inputComplexRelations, this.outputConfig
      );
      complexRelationSplitter.execute();
      List<Path> pathsRelations = new ArrayList<>();
      pathsRelations.add(this.pathOutputComplexRelations);
      RelationsMemberCollector memberCollector = new RelationsMemberCollector(
         pathsRelations, this.fileNamesRelations, this.inputWays, this.inputNodes, this.outputConfig
      );
      memberCollector.execute();
   }
}
