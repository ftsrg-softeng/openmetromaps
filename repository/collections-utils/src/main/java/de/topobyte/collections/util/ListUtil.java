package de.topobyte.collections.util;

import java.util.List;
import java.util.Random;

public class ListUtil {
   public static <T> void shuffle(List<T> list, int nswaps) {
      int size = list.size();
      Random random = new Random();

      for (int i = 0; i < nswaps; i++) {
         int a = random.nextInt(size);
         int b = random.nextInt(size);
         T t = list.get(a);
         list.set(a, list.get(b));
         list.set(b, t);
      }
   }

   public static <T> void swap(List<T> list, int a, int b) {
      T tmp = list.get(a);
      list.set(a, list.get(b));
      list.set(b, tmp);
   }

   public static byte[] asByteArray(List<Byte> list) {
      int n = list.size();
      byte[] array = new byte[n];

      for (int i = 0; i < n; i++) {
         array[i] = list.get(i);
      }

      return array;
   }

   public static short[] asShortArray(List<Short> list) {
      int n = list.size();
      short[] array = new short[n];

      for (int i = 0; i < n; i++) {
         array[i] = list.get(i);
      }

      return array;
   }

   public static int[] asIntArray(List<Integer> list) {
      int n = list.size();
      int[] array = new int[n];

      for (int i = 0; i < n; i++) {
         array[i] = list.get(i);
      }

      return array;
   }

   public static long[] asLongArray(List<Long> list) {
      int n = list.size();
      long[] array = new long[n];

      for (int i = 0; i < n; i++) {
         array[i] = list.get(i);
      }

      return array;
   }

   public static float[] asFloatArray(List<Float> list) {
      int n = list.size();
      float[] array = new float[n];

      for (int i = 0; i < n; i++) {
         array[i] = list.get(i);
      }

      return array;
   }

   public static double[] asDoubleArray(List<Double> list) {
      int n = list.size();
      double[] array = new double[n];

      for (int i = 0; i < n; i++) {
         array[i] = list.get(i);
      }

      return array;
   }

   public static char[] asCharArray(List<Character> list) {
      int n = list.size();
      char[] array = new char[n];

      for (int i = 0; i < n; i++) {
         array[i] = list.get(i);
      }

      return array;
   }

   public static boolean[] asBooleanArray(List<Boolean> list) {
      int n = list.size();
      boolean[] array = new boolean[n];

      for (int i = 0; i < n; i++) {
         array[i] = list.get(i);
      }

      return array;
   }

   public static <T> List<T> prepended(List<T> list, T element) {
      return new ListWithPrependedElement<>(element, list);
   }

   public static <T> List<T> appended(List<T> list, T element) {
      return new ListWithAppendedElement<>(list, element);
   }

   public static <T> T last(List<T> list) {
      return list.get(list.size() - 1);
   }

   public static <T> T removeLast(List<T> list) {
      return list.remove(list.size() - 1);
   }
}
