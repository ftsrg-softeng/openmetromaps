package com.infomatiq.jsi.rtree;

import com.infomatiq.jsi.Rectangle;

public class TreeTraverser {
   private Traversal traversal;
   private RTree rtree;

   public TreeTraverser(RTree tree, Traversal traversal) {
      this.traversal = traversal;
      this.rtree = tree;
   }

   public void traverse() {
      int rootId = this.rtree.rootNodeId;
      Node root = this.rtree.getNode(rootId);
      this.traverse(root, 0);
   }

   private void traverse(Node node, int level) {
      if (node != null) {
         Rectangle mbb = node.getMbb();
         this.traversal.node(mbb);
         if (node.isLeaf()) {
            int nc = node.getEntryCount();

            for (int i = 0; i < nc; i++) {
               int element = node.getId(i);
               Rectangle box = node.getEntryMbb(i);
               this.traversal.element(box, element);
            }
         } else {
            int nc = node.getEntryCount();

            for (int i = 0; i < nc; i++) {
               int id = node.getId(i);
               Node child = this.rtree.getNode(id);
               this.traverse(child, level + 1);
            }
         }
      }
   }
}
