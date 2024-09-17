package de.topobyte.jeography.tiles.manager;

import de.topobyte.jeography.tiles.LoadListener;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractImageManager<T, D> implements ImageManager<T, D> {
   private List<LoadListener<T, D>> listeners = new ArrayList<>();

   @Override
   public void addLoadListener(LoadListener<T, D> listener) {
      this.listeners.add(listener);
   }

   @Override
   public void removeLoadListener(LoadListener<T, D> listener) {
      this.listeners.remove(listener);
   }

   protected void notifyListeners(T thing, D image) {
      for (LoadListener<T, D> listener : this.listeners) {
         listener.loaded(thing, image);
      }
   }

   protected void notifyListenersFail(T thing) {
      for (LoadListener<T, D> listener : this.listeners) {
         listener.loadFailed(thing);
      }
   }
}
