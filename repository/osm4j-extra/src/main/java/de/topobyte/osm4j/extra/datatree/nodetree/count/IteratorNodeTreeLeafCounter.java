package de.topobyte.osm4j.extra.datatree.nodetree.count;

import com.slimjars.dist.gnu.trove.map.TLongLongMap;
import com.slimjars.dist.gnu.trove.map.hash.TLongLongHashMap;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.extra.progress.NodeProgress;
import java.io.IOException;

public class IteratorNodeTreeLeafCounter {
   private DataTree tree;
   private Node head;
   private TLongLongMap counters = new TLongLongHashMap();
   private NodeProgress counter = new NodeProgress();

   public IteratorNodeTreeLeafCounter(DataTree tree, Node head) {
      this.tree = tree;
      this.head = head;
   }

   public Node getHead() {
      return this.head;
   }

   public TLongLongMap getCounters() {
      return this.counters;
   }

   public void execute(OsmIterator input) throws IOException {
      this.counter.printTimed(1000L);

      try {
         this.count(input);
      } finally {
         this.counter.stop();
      }
   }

   private void count(OsmIterator iterator) throws IOException {
      while (iterator.hasNext()) {
         EntityContainer entityContainer = (EntityContainer)iterator.next();
         switch (entityContainer.getType()) {
            case Node:
               OsmNode node = (OsmNode)entityContainer.getEntity();
               this.findLeafsAndIncrementCounters(node);
               this.counter.increment();
               break;
            case Way:
            case Relation:
               return;
         }
      }
   }

   private void findLeafsAndIncrementCounters(OsmNode node) {
      for (Node leaf : this.tree.query(this.head, node.getLongitude(), node.getLatitude())) {
         long path = leaf.getPath();
         this.counters.put(path, this.counters.get(path) + 1L);
      }
   }
}
