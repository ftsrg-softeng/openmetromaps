package de.topobyte.xml4jah.dom;

import de.topobyte.melon.strings.Strings;
import de.topobyte.xml4jah.core.AttributeOrder;
import de.topobyte.xml4jah.core.DocumentWriterConfig;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DocumentWriter {
   private static String LS = System.getProperty("line.separator");
   private OutputStream output;
   private DocumentWriterConfig config;

   public DocumentWriter() {
      this.config = new DocumentWriterConfig();
   }

   public DocumentWriter(DocumentWriterConfig config) {
      this.config = config;
   }

   public void write(Document document, Path output) throws IOException {
      OutputStream os = Files.newOutputStream(output);
      this.write(document, os);
      os.close();
   }

   public void write(Document document, OutputStream output) throws IOException {
      this.output = output;
      if (this.config.isWithDeclaration()) {
         this.write(String.format("<?xml version=\"%s\" encoding=\"%s\"?>", "1.0", "UTF-8"));
         this.write(LS);
      }

      NodeList nodes = document.getChildNodes();

      for (int i = 0; i < nodes.getLength(); i++) {
         Node node = nodes.item(i);
         if (node.getNodeType() != 3) {
            this.write(node, 0);
            if (i < nodes.getLength() - 1) {
               this.write(LS);
            }
         }
      }

      if (this.config.isWithEndingNewline()) {
         this.write(LS);
      }
   }

   private void write(Node node, int depth) throws IOException {
      if (node.getNodeType() == 8) {
         this.writeComment((Comment)node, depth);
      } else {
         NodeList nodes = node.getChildNodes();
         int numChildren = nodes.getLength();
         boolean hasElementsChildren = this.hasElementChildren(nodes);
         if (!hasElementsChildren) {
            if (numChildren == 0) {
               this.writeNoChildren(node, depth);
            } else {
               this.writeWithContent(node, nodes, depth);
            }
         } else {
            this.writeWithChildren(node, nodes, depth);
         }
      }
   }

   private void writeComment(Comment comment, int depth) throws IOException {
      StringBuilder buf = new StringBuilder();
      this.indent(buf, depth);
      buf.append("<!--");
      buf.append(comment.getData());
      buf.append("-->");
      this.write(buf.toString());
   }

   private boolean hasElementChildren(NodeList nodes) {
      for (int i = 0; i < nodes.getLength(); i++) {
         Node child = nodes.item(i);
         if (child.getNodeType() == 1) {
            return true;
         }
      }

      return false;
   }

   private void writeNoChildren(Node node, int depth) throws DOMException, IOException {
      this.writeOpening(node, depth, true);
   }

   private void writeWithContent(Node node, NodeList nodes, int depth) throws IOException {
      this.writeOpening(node, depth, false);

      for (int i = 0; i < nodes.getLength(); i++) {
         Node child = nodes.item(i);
         if (child.getNodeType() == 3) {
            this.writeEscaped(child.getTextContent());
         }
      }

      this.writeEnding(node, 0);
   }

   private void writeWithChildren(Node node, NodeList nodes, int depth) throws IOException {
      this.writeOpening(node, depth, false);
      this.write(LS);

      for (int i = 0; i < nodes.getLength(); i++) {
         Node child = nodes.item(i);
         if (child.getNodeType() == 3) {
            if (this.config.isPreserveEmptyLines()) {
               this.writePreservedNewlines(child);
            }
         } else {
            this.write(child, depth + 1);
            this.write(LS);
         }
      }

      this.writeEnding(node, depth);
   }

   private void writePreservedNewlines(Node child) throws IOException {
      String text = child.getTextContent();
      String[] lines = text.split("\\r?\\n");
      if (lines.length > 2) {
         int more = lines.length - 2;

         for (int k = 0; k < more; k++) {
            this.write(LS);
         }
      }
   }

   private void writeOpening(Node node, int depth, boolean close) throws IOException {
      StringBuilder buf = new StringBuilder();
      this.indent(buf, depth);
      buf.append("<");
      String nodeName = node.getNodeName();
      buf.append(nodeName);
      if (node.getAttributes() != null) {
         AttributeOrder attributeOrder = this.config.getAttributeOrder(nodeName);
         if (node.getAttributes().getLength() != 0) {
            if (attributeOrder == null) {
               this.writeAttributes(buf, node.getAttributes());
            } else {
               this.writeAttributesWithOrder(buf, attributeOrder, node.getAttributes());
            }
         }
      }

      if (close) {
         buf.append("/>");
      } else {
         buf.append(">");
      }

      this.write(buf.toString());
   }

   private void writeEnding(Node node, int depth) throws IOException {
      StringBuilder buf = new StringBuilder();
      this.indent(buf, depth);
      buf.append("</");
      buf.append(node.getNodeName());
      buf.append(">");
      this.write(buf.toString());
   }

   private void writeAttributes(StringBuilder buf, NamedNodeMap attributes) throws IOException {
      for (int i = 0; i < attributes.getLength(); i++) {
         Node item = attributes.item(i);
         buf.append(" ");
         this.appendAttribute(buf, item);
      }
   }

   private void writeAttributesWithOrder(StringBuilder buf, AttributeOrder attributeOrder, NamedNodeMap attributes) {
      Set<String> done = new HashSet<>();

      for (String attribute : attributeOrder.getAttributes()) {
         Node item = attributes.getNamedItem(attribute);
         if (item != null) {
            buf.append(" ");
            this.appendAttribute(buf, item);
            done.add(item.getNodeName());
         }
      }

      for (int i = 0; i < attributes.getLength(); i++) {
         Node item = attributes.item(i);
         if (!done.contains(item.getNodeName())) {
            buf.append(" ");
            this.appendAttribute(buf, item);
         }
      }
   }

   private void appendAttribute(StringBuilder buf, Node item) {
      buf.append(item.getNodeName().toString());
      buf.append("=");
      buf.append('"');
      buf.append(item.getNodeValue().toString());
      buf.append('"');
   }

   private void indent(StringBuilder buf, int depth) {
      Strings.repeat(buf, this.config.getIndent(), depth);
   }

   private void write(String string) throws IOException {
      this.output.write(string.getBytes());
   }

   private void writeEscaped(String string) throws IOException {
      String escaped = Escaping.escapeXml10(string);
      this.output.write(escaped.getBytes());
   }
}
