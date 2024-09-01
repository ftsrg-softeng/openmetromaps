package de.topobyte.osm4j.extra.datatree.nodetree;

import de.topobyte.osm4j.core.access.OsmInputAccessFactory;
import de.topobyte.osm4j.core.access.OsmIteratorInput;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.DataTreeFiles;
import de.topobyte.osm4j.extra.datatree.DataTreeUtil;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.extra.datatree.nodetree.count.NodeTreeLeafCounter;
import de.topobyte.osm4j.extra.datatree.nodetree.count.NodeTreeLeafCounterFactory;
import de.topobyte.osm4j.extra.datatree.nodetree.distribute.NodeTreeDistributor;
import de.topobyte.osm4j.extra.datatree.nodetree.distribute.NodeTreeDistributorFactory;
import de.topobyte.osm4j.extra.datatree.output.DataTreeOutputFactory;
import de.topobyte.osm4j.utils.OsmFileInput;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class NodeTreeCreatorMaxNodes {
   private OsmInputAccessFactory inputFactory;
   private DataTreeOutputFactory outputFactory;
   private int maxNodes;
   private int splitInitial;
   private int splitIteration;
   private Path dirOutput;
   private String fileNames;
   private OsmOutputConfig outputConfig;
   private DataTree tree;
   private NodeTreeLeafCounterFactory counterFactory;
   private NodeTreeDistributorFactory distributorFactory;
   private Deque<NodeTreeLeafCounter> check = new LinkedList<>();

   public NodeTreeCreatorMaxNodes(
      DataTree tree,
      OsmInputAccessFactory inputFactory,
      DataTreeOutputFactory outputFactory,
      int maxNodes,
      int splitInitial,
      int splitIteration,
      Path dirOutput,
      String fileNames,
      OsmOutputConfig outputConfig,
      NodeTreeLeafCounterFactory counterFactory,
      NodeTreeDistributorFactory distributorFactory
   ) {
      this.tree = tree;
      this.inputFactory = inputFactory;
      this.outputFactory = outputFactory;
      this.maxNodes = maxNodes;
      this.splitInitial = splitInitial;
      this.splitIteration = splitIteration;
      this.dirOutput = dirOutput;
      this.fileNames = fileNames;
      this.outputConfig = outputConfig;
      this.counterFactory = counterFactory;
      this.distributorFactory = distributorFactory;
   }

   public void buildTree() throws IOException {
      DataTreeFiles treeFiles = new DataTreeFiles(this.dirOutput, this.fileNames);
      this.tree.getRoot().split(this.splitInitial);
      this.countAndDistribute(this.tree.getRoot(), this.inputFactory);
      int iteration = 0;

      while (!this.check.isEmpty()) {
         System.out.println(String.format("Iteration %d", ++iteration));
         List<Node> largeNodes = new ArrayList<>();

         for (NodeTreeLeafCounter counter : this.check) {
            for (Node node : this.tree.getLeafs(counter.getHead())) {
               long count = counter.getCounters().get(node.getPath());
               if (count > (long)this.maxNodes) {
                  System.out.println(String.format("Node %s has too many nodes: %d", Long.toHexString(node.getPath()), count));
                  largeNodes.add(node);
               }
            }
         }

         this.check.clear();
         System.out.println(String.format("Iteration %d: there are %d large nodes", iteration, largeNodes.size()));

         for (Node nodex : largeNodes) {
            Path path = treeFiles.getPath(nodex);
            System.out.println(String.format("Splitting again: node %s", Long.toHexString(nodex.getPath())));
            nodex.split(this.splitIteration);
            this.countAndDistribute(nodex, new OsmFileInput(path, this.outputConfig.getFileFormat()));
            Files.delete(path);
            Files.delete(path.getParent());
         }
      }
   }

   private void countAndDistribute(Node node, OsmInputAccessFactory inputFactory) throws IOException {
      OsmIteratorInput input = inputFactory.createIterator(false, false);
      NodeTreeLeafCounter counter = this.counterFactory.createLeafCounter(this.tree, input.getIterator(), node);

      try {
         counter.execute();
      } finally {
         input.close();
      }

      DataTreeUtil.mergeUnderfilledSiblings(this.tree, node, this.maxNodes, counter.getCounters());
      input = inputFactory.createIterator(true, this.outputConfig.isWriteMetadata());
      NodeTreeDistributor distributor = this.distributorFactory.createDistributor(this.tree, node, input.getIterator(), this.outputFactory);

      try {
         distributor.execute();
      } finally {
         input.close();
      }

      this.check.add(counter);
   }
}
