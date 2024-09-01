package de.topobyte.melon.enums;

import de.topobyte.melon.enums.naming.DefaultEnumNamer;
import de.topobyte.melon.enums.naming.EnumNamer;
import java.util.ArrayList;
import java.util.List;

public class EnumUtil<T extends Enum<T>> {
   public static <T extends Enum<T>> String buildNameList(Class<T> clazz) {
      return buildNameList(clazz.getEnumConstants());
   }

   public static <T extends Enum<T>> String buildNameList(Class<T> clazz, EnumNamer<T> namer) {
      return buildNameList(clazz.getEnumConstants(), namer);
   }

   public static <T extends Enum<T>> String buildNameList(Class<T> clazz, String separator) {
      return buildNameList(clazz.getEnumConstants(), new DefaultEnumNamer<>(), separator);
   }

   public static <T extends Enum<T>> String buildNameList(Class<T> clazz, EnumNamer<T> namer, String separator) {
      return buildNameList(clazz.getEnumConstants(), namer, separator);
   }

   public static <T extends Enum<T>> String buildNameList(T[] values) {
      return buildNameList(values, new DefaultEnumNamer<>(), ", ");
   }

   public static <T extends Enum<T>> String buildNameList(T[] values, EnumNamer<T> namer) {
      return buildNameList(values, namer, ", ");
   }

   public static <T extends Enum<T>> String buildNameList(T[] values, String separator) {
      return buildNameList(values, new DefaultEnumNamer<>(), separator);
   }

   public static <T extends Enum<T>> String buildNameList(T[] values, EnumNamer<T> namer, String separator) {
      List<String> names = new ArrayList<>();

      for (T value : values) {
         names.add(namer.getName(value));
      }

      return Joiner.on(separator).join(names);
   }

   public static <T extends Enum<T>> String buildNameList(Iterable<T> values) {
      return buildNameList(values, new DefaultEnumNamer<>(), ", ");
   }

   public static <T extends Enum<T>> String buildNameList(Iterable<T> values, EnumNamer<T> namer) {
      return buildNameList(values, namer, ", ");
   }

   public static <T extends Enum<T>> String buildNameList(Iterable<T> values, String separator) {
      return buildNameList(values, new DefaultEnumNamer<>(), separator);
   }

   public static <T extends Enum<T>> String buildNameList(Iterable<T> values, EnumNamer<T> namer, String separator) {
      List<String> names = new ArrayList<>();

      for (T value : values) {
         names.add(namer.getName(value));
      }

      return Joiner.on(separator).join(names);
   }
}
