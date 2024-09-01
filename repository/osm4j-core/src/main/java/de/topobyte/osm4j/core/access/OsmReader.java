package de.topobyte.osm4j.core.access;

public interface OsmReader {
   void setHandler(OsmHandler var1);

   void read() throws OsmInputException;
}
