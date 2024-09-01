package de.topobyte.osm4j.core.access;

import java.io.IOException;

public interface OsmReaderInput {
   void close() throws IOException;

   OsmReader getReader();
}
