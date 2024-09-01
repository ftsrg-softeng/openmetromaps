package de.topobyte.osm4j.core.access;

import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;

public interface OsmHandler {
   void handle(OsmBounds var1) throws IOException;

   void handle(OsmNode var1) throws IOException;

   void handle(OsmWay var1) throws IOException;

   void handle(OsmRelation var1) throws IOException;

   void complete() throws IOException;
}
