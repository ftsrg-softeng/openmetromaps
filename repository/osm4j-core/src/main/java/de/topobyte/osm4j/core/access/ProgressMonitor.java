package de.topobyte.osm4j.core.access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProgressMonitor {
   static final Logger logger = LoggerFactory.getLogger(ProgressMonitor.class);
   private String title;
   private int nodeCount = 0;
   private int wayCount = 0;
   private int relationCount = 0;

   public ProgressMonitor(String title) {
      this.title = title;
   }

   public void nodeProcessed() {
      this.nodeCount++;
      if (this.nodeCount % 10000 == 0) {
         logger.info(this.title + " done n: " + this.nodeCount);
      }
   }

   public void wayProcessed() {
      this.wayCount++;
      if (this.wayCount % 10000 == 0) {
         logger.info(this.title + " done w: " + this.wayCount);
      }
   }

   public void relationProcessed() {
      this.relationCount++;
      if (this.relationCount % 10000 == 0) {
         logger.info(this.title + " done r: " + this.relationCount);
      }
   }
}
