package de.topobyte.jsi;

import com.infomatiq.jsi.Rectangle;

public interface Traversal<T> {
   void element(Rectangle var1, T var2);

   void node(Rectangle var1);
}
