package de.topobyte.osm4j.tbo.access;

import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.core.model.impl.Relation;
import de.topobyte.osm4j.core.model.impl.Way;
import de.topobyte.osm4j.tbo.data.FileHeader;
import java.io.IOException;

public abstract class DefaultHandler implements Handler {
   @Override
   public void handle(FileHeader header) throws IOException {
   }

   @Override
   public void handle(Node node) throws IOException {
   }

   @Override
   public void handle(Way way) throws IOException {
   }

   @Override
   public void handle(Relation relation) throws IOException {
   }

   @Override
   public void complete() throws IOException {
   }
}
