package de.topobyte.lightgeom.convexhull;

import java.util.Arrays;

public class FloatArray {
   public float[] items;
   public int size;
   public boolean ordered;

   public FloatArray() {
      this(true, 16);
   }

   public FloatArray(int capacity) {
      this(true, capacity);
   }

   public FloatArray(boolean ordered, int capacity) {
      this.ordered = ordered;
      this.items = new float[capacity];
   }

   public FloatArray(FloatArray array) {
      this.ordered = array.ordered;
      this.size = array.size;
      this.items = new float[this.size];
      System.arraycopy(array.items, 0, this.items, 0, this.size);
   }

   public FloatArray(float[] array) {
      this(true, array, 0, array.length);
   }

   public FloatArray(boolean ordered, float[] array, int startIndex, int count) {
      this(ordered, count);
      this.size = count;
      System.arraycopy(array, startIndex, this.items, 0, count);
   }

   public void add(float value) {
      float[] items = this.items;
      if (this.size == items.length) {
         items = this.resize(Math.max(8, (int)((float)this.size * 1.75F)));
      }

      items[this.size++] = value;
   }

   public void addAll(FloatArray array) {
      this.addAll(array, 0, array.size);
   }

   public void addAll(FloatArray array, int offset, int length) {
      if (offset + length > array.size) {
         throw new IllegalArgumentException("offset + length must be <= size: " + offset + " + " + length + " <= " + array.size);
      } else {
         this.addAll(array.items, offset, length);
      }
   }

   public void addAll(float... array) {
      this.addAll(array, 0, array.length);
   }

   public void addAll(float[] array, int offset, int length) {
      float[] items = this.items;
      int sizeNeeded = this.size + length;
      if (sizeNeeded > items.length) {
         items = this.resize(Math.max(8, (int)((float)sizeNeeded * 1.75F)));
      }

      System.arraycopy(array, offset, items, this.size, length);
      this.size += length;
   }

   public float get(int index) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("index can't be >= size: " + index + " >= " + this.size);
      } else {
         return this.items[index];
      }
   }

   public void set(int index, float value) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("index can't be >= size: " + index + " >= " + this.size);
      } else {
         this.items[index] = value;
      }
   }

   public void incr(int index, float value) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("index can't be >= size: " + index + " >= " + this.size);
      } else {
         this.items[index] = this.items[index] + value;
      }
   }

   public void mul(int index, float value) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("index can't be >= size: " + index + " >= " + this.size);
      } else {
         this.items[index] = this.items[index] * value;
      }
   }

   public void insert(int index, float value) {
      if (index > this.size) {
         throw new IndexOutOfBoundsException("index can't be > size: " + index + " > " + this.size);
      } else {
         float[] items = this.items;
         if (this.size == items.length) {
            items = this.resize(Math.max(8, (int)((float)this.size * 1.75F)));
         }

         if (this.ordered) {
            System.arraycopy(items, index, items, index + 1, this.size - index);
         } else {
            items[this.size] = items[index];
         }

         this.size++;
         items[index] = value;
      }
   }

   public void swap(int first, int second) {
      if (first >= this.size) {
         throw new IndexOutOfBoundsException("first can't be >= size: " + first + " >= " + this.size);
      } else if (second >= this.size) {
         throw new IndexOutOfBoundsException("second can't be >= size: " + second + " >= " + this.size);
      } else {
         float[] items = this.items;
         float firstValue = items[first];
         items[first] = items[second];
         items[second] = firstValue;
      }
   }

   public boolean contains(float value) {
      int i = this.size - 1;
      float[] items = this.items;

      while (i >= 0) {
         if (items[i--] == value) {
            return true;
         }
      }

      return false;
   }

   public int indexOf(float value) {
      float[] items = this.items;
      int i = 0;

      for (int n = this.size; i < n; i++) {
         if (items[i] == value) {
            return i;
         }
      }

      return -1;
   }

   public int lastIndexOf(char value) {
      float[] items = this.items;

      for (int i = this.size - 1; i >= 0; i--) {
         if (items[i] == (float)value) {
            return i;
         }
      }

      return -1;
   }

   public boolean removeValue(float value) {
      float[] items = this.items;
      int i = 0;

      for (int n = this.size; i < n; i++) {
         if (items[i] == value) {
            this.removeIndex(i);
            return true;
         }
      }

      return false;
   }

   public float removeIndex(int index) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("index can't be >= size: " + index + " >= " + this.size);
      } else {
         float[] items = this.items;
         float value = items[index];
         this.size--;
         if (this.ordered) {
            System.arraycopy(items, index + 1, items, index, this.size - index);
         } else {
            items[index] = items[this.size];
         }

         return value;
      }
   }

   public void removeRange(int start, int end) {
      if (end >= this.size) {
         throw new IndexOutOfBoundsException("end can't be >= size: " + end + " >= " + this.size);
      } else if (start > end) {
         throw new IndexOutOfBoundsException("start can't be > end: " + start + " > " + end);
      } else {
         float[] items = this.items;
         int count = end - start + 1;
         if (this.ordered) {
            System.arraycopy(items, start + count, items, start, this.size - (start + count));
         } else {
            int lastIndex = this.size - 1;

            for (int i = 0; i < count; i++) {
               items[start + i] = items[lastIndex - i];
            }
         }

         this.size -= count;
      }
   }

   public boolean removeAll(FloatArray array) {
      int size = this.size;
      int startSize = size;
      float[] items = this.items;
      int i = 0;

      for (int n = array.size; i < n; i++) {
         float item = array.get(i);

         for (int ii = 0; ii < size; ii++) {
            if (item == items[ii]) {
               this.removeIndex(ii);
               size--;
               break;
            }
         }
      }

      return size != startSize;
   }

   public float pop() {
      return this.items[--this.size];
   }

   public float peek() {
      return this.items[this.size - 1];
   }

   public float first() {
      if (this.size == 0) {
         throw new IllegalStateException("Array is empty.");
      } else {
         return this.items[0];
      }
   }

   public void clear() {
      this.size = 0;
   }

   public float[] shrink() {
      if (this.items.length != this.size) {
         this.resize(this.size);
      }

      return this.items;
   }

   public float[] ensureCapacity(int additionalCapacity) {
      int sizeNeeded = this.size + additionalCapacity;
      if (sizeNeeded > this.items.length) {
         this.resize(Math.max(8, sizeNeeded));
      }

      return this.items;
   }

   public float[] setSize(int newSize) {
      if (newSize > this.items.length) {
         this.resize(Math.max(8, newSize));
      }

      this.size = newSize;
      return this.items;
   }

   protected float[] resize(int newSize) {
      float[] newItems = new float[newSize];
      float[] items = this.items;
      System.arraycopy(items, 0, newItems, 0, Math.min(this.size, newItems.length));
      this.items = newItems;
      return newItems;
   }

   public void sort() {
      Arrays.sort(this.items, 0, this.size);
   }

   public void reverse() {
      float[] items = this.items;
      int i = 0;
      int lastIndex = this.size - 1;

      for (int n = this.size / 2; i < n; i++) {
         int ii = lastIndex - i;
         float temp = items[i];
         items[i] = items[ii];
         items[ii] = temp;
      }
   }

   public void truncate(int newSize) {
      if (this.size > newSize) {
         this.size = newSize;
      }
   }

   public float[] toArray() {
      float[] array = new float[this.size];
      System.arraycopy(this.items, 0, array, 0, this.size);
      return array;
   }

   @Override
   public int hashCode() {
      if (!this.ordered) {
         return super.hashCode();
      } else {
         float[] items = this.items;
         int h = 1;
         int i = 0;

         for (int n = this.size; i < n; i++) {
            h = h * 31 + Float.floatToIntBits(items[i]);
         }

         return h;
      }
   }

   @Override
   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else if (!this.ordered) {
         return false;
      } else if (!(object instanceof FloatArray)) {
         return false;
      } else {
         FloatArray array = (FloatArray)object;
         if (!array.ordered) {
            return false;
         } else {
            int n = this.size;
            if (n != array.size) {
               return false;
            } else {
               float[] items1 = this.items;
               float[] items2 = array.items;

               for (int i = 0; i < n; i++) {
                  if (items1[i] != items2[i]) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   public boolean equals(Object object, float epsilon) {
      if (object == this) {
         return true;
      } else if (!(object instanceof FloatArray)) {
         return false;
      } else {
         FloatArray array = (FloatArray)object;
         int n = this.size;
         if (n != array.size) {
            return false;
         } else if (!this.ordered) {
            return false;
         } else if (!array.ordered) {
            return false;
         } else {
            float[] items1 = this.items;
            float[] items2 = array.items;

            for (int i = 0; i < n; i++) {
               if (Math.abs(items1[i] - items2[i]) > epsilon) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   @Override
   public String toString() {
      if (this.size == 0) {
         return "[]";
      } else {
         float[] items = this.items;
         StringBuilder buffer = new StringBuilder(32);
         buffer.append('[');
         buffer.append(items[0]);

         for (int i = 1; i < this.size; i++) {
            buffer.append(", ");
            buffer.append(items[i]);
         }

         buffer.append(']');
         return buffer.toString();
      }
   }

   public String toString(String separator) {
      if (this.size == 0) {
         return "";
      } else {
         float[] items = this.items;
         StringBuilder buffer = new StringBuilder(32);
         buffer.append(items[0]);

         for (int i = 1; i < this.size; i++) {
            buffer.append(separator);
            buffer.append(items[i]);
         }

         return buffer.toString();
      }
   }

   public static FloatArray with(float... array) {
      return new FloatArray(array);
   }
}
