package de.topobyte.xml.dynsax;

public class Child {
   Element element;
   ChildType type;
   boolean emit;

   public Child(Element element, ChildType type, boolean emit) {
      this.element = element;
      this.type = type;
      this.emit = emit;
   }
}
