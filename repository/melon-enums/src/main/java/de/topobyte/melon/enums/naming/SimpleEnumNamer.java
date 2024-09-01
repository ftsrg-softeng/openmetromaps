package de.topobyte.melon.enums.naming;

public class SimpleEnumNamer<T extends Enum<T>> implements EnumNamer<T> {
   @Override
   public String getName(T value) {
      return value.name().toLowerCase().replaceAll("_", "-");
   }
}
