package de.topobyte.osm4j.utils.split;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.nio.file.Path;

public class EntitySplitter extends AbstractEntitySplitter {
   public EntitySplitter(OsmIterator iterator, Path pathNodes, Path pathWays, Path pathRelations, OsmOutputConfig outputConfig) {
      super(iterator, pathNodes, pathWays, pathRelations, outputConfig);
   }

   public void execute() throws IOException {
      this.init();
      this.passBounds();
      this.run();
      this.finish();
   }

   private void run() throws IOException {
      while (this.iterator.hasNext()) {
         EntityContainer entityContainer = (EntityContainer)this.iterator.next();
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
   }
}
