package de.topobyte.jsi.intersectiontester;

import com.infomatiq.jsi.Rectangle;

public interface RectangleIntersectionTester {
   void add(Rectangle var1, boolean var2);

   boolean isFree(Rectangle var1);
}
