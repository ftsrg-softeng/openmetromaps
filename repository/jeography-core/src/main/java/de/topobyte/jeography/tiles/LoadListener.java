package de.topobyte.jeography.tiles;

public interface LoadListener<T, D> {
   void loaded(T var1, D var2);

   void loadFailed(T var1);
}
