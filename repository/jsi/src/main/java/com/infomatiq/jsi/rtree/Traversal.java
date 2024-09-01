package com.infomatiq.jsi.rtree;

import com.infomatiq.jsi.Rectangle;

public interface Traversal {
   void element(Rectangle var1, int var2);

   void node(Rectangle var1);
}
