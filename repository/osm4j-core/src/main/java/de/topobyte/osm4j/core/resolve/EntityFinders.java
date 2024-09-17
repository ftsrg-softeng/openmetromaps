package de.topobyte.osm4j.core.resolve;

public class EntityFinders {
   public static EntityFinder create(OsmEntityProvider provider, EntityNotFoundStrategy strategy) {
      switch (strategy) {
         case IGNORE:
         default:
            return new EntityFinderIgnoreMissing(provider);
         case THROW:
            return new EntityFinderThrowMissing(provider);
         case LOG_DEBUG:
            return new EntityFinderLogMissing(provider) {
               @Override
               protected void log(String message) {
                  logger.debug(message);
               }
            };
         case LOG_INFO:
            return new EntityFinderLogMissing(provider) {
               @Override
               protected void log(String message) {
                  logger.info(message);
               }
            };
         case LOG_WARN:
            return new EntityFinderLogMissing(provider) {
               @Override
               protected void log(String message) {
                  logger.warn(message);
               }
            };
      }
   }
}
