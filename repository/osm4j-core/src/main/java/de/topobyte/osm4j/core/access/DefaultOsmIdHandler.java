package de.topobyte.osm4j.core.access;

import de.topobyte.osm4j.core.model.iface.OsmBounds;
import java.io.IOException;

public abstract class DefaultOsmIdHandler implements OsmIdHandler {
   @Override
   public void handle(OsmBounds bounds) throws IOException {
   }

   @Override
   public void handleNode(long id) throws IOException {
   }

   @Override
   public void handleWay(long id) throws IOException {
   }

   @Override
   public void handleRelation(long id) throws IOException {
   }

   @Override
   public void complete() throws IOException {
   }
}
