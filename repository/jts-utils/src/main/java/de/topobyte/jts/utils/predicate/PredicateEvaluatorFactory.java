package de.topobyte.jts.utils.predicate;

import com.vividsolutions.jts.geom.Geometry;

public interface PredicateEvaluatorFactory {
   PredicateEvaluator createPredicateEvaluator(Geometry var1);
}
