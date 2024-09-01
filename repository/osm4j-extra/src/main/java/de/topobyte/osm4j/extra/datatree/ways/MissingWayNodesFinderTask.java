package de.topobyte.osm4j.extra.datatree.ways;

import com.slimjars.dist.gnu.trove.iterator.TLongIterator;
import com.slimjars.dist.gnu.trove.iterator.TLongObjectIterator;
import com.slimjars.dist.gnu.trove.list.TLongList;
import com.slimjars.dist.gnu.trove.list.array.TLongArrayList;
import com.slimjars.dist.gnu.trove.set.TLongSet;
import com.slimjars.dist.gnu.trove.set.hash.TLongHashSet;
import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmIdIterator;
import de.topobyte.osm4j.core.access.OsmIdIteratorInput;
import de.topobyte.osm4j.core.access.OsmIteratorInput;
import de.topobyte.osm4j.core.dataset.InMemoryMapDataSet;
import de.topobyte.osm4j.core.dataset.MapDataSetLoader;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.IdContainer;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.extra.idlist.IdListOutputStream;
import de.topobyte.osm4j.extra.threading.Task;
import de.topobyte.osm4j.utils.OsmFile;
import de.topobyte.osm4j.utils.OsmFileInput;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class MissingWayNodesFinderTask implements Task {
   private long counter = 0L;
   private long found = 0L;
   private long notFound = 0L;
   private OsmFile fileNodes;
   private OsmFile fileWays;
   private File fileOutput;
   private boolean verbose;

   public MissingWayNodesFinderTask(OsmFile inputNodes, OsmFile inputWays, File fileOutput, boolean verbose) {
      this.fileNodes = inputNodes;
      this.fileWays = inputWays;
      this.fileOutput = fileOutput;
      this.verbose = verbose;
   }

   public long getCounter() {
      return this.counter;
   }

   public long getFound() {
      return this.found;
   }

   public long getNotFound() {
      return this.notFound;
   }

   @Override
   public void execute() throws IOException {
      if (this.verbose) {
         long nodesSize = this.fileNodes.getPath().toFile().length();
         System.out.println(String.format("Loading nodes file of size: %.3fMB", (double)nodesSize / 1024.0 / 1024.0));
      }

      OsmIdIteratorInput nodeInput = new OsmFileInput(this.fileNodes).createIdIterator();
      TLongSet nodeIds = this.read(nodeInput.getIterator());
      if (this.verbose) {
         long waysSize = this.fileWays.getPath().toFile().length();
         System.out.println(String.format("Loading ways file of size: %.3fMB", (double)waysSize / 1024.0 / 1024.0));
      }

      OsmIteratorInput wayInput = new OsmFileInput(this.fileWays).createIterator(false, false);
      InMemoryMapDataSet dataWays = MapDataSetLoader.read(wayInput, true, true, true);
      nodeInput.close();
      wayInput.close();
      if (this.verbose) {
         System.out.println("Number of ways: " + dataWays.getWays().size());
      }

      TLongSet missingIds = new TLongHashSet();
      TLongObjectIterator<OsmWay> ways = dataWays.getWays().iterator();

      while (ways.hasNext()) {
         ways.advance();
         OsmWay way = (OsmWay)ways.value();
         this.build(way, nodeIds, missingIds);
      }

      if (this.verbose) {
         System.out.println("Sorting id list of size: " + missingIds.size());
      }

      TLongList missingIdList = new TLongArrayList(missingIds);
      missingIdList.sort();
      if (this.verbose) {
         System.out.println("Writing missing ids");
      }

      OutputStream bos = StreamUtil.bufferedOutputStream(this.fileOutput);
      IdListOutputStream idOutput = new IdListOutputStream(bos);
      TLongIterator iterator = missingIdList.iterator();

      while (iterator.hasNext()) {
         idOutput.write(iterator.next());
      }

      idOutput.close();
   }

   private TLongSet read(OsmIdIterator idIterator) {
      TLongSet ids = new TLongHashSet();

      while (idIterator.hasNext()) {
         IdContainer container = (IdContainer)idIterator.next();
         if (container.getType() != EntityType.Node) {
            break;
         }

         ids.add(container.getId());
      }

      return ids;
   }

   private void build(OsmWay way, TLongSet nodeIds, TLongSet missing) throws IOException {
      for (int i = 0; i < way.getNumberOfNodes(); i++) {
         long nodeId = way.getNodeId(i);
         if (nodeIds.contains(nodeId)) {
            this.found++;
         } else {
            this.notFound++;
            missing.add(nodeId);
         }
      }

      this.counter++;
   }
}
