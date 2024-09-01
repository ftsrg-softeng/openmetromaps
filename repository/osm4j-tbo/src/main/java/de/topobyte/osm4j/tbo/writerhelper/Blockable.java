package de.topobyte.osm4j.tbo.writerhelper;

import de.topobyte.compactio.CompactWriter;
import java.io.IOException;

public interface Blockable {
   void write(CompactWriter var1) throws IOException;
}
