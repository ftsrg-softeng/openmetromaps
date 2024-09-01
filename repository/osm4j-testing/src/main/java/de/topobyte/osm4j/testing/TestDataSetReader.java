package de.topobyte.osm4j.testing;

import de.topobyte.osm4j.core.access.OsmHandler;
import de.topobyte.osm4j.core.access.OsmInputException;
import de.topobyte.osm4j.core.access.OsmReader;
import de.topobyte.osm4j.testing.model.TestNode;
import de.topobyte.osm4j.testing.model.TestRelation;
import de.topobyte.osm4j.testing.model.TestWay;
import java.io.IOException;

public class TestDataSetReader implements OsmReader {
   private TestDataSet data;
   private OsmHandler handler;

   public TestDataSetReader(TestDataSet data) {
      this.data = data;
   }

   public void setHandler(OsmHandler handler) {
      this.handler = handler;
   }

   public void read() throws OsmInputException {
      try {
         if (this.data.hasBounds()) {
            this.handler.handle(this.data.getBounds());
         }

         for (TestNode node : this.data.getNodes()) {
            this.handler.handle(node);
         }

         for (TestWay way : this.data.getWays()) {
            this.handler.handle(way);
         }

         for (TestRelation relation : this.data.getRelations()) {
            this.handler.handle(relation);
         }

         this.handler.complete();
      } catch (IOException var3) {
         throw new OsmInputException(var3);
      }
   }
}
