package de.topobyte.osm4j.xml.output;

import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmMetadata;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.translate.CharSequenceTranslator;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

class XmlWriter {
   private final String indent1;
   private final String indent2;
   private final String newline;
   private final boolean printMetadata;
   private DecimalFormat f = new DecimalFormat("0.#######;-0.#######", new DecimalFormatSymbols(Locale.US));
   private DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
   private CharSequenceTranslator escaper = StringEscapeUtils.ESCAPE_XML11;
   private String templateBounds = "<bounds minlon=\"%f\" minlat=\"%f\" maxlon=\"%f\" maxlat=\"%f\"/>";

   public XmlWriter(String indent1, String indent2, String newline, boolean printMetadata) {
      this.indent1 = indent1;
      this.indent2 = indent2;
      this.newline = newline;
      this.printMetadata = printMetadata;
   }

   public void write(BuilderWriter buf, OsmBounds bounds) {
      buf.append(this.indent1);
      buf.append(String.format(this.templateBounds, bounds.getLeft(), bounds.getBottom(), bounds.getRight(), bounds.getTop()));
   }

   public void write(BuilderWriter buf, OsmNode node) {
      buf.append(this.indent1);
      buf.append("<node id=\"");
      buf.append(node.getId());
      buf.append("\"");
      buf.append(" lat=\"");
      buf.append(this.f.format(node.getLatitude()));
      buf.append("\"");
      buf.append(" lon=\"");
      buf.append(this.f.format(node.getLongitude()));
      buf.append("\"");
      if (this.printMetadata) {
         OsmMetadata metadata = node.getMetadata();
         this.printMetadata(buf, metadata);
      }

      if (node.getNumberOfTags() == 0) {
         buf.append("/>");
      } else {
         buf.append(">");
         buf.append(this.newline);
         this.printTags(buf, node);
         buf.append(this.indent1);
         buf.append("</node>");
      }
   }

   public void write(BuilderWriter buf, OsmWay way) {
      buf.append(this.indent1);
      buf.append("<way id=\"");
      buf.append(way.getId());
      buf.append("\"");
      if (this.printMetadata) {
         OsmMetadata metadata = way.getMetadata();
         this.printMetadata(buf, metadata);
      }

      if (way.getNumberOfTags() == 0 && way.getNumberOfNodes() == 0) {
         buf.append("/>");
      } else {
         buf.append(">");
         buf.append(this.newline);

         for (int i = 0; i < way.getNumberOfNodes(); i++) {
            long nodeId = way.getNodeId(i);
            buf.append(this.indent2);
            buf.append("<nd ref=\"");
            buf.append(nodeId);
            buf.append("\"/>");
            buf.append(this.newline);
         }

         this.printTags(buf, way);
         buf.append(this.indent1);
         buf.append("</way>");
      }
   }

   public void write(BuilderWriter buf, OsmRelation relation) {
      buf.append(this.indent1);
      buf.append("<relation id=\"");
      buf.append(relation.getId());
      buf.append("\"");
      if (this.printMetadata) {
         OsmMetadata metadata = relation.getMetadata();
         this.printMetadata(buf, metadata);
      }

      if (relation.getNumberOfTags() == 0 && relation.getNumberOfMembers() == 0) {
         buf.append("/>");
      } else {
         buf.append(">");
         buf.append(this.newline);

         for (int i = 0; i < relation.getNumberOfMembers(); i++) {
            OsmRelationMember member = relation.getMember(i);
            EntityType type = member.getType();
            String t = type == EntityType.Node ? "node" : (type == EntityType.Way ? "way" : "relation");
            buf.append(this.indent2);
            buf.append("<member type=\"");
            buf.append(t);
            buf.append("\" ref=\"");
            buf.append(member.getId());
            buf.append("\" role=\"");
            this.escape(buf, member.getRole());
            buf.append("\"/>");
            buf.append(this.newline);
         }

         this.printTags(buf, relation);
         buf.append(this.indent1);
         buf.append("</relation>");
      }
   }

   private void printMetadata(BuilderWriter buf, OsmMetadata metadata) {
      if (metadata != null) {
         buf.append(" version=\"");
         buf.append(metadata.getVersion());
         buf.append("\"");
         buf.append(" timestamp=\"");
         buf.append(this.formatter.print(metadata.getTimestamp()));
         buf.append("\"");
         if (metadata.getUid() >= 0L) {
            buf.append(" uid=\"");
            buf.append(metadata.getUid());
            buf.append("\"");
            String user = metadata.getUser();
            buf.append(" user=\"");
            this.escape(buf, user);
            buf.append("\"");
         }

         buf.append(" changeset=\"");
         buf.append(metadata.getChangeset());
         buf.append("\"");
         if (!metadata.isVisible()) {
            buf.append(" visible=\"false\"");
         }
      }
   }

   private void printTags(BuilderWriter buf, OsmEntity entity) {
      for (int i = 0; i < entity.getNumberOfTags(); i++) {
         OsmTag tag = entity.getTag(i);
         buf.append(this.indent2);
         buf.append("<tag k=\"");
         this.escape(buf, tag.getKey());
         buf.append("\" v=\"");
         this.escape(buf, tag.getValue());
         buf.append("\"/>");
         buf.append(this.newline);
      }
   }

   private void escape(BuilderWriter buf, String string) {
      try {
         this.escaper.translate(string, buf);
      } catch (IOException var4) {
         throw new RuntimeException(var4);
      }
   }
}
