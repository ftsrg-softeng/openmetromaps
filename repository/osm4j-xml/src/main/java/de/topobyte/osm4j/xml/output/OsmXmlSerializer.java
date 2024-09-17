package de.topobyte.osm4j.xml.output;

import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;

public class OsmXmlSerializer {
   private XmlWriter writer;

   public OsmXmlSerializer(boolean printMetadata) {
      this.writer = new XmlWriter("", "  ", "\n", printMetadata);
   }

   public OsmXmlSerializer(String indent1, String indent2, boolean printMetadata) {
      this.writer = new XmlWriter(indent1, indent2, "\n", printMetadata);
   }

   public OsmXmlSerializer(String indent1, String indent2, String newline, boolean printMetadata) {
      this.writer = new XmlWriter(indent1, indent2, newline, printMetadata);
   }

   public String write(OsmBounds bounds) {
      BuilderWriter buf = new BuilderWriter();
      this.writer.write(buf, bounds);
      return buf.toString();
   }

   public void write(StringBuilder buf, OsmBounds bounds) {
      BuilderWriter builder = new BuilderWriter(buf);
      this.writer.write(builder, bounds);
   }

   public String write(OsmNode node) {
      BuilderWriter buf = new BuilderWriter();
      this.writer.write(buf, node);
      return buf.toString();
   }

   public void write(StringBuilder buf, OsmNode node) {
      BuilderWriter builder = new BuilderWriter(buf);
      this.writer.write(builder, node);
   }

   public String write(OsmWay way) {
      BuilderWriter buf = new BuilderWriter();
      this.writer.write(buf, way);
      return buf.toString();
   }

   public void write(StringBuilder buf, OsmWay way) {
      BuilderWriter builder = new BuilderWriter(buf);
      this.writer.write(builder, way);
   }

   public String write(OsmRelation relation) {
      BuilderWriter buf = new BuilderWriter();
      this.writer.write(buf, relation);
      return buf.toString();
   }

   public void write(StringBuilder buf, OsmRelation relation) {
      BuilderWriter builder = new BuilderWriter(buf);
      this.writer.write(builder, relation);
   }
}
