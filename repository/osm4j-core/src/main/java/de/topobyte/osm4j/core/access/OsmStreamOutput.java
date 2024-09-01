package de.topobyte.osm4j.core.access;

import java.io.IOException;

public interface OsmStreamOutput {
   void close() throws IOException;

   OsmOutputStream getOsmOutput();
}
