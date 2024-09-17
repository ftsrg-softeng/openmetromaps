package de.topobyte.osm4j.geometry.relation;

import com.slimjars.dist.gnu.trove.list.TLongList;
import com.slimjars.dist.gnu.trove.list.array.TLongArrayList;
import com.slimjars.dist.gnu.trove.set.TLongSet;
import com.slimjars.dist.gnu.trove.set.hash.TLongHashSet;
import java.util.ArrayList;
import java.util.List;

public class ChainOfNodes {
   private TLongList nodeIds;

   public ChainOfNodes(TLongList nodeIds) {
      this.nodeIds = nodeIds;
   }

   public TLongList getNodes() {
      return this.nodeIds;
   }

   public boolean isClosed() {
      if (this.nodeIds.isEmpty()) {
         return true;
      } else {
         int size = this.nodeIds.size();
         return this.nodeIds.get(0) == this.nodeIds.get(size - 1);
      }
   }

   public int getLength() {
      return this.nodeIds.size();
   }

   public boolean isValidRing() {
      if (!this.isClosed()) {
         return false;
      } else {
         int size = this.nodeIds.size();
         return size == 0 || size >= 4;
      }
   }

   public boolean hasNodeIntersections() {
      int size = this.nodeIds.size();
      TLongSet before = new TLongHashSet();
      before.add(this.nodeIds.get(0));

      for (int i = 1; i < size - 1; i++) {
         long id = this.nodeIds.get(i);
         if (before.contains(id)) {
            return true;
         }

         before.add(id);
      }

      return this.isClosed() ? false : before.contains(this.nodeIds.get(size - 1));
   }

   public List<ChainOfNodes> resolveNodeIntersections() {
      List<ChainOfNodes> results = new ArrayList<>();
      LongSetStack stack = new LongSetStack();
      int size = this.nodeIds.size();

      for (int i = 0; i < size; i++) {
         long id = this.nodeIds.get(i);
         if (!stack.contains(id)) {
            stack.push(id);
         } else {
            TLongList list = new TLongArrayList();
            list.add(id);

            long popped;
            do {
               popped = stack.pop();
               list.add(popped);
            } while (popped != id);

            results.add(new ChainOfNodes(list));
            stack.push(id);
         }
      }

      if (stack.size() > 1) {
         TLongList list = new TLongArrayList();

         while (!stack.isEmpty()) {
            list.add(stack.pop());
         }

         results.add(new ChainOfNodes(list));
      }

      return results;
   }
}
