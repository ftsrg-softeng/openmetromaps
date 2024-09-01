package de.topobyte.swing.util.dnd.panel;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PanelTransferable<T> implements Transferable {
   static final Logger logger = LoggerFactory.getLogger(PanelTransferable.class);
   T data;
   List<DataFlavor> flavorList;

   public abstract Object getTransferData(DataFlavor var1, T var2);

   public PanelTransferable(T data, List<DataFlavor> flavorList) {
      this.data = data;
      this.flavorList = flavorList;
   }

   @Override
   public DataFlavor[] getTransferDataFlavors() {
      logger.debug("getTransferDataFlavors()");
      return this.flavorList.toArray(new DataFlavor[0]);
   }

   @Override
   public boolean isDataFlavorSupported(DataFlavor flavor) {
      for (DataFlavor accepted : this.flavorList) {
         if (flavor.equals(accepted)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public Object getTransferData(DataFlavor flavor) {
      for (DataFlavor accepted : this.flavorList) {
         if (flavor.equals(accepted)) {
            return this.getTransferData(flavor, this.data);
         }
      }

      return null;
   }
}
