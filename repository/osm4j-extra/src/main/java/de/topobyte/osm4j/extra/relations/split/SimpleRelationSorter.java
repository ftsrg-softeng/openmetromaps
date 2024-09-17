package de.topobyte.osm4j.extra.relations.split;

import de.topobyte.osm4j.core.access.OsmIteratorInput;
import de.topobyte.osm4j.core.access.OsmIteratorInputFactory;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.util.RelationIterator;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.nio.file.Path;

public class SimpleRelationSorter extends RelationSorterBase {
   public SimpleRelationSorter(
      Path pathInputBboxes,
      Path pathOutput,
      String fileNamesRelations,
      OsmIteratorInputFactory iteratorFactory,
      OsmOutputConfig outputConfig,
      Path pathOutputBboxList,
      int maxMembers
   ) {
      super(pathInputBboxes, pathOutput, fileNamesRelations, iteratorFactory, outputConfig, pathOutputBboxList, maxMembers);
   }

   public void execute() throws IOException {
      this.ensureOutputDirectory();
      this.createBboxOutput();
      this.createBatchOutputs();
      OsmIteratorInput iteratorInput = this.iteratorFactory.createIterator(true, this.outputConfig.isWriteMetadata());
      RelationIterator relations = new RelationIterator(iteratorInput.getIterator());
      int relationCount = 0;

      for (OsmRelation relation : relations) {
         if (!this.idToBatch.containsKey(relation.getId())) {
            System.out.println("not available: " + relation.getId());
         }

         int batchNum = this.idToBatch.get(relation.getId());
         OsmStreamOutput osmOutput = this.outputs.get(batchNum);
         osmOutput.getOsmOutput().write(relation);
         relationCount++;
      }

      iteratorInput.close();
      this.closeOutputs();
      System.out.println(String.format("Wrote %s relations in %d batches", this.format.format((long)relationCount), this.batches.size()));
   }
}
