// Copyright 2018 Sebastian Kuerten
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

package org.openmetromaps.model.gtfs;

import java.util.*;

import org.openmetromaps.gtfs.DraftLine;
import org.openmetromaps.gtfs.DraftModel;
import org.openmetromaps.gtfs.DraftStation;
import org.openmetromaps.maps.ThreadSafeDistributedMap;
import org.openmetromaps.maps.model.Coordinate;
import org.openmetromaps.maps.model.Line;
import org.openmetromaps.maps.model.ModelData;
import org.openmetromaps.maps.model.Station;
import org.openmetromaps.maps.model.Stop;

import com.google.common.base.Strings;

public class DraftModelConverter
{

	protected List<Line> linesList = new ArrayList<>();
	protected List<Station> stationsList = new ArrayList<>();
	protected List<Stop> stopsList = new ArrayList<>();

	protected Map<Line, Integer> lineToIndex = new HashMap<>();
	protected Map<Station, Integer> stationToIndex = new HashMap<>();
	protected Map<Stop, Integer> stopToIndex = new HashMap<>();

	public ModelData convert(DraftModel draftModel)
	{
		List<DraftLine> draftLines = draftModel.getLines();

		Map<DraftLine, Line> draftToLine = new ThreadSafeDistributedMap<>();
		Map<String, Station> idToStation = new ThreadSafeDistributedMap<>();

		int id = 0;
		for (DraftLine draftLine : draftLines) {
			String name = draftLine.getSource();
			String color = draftLine.getColor();
			if (!Strings.isNullOrEmpty(color) && color.length() == 6) {
				color = "#" + color;
			} else {
				color = "#FFFFFF";
			}

			Line line = new Line(id++, name, color, false, null);
			linesList.add(line);
			draftToLine.put(draftLine, line);
		}

        for (Map.Entry<DraftLine, Line> entry : draftToLine.entrySet()) {
            DraftLine draftLine = entry.getKey();
			Line line = entry.getValue();
			List<Stop> stops = new ArrayList<>();
			line.setStops(stops);

			for (DraftStation draftStation : draftLine.getStations()) {
				String stopId = draftStation.getId();

				Station station = idToStation.get(stopId);
				if (station == null) {
					Coordinate location = new Coordinate(draftStation.getLon(),
							draftStation.getLat());

					station = new Station(idToStation.size(), draftStation.getName(), location,
							new ArrayList<Stop>());
					stationsList.add(station);

					idToStation.put(stopId, station);
				}

				Stop stop = new Stop(station, line);
				stops.add(stop);
				station.getStops().add(stop);
			}
		}

		for (int i = 0; i < linesList.size(); i++) {
			Line line = linesList.get(i);
			lineToIndex.put(line, i);
		}

		int k = -1;
		for (int i = 0; i < stationsList.size(); i++) {
			Station station = stationsList.get(i);
			stationToIndex.put(station, i);

			List<Stop> stops = station.getStops();
			for (Stop stop : stops) {
				k++;
				stopToIndex.put(stop, k);
				stopsList.add(stop);
			}
		}

		return new ModelData(linesList, stationsList);
	}

	private boolean closeEnough(Coordinate loc1, Coordinate loc2) {
		double R = 6378.137; // Radius of earth in KM
		double dLat = loc2.getLatitude() * Math.PI / 180 - loc1.getLatitude() * Math.PI / 180;
		double dLon = loc2.getLongitude() * Math.PI / 180 - loc1.getLongitude() * Math.PI / 180;
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(loc1.getLatitude() * Math.PI / 180) * Math.cos(loc2.getLatitude() * Math.PI / 180) * Math.sin(dLon/2) * Math.sin(dLon/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = R * c * 1000; // meters
		return d < 200;
	}

}
