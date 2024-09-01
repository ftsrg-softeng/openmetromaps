package de.topobyte.adt.graph.util;

import java.util.List;

public interface EnumerationBuilder<T> {
   List<T> buildEnumeration();

   List<T> getEnumeration();
}
