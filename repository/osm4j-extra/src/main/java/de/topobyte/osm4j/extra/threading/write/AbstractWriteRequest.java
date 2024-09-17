package de.topobyte.osm4j.extra.threading.write;

import de.topobyte.osm4j.core.access.OsmOutputStream;

public abstract class AbstractWriteRequest<T> implements WriteRequest {
   protected T object;
   protected OsmOutputStream output;

   public AbstractWriteRequest(T node, OsmOutputStream output) {
      this.object = node;
      this.output = output;
   }
}
