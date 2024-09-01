package de.topobyte.osm4j.extra.relations.split;

import com.slimjars.dist.gnu.trove.map.TLongObjectMap;
import com.slimjars.dist.gnu.trove.set.TLongSet;
import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmIteratorInputFactory;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.extra.relations.Group;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import de.topobyte.osm4j.utils.sort.MemorySort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ComplexRelationSorter extends RelationSorterBase {
   private String fileNamesRelationsSorted;
   private boolean keepUnsortedRelations;

   public ComplexRelationSorter(
      Path pathInputBboxes,
      Path pathOutput,
      String fileNamesRelationsUnsorted,
      String fileNamesRelations,
      OsmIteratorInputFactory iteratorFactory,
      OsmOutputConfig outputConfig,
      Path pathOutputBboxList,
      int maxMembers,
      boolean keepUnsortedRelations
   ) {
      super(pathInputBboxes, pathOutput, fileNamesRelationsUnsorted, iteratorFactory, outputConfig, pathOutputBboxList, maxMembers);
      this.fileNamesRelationsSorted = fileNamesRelations;
      this.keepUnsortedRelations = keepUnsortedRelations;
   }

   public void execute() throws IOException {
      this.ensureOutputDirectory();
      this.createBboxOutput();
      this.createBatchOutputs();
      ComplexRelationGrouper grouper = new ComplexRelationGrouper(this.iteratorFactory, true, false);
      grouper.buildGroups();
      grouper.readGroupRelations(this.outputConfig.isWriteMetadata());
      List<Group> groups = grouper.getGroups();
      TLongObjectMap<OsmRelation> groupRelations = grouper.getGroupRelations();
      int relationCount = 0;

      for (Group group : groups) {
         long representative = group.getStart();
         if (!this.idToBatch.containsKey(representative)) {
            System.out.println("not available: " + representative);
         }

         int batchNum = this.idToBatch.get(representative);
         OsmStreamOutput osmOutput = this.outputs.get(batchNum);
         TLongSet ids = group.getRelationIds();

         for (long id : ids.toArray()) {
            OsmRelation relation = (OsmRelation)groupRelations.get(id);
            if (relation == null) {
               System.out.println("relation not found: " + id);
            } else {
               osmOutput.getOsmOutput().write(relation);
               relationCount++;
            }
         }
      }

      this.closeOutputs();
      System.out.println(String.format("Wrote %s relations in %d batches", this.format.format((long)relationCount), this.batches.size()));
      boolean useMetadata = this.outputConfig.isWriteMetadata();

      for (int i = 0; i < this.batches.size(); i++) {
         int idx = i + 1;
         System.out.println("sorting " + idx);
         Path pathUnsorted = this.batchFile(idx, this.fileNamesRelations);
         Path pathSorted = this.batchFile(idx, this.fileNamesRelationsSorted);
         InputStream input = StreamUtil.bufferedInputStream(pathUnsorted);
         OutputStream output = StreamUtil.bufferedOutputStream(pathSorted);
         OsmIterator osmInput = OsmIoUtils.setupOsmIterator(input, this.outputConfig.getFileFormat(), useMetadata);
         OsmOutputStream osmOutput = OsmIoUtils.setupOsmOutput(output, this.outputConfig);
         MemorySort memorySort = new MemorySort(osmOutput, osmInput);
         memorySort.setIgnoreDuplicates(true);
         memorySort.run();
         output.close();
         input.close();
         if (!this.keepUnsortedRelations) {
            Files.delete(pathUnsorted);
         }
      }
   }
}
