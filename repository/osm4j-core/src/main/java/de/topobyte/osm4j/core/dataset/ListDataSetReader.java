package de.topobyte.osm4j.core.dataset;

import de.topobyte.osm4j.core.access.OsmHandler;
import de.topobyte.osm4j.core.access.OsmInputException;
import de.topobyte.osm4j.core.access.OsmReader;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;

public class ListDataSetReader implements OsmReader {
   private InMemoryListDataSet data;
   private OsmHandler handler;

   public ListDataSetReader(InMemoryListDataSet data) {
      this.data = data;
   }

   @Override
   public void setHandler(OsmHandler handler) {
      this.handler = handler;
   }

   @Override
   public void read() throws OsmInputException {
      try {
         if (this.data.hasBounds()) {
            this.handler.handle(this.data.getBounds());
         }

         for (OsmNode node : this.data.getNodes()) {
            this.handler.handle(node);
         }

         for (OsmWay way : this.data.getWays()) {
            this.handler.handle(way);
         }

         for (OsmRelation relation : this.data.getRelations()) {
            this.handler.handle(relation);
         }

         this.handler.complete();
      } catch (IOException var3) {
         throw new OsmInputException(var3);
      }
   }
}
