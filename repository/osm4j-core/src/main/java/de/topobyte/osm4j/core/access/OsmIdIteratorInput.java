package de.topobyte.osm4j.core.access;

import java.io.IOException;

public interface OsmIdIteratorInput {
   void close() throws IOException;

   OsmIdIterator getIterator() throws IOException;
}
