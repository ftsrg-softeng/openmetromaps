package org.openmetromaps.maps;

import org.openmetromaps.maps.model.ModelData;
import org.openmetromaps.maps.model.Station;
import org.openmetromaps.maps.model.Stop;

import java.util.ArrayList;
import java.util.List;

public class Navigation {
    public enum PathOptimization {
        LEAST_STOPS, LEAST_TRANSFERS
    }

    public static List<Stop> findPath(ModelData model, Station src, Station dst, PathOptimization pathOptimization) {
        return new ArrayList<>();
    }
}
