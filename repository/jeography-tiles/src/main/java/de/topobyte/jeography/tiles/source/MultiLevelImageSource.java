package de.topobyte.jeography.tiles.source;

import java.util.List;

public class MultiLevelImageSource<T, D> implements ImageSource<T, D> {
   private List<ImageSource<T, D>> sources;

   public MultiLevelImageSource(List<ImageSource<T, D>> sources) {
      this.sources = sources;
   }

   public D load(T thing) {
      for (ImageSource<T, D> source : this.sources) {
         D result = (D)source.load(thing);
         if (result != null) {
            return result;
         }
      }

      return null;
   }
}
