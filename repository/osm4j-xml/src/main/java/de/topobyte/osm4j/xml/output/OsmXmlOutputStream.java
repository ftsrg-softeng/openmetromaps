package de.topobyte.osm4j.xml.output;

import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class OsmXmlOutputStream implements OsmOutputStream {
   private final String newline = "\n";
   private final PrintWriter out;
   private final XmlWriter writer;

   public OsmXmlOutputStream(PrintWriter out, boolean printMetadata) {
      this.out = out;
      this.writer = new XmlWriter("  ", "    ", "\n", printMetadata);
      this.writeHeader();
   }

   public OsmXmlOutputStream(OutputStream os, boolean printMetadata) {
      this(new PrintWriter(os), printMetadata);
   }

   private void writeHeader() {
      this.out.println("<?xml version='1.0' encoding='UTF-8'?>");
      this.out.println("<osm version=\"0.6\">");
   }

   public void complete() {
      this.out.println("</osm>");
      this.out.flush();
   }

   public void write(OsmBounds bounds) throws IOException {
      BuilderWriter buf = new BuilderWriter();
      this.writer.write(buf, bounds);
      buf.append("\n");
      this.out.print(buf.toString());
   }

   public void write(OsmNode node) {
      BuilderWriter buf = new BuilderWriter();
      this.writer.write(buf, node);
      buf.append("\n");
      this.out.print(buf.toString());
   }

   public void write(OsmWay way) {
      BuilderWriter buf = new BuilderWriter();
      this.writer.write(buf, way);
      buf.append("\n");
      this.out.print(buf.toString());
   }

   public void write(OsmRelation relation) {
      BuilderWriter buf = new BuilderWriter();
      this.writer.write(buf, relation);
      buf.append("\n");
      this.out.print(buf.toString());
   }
}
