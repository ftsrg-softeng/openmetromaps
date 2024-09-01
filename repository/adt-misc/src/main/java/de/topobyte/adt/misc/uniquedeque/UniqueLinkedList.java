package de.topobyte.adt.misc.uniquedeque;

import java.util.HashMap;
import java.util.Map;

public class UniqueLinkedList<T> {
   private ListEntry<T> head = null;
   private ListEntry<T> tail = null;
   private Map<T, ListEntry<T>> entryMap = new HashMap<>();
   private int size = 0;

   ListEntry<T> getHead() {
      return this.head;
   }

   ListEntry<T> getTail() {
      return this.tail;
   }

   public void addFirst(T element) {
      this.size++;
      ListEntry<T> oldHead = this.head;
      this.head = new ListEntry<>(element);
      this.entryMap.put(element, this.head);
      this.head.setNext(oldHead);
      if (oldHead == null) {
         this.tail = this.head;
      } else {
         oldHead.setPrevious(this.head);
      }
   }

   public void addLast(T element) {
      this.size++;
      ListEntry<T> oldTail = this.tail;
      this.tail = new ListEntry<>(element);
      this.entryMap.put(element, this.tail);
      this.tail.setPrevious(oldTail);
      if (oldTail == null) {
         this.head = this.tail;
      } else {
         oldTail.setNext(this.tail);
      }
   }

   public int size() {
      return this.size;
   }

   public boolean contains(T element) {
      return this.entryMap.containsKey(element);
   }

   public T getFirst() {
      return this.head == null ? null : this.head.getElement();
   }

   public T getLast() {
      return this.tail == null ? null : this.tail.getElement();
   }

   public void clear() {
      this.head = null;
      this.tail = null;
      this.size = 0;
   }

   public T removeFirst() {
      if (this.size == 0) {
         return null;
      } else {
         this.size--;
         ListEntry<T> oldHead = this.head;
         ListEntry<T> newHead = oldHead.getNext();
         this.head = newHead;
         if (newHead == null) {
            this.tail = null;
         } else {
            newHead.setPrevious(null);
         }

         return oldHead.getElement();
      }
   }

   public T removeLast() {
      if (this.size == 0) {
         return null;
      } else {
         this.size--;
         ListEntry<T> oldTail = this.tail;
         ListEntry<T> newTail = oldTail.getPrevious();
         this.tail = newTail;
         if (newTail == null) {
            this.head = null;
         } else {
            newTail.setNext(null);
         }

         return oldTail.getElement();
      }
   }

   public void moveToFront(T element) {
      ListEntry<T> listEntry = this.entryMap.get(element);
      if (listEntry != null) {
         if (this.head != listEntry) {
            ListEntry<T> oldNext = listEntry.getNext();
            ListEntry<T> oldPrev = listEntry.getPrevious();
            oldPrev.setNext(oldNext);
            if (oldNext == null) {
               this.tail = oldPrev;
            } else {
               oldNext.setPrevious(oldPrev);
            }

            ListEntry<T> oldHead = this.head;
            this.head = listEntry;
            this.head.setNext(oldHead);
            oldHead.setPrevious(listEntry);
            listEntry.setPrevious(null);
         }
      }
   }

   public void moveToTail(T element) {
      ListEntry<T> listEntry = this.entryMap.get(element);
      if (listEntry != null) {
         if (this.tail != listEntry) {
            ListEntry<T> oldNext = listEntry.getNext();
            ListEntry<T> oldPrev = listEntry.getPrevious();
            oldNext.setPrevious(oldPrev);
            if (oldPrev == null) {
               this.head = oldNext;
            } else {
               oldPrev.setNext(oldNext);
            }

            ListEntry<T> oldTail = this.tail;
            this.tail = listEntry;
            this.tail.setPrevious(oldTail);
            oldTail.setNext(listEntry);
            listEntry.setNext(null);
         }
      }
   }
}
