package de.topobyte.osm4j.utils.split;

import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.utils.buffer.OsmBuffer;
import java.io.IOException;

class RunnableEntitySplitter implements Runnable {
   private final OsmBuffer buffer;
   private final boolean passNodes;
   private final boolean passWays;
   private final boolean passRelations;
   private final OsmOutputStream oosNodes;
   private final OsmOutputStream oosWays;
   private final OsmOutputStream oosRelations;

   public RunnableEntitySplitter(OsmBuffer buffer, OsmOutputStream oosNodes, OsmOutputStream oosWays, OsmOutputStream oosRelations) {
      this.buffer = buffer;
      this.oosNodes = oosNodes;
      this.oosWays = oosWays;
      this.oosRelations = oosRelations;
      this.passNodes = oosNodes != null;
      this.passWays = oosWays != null;
      this.passRelations = oosRelations != null;
   }

   @Override
   public void run() {
      try {
         while (this.buffer.hasNext()) {
            EntityContainer entityContainer = this.buffer.next();
            switch (entityContainer.getType()) {
               case Node:
                  if (this.passNodes) {
                     this.oosNodes.write((OsmNode)entityContainer.getEntity());
                  }
                  break;
               case Way:
                  if (this.passWays) {
                     this.oosWays.write((OsmWay)entityContainer.getEntity());
                     break;
                  } else {
                     if (this.passRelations) {
                        break;
                     }

                     return;
                  }
               case Relation:
                  if (!this.passRelations) {
                     return;
                  }

                  this.oosRelations.write((OsmRelation)entityContainer.getEntity());
            }
         }
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }
}
