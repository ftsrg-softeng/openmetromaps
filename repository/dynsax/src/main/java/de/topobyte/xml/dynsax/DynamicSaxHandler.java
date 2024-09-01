package de.topobyte.xml.dynsax;

import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class DynamicSaxHandler extends DefaultHandler {
   private Element root;
   private boolean emitRoot;
   private Stack<DynamicSaxHandler.TreePosition> state = new Stack<>();

   public void setRoot(Element root, boolean emitRoot) {
      this.root = root;
      this.emitRoot = emitRoot;
      this.init(root);
   }

   private void init(Element element) {
      element.init();

      for (Child child : element.children) {
         this.init(child.element);
      }
   }

   public abstract void emit(Data var1) throws ParsingException;

   @Override
   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if (this.state.isEmpty()) {
         if (qName.equals(this.root.identifier)) {
            Data data = new Data(this.root);
            this.getAttributes(data, attributes, this.root);
            this.state.push(new DynamicSaxHandler.TreePosition(this.root, null, data));
         }
      } else {
         DynamicSaxHandler.TreePosition top = this.state.peek();
         Element topElement = top.element;
         Child child = topElement.lookup.get(qName);
         if (child == null) {
            return;
         }

         Element childElement = child.element;
         Data data = new Data(childElement);
         this.getAttributes(data, attributes, childElement);
         this.state.push(new DynamicSaxHandler.TreePosition(childElement, child, data));
      }
   }

   private void getAttributes(Data data, Attributes attributes, Element element) {
      for (String name : element.attributes) {
         String value = attributes.getValue(name);
         if (value != null) {
            data.addAttribute(name, value);
         }
      }
   }

   @Override
   public void endElement(String uri, String localName, String qName) throws SAXException {
      DynamicSaxHandler.TreePosition top = this.state.peek();
      Element topElement = top.element;
      Child child = top.child;
      if (topElement.identifier.equals(qName)) {
         this.state.pop();
         if (child == null) {
            if (this.emitRoot) {
               try {
                  this.emit(top.data);
               } catch (ParsingException var10) {
                  throw new SAXException("while emitting root element", var10);
               }
            }

            return;
         }

         Data data = top.data;
         if (child.emit) {
            try {
               this.emit(data);
            } catch (ParsingException var11) {
               throw new SAXException("while emitting element", var11);
            }
         }

         DynamicSaxHandler.TreePosition parent = this.state.peek();
         Data parentData = parent.data;
         if (child.type == ChildType.SINGLE) {
            parentData.setSingle(qName, data);
         } else if (child.type == ChildType.LIST) {
            parentData.addToList(qName, data);
         }
      }
   }

   @Override
   public void characters(char[] ch, int start, int length) throws SAXException {
      DynamicSaxHandler.TreePosition top = this.state.peek();
      Data data = top.data;
      data.buffer.append(ch, start, length);
   }

   private class TreePosition {
      private Element element;
      private Child child;
      private Data data;

      public TreePosition(Element element, Child child, Data data) {
         this.element = element;
         this.child = child;
         this.data = data;
      }
   }
}
