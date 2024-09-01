package de.topobyte.swing.util.dnd;

import javax.swing.TransferHandler.TransferSupport;

public interface DestinationTransferHandler {
   boolean canImport(TransferSupport var1);

   boolean importData(TransferSupport var1);
}
