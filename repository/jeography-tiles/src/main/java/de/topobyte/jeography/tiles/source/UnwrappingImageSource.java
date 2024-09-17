package de.topobyte.jeography.tiles.source;

import de.topobyte.jeography.tiles.BufferedImageAndBytes;
import java.awt.image.BufferedImage;

public class UnwrappingImageSource<T> implements ImageSource<T, BufferedImage> {
   public ImageSource<T, BufferedImageAndBytes> source;

   public UnwrappingImageSource(ImageSource<T, BufferedImageAndBytes> source) {
      this.source = source;
   }

   public BufferedImage load(T thing) {
      BufferedImageAndBytes result = (BufferedImageAndBytes)this.source.load(thing);
      return result != null ? result.getImage() : null;
   }
}
