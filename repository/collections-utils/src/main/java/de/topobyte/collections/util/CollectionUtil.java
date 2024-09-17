package de.topobyte.collections.util;

import java.util.Collection;
import java.util.Iterator;

public class CollectionUtil {
   public static byte[] asByteArray(Collection<Byte> collection) {
      byte[] array = new byte[collection.size()];
      Iterator<Byte> iterator = collection.iterator();
      int i = 0;

      while (iterator.hasNext()) {
         array[i++] = iterator.next();
      }

      return array;
   }

   public static short[] asShortArray(Collection<Short> collection) {
      short[] array = new short[collection.size()];
      Iterator<Short> iterator = collection.iterator();
      int i = 0;

      while (iterator.hasNext()) {
         array[i++] = iterator.next();
      }

      return array;
   }

   public static int[] asIntArray(Collection<Integer> collection) {
      int[] array = new int[collection.size()];
      Iterator<Integer> iterator = collection.iterator();
      int i = 0;

      while (iterator.hasNext()) {
         array[i++] = iterator.next();
      }

      return array;
   }

   public static long[] asLongArray(Collection<Long> collection) {
      long[] array = new long[collection.size()];
      Iterator<Long> iterator = collection.iterator();
      int i = 0;

      while (iterator.hasNext()) {
         array[i++] = iterator.next();
      }

      return array;
   }

   public static float[] asFloatArray(Collection<Float> collection) {
      float[] array = new float[collection.size()];
      Iterator<Float> iterator = collection.iterator();
      int i = 0;

      while (iterator.hasNext()) {
         array[i++] = iterator.next();
      }

      return array;
   }

   public static double[] asDoubleArray(Collection<Double> collection) {
      double[] array = new double[collection.size()];
      Iterator<Double> iterator = collection.iterator();
      int i = 0;

      while (iterator.hasNext()) {
         array[i++] = iterator.next();
      }

      return array;
   }

   public static char[] asCharArray(Collection<Character> collection) {
      char[] array = new char[collection.size()];
      Iterator<Character> iterator = collection.iterator();
      int i = 0;

      while (iterator.hasNext()) {
         array[i++] = iterator.next();
      }

      return array;
   }

   public static boolean[] asBooleanArray(Collection<Boolean> collection) {
      boolean[] array = new boolean[collection.size()];
      Iterator<Boolean> iterator = collection.iterator();
      int i = 0;

      while (iterator.hasNext()) {
         array[i++] = iterator.next();
      }

      return array;
   }
}
