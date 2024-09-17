package de.topobyte.swing.util.tree;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

public class TreeModelListenerList {
   private List<TreeModelListener> listeners = new ArrayList<>();

   public void addTreeModelListener(TreeModelListener l) {
      this.listeners.add(l);
   }

   public void removeTreeModelListener(TreeModelListener l) {
      this.listeners.remove(l);
   }

   public void triggerTreeStructureChanged(TreeModelEvent event) {
      for (TreeModelListener listener : this.listeners) {
         listener.treeStructureChanged(event);
      }
   }

   public void triggerTreeNodesChanged(TreeModelEvent event) {
      for (TreeModelListener listener : this.listeners) {
         listener.treeNodesChanged(event);
      }
   }

   public void triggerTreeNodesInserted(TreeModelEvent event) {
      for (TreeModelListener listener : this.listeners) {
         listener.treeNodesInserted(event);
      }
   }

   public void triggerTreeNodesRemoved(TreeModelEvent event) {
      for (TreeModelListener listener : this.listeners) {
         listener.treeNodesRemoved(event);
      }
   }
}
