package de.topobyte.osm4j.extra.entitywriter;

import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import java.io.IOException;

public class NodeWriter implements EntityWriter {
   private OsmOutputStream output;

   public NodeWriter(OsmOutputStream output) {
      this.output = output;
   }

   @Override
   public void write(OsmEntity entity) throws IOException {
      this.output.write((OsmNode)entity);
   }
}
