package de.topobyte.osm4j.extra.datatree.output;

import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.extra.datatree.Node;
import java.io.IOException;

public interface DataTreeOutputFactory {
   OsmStreamOutput init(Node var1, boolean var2) throws IOException;
}
