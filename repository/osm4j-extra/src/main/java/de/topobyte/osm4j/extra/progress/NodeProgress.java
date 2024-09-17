package de.topobyte.osm4j.extra.progress;

import java.text.NumberFormat;
import java.util.Locale;

public class NodeProgress {
   private long num = 0L;
   private long start;
   private long last;
   private boolean running = true;

   public void increment() {
      this.num++;
   }

   public void increment(long n) {
      this.num += n;
   }

   public long getCount() {
      return this.num;
   }

   public void print(long time) {
      double seconds = (double)(time / 1000L);
      long rSeconds = Math.round(seconds);
      long perSecond = Math.round((double)this.num / seconds);
      NumberFormat format = NumberFormat.getInstance(Locale.US);
      format.setGroupingUsed(true);
      System.out.println(String.format("%ds: processed: %s per second: %s", rSeconds, format.format(this.num), format.format(perSecond)));
   }

   public void printTimed(final long interval) {
      this.start = System.currentTimeMillis();
      this.last = this.start;
      new Thread(new Runnable() {
         @Override
         public void run() {
            while (NodeProgress.this.running) {
               long current = System.currentTimeMillis();
               long past = current - NodeProgress.this.last;
               if (past >= interval) {
                  NodeProgress.this.print(current - NodeProgress.this.start);
                  NodeProgress.this.last = current;
               }

               long remaining = interval - (current - NodeProgress.this.last);

               try {
                  Thread.sleep(remaining);
               } catch (InterruptedException var8) {
               }
            }
         }
      }).start();
   }

   public void stop() {
      this.running = false;
   }
}
