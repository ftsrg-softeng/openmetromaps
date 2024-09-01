package de.topobyte.osm4j.geometry.relation;

import com.slimjars.dist.gnu.trove.set.TLongSet;
import com.slimjars.dist.gnu.trove.set.hash.TLongHashSet;
import com.slimjars.dist.gnu.trove.stack.TLongStack;
import com.slimjars.dist.gnu.trove.stack.array.TLongArrayStack;

class LongSetStack {
   private TLongSet set = new TLongHashSet();
   private TLongStack stack = new TLongArrayStack();

   public void push(long value) {
      this.set.add(value);
      this.stack.push(value);
   }

   public boolean contains(long value) {
      return this.set.contains(value);
   }

   public long pop() {
      long value = this.stack.pop();
      this.set.remove(value);
      return value;
   }

   public long peek() {
      return this.stack.peek();
   }

   public int size() {
      return this.stack.size();
   }

   public boolean isEmpty() {
      return this.set.isEmpty();
   }
}
