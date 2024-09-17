package de.topobyte.jts.utils.predicate;

import com.vividsolutions.jts.geom.Geometry;

public class PredicateEvaluatorFactoryJtsPrepared implements PredicateEvaluatorFactory {
   @Override
   public PredicateEvaluator createPredicateEvaluator(Geometry geometry) {
      return new PredicateEvaluatorPrepared(geometry);
   }
}
