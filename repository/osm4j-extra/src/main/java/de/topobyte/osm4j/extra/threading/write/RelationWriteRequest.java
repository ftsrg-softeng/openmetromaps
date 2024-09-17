package de.topobyte.osm4j.extra.threading.write;

import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import java.io.IOException;

public class RelationWriteRequest extends AbstractWriteRequest<OsmRelation> {
   public RelationWriteRequest(OsmRelation relation, OsmOutputStream output) {
      super(relation, output);
   }

   @Override
   public void perform() throws IOException {
      this.output.write(this.object);
   }
}
