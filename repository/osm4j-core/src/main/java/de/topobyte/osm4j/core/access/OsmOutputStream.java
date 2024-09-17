package de.topobyte.osm4j.core.access;

import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;

public interface OsmOutputStream {
   void write(OsmBounds var1) throws IOException;

   void write(OsmNode var1) throws IOException;

   void write(OsmWay var1) throws IOException;

   void write(OsmRelation var1) throws IOException;

   void complete() throws IOException;
}
