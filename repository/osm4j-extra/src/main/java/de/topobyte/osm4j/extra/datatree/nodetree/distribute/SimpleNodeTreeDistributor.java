package de.topobyte.osm4j.extra.datatree.nodetree.distribute;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.extra.datatree.output.DataTreeOutputFactory;
import de.topobyte.osm4j.extra.progress.NodeProgress;
import java.io.IOException;

public class SimpleNodeTreeDistributor extends AbstractNodeTreeDistributor {
   private DataTreeOutputFactory outputFactory;

   public SimpleNodeTreeDistributor(DataTree tree, Node head, OsmIterator iterator, DataTreeOutputFactory outputFactory) {
      super(tree, head, iterator);
      this.outputFactory = outputFactory;
   }

   @Override
   protected void initOutputs() throws IOException {
      for (Node leaf : this.tree.getLeafs(this.head)) {
         OsmStreamOutput output = this.outputFactory.init(leaf, true);
         this.outputs.put(leaf, output);
      }
   }

   @Override
   protected void distributeNodes() throws IOException {
      NodeProgress counter = new NodeProgress();
      counter.printTimed(1000L);

      label18:
      while (this.iterator.hasNext()) {
         EntityContainer entityContainer = (EntityContainer)this.iterator.next();
         switch (entityContainer.getType()) {
            case Node:
               OsmNode node = (OsmNode)entityContainer.getEntity();
               this.writeToLeafs(node);
               counter.increment();
               break;
            case Way:
            case Relation:
               break label18;
         }
      }

      counter.stop();
   }

   private void writeToLeafs(OsmNode node) throws IOException {
      for (Node leaf : this.tree.query(this.head, node.getLongitude(), node.getLatitude())) {
         OsmStreamOutput output = this.outputs.get(leaf);
         output.getOsmOutput().write(node);
      }
   }
}
