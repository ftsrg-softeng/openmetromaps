package org.openmetromaps.maps;

import org.openmetromaps.maps.model.*;

import java.util.*;
import java.util.stream.Stream;

public class Navigation {
    public enum RoutePreference {
        FEWER_STOPS, FEWER_TRANSFERS
    }

    public static List<Stop> findPath(ModelData model, Station src, Station dst, RoutePreference preference, Set<Line> excludedLines) {
        int transferWeight, stopWeight;
        switch (preference) {
            case FEWER_STOPS -> {
                transferWeight = 0;
                stopWeight = 1;
            }
            case FEWER_TRANSFERS -> {
                transferWeight = 1;
                stopWeight = 0;
            }
            default -> throw new IllegalStateException("Unexpected value: " + preference);
        }

        Map<Stop, Vertex> stops2Vertex = new HashMap<>();
        DestinationVertex dstVertex = new DestinationVertex();
        Dijkstra.Config config = new Dijkstra.Config(src, dst, stops2Vertex, dstVertex, transferWeight, stopWeight, excludedLines);
        model.stations.stream()
                .flatMap(station -> station.getStops().stream())
                .forEach(s -> config.stops2Vertex.put(s, new Vertex(s, config)));

        Dijkstra.calculateShortestPathFromSource(new SourceVertex(config));

        return filterRedundantTransfers(dstVertex.getShortestPath().stream().skip(1).map(v -> v.stop).toList());
    }

    private static List<Stop> filterRedundantTransfers(List<Stop> path) {
        for (int i = 0; i + 2 < path.size(); i++) {
            if (path.get(i).getStation() == path.get(i + 1).getStation() && path.get(i).getStation() == path.get(i + 2).getStation())
                path.remove(i + 1);
        }

        return path;
    }

    // =====================================================
    // based on https://www.baeldung.com/java-dijkstra
    private static class Vertex {
        private final Stop stop;
        protected final Dijkstra.Config config;

        private LinkedList<Vertex> shortestPath = new LinkedList<>();

        private Integer distance = Integer.MAX_VALUE;

        public Vertex(Stop stop, Dijkstra.Config config) {
            this.stop = stop;
            this.config = config;
        }

        public Stream<AbstractMap.SimpleEntry<Vertex, Integer>> getAdjacentNodes() {
            if (stop.getStation() == config.dst)
                return Stream.of(new AbstractMap.SimpleEntry<>(config.dstVertex, 0));

            var transferTargets = stop.getStation().getStops().stream().map(s -> new AbstractMap.SimpleEntry<>(config.stops2Vertex.get(s), config.transferWeight));
            var neighbouringStopsOnLine = StationUtil.getNeighbouringStops(stop).stream().map(s -> new AbstractMap.SimpleEntry<>(config.stops2Vertex.get(s), config.stopWeight));

            if (stop.getStation() == config.src)
                transferTargets = Stream.empty();

            return Stream.concat(transferTargets, neighbouringStopsOnLine)
                    .filter(pair -> !config.excludedLines.contains(pair.getKey().stop.getLine()));
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public List<Vertex> getShortestPath() {
            return shortestPath;
        }

        public void setShortestPath(LinkedList<Vertex> shortestPath) {
            this.shortestPath = shortestPath;
        }

    }

    private static class SourceVertex extends Vertex {
        public SourceVertex(Dijkstra.Config config) {
            super(null, config);
        }

        @Override
        public Stream<AbstractMap.SimpleEntry<Vertex, Integer>> getAdjacentNodes() {
            return config.src.getStops().stream().map(s -> new AbstractMap.SimpleEntry<>(config.stops2Vertex.get(s), 0));
        }
    }

    private static class DestinationVertex extends Vertex {
        public DestinationVertex() {
            super(null, null);
        }

        @Override
        public Stream<AbstractMap.SimpleEntry<Vertex, Integer>> getAdjacentNodes() {
            return Stream.empty();
        }
    }

    private static class Dijkstra {
        private record Config(Station src, Station dst, Map<Stop, Vertex> stops2Vertex, DestinationVertex dstVertex,
                              int transferWeight, int stopWeight, Set<Line> excludedLines) {
        }

        public static void calculateShortestPathFromSource(Vertex source) {
            source.setDistance(0);

            Set<Vertex> settledVertices = new HashSet<>();
            Set<Vertex> unsettledVertices = new HashSet<>();
            unsettledVertices.add(source);

            while (unsettledVertices.size() != 0) {
                Vertex currentVertex = getLowestDistanceNode(unsettledVertices);
                unsettledVertices.remove(currentVertex);
                for (var adjacencyPair : (Iterable<AbstractMap.SimpleEntry<Vertex, Integer>>) currentVertex.getAdjacentNodes()::iterator) {
                    Vertex adjacentVertex = adjacencyPair.getKey();
                    Integer edgeWeigh = adjacencyPair.getValue();

                    if (!settledVertices.contains(adjacentVertex)) {
                        CalculateMinimumDistance(adjacentVertex, edgeWeigh, currentVertex);
                        unsettledVertices.add(adjacentVertex);
                    }
                }
                settledVertices.add(currentVertex);
            }
        }

        private static void CalculateMinimumDistance(Vertex evaluationVertex, Integer edgeWeigh, Vertex sourceVertex) {
            Integer sourceDistance = sourceVertex.getDistance();
            if (sourceDistance + edgeWeigh < evaluationVertex.getDistance()) {
                evaluationVertex.setDistance(sourceDistance + edgeWeigh);
                LinkedList<Vertex> shortestPath = new LinkedList<>(sourceVertex.getShortestPath());
                shortestPath.add(sourceVertex);
                evaluationVertex.setShortestPath(shortestPath);
            }
        }

        private static Vertex getLowestDistanceNode(Set<Vertex> unsettledVertices) {
            Vertex lowestDistanceVertex = null;
            int lowestDistance = Integer.MAX_VALUE;
            for (Vertex vertex : unsettledVertices) {
                int nodeDistance = vertex.getDistance();
                if (nodeDistance < lowestDistance) {
                    lowestDistance = nodeDistance;
                    lowestDistanceVertex = vertex;
                }
            }
            return lowestDistanceVertex;
        }
    }
}
