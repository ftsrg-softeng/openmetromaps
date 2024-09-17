package de.topobyte.osm4j.xml.dynsax;

import de.topobyte.osm4j.core.access.OsmHandler;
import de.topobyte.osm4j.core.access.OsmInputException;
import de.topobyte.osm4j.core.access.OsmReader;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class OsmXmlReader implements OsmReader {
   private OsmHandler handler;
   private boolean parseMetadata;
   private InputStream inputStream;

   public OsmXmlReader(InputStream inputStream, boolean parseMetadata) {
      this.inputStream = inputStream;
      this.parseMetadata = parseMetadata;
   }

   public OsmXmlReader(File file, boolean parseMetadata) throws FileNotFoundException {
      InputStream fis = new FileInputStream(file);
      this.inputStream = new BufferedInputStream(fis);
      this.parseMetadata = parseMetadata;
   }

   public OsmXmlReader(String pathname, boolean parseMetadata) throws FileNotFoundException {
      this(new File(pathname), parseMetadata);
   }

   public void setHandler(OsmHandler handler) {
      this.handler = handler;
   }

   public void read() throws OsmInputException {
      SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

      SAXParser parser;
      try {
         parser = saxParserFactory.newSAXParser();
      } catch (Exception var7) {
         throw new OsmInputException("error while creating xml parser", var7);
      }

      OsmSaxHandler saxHandler = OsmSaxHandler.createInstance(this.handler, this.parseMetadata);

      try {
         parser.parse(this.inputStream, saxHandler);
      } catch (Exception var6) {
         throw new OsmInputException("error while parsing xml data", var6);
      }

      try {
         this.handler.complete();
      } catch (IOException var5) {
         throw new OsmInputException("error while completing handler", var5);
      }
   }
}
