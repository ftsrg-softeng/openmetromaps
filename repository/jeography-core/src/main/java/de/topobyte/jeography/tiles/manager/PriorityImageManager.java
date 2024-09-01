package de.topobyte.jeography.tiles.manager;

public interface PriorityImageManager<T, D, P extends Comparable<P>> extends ImageManager<T, D> {
   D get(T var1, P var2);

   void cancelJobs();

   void setIgnorePendingProductions();
}
