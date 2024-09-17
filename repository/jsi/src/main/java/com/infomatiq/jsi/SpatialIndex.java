package com.infomatiq.jsi;

import com.slimjars.dist.gnu.trove.procedure.TIntProcedure;

public interface SpatialIndex {
   void add(Rectangle var1, int var2);

   boolean delete(Rectangle var1, int var2);

   void nearest(Point var1, TIntProcedure var2, float var3);

   void nearestN(Point var1, TIntProcedure var2, int var3, float var4);

   void nearestNUnsorted(Point var1, TIntProcedure var2, int var3, float var4);

   void intersects(Rectangle var1, TIntProcedure var2);

   void contains(Rectangle var1, TIntProcedure var2);

   int size();

   Rectangle getBounds();
}
