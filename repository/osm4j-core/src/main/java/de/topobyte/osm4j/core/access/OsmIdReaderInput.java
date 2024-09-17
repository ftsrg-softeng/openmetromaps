package de.topobyte.osm4j.core.access;

import java.io.IOException;

public interface OsmIdReaderInput {
   void close() throws IOException;

   OsmIdReader getReader();
}
