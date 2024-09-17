package de.topobyte.jeography.tiles.manager;

import de.topobyte.jeography.tiles.LoadListener;
import de.topobyte.jeography.tiles.source.ImageSource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageManagerSourceRam<T, D> extends AbstractImageManagerWithMemoryCachePlus<T, D> implements PriorityImageManager<T, D, Integer> {
   static final Logger logger = LoggerFactory.getLogger(ImageManagerSourceRam.class);
   private final int nThreads;
   Object lock = new Object();
   Set<T> toProvideSet = new HashSet<>();
   PriorityQueue<ImageManagerSourceRam<T, D>.PriorityEntry> toProvideQueue = new PriorityQueue<>();
   Set<T> providing = new HashSet<>();
   List<ImageManagerSourceRam<T, D>.Message> messages = new ArrayList<>();
   Set<LoadListener<T, D>> listeners = new HashSet<>();
   ImageSource<T, D> imageSource = null;
   int settingsId = 1;

   public ImageManagerSourceRam(int nThreads, int cacheSize, ImageSource<T, D> source) {
      super(cacheSize);
      this.nThreads = nThreads;
      this.imageSource = source;

      for (int i = 0; i < nThreads; i++) {
         ImageManagerSourceRam<T, D>.LoadThread loadThread = new ImageManagerSourceRam.LoadThread();
         Thread thread = new Thread(loadThread);
         thread.start();
      }
   }

   public ImageSource<T, D> getImageSource() {
      return this.imageSource;
   }

   public D get(T thing) {
      return this.get(thing, 0);
   }

   public D get(T thing, Integer priority) {
      D image = this.memoryCache.get(thing);
      if (image != null) {
         return image;
      } else {
         this.produce(thing, priority);
         return null;
      }
   }

   public void cancelJobs() {
      synchronized (this.lock) {
         this.toProvideQueue.clear();
         this.toProvideSet.clear();
      }
   }

   @Override
   public void willNeed(T thing) {
      this.memoryCache.refresh(thing);
   }

   private void produce(T thing, int priority) {
      synchronized (this.lock) {
         if (!this.toProvideSet.contains(thing) && !this.providing.contains(thing)) {
            this.toProvideQueue.add(new ImageManagerSourceRam.PriorityEntry(priority, thing));
            this.toProvideSet.add(thing);
            this.lock.notify();
         }
      }
   }

   public void setIgnorePendingProductions() {
      synchronized (this.lock) {
         this.settingsId++;
         this.providing.clear();
      }
   }

   public void destroy() {
      this.stopRunning();
   }

   public void stopRunning() {
      synchronized (this.lock) {
         for (int i = 0; i < this.nThreads; i++) {
            this.messages.add(new ImageManagerSourceRam.Message(ImageManagerSourceRam.MessageType.Kill));
         }

         this.lock.notifyAll();
      }
   }

   public void unchache(T thing) {
      this.memoryCache.remove(thing);
   }

   public void clearCache() {
      this.memoryCache.clear();
   }

   @Override
   public void setCacheHintMinimumSize(int size) {
      if (this.memoryCache.getSize() < size) {
         this.memoryCache.setSize(size);
      } else if (size < this.desiredCacheSize) {
         this.memoryCache.setSize(this.desiredCacheSize);
      }
   }

   void notifyLoaded(T thing, D data) {
      this.memoryCache.put(thing, data);
      this.notifyListeners(thing, data);
   }

   void notifyFailed(T thing) {
      this.notifyListenersFail(thing);
   }

   class LoadThread implements Runnable {
      @Override
      public void run() {
         while (true) {
            int mySettingsId = 0;
            T provide = null;
            synchronized (ImageManagerSourceRam.this.lock) {
               if (ImageManagerSourceRam.this.messages.isEmpty() && ImageManagerSourceRam.this.toProvideQueue.isEmpty()) {
                  try {
                     ImageManagerSourceRam.this.lock.wait();
                  } catch (InterruptedException var8) {
                  }
               } else {
                  if (!ImageManagerSourceRam.this.messages.isEmpty()) {
                     ImageManagerSourceRam<T, D>.Message message = ImageManagerSourceRam.this.messages.remove(ImageManagerSourceRam.this.messages.size() - 1);
                     if (message.type == ImageManagerSourceRam.MessageType.Kill) {
                        ImageManagerSourceRam.logger.debug("thread stopped");
                        return;
                     }
                  }

                  ImageManagerSourceRam<T, D>.PriorityEntry entry = ImageManagerSourceRam.this.toProvideQueue.remove();
                  provide = entry.thing;
                  mySettingsId = ImageManagerSourceRam.this.settingsId;
                  ImageManagerSourceRam.this.toProvideSet.remove(provide);
                  ImageManagerSourceRam.this.providing.add(provide);
               }
            }

            if (provide != null) {
               D data = (D)ImageManagerSourceRam.this.imageSource.load(provide);
               synchronized (ImageManagerSourceRam.this.lock) {
                  if (mySettingsId == ImageManagerSourceRam.this.settingsId) {
                     if (data == null) {
                        ImageManagerSourceRam.this.notifyFailed(provide);
                     } else {
                        ImageManagerSourceRam.this.notifyLoaded(provide, data);
                     }

                     ImageManagerSourceRam.this.providing.remove(provide);
                  }
               }
            }
         }
      }
   }

   private class Message {
      final ImageManagerSourceRam.MessageType type;

      public Message(ImageManagerSourceRam.MessageType type) {
         this.type = type;
      }
   }

   private static enum MessageType {
      Kill;
   }

   private class PriorityEntry implements Comparable<ImageManagerSourceRam<T, D>.PriorityEntry> {
      final int priority;
      final T thing;

      PriorityEntry(int priority, T thing) {
         this.priority = priority;
         this.thing = thing;
      }

      public int compareTo(ImageManagerSourceRam<T, D>.PriorityEntry o) {
         return this.priority - o.priority;
      }
   }
}
