package de.topobyte.osm4j.extra.datatree.nodetree.count;

import com.slimjars.dist.gnu.trove.map.TLongLongMap;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.Node;
import java.io.IOException;

public class SimpleNodeTreeLeafCounter implements NodeTreeLeafCounter {
   private OsmIterator iterator;
   private IteratorNodeTreeLeafCounter counter;

   public SimpleNodeTreeLeafCounter(DataTree tree, Node head, OsmIterator iterator) {
      this.iterator = iterator;
      this.counter = new IteratorNodeTreeLeafCounter(tree, head);
   }

   @Override
   public Node getHead() {
      return this.counter.getHead();
   }

   @Override
   public TLongLongMap getCounters() {
      return this.counter.getCounters();
   }

   @Override
   public void execute() throws IOException {
      this.counter.execute(this.iterator);
   }
}
