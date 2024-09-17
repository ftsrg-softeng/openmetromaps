package de.topobyte.osm4j.core.resolve;

public enum EntityNotFoundStrategy {
   IGNORE,
   LOG_WARN,
   LOG_INFO,
   LOG_DEBUG,
   THROW;
}
