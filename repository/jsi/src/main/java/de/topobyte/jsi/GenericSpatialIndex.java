package de.topobyte.jsi;

import com.infomatiq.jsi.Point;
import com.infomatiq.jsi.Rectangle;
import com.slimjars.dist.gnu.trove.procedure.TObjectProcedure;
import java.util.List;
import java.util.Set;

public interface GenericSpatialIndex<T> {
   void add(Rectangle var1, T var2);

   void contains(Rectangle var1, TObjectProcedure<T> var2);

   Set<T> contains(Rectangle var1);

   boolean delete(Rectangle var1, T var2);

   void intersects(Rectangle var1, TObjectProcedure<T> var2);

   Set<T> intersects(Rectangle var1);

   List<T> intersectionsAsList(Rectangle var1);

   void nearest(Point var1, TObjectProcedure<T> var2, float var3);

   Set<T> nearest(Point var1, float var2);

   int size();
}
