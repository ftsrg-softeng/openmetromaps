package de.topobyte.jeography.tiles.manager;

import de.topobyte.jeography.tiles.LoadListener;

public interface ImageManager<T, D> {
   D get(T var1);

   void willNeed(T var1);

   void addLoadListener(LoadListener<T, D> var1);

   void removeLoadListener(LoadListener<T, D> var1);

   void destroy();

   void setCacheHintMinimumSize(int var1);
}
