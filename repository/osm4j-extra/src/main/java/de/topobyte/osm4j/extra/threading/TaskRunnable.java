package de.topobyte.osm4j.extra.threading;

import java.io.IOException;

public class TaskRunnable implements Runnable {
   private Task task;

   public TaskRunnable(Task task) {
      this.task = task;
   }

   @Override
   public void run() {
      try {
         this.task.execute();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }
}
