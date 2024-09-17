package de.topobyte.osm4j.utils.buffer;

import de.topobyte.osm4j.core.model.iface.EntityContainer;

public class EntityBuffer {
   private int position = 0;
   private int size = 0;
   private EntityContainer[] elements;

   public EntityBuffer(int n) {
      this.elements = new EntityContainer[n];
   }

   public void add(EntityContainer e) {
      this.elements[this.size++] = e;
   }

   public boolean isEmpty() {
      return this.position == this.size;
   }

   public int size() {
      return this.size - this.position;
   }

   public EntityContainer remove() {
      EntityContainer container = this.elements[this.position];
      this.elements[this.position++] = null;
      return container;
   }

   public void clear() {
      this.position = 0;
      this.size = 0;
   }
}
