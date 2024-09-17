package de.topobyte.osm4j.core.access;

import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;

public class OsmOutputStreamHandler implements OsmHandler {
   private final OsmOutputStream osmOutput;

   public OsmOutputStreamHandler(OsmOutputStream osmOutput) {
      this.osmOutput = osmOutput;
   }

   @Override
   public void handle(OsmBounds bounds) throws IOException {
      this.osmOutput.write(bounds);
   }

   @Override
   public void handle(OsmNode node) throws IOException {
      this.osmOutput.write(node);
   }

   @Override
   public void handle(OsmWay way) throws IOException {
      this.osmOutput.write(way);
   }

   @Override
   public void handle(OsmRelation relation) throws IOException {
      this.osmOutput.write(relation);
   }

   @Override
   public void complete() throws IOException {
      this.osmOutput.complete();
   }
}
