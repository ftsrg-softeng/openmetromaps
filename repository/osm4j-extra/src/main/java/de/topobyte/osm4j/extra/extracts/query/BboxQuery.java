package de.topobyte.osm4j.extra.extracts.query;

import de.topobyte.adt.geo.BBox;
import de.topobyte.jts.utils.predicate.PredicateEvaluatorRectangle;
import de.topobyte.osm4j.extra.extracts.BatchFileNames;
import de.topobyte.osm4j.extra.extracts.ExtractionPaths;
import de.topobyte.osm4j.extra.extracts.TreeFileNames;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.nio.file.Path;

public class BboxQuery extends Query {
   public BboxQuery(
      BBox bbox,
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
         bbox.toEnvelope(),
         new PredicateEvaluatorRectangle(bbox.getLon1(), bbox.getLat2(), bbox.getLon2(), bbox.getLat1()),
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
