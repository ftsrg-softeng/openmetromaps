package de.topobyte.jsi;

public class TreeTraverser<T> {
   private Traversal<T> traversal;
   private GenericRTree<T> tree;

   public TreeTraverser(GenericRTree<T> tree, Traversal<T> traversal) {
      this.tree = tree;
      this.traversal = traversal;
   }

   public void traverse() {
      TraversalAdapter<T> adapter = new TraversalAdapter<>(this.tree, this.traversal);
      com.infomatiq.jsi.rtree.TreeTraverser traverser = new com.infomatiq.jsi.rtree.TreeTraverser(this.tree.rtree, adapter);
      traverser.traverse();
   }
}
