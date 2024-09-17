package de.topobyte.osm4j.core.access;

import java.io.IOException;

public interface OsmIteratorInputFactory {
   OsmIteratorInput createIterator(boolean var1, boolean var2) throws IOException;

   OsmIdIteratorInput createIdIterator() throws IOException;
}
