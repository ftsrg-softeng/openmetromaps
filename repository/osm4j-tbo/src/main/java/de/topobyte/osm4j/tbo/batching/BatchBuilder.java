package de.topobyte.osm4j.tbo.batching;

public interface BatchBuilder<T> {
   void add(T var1);

   boolean full();

   boolean fits(T var1);

   void clear();

   int bufferSizeHint();
}
