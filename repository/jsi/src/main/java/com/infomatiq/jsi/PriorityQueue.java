package com.infomatiq.jsi;

import com.slimjars.dist.gnu.trove.list.array.TFloatArrayList;
import com.slimjars.dist.gnu.trove.list.array.TIntArrayList;

public class PriorityQueue {
   public static final boolean SORT_ORDER_ASCENDING = true;
   public static final boolean SORT_ORDER_DESCENDING = false;
   private TIntArrayList values = null;
   private TFloatArrayList priorities = null;
   private boolean sortOrder = true;
   private static boolean INTERNAL_CONSISTENCY_CHECKING = false;

   public PriorityQueue(boolean sortOrder) {
      this(sortOrder, 10);
   }

   public PriorityQueue(boolean sortOrder, int initialCapacity) {
      this.sortOrder = sortOrder;
      this.values = new TIntArrayList(initialCapacity);
      this.priorities = new TFloatArrayList(initialCapacity);
   }

   private boolean sortsEarlierThan(float p1, float p2) {
      return this.sortOrder ? p1 < p2 : p2 < p1;
   }

   public void insert(int value, float priority) {
      this.values.add(value);
      this.priorities.add(priority);
      this.promote(this.values.size() - 1, value, priority);
   }

   private void promote(int index, int value, float priority) {
      while (index > 0) {
         int parentIndex = (index - 1) / 2;
         float parentPriority = this.priorities.get(parentIndex);
         if (!this.sortsEarlierThan(parentPriority, priority)) {
            this.values.set(index, this.values.get(parentIndex));
            this.priorities.set(index, parentPriority);
            index = parentIndex;
            continue;
         }
         break;
      }

      this.values.set(index, value);
      this.priorities.set(index, priority);
      if (INTERNAL_CONSISTENCY_CHECKING) {
         this.check();
      }
   }

   public int size() {
      return this.values.size();
   }

   public void clear() {
      this.values.clear();
      this.priorities.clear();
   }

   public void reset() {
      this.values.reset();
      this.priorities.reset();
   }

   public int getValue() {
      return this.values.get(0);
   }

   public float getPriority() {
      return this.priorities.get(0);
   }

   private void demote(int index, int value, float priority) {
      for (int childIndex = index * 2 + 1; childIndex < this.values.size(); childIndex = childIndex * 2 + 1) {
         float childPriority = this.priorities.get(childIndex);
         if (childIndex + 1 < this.values.size()) {
            float rightPriority = this.priorities.get(childIndex + 1);
            if (this.sortsEarlierThan(rightPriority, childPriority)) {
               childPriority = rightPriority;
               childIndex++;
            }
         }

         if (!this.sortsEarlierThan(childPriority, priority)) {
            break;
         }

         this.priorities.set(index, childPriority);
         this.values.set(index, this.values.get(childIndex));
         index = childIndex;
      }

      this.values.set(index, value);
      this.priorities.set(index, priority);
   }

   public int pop() {
      int ret = this.values.get(0);
      int lastIndex = this.values.size() - 1;
      int tempValue = this.values.get(lastIndex);
      float tempPriority = this.priorities.get(lastIndex);
      this.values.removeAt(lastIndex);
      this.priorities.removeAt(lastIndex);
      if (lastIndex > 0) {
         this.demote(0, tempValue, tempPriority);
      }

      if (INTERNAL_CONSISTENCY_CHECKING) {
         this.check();
      }

      return ret;
   }

   public void setSortOrder(boolean sortOrder) {
      if (this.sortOrder != sortOrder) {
         this.sortOrder = sortOrder;

         for (int i = this.values.size() / 2 - 1; i >= 0; i--) {
            this.demote(i, this.values.get(i), this.priorities.get(i));
         }
      }

      if (INTERNAL_CONSISTENCY_CHECKING) {
         this.check();
      }
   }

   private void check() {
      int lastIndex = this.values.size() - 1;

      for (int i = 0; i < this.values.size() / 2; i++) {
         float currentPriority = this.priorities.get(i);
         int leftIndex = i * 2 + 1;
         if (leftIndex <= lastIndex) {
            float leftPriority = this.priorities.get(leftIndex);
            if (this.sortsEarlierThan(leftPriority, currentPriority)) {
               System.err.println("Internal error in PriorityQueue");
            }
         }

         int rightIndex = i * 2 + 2;
         if (rightIndex <= lastIndex) {
            float rightPriority = this.priorities.get(rightIndex);
            if (this.sortsEarlierThan(rightPriority, currentPriority)) {
               System.err.println("Internal error in PriorityQueue");
            }
         }
      }
   }
}
