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
import java.util.ArrayList;
import java.util.Collections;

import org.openmetromaps.maps.graph.Node;

public class MapViewStatus
{

	private List<Node> selectedNodes = new ArrayList<>();

	public boolean isNodeSelected(Node node)
	{
		return selectedNodes.contains(node);
	}

	public void selectNode(Node node)
	{
		selectedNodes.add(node);
	}

	public void unselectNode(Node node)
	{
		selectedNodes.remove(node);
	}

	public void selectNoNodes()
	{
		selectedNodes.clear();
	}

	public int getNumSelectedNodes()
	{
		return selectedNodes.size();
	}

	public List<Node> getSelectedNodes()
	{
		return Collections.unmodifiableList(selectedNodes);
	}

}
