package de.topobyte.osm4j.utils.buffer;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;

public class RunnableBufferBridge implements StoppableRunnable {
   private final OsmIterator input;
   private final OsmBuffer output;
   private boolean stopped = false;

   public RunnableBufferBridge(OsmIterator input, OsmBuffer output) {
      this.input = input;
      this.output = output;
   }

   @Override
   public void stop() {
      this.stopped = true;
      this.output.setInvalid();
   }

   @Override
   public void run() {
      try {
         if (!this.stopped && this.input.hasBounds()) {
            this.output.write(this.input.getBounds());
         }

         while (!this.stopped && this.input.hasNext()) {
            EntityContainer container = (EntityContainer)this.input.next();
            switch (container.getType()) {
               case Node:
                  this.output.write((OsmNode)container.getEntity());
                  break;
               case Way:
                  this.output.write((OsmWay)container.getEntity());
                  break;
               case Relation:
                  this.output.write((OsmRelation)container.getEntity());
            }
         }

         if (!this.stopped) {
            this.output.complete();
         }
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }
}
