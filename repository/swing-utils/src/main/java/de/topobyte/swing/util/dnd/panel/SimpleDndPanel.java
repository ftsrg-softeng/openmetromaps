package de.topobyte.swing.util.dnd.panel;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.TransferHandler.TransferSupport;

public abstract class SimpleDndPanel<T> extends StringDndPanel<T> {
   private static final long serialVersionUID = -5651983538515318609L;
   private final DataFlavor flavor;

   public SimpleDndPanel(DataFlavor flavor, T data, boolean isSource, boolean isDestination) {
      super(data, isSource, isDestination);
      this.flavor = flavor;
   }

   @Override
   public List<DataFlavor> getSupportedFlavors() {
      ArrayList<DataFlavor> flavors = new ArrayList<>();
      flavors.add(this.flavor);
      return flavors;
   }

   @Override
   public Object getTransferData(DataFlavor requestedFlavor, T t) {
      return this.flavor.equals(requestedFlavor) ? this.getTransferData(t) : null;
   }

   @Override
   public boolean importData(TransferSupport ts) {
      for (DataFlavor f : ts.getDataFlavors()) {
         if (f.equals(this.flavor)) {
            try {
               Object transferData = ts.getTransferable().getTransferData(this.flavor);
               return this.importData(transferData);
            } catch (UnsupportedFlavorException var7) {
               logger.debug("flavor not supported though it should be");
            } catch (IOException var8) {
               logger.debug("IOException while fetching transfer data: " + var8.getMessage());
            }
         }
      }

      return false;
   }

   public abstract Object getTransferData(T var1);

   public abstract boolean importData(Object var1);
}
