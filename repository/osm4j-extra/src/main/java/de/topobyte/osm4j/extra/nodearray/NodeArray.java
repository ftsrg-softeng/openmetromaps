package de.topobyte.osm4j.extra.nodearray;

import de.topobyte.osm4j.core.model.iface.OsmNode;
import java.io.Closeable;
import java.io.IOException;

public interface NodeArray extends Closeable {
   boolean supportsContainment();

   boolean contains(long var1) throws IOException;

   OsmNode get(long var1) throws IOException;

   int bytesPerRecord();
}
