package de.topobyte.melon.enums;

import de.topobyte.melon.enums.naming.EnumNamer;
import java.util.HashMap;
import java.util.Map;

public class EnumLookup<T extends Enum<T>> implements EnumNamer<T> {
   private T defaultValue = (T)null;
   private Map<String, T> map = new HashMap<>();
   private Map<T, String> reverseMap = new HashMap<>();

   public void put(String name, T element) {
      this.map.put(name, element);
      this.reverseMap.put(element, name);
   }

   public void setDefaultValue(T defaultValue) {
      this.defaultValue = defaultValue;
   }

   public T get(String name) {
      T value = this.map.get(name);
      return value != null ? value : this.defaultValue;
   }

   @Override
   public String getName(T value) {
      return this.reverseMap.get(value);
   }
}
