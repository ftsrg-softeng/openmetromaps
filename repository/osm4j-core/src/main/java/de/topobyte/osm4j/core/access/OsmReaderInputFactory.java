package de.topobyte.osm4j.core.access;

import java.io.IOException;

public interface OsmReaderInputFactory {
   OsmReaderInput createReader(boolean var1, boolean var2) throws IOException;

   OsmIdReaderInput createIdReader() throws IOException;
}
