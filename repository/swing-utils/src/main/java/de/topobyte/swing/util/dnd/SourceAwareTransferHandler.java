package de.topobyte.swing.util.dnd;

import java.awt.datatransfer.Transferable;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SourceAwareTransferHandler extends TransferHandler {
   private static final long serialVersionUID = -8338665701896075432L;
   static final Logger logger = LoggerFactory.getLogger(SourceAwareTransferHandler.class);
   private JComponent source;

   @Override
   public Transferable createTransferable(JComponent c) {
      logger.debug("createTransferable");
      this.source = c;
      return null;
   }

   @Override
   public void exportDone(JComponent c, Transferable t, int action) {
      this.source = null;
   }

   public boolean isDragWithinSameComponent() {
      return this.source != null;
   }
}
