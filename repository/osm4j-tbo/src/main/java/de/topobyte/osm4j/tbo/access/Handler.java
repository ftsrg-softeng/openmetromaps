package de.topobyte.osm4j.tbo.access;

import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.core.model.impl.Relation;
import de.topobyte.osm4j.core.model.impl.Way;
import de.topobyte.osm4j.tbo.data.FileHeader;
import java.io.IOException;

public interface Handler {
   void handle(FileHeader var1) throws IOException;

   void handle(Node var1) throws IOException;

   void handle(Way var1) throws IOException;

   void handle(Relation var1) throws IOException;

   void complete() throws IOException;
}
