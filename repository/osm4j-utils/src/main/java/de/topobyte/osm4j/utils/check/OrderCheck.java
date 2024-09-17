package de.topobyte.osm4j.utils.check;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import java.io.IOException;

public class OrderCheck {
   private OsmIterator iterator;
   private long nc = -1L;
   private long wc = -1L;
   private long rc = -1L;
   private long nw = 0L;
   private long ww = 0L;
   private long rw = 0L;
   private long nd = 0L;
   private long wd = 0L;
   private long rd = 0L;

   public OrderCheck(OsmIterator iterator) {
      this.iterator = iterator;
   }

   public void run() throws IOException {
      while (this.iterator.hasNext()) {
         EntityContainer container = (EntityContainer)this.iterator.next();
         OsmEntity entity = container.getEntity();
         long id = entity.getId();
         switch (container.getType()) {
            case Node:
               if (id < this.nc) {
                  this.nw++;
               } else if (id == this.nc) {
                  this.nd++;
               }

               this.nc = id;
               break;
            case Way:
               if (id < this.wc) {
                  this.ww++;
               } else if (id == this.wc) {
                  this.wd++;
               }

               this.wc = id;
               break;
            case Relation:
               if (id < this.rc) {
                  this.rw++;
               } else if (id == this.rc) {
                  this.rd++;
               }

               this.rc = id;
         }
      }
   }

   public boolean isAllFine() {
      return !this.hasWrongOrder() && !this.hasDuplicates();
   }

   public boolean hasWrongOrder() {
      return this.nw != 0L || this.ww != 0L || this.rw != 0L;
   }

   public boolean hasDuplicates() {
      return this.nd != 0L || this.wd != 0L || this.rd != 0L;
   }

   public long getNodesWrong() {
      return this.nw;
   }

   public long getWaysWrong() {
      return this.ww;
   }

   public long getRelationsWrong() {
      return this.rw;
   }

   public long getNodeDuplicates() {
      return this.nd;
   }

   public long getWayDuplicates() {
      return this.wd;
   }

   public long getRelationDuplicates() {
      return this.rd;
   }
}
