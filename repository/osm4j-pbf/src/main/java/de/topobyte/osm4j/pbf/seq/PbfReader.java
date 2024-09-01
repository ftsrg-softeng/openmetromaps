package de.topobyte.osm4j.pbf.seq;

import de.topobyte.osm4j.core.access.OsmHandler;
import de.topobyte.osm4j.core.access.OsmInputException;
import de.topobyte.osm4j.core.access.OsmReader;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class PbfReader implements OsmReader {
   private OsmHandler handler;
   private boolean parseMetadata;
   private InputStream input;

   public PbfReader(InputStream input, boolean parseMetadata) {
      this.input = input;
      this.parseMetadata = parseMetadata;
   }

   public PbfReader(File file, boolean parseMetadata) throws FileNotFoundException {
      InputStream fis = new FileInputStream(file);
      this.input = new BufferedInputStream(fis);
      this.parseMetadata = parseMetadata;
   }

   public PbfReader(String pathname, boolean parseMetadata) throws FileNotFoundException {
      this(new File(pathname), parseMetadata);
   }

   public void setHandler(OsmHandler handler) {
      this.handler = handler;
   }

   public void read() throws OsmInputException {
      PbfParser parser = new PbfParser(this.handler, this.parseMetadata);

      try {
         parser.parse(this.input);
      } catch (IOException var4) {
         throw new OsmInputException("error while parsing data", var4);
      }

      try {
         this.handler.complete();
      } catch (IOException var3) {
         throw new OsmInputException("error while completing handler", var3);
      }
   }
}
