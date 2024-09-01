package de.topobyte.jsi;

import com.infomatiq.jsi.Rectangle;

class TraversalAdapter<T> implements com.infomatiq.jsi.rtree.Traversal {
   private GenericRTree<T> tree;
   private Traversal<T> traversal;

   public TraversalAdapter(GenericRTree<T> tree, Traversal<T> traversal) {
      this.tree = tree;
      this.traversal = traversal;
   }

   @Override
   public void element(Rectangle rectangle, int nodeId) {
      T element = (T)this.tree.idToThing.get(nodeId);
      this.traversal.element(rectangle, element);
   }

   @Override
   public void node(Rectangle rectangle) {
      this.traversal.node(rectangle);
   }
}
