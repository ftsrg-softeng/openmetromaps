package de.topobyte.osm4j.extra.threading;

import java.io.IOException;

public interface Task {
   void execute() throws IOException;
}
