package de.topobyte.melon.enums.naming;

public class DefaultEnumNamer<T extends Enum<T>> implements EnumNamer<T> {
   @Override
   public String getName(T value) {
      return value.name();
   }
}
