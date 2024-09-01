package de.topobyte.osm4j.core.access;

import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;

public abstract class DefaultOsmHandler implements OsmHandler {
   @Override
   public void handle(OsmBounds bounds) throws IOException {
   }

   @Override
   public void handle(OsmNode node) throws IOException {
   }

   @Override
   public void handle(OsmWay way) throws IOException {
   }

   @Override
   public void handle(OsmRelation relation) throws IOException {
   }

   @Override
   public void complete() throws IOException {
   }
}
