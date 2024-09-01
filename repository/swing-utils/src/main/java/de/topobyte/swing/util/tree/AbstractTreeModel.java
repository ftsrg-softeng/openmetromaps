package de.topobyte.swing.util.tree;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;

public abstract class AbstractTreeModel implements TreeModel {
   private TreeModelListenerList listeners = new TreeModelListenerList();

   @Override
   public void addTreeModelListener(TreeModelListener l) {
      this.listeners.addTreeModelListener(l);
   }

   @Override
   public void removeTreeModelListener(TreeModelListener l) {
      this.listeners.removeTreeModelListener(l);
   }

   public void triggerTreeStructureChanged(TreeModelEvent event) {
      this.listeners.triggerTreeStructureChanged(event);
   }

   public void triggerTreeNodesChanged(TreeModelEvent event) {
      this.listeners.triggerTreeNodesChanged(event);
   }

   public void triggerTreeNodesInserted(TreeModelEvent event) {
      this.listeners.triggerTreeNodesInserted(event);
   }

   public void triggerTreeNodesRemoved(TreeModelEvent event) {
      this.listeners.triggerTreeNodesRemoved(event);
   }
}
