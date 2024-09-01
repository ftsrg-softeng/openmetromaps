package de.topobyte.osm4j.extra.ways;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.extra.threading.Buffer;
import de.topobyte.osm4j.utils.buffer.StoppableRunnable;
import java.io.IOException;

public class RunnableWayBatchBuilder implements StoppableRunnable {
   private int maxWays;
   private int maxWayNodes;
   private final OsmIterator input;
   private Buffer<WayBatch> output;
   private boolean stopped = false;

   public RunnableWayBatchBuilder(OsmIterator input, int maxWays, int maxWayNodes, Buffer<WayBatch> output) {
      this.input = input;
      this.maxWays = maxWays;
      this.maxWayNodes = maxWayNodes;
      this.output = output;
   }

   public void stop() {
      this.stopped = true;
      this.output.setInvalid();
   }

   public void run() {
      try {
         WayBatch batch = new WayBatch(this.maxWays, this.maxWayNodes);

         while (!this.stopped && this.input.hasNext()) {
            EntityContainer container = (EntityContainer)this.input.next();
            switch (container.getType()) {
               case Way:
                  OsmWay way = (OsmWay)container.getEntity();
                  if (way.getNumberOfNodes() != 0) {
                     if (batch.fits(way)) {
                        batch.add(way);
                     } else {
                        System.out.println("does not fit");
                        this.output.write(batch);
                        System.out.println("write returned");
                        batch = new WayBatch(this.maxWays, this.maxWayNodes);
                        batch.add(way);
                     }
                  }
               case Node:
               case Relation:
            }
         }

         if (!this.stopped) {
            if (!batch.getElements().isEmpty()) {
               this.output.write(batch);
            }

            this.output.complete();
         }
      } catch (IOException var4) {
         throw new RuntimeException(var4);
      }
   }
}
