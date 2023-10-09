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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.openmetromaps.maps.graph.NetworkLine;
import org.openmetromaps.maps.graph.Node;

public class MapViewStatus
{

	private Set<Node> selectedNodes = new HashSet<>();
	private Set<NetworkLine> hiddenLines = new HashSet<>();
	private Set<NetworkLine> selectedLines = new HashSet<>();

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

	public Set<Node> getSelectedNodes()
	{
		return Collections.unmodifiableSet(selectedNodes);
	}

	public boolean isLineHidden(NetworkLine line) {
		return hiddenLines.contains(line);
	}

	public void hideLine(NetworkLine line) {
		hiddenLines.add(line);
	}

	public void unhideLine(NetworkLine line) {
		hiddenLines.remove(line);
	}

	public void hideNoLines() {
		hiddenLines.clear();
	}

	public int getNumHiddenLines() {
		return hiddenLines.size();
	}

	public Set<NetworkLine> getHiddenLines() {
		return Collections.unmodifiableSet(hiddenLines);
	}

	public boolean isLineSelected(NetworkLine line) {
		return selectedLines.contains(line);
	}

	public void selectLine(NetworkLine line) {
		selectedLines.add(line);
	}

	public void unselectLine(NetworkLine line) {
		selectedLines.remove(line);
	}

	public void selectNoLines() {
		selectedLines.clear();
	}

	public int getNumSelectedLines() {
		return selectedLines.size();
	}

	public Set<NetworkLine> getSelectedLines() {
		return Collections.unmodifiableSet(selectedLines);
	}
}
