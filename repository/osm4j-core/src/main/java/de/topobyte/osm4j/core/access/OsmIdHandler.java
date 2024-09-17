package de.topobyte.osm4j.core.access;

import de.topobyte.osm4j.core.model.iface.OsmBounds;
import java.io.IOException;

public interface OsmIdHandler {
   void handle(OsmBounds var1) throws IOException;

   void handleNode(long var1) throws IOException;

   void handleWay(long var1) throws IOException;

   void handleRelation(long var1) throws IOException;

   void complete() throws IOException;
}
