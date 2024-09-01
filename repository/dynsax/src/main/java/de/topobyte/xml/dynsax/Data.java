package de.topobyte.xml.dynsax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
   private Element element;
   private Map<String, String> attributes = new HashMap<>();
   private Map<String, Data> singles = new HashMap<>();
   private Map<String, List<Data>> lists = new HashMap<>();
   StringBuilder buffer = new StringBuilder();

   public Data(Element element) {
      this.element = element;
   }

   public Element getElement() {
      return this.element;
   }

   void setSingle(String name, Data data) {
      this.singles.put(name, data);
   }

   void addToList(String name, Data data) {
      List<Data> list = this.lists.get(name);
      if (list == null) {
         list = new ArrayList<>();
         this.lists.put(name, list);
      }

      list.add(data);
   }

   void addAttribute(String name, String value) {
      this.attributes.put(name, value);
   }

   public String getAttribute(String name) {
      return this.attributes.get(name);
   }

   public Data getSingle(String name) {
      return this.singles.get(name);
   }

   public List<Data> getList(String name) {
      return this.lists.get(name);
   }

   public String getText() {
      return this.buffer.toString();
   }
}
