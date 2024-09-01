package de.topobyte.jeography.tiles.source;

import de.topobyte.jeography.tiles.BufferedImageAndBytes;
import de.topobyte.jeography.tiles.UrlProvider;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageSourceUrlPattern<T> implements ImageSource<T, BufferedImageAndBytes> {
   static final Logger logger = LoggerFactory.getLogger(ImageSourceUrlPattern.class);
   private UrlProvider<T> resolver;
   private String userAgent;
   private final int nTries;
   private boolean online = true;

   public ImageSourceUrlPattern(UrlProvider<T> resolver, int nTries) {
      this.resolver = resolver;
      this.nTries = nTries;
   }

   public void setPathResoluter(UrlProvider<T> resolver) {
      this.resolver = resolver;
   }

   public void setUserAgent(String userAgent) {
      this.userAgent = userAgent;
   }

   public boolean isOnline() {
      return this.online;
   }

   public void setOnline(boolean online) {
      this.online = online;
   }

   public BufferedImageAndBytes load(T thing) {
      return this.loadImage(thing);
   }

   public BufferedImageAndBytes loadImage(T thing) {
      String iurl = this.resolver.getUrl(thing);

      for (int k = 0; k < this.nTries && this.online; k++) {
         if (k > 0) {
            logger.debug("retry #" + k);
         }

         InputStream cis = null;
         InputStream bis = null;

         try {
            URL url = new URL(iurl);
            URLConnection connection = url.openConnection();
            if (this.userAgent != null) {
               connection.setRequestProperty("User-Agent", this.userAgent);
            }

            cis = connection.getInputStream();
            bis = new BufferedInputStream(cis);
            ByteArrayOutputStream baos = new ByteArrayOutputStream(40000);
            byte[] buffer = new byte[1024];

            while (true) {
               int b = bis.read(buffer);
               if (b < 0) {
                  byte[] bytes = baos.toByteArray();
                  ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                  BufferedImage image = ImageIO.read(bais);
                  return new BufferedImageAndBytes(image, bytes);
               }

               baos.write(buffer, 0, b);
            }
         } catch (MalformedURLException var30) {
            logger.warn("Malformed URL: " + var30.getMessage());
         } catch (IOException var31) {
            logger.warn("IOException: " + var31.getMessage());
         } finally {
            try {
               if (cis != null) {
                  cis.close();
               }
            } catch (IOException var29) {
               logger.warn("unable to close InputStream: " + var29.getMessage());
            }

            try {
               if (bis != null) {
                  bis.close();
               }
            } catch (IOException var28) {
               logger.warn("unable to close InputStream: " + var28.getMessage());
            }
         }
      }

      return null;
   }
}
