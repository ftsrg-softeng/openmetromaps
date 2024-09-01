package de.topobyte.osm4j.extra.threading.write;

import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import java.io.IOException;

public class NodeWriteRequest extends AbstractWriteRequest<OsmNode> {
   public NodeWriteRequest(OsmNode node, OsmOutputStream output) {
      super(node, output);
   }

   @Override
   public void perform() throws IOException {
      this.output.write(this.object);
   }
}
