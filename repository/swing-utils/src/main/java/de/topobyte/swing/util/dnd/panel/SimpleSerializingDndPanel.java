package de.topobyte.swing.util.dnd.panel;

import java.awt.datatransfer.DataFlavor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class SimpleSerializingDndPanel<T> extends SimpleDndPanel<T> {
   private static final long serialVersionUID = -7876181099458735610L;

   public SimpleSerializingDndPanel(DataFlavor flavor, T data, boolean isSource, boolean isDestination) {
      super(flavor, data, isSource, isDestination);
   }

   @Override
   public Object getTransferData(T t) {
      logger.debug("returning serialized flavor");
      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      try {
         ObjectOutputStream oos = new ObjectOutputStream(baos);
         oos.writeObject(t);
         oos.close();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      byte[] bytes = baos.toByteArray();
      return new ByteArrayInputStream(bytes);
   }

   @Override
   public boolean importData(Object transferData) {
      try {
         InputStream is = (InputStream)transferData;
         ObjectInputStream ois = new ObjectInputStream(is);
         T data = (T)ois.readObject();
         this.setup(data);
         return true;
      } catch (IOException var5) {
         logger.debug("unable to get transfer data: " + var5.getMessage());
      } catch (ClassNotFoundException var6) {
         logger.debug("unable to read from object input stream: " + var6.getMessage());
      }

      return false;
   }
}
