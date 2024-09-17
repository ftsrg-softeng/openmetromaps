package com.infomatiq.jsi.rtree;

import com.slimjars.dist.gnu.trove.list.array.TFloatArrayList;
import com.slimjars.dist.gnu.trove.list.array.TIntArrayList;
import com.slimjars.dist.gnu.trove.procedure.TIntProcedure;

public class SortedList {
   private static final int DEFAULT_PREFERRED_MAXIMUM_SIZE = 10;
   private int preferredMaximumSize = 1;
   private TIntArrayList ids = null;
   private TFloatArrayList priorities = null;

   public void init(int preferredMaximumSize) {
      this.preferredMaximumSize = preferredMaximumSize;
      this.ids.clear(preferredMaximumSize);
      this.priorities.clear(preferredMaximumSize);
   }

   public void reset() {
      this.ids.reset();
      this.priorities.reset();
   }

   public SortedList() {
      this.ids = new TIntArrayList(10);
      this.priorities = new TFloatArrayList(10);
   }

   public void add(int id, float priority) {
      float lowestPriority = Float.NEGATIVE_INFINITY;
      if (this.priorities.size() > 0) {
         lowestPriority = this.priorities.get(this.priorities.size() - 1);
      }

      if (priority != lowestPriority && (!(priority < lowestPriority) || this.ids.size() >= this.preferredMaximumSize)) {
         if (priority > lowestPriority) {
            if (this.ids.size() >= this.preferredMaximumSize) {
               int lowestPriorityIndex = this.ids.size() - 1;

               while (lowestPriorityIndex - 1 >= 0 && this.priorities.get(lowestPriorityIndex - 1) == lowestPriority) {
                  lowestPriorityIndex--;
               }

               if (lowestPriorityIndex >= this.preferredMaximumSize - 1) {
                  this.ids.remove(lowestPriorityIndex, this.ids.size() - lowestPriorityIndex);
                  this.priorities.remove(lowestPriorityIndex, this.priorities.size() - lowestPriorityIndex);
               }
            }

            int insertPosition = this.ids.size();

            while (insertPosition - 1 >= 0 && priority > this.priorities.get(insertPosition - 1)) {
               insertPosition--;
            }

            this.ids.insert(insertPosition, id);
            this.priorities.insert(insertPosition, priority);
         }
      } else {
         this.ids.add(id);
         this.priorities.add(priority);
      }
   }

   public float getLowestPriority() {
      float lowestPriority = Float.NEGATIVE_INFINITY;
      if (this.priorities.size() >= this.preferredMaximumSize) {
         lowestPriority = this.priorities.get(this.priorities.size() - 1);
      }

      return lowestPriority;
   }

   public void forEachId(TIntProcedure v) {
      int i = 0;

      while (i < this.ids.size() && v.execute(this.ids.get(i))) {
         i++;
      }
   }

   public int[] toNativeArray() {
      return this.ids.toArray();
   }
}
