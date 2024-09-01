package de.topobyte.osm4j.extra.extracts.query;

import com.vividsolutions.jts.geom.Geometry;
import de.topobyte.jts.utils.predicate.PredicateEvaluatorPrepared;
import de.topobyte.osm4j.extra.extracts.BatchFileNames;
import de.topobyte.osm4j.extra.extracts.ExtractionPaths;
import de.topobyte.osm4j.extra.extracts.TreeFileNames;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.nio.file.Path;

public class RegionQuery extends Query {
   public RegionQuery(
      Geometry region,
      Path pathOutput,
      Path pathTmp,
      ExtractionPaths paths,
      TreeFileNames treeNames,
      BatchFileNames relationNames,
      FileFormat inputFormat,
      OsmOutputConfig outputConfigIntermediate,
      OsmOutputConfig outputConfig,
      boolean keepTmp,
      boolean fastRelationTests,
      RelationFilter relationFilter
   ) {
      super(
         region.getEnvelopeInternal(),
         new PredicateEvaluatorPrepared(region),
         pathOutput,
         pathTmp,
         paths,
         treeNames,
         relationNames,
         inputFormat,
         outputConfigIntermediate,
         outputConfig,
         keepTmp,
         fastRelationTests,
         relationFilter
      );
   }
}
