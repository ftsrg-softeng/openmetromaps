package de.topobyte.osm4j.extra.datatree.nodetree.distribute;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.extra.progress.NodeProgress;
import java.io.IOException;

public abstract class NodeIteratorRunnable implements Runnable {
   private OsmIterator iterator;

   public NodeIteratorRunnable(OsmIterator iterator) {
      this.iterator = iterator;
   }

   @Override
   public void run() {
      NodeProgress counter = new NodeProgress();
      counter.printTimed(1000L);

      try {
         label43:
         while (this.iterator.hasNext()) {
            EntityContainer entityContainer = (EntityContainer)this.iterator.next();
            switch (entityContainer.getType()) {
               case Node:
                  this.handle((OsmNode)entityContainer.getEntity());
                  counter.increment();
                  break;
               case Way:
               case Relation:
                  break label43;
            }
         }

         this.finished();
      } catch (IOException var6) {
         throw new RuntimeException(var6);
      } finally {
         counter.stop();
      }
   }

   protected abstract void handle(OsmNode var1) throws IOException;

   protected abstract void finished() throws IOException;
}
