package de.topobyte.osm4j.extra.idlist;

import java.io.IOException;

public interface IdInput {
   long next() throws IOException;

   void close() throws IOException;
}
