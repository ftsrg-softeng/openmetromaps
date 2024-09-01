package de.topobyte.xml.dynsax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Element {
   String identifier;
   boolean hasText;
   List<Child> children = new ArrayList<>();
   Map<String, Child> lookup = null;
   List<String> attributes = new ArrayList<>();

   public Element(String identifier, boolean hasText) {
      this.identifier = identifier;
      this.hasText = hasText;
   }

   public void addChild(Child child) {
      this.children.add(child);
   }

   public void init() {
      this.lookup = new HashMap<>();

      for (Child child : this.children) {
         this.lookup.put(child.element.identifier, child);
      }
   }

   public void addAttribute(String attribute) {
      this.attributes.add(attribute);
   }
}
