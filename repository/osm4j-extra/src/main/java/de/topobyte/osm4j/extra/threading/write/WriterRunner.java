package de.topobyte.osm4j.extra.threading.write;

import de.topobyte.osm4j.extra.threading.ObjectBuffer;
import de.topobyte.osm4j.utils.buffer.StoppableRunnable;
import java.io.IOException;

public class WriterRunner implements StoppableRunnable {
   private boolean stopped = false;
   private ObjectBuffer<? extends WriteRequest> objectBuffer;

   public WriterRunner(ObjectBuffer<? extends WriteRequest> objectBuffer) {
      this.objectBuffer = objectBuffer;
   }

   public void run() {
      while (!this.stopped && this.objectBuffer.hasNext()) {
         WriteRequest request = this.objectBuffer.next();

         try {
            request.perform();
         } catch (IOException var3) {
            throw new RuntimeException(var3);
         }
      }
   }

   public void stop() {
      this.stopped = true;
   }
}
