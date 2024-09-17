package de.topobyte.osm4j.core.access.wrapper;

import de.topobyte.osm4j.core.access.OsmElementCounter;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;

public class OsmElementCounterIteratorAdapter implements OsmElementCounter {
   private OsmIterator iterator;
   private int numNodes = 0;
   private int numWays = 0;
   private int numRelations = 0;

   public OsmElementCounterIteratorAdapter(OsmIterator iterator) {
      this.iterator = iterator;
   }

   @Override
   public void count() {
      while (this.iterator.hasNext()) {
         EntityContainer container = this.iterator.next();
         switch (container.getType()) {
            case Node:
               this.numNodes++;
               break;
            case Way:
               this.numWays++;
               break;
            case Relation:
               this.numRelations++;
         }
      }
   }

   @Override
   public long getNumberOfNodes() {
      return (long)this.numNodes;
   }

   @Override
   public long getNumberOfWays() {
      return (long)this.numWays;
   }

   @Override
   public long getNumberOfRelations() {
      return (long)this.numRelations;
   }

   @Override
   public long getTotalNumberOfElements() {
      return (long)(this.numNodes + this.numWays + this.numRelations);
   }

   @Override
   public long getNumberOfElements(EntityType type) {
      switch (type) {
         case Node:
            return (long)this.numNodes;
         case Way:
            return (long)this.numWays;
         case Relation:
            return (long)this.numRelations;
         default:
            return 0L;
      }
   }
}
