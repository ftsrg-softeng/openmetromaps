package de.topobyte.swing.util.dnd;

import java.awt.datatransfer.Transferable;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;

public class DestinationSourceTransferHandler extends TransferHandler {
   private static final long serialVersionUID = -7375752251790523942L;
   private DestinationTransferHandler destHandler;
   private SourceTransferHandler sourceHandler;

   public void setDestinationHandler(DestinationTransferHandler destHandler) {
      this.destHandler = destHandler;
   }

   public void setSourceHandler(SourceTransferHandler sourceHandler) {
      this.sourceHandler = sourceHandler;
   }

   @Override
   public boolean canImport(TransferSupport ts) {
      return this.destHandler.canImport(ts);
   }

   @Override
   public boolean importData(TransferSupport ts) {
      return this.destHandler.importData(ts);
   }

   @Override
   public int getSourceActions(JComponent c) {
      return this.sourceHandler.getSourceActions(c);
   }

   @Override
   public Transferable createTransferable(JComponent c) {
      return this.sourceHandler.createTransferable(c);
   }

   @Override
   public void exportDone(JComponent c, Transferable t, int action) {
      this.sourceHandler.exportDone(c, t, action);
   }
}
