package de.topobyte.osm4j.extra.threading.write;

import java.io.IOException;

public interface WriteRequest {
   void perform() throws IOException;
}
