package de.topobyte.osm4j.extra.relations;

import de.topobyte.osm4j.core.access.OsmIteratorInputFactory;
import de.topobyte.osm4j.extra.relations.split.ComplexRelationSorter;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ComplexRelationsSorterAndMemberCollector {
   private int maxMembers;
   private OsmIteratorInputFactory inputComplexRelations;
   private Path pathInputComplexRelationsBboxes;
   private OsmIteratorInputFactory inputWays;
   private OsmIteratorInputFactory inputNodes;
   private Path pathOutputComplexRelations;
   private String fileNamesRelationsUnsorted;
   private String fileNamesRelations;
   private boolean keepUnsortedRelations;
   private OsmOutputConfig outputConfig;
   private Path pathOutputBboxList;

   public ComplexRelationsSorterAndMemberCollector(
      OsmIteratorInputFactory inputComplexRelations,
      Path pathInputComplexRelationsBboxes,
      Path pathOutputComplexRelations,
      String fileNamesRelationsUnsorted,
      String fileNamesRelations,
      OsmIteratorInputFactory inputWays,
      OsmIteratorInputFactory inputNodes,
      OsmOutputConfig outputConfig,
      Path pathOutputBboxList,
      int maxMembers,
      boolean keepUnsortedRelations
   ) {
      this.inputComplexRelations = inputComplexRelations;
      this.pathInputComplexRelationsBboxes = pathInputComplexRelationsBboxes;
      this.pathOutputComplexRelations = pathOutputComplexRelations;
      this.fileNamesRelationsUnsorted = fileNamesRelationsUnsorted;
      this.fileNamesRelations = fileNamesRelations;
      this.inputWays = inputWays;
      this.inputNodes = inputNodes;
      this.outputConfig = outputConfig;
      this.pathOutputBboxList = pathOutputBboxList;
      this.maxMembers = maxMembers;
      this.keepUnsortedRelations = keepUnsortedRelations;
   }

   public void execute() throws IOException {
      Files.createDirectories(this.pathOutputComplexRelations);
      ComplexRelationSorter complexRelationSorter = new ComplexRelationSorter(
         this.pathInputComplexRelationsBboxes,
         this.pathOutputComplexRelations,
         this.fileNamesRelationsUnsorted,
         this.fileNamesRelations,
         this.inputComplexRelations,
         this.outputConfig,
         this.pathOutputBboxList,
         this.maxMembers,
         this.keepUnsortedRelations
      );
      complexRelationSorter.execute();
      List<Path> pathsRelations = new ArrayList<>();
      pathsRelations.add(this.pathOutputComplexRelations);
      RelationsMemberCollector memberCollector = new RelationsMemberCollector(
         pathsRelations, this.fileNamesRelations, this.inputWays, this.inputNodes, this.outputConfig
      );
      memberCollector.execute();
   }
}
