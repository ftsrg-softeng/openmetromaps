package de.topobyte.melon.enums;

import de.topobyte.melon.enums.naming.DefaultEnumNamer;
import de.topobyte.melon.enums.naming.EnumNamer;

public class EnumLookups {
   public static <T extends Enum<T>> EnumLookup<T> build(Class<T> clazz) {
      DefaultEnumNamer<T> namer = new DefaultEnumNamer<>();
      return build(clazz, namer);
   }

   public static <T extends Enum<T>> EnumLookup<T> build(Class<T> clazz, T defaultValue) {
      DefaultEnumNamer<T> namer = new DefaultEnumNamer<>();
      return build(clazz, namer, defaultValue);
   }

   public static <T extends Enum<T>> EnumLookup<T> build(Class<T> clazz, EnumNamer<T> namer) {
      EnumLookup<T> lookup = new EnumLookup<>();
      T[] values = (T[])clazz.getEnumConstants();

      for (T value : values) {
         lookup.put(namer.getName(value), value);
      }

      return lookup;
   }

   public static <T extends Enum<T>> EnumLookup<T> build(Class<T> clazz, EnumNamer<T> namer, T defaultValue) {
      EnumLookup<T> lookup = build(clazz, namer);
      lookup.setDefaultValue(defaultValue);
      return lookup;
   }
}
