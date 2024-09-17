package de.topobyte.osm4j.extra.idbboxlist;

import java.io.IOException;

public interface IdBboxInput {
   IdBboxEntry next() throws IOException;

   void close() throws IOException;
}
