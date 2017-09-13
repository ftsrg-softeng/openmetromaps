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
import java.util.List;
import java.util.Map;

import org.openmetromaps.maps.graph.Edge;
import org.openmetromaps.maps.graph.LineNetwork;
import org.openmetromaps.maps.graph.NetworkLine;
import org.openmetromaps.maps.graph.Node;
import org.openmetromaps.maps.model.Stop;
import org.openmetromaps.maps.painting.core.ColorCode;
import org.openmetromaps.maps.painting.core.IPaintInfo;
import org.openmetromaps.maps.painting.core.PaintFactory;
import org.openmetromaps.maps.painting.core.Painter;
import org.openmetromaps.maps.painting.core.geom.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.adt.geo.Coordinate;

public class StationDrawerSimple extends AbstractStationDrawer
{

	static final Logger logger = LoggerFactory
			.getLogger(StationDrawerSimple.class);

	public StationDrawerSimple(PaintFactory pf, LineNetwork data,
			Map<NetworkLine, ColorCode> colors, float scale,
			LocationToPoint ltp, float spreadFactor)
	{
		super(pf, data, colors, scale, ltp, spreadFactor);
	}

	@Override
	public void drawStation(Painter g, Node node, Path path)
	{
		List<Stop> stops = node.station.getStops();
		if (stops.size() == 1) {
			Stop stop = stops.get(0);
			IPaintInfo paint = lineToPaintForStations[stop.getLine().getId()];
			g.setPaintInfo(paint);
			double px = ltp.getX(stop.getLocation().lon);
			double py = ltp.getY(stop.getLocation().lat);
			g.drawCircle(px, py, circleRadius);
			return;
		}

		List<Edge> edges = node.edges;

		boolean moreThanDot = false;

		List<List<NetworkLine>> done = new ArrayList<>();

		Coordinate location = node.location;
		double px = ltp.getX(location.lon);
		double py = ltp.getY(location.lat);

		path.reset();
		for (Edge edge : edges) {
			List<NetworkLine> lines = edge.lines;
			moreThanDot |= lines.size() > 1;

			if (lines.size() == 1) {
				continue;
			}

			if (done.contains(lines)) {
				continue;
			}
			done.add(lines);

			SegmentEndPointPaintInfo spi = endpointInfo(edge, node, ltp,
					lineWidth, spreadFactor, lines.size());

			path.moveTo(px + spi.sx, py + spi.sy);
			path.lineTo(px + spi.ex, py + spi.ey);

			spiPool.give(spi);
		}

		if (moreThanDot) {
			g.setPaintInfo(paintStationsStrokeOutline);
			g.draw(path);
			g.setPaintInfo(paintStationsStroke);
			g.draw(path);
		} else {
			drawPuntal(g, px, py);
		}
	}

}