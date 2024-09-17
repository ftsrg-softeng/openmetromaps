package de.topobyte.osm4j.extra.datatree.nodetree.distribute;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractNodeTreeDistributor implements NodeTreeDistributor {
   protected DataTree tree;
   protected OsmIterator iterator;
   protected OsmOutputConfig outputConfig;
   protected Node head;
   protected Map<Node, OsmStreamOutput> outputs = new HashMap<>();

   public AbstractNodeTreeDistributor(DataTree tree, Node head, OsmIterator iterator) {
      this.tree = tree;
      this.iterator = iterator;
      this.head = head;
   }

   public Node getHead() {
      return this.head;
   }

   public Map<Node, OsmStreamOutput> getOutputs() {
      return this.outputs;
   }

   @Override
   public void execute() throws IOException {
      this.initOutputs();
      this.distributeNodes();
      this.finish();
   }

   protected abstract void initOutputs() throws IOException;

   protected abstract void distributeNodes() throws IOException;

   private void finish() throws IOException {
      for (OsmStreamOutput output : this.outputs.values()) {
         output.getOsmOutput().complete();
         output.close();
      }
   }
}
