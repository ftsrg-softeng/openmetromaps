package de.topobyte.osm4j.core.access;

public interface OsmIdReader {
   void setIdHandler(OsmIdHandler var1);

   void read() throws OsmInputException;
}
