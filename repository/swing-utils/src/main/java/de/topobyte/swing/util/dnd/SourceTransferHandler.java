package de.topobyte.swing.util.dnd;

import java.awt.datatransfer.Transferable;
import javax.swing.JComponent;

public interface SourceTransferHandler {
   int getSourceActions(JComponent var1);

   Transferable createTransferable(JComponent var1);

   void exportDone(JComponent var1, Transferable var2, int var3);
}
