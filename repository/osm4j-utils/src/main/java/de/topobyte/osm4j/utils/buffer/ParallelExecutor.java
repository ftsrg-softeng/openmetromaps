package de.topobyte.osm4j.utils.buffer;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ParallelExecutor {
   private Collection<Runnable> tasks;
   private Throwable exceptionFromThread = null;
   private List<StoppableRunnable> stoppables = new ArrayList<>();
   private List<Thread> threads = new ArrayList<>();

   public ParallelExecutor(Collection<Runnable> tasks) {
      this.tasks = tasks;
   }

   public void execute() throws IOException {
      for (Runnable task : this.tasks) {
         this.threads.add(new Thread(task));
         if (task instanceof StoppableRunnable) {
            this.stoppables.add((StoppableRunnable)task);
         }
      }

      for (Thread thread : this.threads) {
         thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
               for (StoppableRunnable stoppable : ParallelExecutor.this.stoppables) {
                  stoppable.stop();
               }

               for (Thread thread : ParallelExecutor.this.threads) {
                  thread.interrupt();
               }

               ParallelExecutor.this.exceptionFromThread = e;
            }
         });
      }

      for (Thread thread : this.threads) {
         thread.start();
      }

      label39:
      while (true) {
         try {
            Iterator i$ = this.threads.iterator();

            while (true) {
               if (!i$.hasNext()) {
                  break label39;
               }

               Thread thread = (Thread)i$.next();
               thread.join();
            }
         } catch (InterruptedException var3) {
         }
      }

      if (this.exceptionFromThread != null) {
         Throwable cause = this.exceptionFromThread.getCause();
         if (cause instanceof IOException) {
            throw (IOException)cause;
         } else {
            throw new RuntimeException(this.exceptionFromThread);
         }
      }
   }
}
