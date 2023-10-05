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

import java.util.List;

import org.openmetromaps.maps.graph.LineNetwork;

public class MapView
{

	private String name;
	private List<Edges> edges;
	private LineNetwork lineNetwork;
	private ViewConfig config;

	public MapView(String name, List<Edges> edges, LineNetwork lineNetwork,
			ViewConfig config)
	{
		this.name = name;
		this.edges = edges;
		this.lineNetwork = lineNetwork;
		this.config = config;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Edges> getEdges()
	{
		return edges;
	}

	public void setEdges(List<Edges> edges)
	{
		this.edges = edges;
	}

	public LineNetwork getLineNetwork()
	{
		return lineNetwork;
	}

	public void setLineNetwork(LineNetwork lineNetwork)
	{
		this.lineNetwork = lineNetwork;
	}

	public ViewConfig getConfig()
	{
		return config;
	}

	public void setConfig(ViewConfig config)
	{
		this.config = config;
	}

}
