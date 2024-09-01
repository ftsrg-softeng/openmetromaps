package de.topobyte.jsi;

import com.infomatiq.jsi.Rectangle;

public class TraversalPrint<T> implements Traversal<T> {
   @Override
   public void element(Rectangle rectangle, T element) {
      System.out.println(String.format("%s %s", element.toString(), rectangle.toString()));
   }

   @Override
   public void node(Rectangle rectangle) {
      System.out.println(String.format("inner node: %s", rectangle.toString()));
   }
}
