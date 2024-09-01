package de.topobyte.osm4j.extra.threading.write;

import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.extra.entitywriter.EntityWriter;
import java.io.IOException;

public class EntityWriterWriteRequest implements WriteRequest {
   private EntityWriter writer;
   private OsmEntity element;

   public EntityWriterWriteRequest(EntityWriter writer, OsmEntity element) {
      this.writer = writer;
      this.element = element;
   }

   @Override
   public void perform() throws IOException {
      this.writer.write(this.element);
   }
}
