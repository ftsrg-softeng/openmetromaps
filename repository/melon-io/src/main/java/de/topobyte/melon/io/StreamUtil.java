package de.topobyte.melon.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class StreamUtil {
   public static InputStream bufferedInputStream(File file) throws FileNotFoundException {
      InputStream in = new FileInputStream(file);
      return new BufferedInputStream(in);
   }

   public static InputStream bufferedInputStream(String path) throws FileNotFoundException {
      InputStream in = new FileInputStream(path);
      return new BufferedInputStream(in);
   }

   public static InputStream bufferedInputStream(Path path) throws IOException {
      InputStream in = Files.newInputStream(path);
      return new BufferedInputStream(in);
   }

   public static OutputStream bufferedOutputStream(File file) throws FileNotFoundException {
      OutputStream out = new FileOutputStream(file);
      return new BufferedOutputStream(out);
   }

   public static OutputStream bufferedOutputStream(String path) throws FileNotFoundException {
      OutputStream out = new FileOutputStream(path);
      return new BufferedOutputStream(out);
   }

   public static OutputStream bufferedOutputStream(Path path) throws IOException {
      OutputStream out = Files.newOutputStream(path);
      return new BufferedOutputStream(out);
   }
}
