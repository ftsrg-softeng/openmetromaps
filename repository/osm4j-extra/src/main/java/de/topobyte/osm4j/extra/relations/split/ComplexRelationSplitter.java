package de.topobyte.osm4j.extra.relations.split;

import com.slimjars.dist.gnu.trove.map.TLongObjectMap;
import com.slimjars.dist.gnu.trove.set.TLongSet;
import com.slimjars.dist.gnu.trove.set.hash.TLongHashSet;
import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmIteratorInputFactory;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.dataset.InMemoryMapDataSet;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.extra.relations.Group;
import de.topobyte.osm4j.extra.relations.RelationGroupUtil;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ComplexRelationSplitter {
   private int maxMembers = 100000;
   private Path pathOutput;
   private String fileNamesRelations;
   private OsmIteratorInputFactory iteratorFactory;
   private OsmOutputConfig outputConfig;
   private List<Group> groups;
   private TLongObjectMap<OsmRelation> groupRelations;
   private int batchCount = 0;
   private int relationCount = 0;
   private long start = System.currentTimeMillis();
   private NumberFormat format = NumberFormat.getNumberInstance(Locale.US);

   public ComplexRelationSplitter(Path pathOutput, String fileNamesRelations, OsmIteratorInputFactory iteratorFactory, OsmOutputConfig outputConfig) {
      this.pathOutput = pathOutput;
      this.fileNamesRelations = fileNamesRelations;
      this.iteratorFactory = iteratorFactory;
      this.outputConfig = outputConfig;
   }

   public void execute() throws IOException {
      if (!Files.exists(this.pathOutput)) {
         System.out.println("Creating output directory");
         Files.createDirectories(this.pathOutput);
      }

      if (!Files.isDirectory(this.pathOutput)) {
         System.out.println("Output path is not a directory");
         System.exit(1);
      }

      if (this.pathOutput.toFile().list().length != 0) {
         System.out.println("Output directory is not empty");
         System.exit(1);
      }

      ComplexRelationGrouper grouper = new ComplexRelationGrouper(this.iteratorFactory, false, true);
      grouper.buildGroups();
      grouper.readGroupRelations(this.outputConfig.isWriteMetadata());
      this.groups = grouper.getGroups();
      this.groupRelations = grouper.getGroupRelations();
      this.determineGroupSizes();
      this.sortGroupsBySize();
      this.processGroupBatches();
   }

   private void determineGroupSizes() {
      InMemoryMapDataSet data = new InMemoryMapDataSet();
      data.setRelations(this.groupRelations);

      for (Group group : this.groups) {
         group.setNumMembers(RelationGroupUtil.groupSize(group, data));
      }
   }

   private void sortGroupsBySize() {
      Collections.sort(this.groups, new Comparator<Group>() {
         public int compare(Group o1, Group o2) {
            return Integer.compare(o2.getNumMembers(), o1.getNumMembers());
         }
      });
   }

   private void processGroupBatches() throws IOException {
      GroupBatch batch = new GroupBatch(this.maxMembers);

      label26:
      while (!this.groups.isEmpty()) {
         Iterator<Group> iterator = this.groups.iterator();

         while (iterator.hasNext()) {
            Group group = iterator.next();
            if (batch.fits(group)) {
               iterator.remove();
               batch.add(group);
               if (batch.isFull()) {
                  this.process(batch);
                  batch.clear();
                  this.status();
                  continue label26;
               }
            }
         }

         this.process(batch);
         batch.clear();
         this.status();
      }
   }

   private void status() {
      long now = System.currentTimeMillis();
      long past = now - this.start;
      double seconds = (double)(past / 1000L);
      long perSecond = Math.round((double)this.relationCount / seconds);
      System.out
         .println(
            String.format(
               "Processed: %s relations, time passed: %.2f per second: %s",
               this.format.format((long)this.relationCount),
               (double)(past / 1000L) / 60.0,
               this.format.format(perSecond)
            )
         );
   }

   private void process(GroupBatch batch) throws IOException {
      System.out.println(String.format("groups: %d, members: %d", batch.getElements().size(), batch.getSize()));
      List<Group> groups = batch.getElements();
      TLongSet batchRelationIds = new TLongHashSet();

      for (Group group : groups) {
         batchRelationIds.addAll(group.getRelationIds());
      }

      List<OsmRelation> relations = new ArrayList<>();

      for (long relationId : batchRelationIds.toArray()) {
         OsmRelation relation = (OsmRelation)this.groupRelations.get(relationId);
         if (relation == null) {
            System.out.println("relation not found: " + relationId);
         } else {
            relations.add(relation);
         }
      }

      this.batchCount++;
      String subdirName = String.format("%d", this.batchCount);
      Path subdir = this.pathOutput.resolve(subdirName);
      Path path = subdir.resolve(this.fileNamesRelations);
      Files.createDirectory(subdir);
      OutputStream output = StreamUtil.bufferedOutputStream(path.toFile());
      OsmOutputStream osmOutput = OsmIoUtils.setupOsmOutput(output, this.outputConfig);

      for (OsmRelation relation : relations) {
         osmOutput.write(relation);
      }

      osmOutput.complete();
      output.close();
      this.relationCount = this.relationCount + relations.size();
   }
}
