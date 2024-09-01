package de.topobyte.osm4j.extra.threading;

import de.topobyte.osm4j.utils.buffer.StoppableRunnable;

public abstract class StatusRunnable implements StoppableRunnable {
   private boolean running = true;
   private long start;
   private long last;
   private long interval;

   public StatusRunnable(long interval) {
      this.interval = interval;
   }

   public void run() {
      this.start = System.currentTimeMillis();
      this.last = this.start;

      while (this.running) {
         long current = System.currentTimeMillis();
         long past = current - this.last;
         if (past >= this.interval) {
            this.printStatus();
            this.last = current;
         }

         long remaining = this.interval - (current - this.last);

         try {
            Thread.sleep(remaining);
         } catch (InterruptedException var8) {
         }
      }
   }

   public void stop() {
      this.running = false;
   }

   protected abstract void printStatus();
}
