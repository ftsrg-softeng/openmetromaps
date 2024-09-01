package de.topobyte.melon.enums.naming;

public interface EnumNamer<T extends Enum<T>> {
   String getName(T var1);
}
