package de.topobyte.osm4j.extra.threading.write;

import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;

public class WayWriteRequest extends AbstractWriteRequest<OsmWay> {
   public WayWriteRequest(OsmWay way, OsmOutputStream output) {
      super(way, output);
   }

   @Override
   public void perform() throws IOException {
      this.output.write(this.object);
   }
}
