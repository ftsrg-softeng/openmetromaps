package de.topobyte.osm4j.core.access;

import java.io.IOException;
import java.io.OutputStream;

public class OsmOutputStreamStreamOutput implements OsmStreamOutput {
   private OutputStream output;
   private OsmOutputStream osmOutput;

   public OsmOutputStreamStreamOutput(OutputStream output, OsmOutputStream osmOutput) {
      this.output = output;
      this.osmOutput = osmOutput;
   }

   public OutputStream getOutputStream() {
      return this.output;
   }

   @Override
   public OsmOutputStream getOsmOutput() {
      return this.osmOutput;
   }

   @Override
   public void close() throws IOException {
      this.output.close();
   }
}
