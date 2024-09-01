package de.topobyte.osm4j.core.access.wrapper;

import de.topobyte.osm4j.core.access.OsmHandler;
import de.topobyte.osm4j.core.access.OsmIdHandler;
import de.topobyte.osm4j.core.access.OsmIdReader;
import de.topobyte.osm4j.core.access.OsmInputException;
import de.topobyte.osm4j.core.access.OsmReader;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;

public class OsmIdReaderAdapter implements OsmIdReader, OsmHandler {
   private OsmReader reader;
   private OsmIdHandler handler;

   public OsmIdReaderAdapter(OsmReader reader) {
      this.reader = reader;
      reader.setHandler(this);
   }

   @Override
   public void setIdHandler(OsmIdHandler handler) {
      this.handler = handler;
   }

   @Override
   public void read() throws OsmInputException {
      this.reader.read();
   }

   @Override
   public void handle(OsmBounds bounds) throws IOException {
      this.handler.handle(bounds);
   }

   @Override
   public void handle(OsmNode node) throws IOException {
      this.handler.handleNode(node.getId());
   }

   @Override
   public void handle(OsmWay way) throws IOException {
      this.handler.handleWay(way.getId());
   }

   @Override
   public void handle(OsmRelation relation) throws IOException {
      this.handler.handleRelation(relation.getId());
   }

   @Override
   public void complete() throws IOException {
      this.handler.complete();
   }
}
