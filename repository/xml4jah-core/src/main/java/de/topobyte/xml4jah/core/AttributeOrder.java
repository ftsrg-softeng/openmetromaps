package de.topobyte.xml4jah.core;

import java.util.List;

public class AttributeOrder {
   private List<String> attributes;

   public AttributeOrder(List<String> attributes) {
      this.attributes = attributes;
   }

   public List<String> getAttributes() {
      return this.attributes;
   }

   public void setAttributes(List<String> attributes) {
      this.attributes = attributes;
   }
}
