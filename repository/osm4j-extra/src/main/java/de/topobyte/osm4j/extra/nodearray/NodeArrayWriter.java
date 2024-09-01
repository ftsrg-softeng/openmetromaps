package de.topobyte.osm4j.extra.nodearray;

import de.topobyte.osm4j.core.model.iface.OsmNode;
import java.io.IOException;

public interface NodeArrayWriter {
   void write(OsmNode var1) throws IOException;

   void finish() throws IOException;
}
