package de.topobyte.swing.util.dnd.panel;

import de.topobyte.swing.util.dnd.DestinationSourceTransferHandler;
import de.topobyte.swing.util.dnd.DestinationTransferHandler;
import de.topobyte.swing.util.dnd.SourceAwareTransferHandler;
import de.topobyte.swing.util.dnd.SourceTransferHandler;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DndPanel<T> extends JPanel implements DragGestureListener {
   private static final long serialVersionUID = 735894929836107251L;
   static final Logger logger = LoggerFactory.getLogger(DndPanel.class);
   private T data;
   private boolean isSource;
   private boolean isDestination;

   public DndPanel(T data, boolean isSource, boolean isDestination) {
      this.isSource = isSource;
      this.isDestination = isDestination;
      this.setupDragndrop();
      this.data = data;
      this.setup(data);
      JComponent component = this.getComponent();
      this.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0);
      this.add(component, c);
   }

   @Override
   public void dragGestureRecognized(DragGestureEvent dge) {
      logger.debug("drag gesture");
      TransferHandler handler = this.getTransferHandler();
      handler.exportAsDrag(this, dge.getTriggerEvent(), 1);
   }

   private void setupDragndrop() {
      DndPanel<T>.SourceTransferhandler sourceHandler = new DndPanel.SourceTransferhandler();
      DndPanel<T>.DestinationTransferhandler destHandler = new DndPanel.DestinationTransferhandler();
      DestinationSourceTransferHandler transferHandler = new DestinationSourceTransferHandler();
      transferHandler.setSourceHandler(sourceHandler);
      transferHandler.setDestinationHandler(destHandler);
      this.setTransferHandler(transferHandler);
      DragSource dragSource = new DragSource();
      dragSource.createDefaultDragGestureRecognizer(this, 1, this);
   }

   public abstract void setup(T var1);

   public T getData() {
      return this.data;
   }

   public void setData(T data) {
      this.data = data;
   }

   public boolean isDestination() {
      return this.isDestination;
   }

   public boolean isSource() {
      return this.isSource;
   }

   public abstract List<DataFlavor> getSupportedFlavors();

   public abstract Object getTransferData(DataFlavor var1, T var2);

   public abstract boolean importData(TransferSupport var1);

   public abstract JComponent getComponent();

   class DestinationTransferhandler implements DestinationTransferHandler {
      @Override
      public boolean canImport(TransferSupport ts) {
         if (!DndPanel.this.isDestination()) {
            return false;
         } else {
            DataFlavor[] flavors = ts.getDataFlavors();
            List<DataFlavor> supportedFlavors = DndPanel.this.getSupportedFlavors();

            for (DataFlavor flavor : flavors) {
               if (supportedFlavors.contains(flavor)) {
                  return true;
               }
            }

            return false;
         }
      }

      @Override
      public boolean importData(TransferSupport ts) {
         return !DndPanel.this.isDestination() ? false : DndPanel.this.importData(ts);
      }
   }

   class SourceTransferhandler extends SourceAwareTransferHandler implements SourceTransferHandler {
      private static final long serialVersionUID = 1320181642257143993L;

      @Override
      public int getSourceActions(JComponent c) {
         return !DndPanel.this.isSource() ? 0 : 1;
      }

      @Override
      public Transferable createTransferable(JComponent c) {
         if (!DndPanel.this.isSource()) {
            return null;
         } else {
            super.createTransferable(c);
            DndPanel.logger.debug("createTransferable");
            return new PanelTransferable<T>(DndPanel.this.getData(), DndPanel.this.getSupportedFlavors()) {
               @Override
               public Object getTransferData(DataFlavor flavor, T t) {
                  return DndPanel.this.getTransferData(flavor, t);
               }
            };
         }
      }

      @Override
      public void exportDone(JComponent c, Transferable t, int action) {
         if (DndPanel.this.isSource()) {
            super.exportDone(c, t, action);
         }
      }
   }
}
