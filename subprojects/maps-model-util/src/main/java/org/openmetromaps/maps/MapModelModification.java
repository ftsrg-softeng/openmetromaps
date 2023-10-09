package org.openmetromaps.maps;

import org.openmetromaps.maps.model.Line;
import org.openmetromaps.maps.model.ModelData;
import org.openmetromaps.maps.model.Station;

import java.util.Set;

public class MapModelModification {
    public static void mergeStations(ModelData model, Station stationA, Station stationB) {
    }

    public static void deleteStation(ModelData model, Station station) {
    }

    public static void splitStation(ModelData model, Station station, Set<Line> linesInOneStop) {
    }

    public static void newStationAtEndOfLine(ModelData model, Station station) {
    }

    public static void newStationBetweenTwoNeighbouringNodes(ModelData model, Station stationA, Station stationB) {
    }
}
