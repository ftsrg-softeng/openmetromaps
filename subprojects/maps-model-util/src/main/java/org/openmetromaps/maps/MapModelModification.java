package org.openmetromaps.maps;

import org.openmetromaps.maps.model.*;
import org.openmetromaps.maps.model.ModelData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MapModelModification {
    public static void mergeStations(ModelData model, Station stationA, Station stationB) {
        for (Stop stopToMerge : stationB.getStops()) {
            if (StationUtil.getNeighbouringStops(stopToMerge).stream().anyMatch(neighbour -> stationA.getStops().contains(neighbour))) {
                stopToMerge.getLine().getStops().remove(stopToMerge);
            }
            else {
                stationA.getStops().add(stopToMerge);
                stopToMerge.setStation(stationA);
            }
            model.stations.remove(stationB);
        }
    }

    public static void deleteStation(ModelData model, Station station) {
        for (Stop stopToDelete : station.getStops()) {
            stopToDelete.getLine().getStops().remove(stopToDelete);
            model.stations.remove(station);
        }
    }

    public static void splitStation(ModelData model, Station station, Set<Line> linesInOneStop) {
        List<Stop> newStops = new ArrayList<>();
        Station newStation = new Station(
                model.stations.size(),
                "New",
                new Coordinate(station.getLocation().getLongitude(), station.getLocation().getLatitude()+0.001),
                newStops
        );

        for(Stop stop : station.getStops()) {
            if(linesInOneStop.contains(stop.getLine())) {
                stop.setStation(newStation);
                newStops.add(stop);
            }
        }

        station.getStops().removeAll(station.getStops().stream().filter(s -> linesInOneStop.contains(s.getLine())).toList());

        model.stations.add(newStation);
    }

    public static void newStationAtEndOfLine(ModelData model, Station station) {
        List<Stop> newStops = new ArrayList<>();
        Station newStation = new Station(
                model.stations.size(),
                "New",
                new Coordinate(station.getLocation().getLongitude(), station.getLocation().getLatitude()+0.001),
                newStops
        );
        for(Stop stop : station.getStops()) {
            if(StationUtil.isStopEndOfLine(stop)) {
                List<Stop> stopsOfLine = stop.getLine().getStops();
                Stop newStop = new Stop(newStation, stop.getLine());
                if(stopsOfLine.get(0) == stop) {
                    stopsOfLine.add(0, newStop);
                }
                else {
                    stopsOfLine.add(newStop);
                }
                newStops.add(newStop);
            }
        }
        model.stations.add(newStation);
    }

    public static void newStationBetweenTwoNeighbouringNodes(ModelData model, Station stationA, Station stationB) {
        List<Stop> newStops = new ArrayList<>();
        Station newStation = new Station(
                model.stations.size(),
                "New",
                new Coordinate(
                        stationA.getLocation().getLongitude()*0.5+stationB.getLocation().getLongitude()*0.5,
                        stationA.getLocation().getLatitude()*0.5+stationB.getLocation().getLatitude()*0.5
                ),
                newStops
        );

        for (Stop stopA : stationA.getStops()) {
            List<Stop> neighbourStopsA = StationUtil.getNeighbouringStops(stopA);
            for (Stop stopB : stationB.getStops()) {
                if (neighbourStopsA.contains(stopB)) {
                    List<Stop> stopsOfLine = stopA.getLine().getStops();
                    Stop newStop = new Stop(newStation, stopA.getLine());
                    int indexA = stopsOfLine.indexOf(stopA);
                    int indexB = stopsOfLine.indexOf(stopB);
                    if(Math.abs(indexA - indexB) == 1) {
                        stopsOfLine.add(Math.min(indexA, indexB)+1, newStop);
                    }
                    else if(stopA.getLine().isCircular() && indexA == 0 && indexB == stopsOfLine.size() - 1) {
                        stopsOfLine.add(0, newStop);
                    }
                    else if(stopA.getLine().isCircular() && indexB == 0 && indexA == stopsOfLine.size() - 1) {
                        stopsOfLine.add(0, newStop);
                    }
                }
            }
        }

        model.stations.add(newStation);
    }
}
