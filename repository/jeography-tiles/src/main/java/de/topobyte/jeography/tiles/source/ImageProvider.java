package de.topobyte.jeography.tiles.source;

import de.topobyte.jeography.tiles.LoadListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ImageProvider<T, D> implements ImageSource<T, D> {
   static final Logger logger = LoggerFactory.getLogger(ImageProvider.class);
   Object lock = new Object();
   List<ImageProvider<T, D>.Message> toProvideList = new ArrayList<>();
   Set<T> toProvideSet = new HashSet<>();
   Set<T> providing = new HashSet<>();
   Set<LoadListener<T, D>> listeners = new HashSet<>();
   private int nThreads;

   public abstract D load(T var1);

   public void provide(T thing) {
      synchronized (this.lock) {
         if (!this.toProvideSet.contains(thing) && !this.providing.contains(thing)) {
            this.toProvideList.add(new ImageProvider.Message(ImageProvider.MessageType.Data, thing));
            this.toProvideSet.add(thing);
            this.lock.notify();
         }
      }
   }

   public void stopRunning() {
      synchronized (this.lock) {
         for (int i = 0; i < this.nThreads; i++) {
            this.toProvideList.add(new ImageProvider.Message(ImageProvider.MessageType.Kill, null));
         }

         this.lock.notifyAll();
      }
   }

   public void addLoadListener(LoadListener<T, D> listener) {
      this.listeners.add(listener);
   }

   public void removeLoadListener(LoadListener<T, D> listener) {
      this.listeners.remove(listener);
   }

   void notifyLoaded(T thing, D data) {
      for (LoadListener<T, D> listener : this.listeners) {
         listener.loaded(thing, data);
      }
   }

   void notifyFailed(T thing) {
      for (LoadListener<T, D> listener : this.listeners) {
         listener.loadFailed(thing);
      }
   }

   public ImageProvider(int nThreads) {
      this.nThreads = nThreads;

      for (int i = 0; i < nThreads; i++) {
         ImageProvider<T, D>.LoadThread loadThread = new ImageProvider.LoadThread();
         Thread thread = new Thread(loadThread);
         thread.start();
      }
   }

   class LoadThread implements Runnable {
      @Override
      public void run() {
         while (true) {
            T provide = null;
            synchronized (ImageProvider.this.lock) {
               if (ImageProvider.this.toProvideList.isEmpty()) {
                  try {
                     ImageProvider.this.lock.wait();
                  } catch (InterruptedException var7) {
                  }
               } else {
                  ImageProvider<T, D>.Message message = ImageProvider.this.toProvideList.remove(ImageProvider.this.toProvideList.size() - 1);
                  if (message.type == ImageProvider.MessageType.Kill) {
                     ImageProvider.logger.debug("thread stopped");
                     return;
                  }

                  provide = message.data;
                  ImageProvider.this.toProvideSet.remove(provide);
                  ImageProvider.this.providing.add(provide);
               }
            }

            if (provide != null) {
               D loaded = ImageProvider.this.load(provide);
               synchronized (ImageProvider.this.lock) {
                  if (loaded == null) {
                     ImageProvider.this.notifyFailed(provide);
                  } else {
                     ImageProvider.this.notifyLoaded(provide, loaded);
                  }

                  ImageProvider.this.providing.remove(provide);
               }
            }
         }
      }
   }

   private class Message {
      ImageProvider.MessageType type;
      T data;

      public Message(ImageProvider.MessageType type, T data) {
         this.type = type;
         this.data = data;
      }
   }

   private static enum MessageType {
      Data,
      Kill;
   }
}
