package de.topobyte.melon.resources;

import java.io.InputStream;
import java.net.URL;

public class Resources {
   public static URL url(String name) {
      return Thread.currentThread().getContextClassLoader().getResource(name);
   }

   public static InputStream stream(String name) {
      return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
   }
}
