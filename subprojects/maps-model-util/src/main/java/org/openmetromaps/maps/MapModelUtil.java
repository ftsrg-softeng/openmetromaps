// Copyright 2017 Sebastian Kuerten
//
// This file is part of OpenMetroMaps.
//
// OpenMetroMaps is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// OpenMetroMaps is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with OpenMetroMaps. If not, see <http://www.gnu.org/licenses/>.

package org.openmetromaps.maps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openmetromaps.maps.model.Line;
import org.openmetromaps.maps.model.ModelData;
import org.openmetromaps.maps.model.Station;
import org.openmetromaps.maps.model.Stop;

public class MapModelUtil
{

	public static List<Edges> allEdges(MapModel model)
	{
		return allEdges(model.getData());
	}

	public static List<Edges> allEdges(ModelData data)
	{
		List<Edges> edges = new ArrayList<>();
		Set<String> names = new HashSet<>();
		for (Line line : data.lines) {
			if (names.contains(line.getName())) {
				continue;
			}
			names.add(line.getName());
			edges.add(new Edges(line.getName()));
		}
		return edges;
	}

	public static Station findStation(List<Station> stations, String name)
	{
		for (Station station : stations) {
			if (station.getName().equals(name)) {
				return station;
			}
		}
		return null;
	}

	public static int findStop(List<Stop> stops, String stationName)
	{
		for (int i = 0; i < stops.size(); i++) {
			Stop stop = stops.get(i);
			if (stop.getStation().getName().equals(stationName)) {
				return i;
			}
		}
		return -1;
	}

	public static void sortStationsByName(List<Station> stations)
	{
		Collections.sort(stations, new StationComparatorByName());
	}

	public static void sortLinesByName(List<Line> lines)
	{
		Collections.sort(lines, new LineComparatorByName());
	}

	public static boolean modelsSemanticallyEqual(ModelData dataA, ModelData dataB) {
		return
			dataA.stations.stream().allMatch(stationA -> dataB.stations.stream().anyMatch(stationB -> stationsSemanticallyEqual(stationA, stationB))) &&
			dataA.lines.stream().allMatch(lineA -> dataB.lines.stream().anyMatch(lineB -> linesSemanticallyEqual(lineA, lineB)));
	}

	private static boolean stationsSemanticallyEqual(Station stationA, Station stationB) {
		List<String> stationALines = stationA.getStops().stream().map(s -> s.getLine().getName()).sorted().toList();
		List<String> stationBLines = stationB.getStops().stream().map(s -> s.getLine().getName()).sorted().toList();

		return
			stationA.getName().equals(stationB.getName()) &&
			doubleEqual(stationA.getLocation().getLatitude(), stationB.getLocation().getLatitude()) &&
			doubleEqual(stationA.getLocation().getLongitude(), stationB.getLocation().getLongitude()) &&
			stationALines.equals(stationBLines);
	}

	private static boolean linesSemanticallyEqual(Line lineA, Line lineB) {
		List<String> lineAStops = lineA.getStops().stream().map(s -> s.getStation().getName()).toList();
		List<String> lineBStops = lineB.getStops().stream().map(s -> s.getStation().getName()).toList();

		return
			lineA.getName().equals(lineB.getName()) &&
			lineAStops.equals(lineBStops);
	}

	private static boolean doubleEqual(double d1, double d2) {
		return Math.abs(d1 - d2) < 0.0001;
	}
}
