package de.topobyte.xml4jah.core;

import java.util.HashMap;
import java.util.Map;

public class DocumentWriterConfig {
   private String indent = "  ";
   private boolean withDeclaration = true;
   private boolean withEndingNewline = true;
   private boolean preserveEmptyLines = false;
   private Map<String, AttributeOrder> attributeOrders = new HashMap<>();

   public String getIndent() {
      return this.indent;
   }

   public void setIndent(String indent) {
      this.indent = indent;
   }

   public boolean isWithDeclaration() {
      return this.withDeclaration;
   }

   public void setWithDeclaration(boolean withDeclaration) {
      this.withDeclaration = withDeclaration;
   }

   public boolean isWithEndingNewline() {
      return this.withEndingNewline;
   }

   public void setWithEndingNewline(boolean withEndingNewline) {
      this.withEndingNewline = withEndingNewline;
   }

   public boolean isPreserveEmptyLines() {
      return this.preserveEmptyLines;
   }

   public void setPreserveEmptyLines(boolean preserveEmptyLines) {
      this.preserveEmptyLines = preserveEmptyLines;
   }

   public AttributeOrder getAttributeOrder(String nodeName) {
      return this.attributeOrders.get(nodeName);
   }

   public void setAttributeOrder(String nodeName, AttributeOrder order) {
      this.attributeOrders.put(nodeName, order);
   }
}
