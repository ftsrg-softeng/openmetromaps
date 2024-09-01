package de.topobyte.utilities.apache.commons.cli.parsing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnumArgument<K extends Enum<K>> {
   private Class<K> clazz;
   private Map<String, K> argSwitch = new HashMap<>();

   public EnumArgument(Class<K> clazz) {
      this.clazz = clazz;
      K[] values = clazz.getEnumConstants();

      for (K value : values) {
         this.argSwitch.put(this.getName(value), value);
      }
   }

   private String getName(K value) {
      return value.name().toLowerCase().replaceAll("_", "-");
   }

   public K parse(String argument) {
      return this.argSwitch.get(argument);
   }

   public List<String> getPossibleNames(boolean sort) {
      List<String> names = new ArrayList<>();
      K[] values = this.clazz.getEnumConstants();

      for (K value : values) {
         names.add(this.getName(value));
      }

      if (sort) {
         Collections.sort(names);
      }

      return names;
   }
}
