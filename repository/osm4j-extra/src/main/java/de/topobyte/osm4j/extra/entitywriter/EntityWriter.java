package de.topobyte.osm4j.extra.entitywriter;

import de.topobyte.osm4j.core.model.iface.OsmEntity;
import java.io.IOException;

public interface EntityWriter {
   void write(OsmEntity var1) throws IOException;
}
