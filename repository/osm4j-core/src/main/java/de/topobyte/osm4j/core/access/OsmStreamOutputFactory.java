package de.topobyte.osm4j.core.access;

import java.io.IOException;

public interface OsmStreamOutputFactory {
   OsmStreamOutput createOutput(boolean var1) throws IOException;
}
