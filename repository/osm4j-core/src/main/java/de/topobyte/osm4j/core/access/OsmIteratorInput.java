package de.topobyte.osm4j.core.access;

import java.io.IOException;

public interface OsmIteratorInput {
   void close() throws IOException;

   OsmIterator getIterator() throws IOException;
}
