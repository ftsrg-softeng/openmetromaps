package de.topobyte.osm4j.extra.entitywriter;

import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import java.io.IOException;

public class RelationWriter implements EntityWriter {
   private OsmOutputStream output;

   public RelationWriter(OsmOutputStream output) {
      this.output = output;
   }

   @Override
   public void write(OsmEntity entity) throws IOException {
      this.output.write((OsmRelation)entity);
   }
}
