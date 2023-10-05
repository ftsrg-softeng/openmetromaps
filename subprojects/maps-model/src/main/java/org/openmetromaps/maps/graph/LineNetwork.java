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

package org.openmetromaps.maps.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openmetromaps.maps.model.Station;

public class LineNetwork
{

	public List<Node> nodes = new ArrayList<>();
	public List<Edge> edges = new ArrayList<>();
	public List<NetworkLine> lines = new ArrayList<>();

	private Map<Station, Node> stationToNode;

	public List<Node> getNodes()
	{
		return nodes;
	}

	public List<Edge> getEdges()
	{
		return edges;
	}

	public List<NetworkLine> getLines()
	{
		return lines;
	}

	public Map<Station, Node> getStationToNode()
	{
		return stationToNode;
	}

	public void setStationToNode(Map<Station, Node> stationToNode)
	{
		this.stationToNode = stationToNode;
	}

}
