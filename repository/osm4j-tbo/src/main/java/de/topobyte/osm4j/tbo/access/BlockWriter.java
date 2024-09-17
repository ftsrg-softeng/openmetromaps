package de.topobyte.osm4j.tbo.access;

import de.topobyte.osm4j.tbo.data.FileBlock;
import de.topobyte.osm4j.tbo.data.FileHeader;
import java.io.IOException;

public interface BlockWriter {
   void writeHeader(FileHeader var1) throws IOException;

   void writeBlock(FileBlock var1) throws IOException;
}
