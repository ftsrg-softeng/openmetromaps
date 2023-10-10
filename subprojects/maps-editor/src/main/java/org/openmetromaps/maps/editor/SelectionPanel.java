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

package org.openmetromaps.maps.editor;

import de.topobyte.awt.util.GridBagConstraintsEditor;
import org.openmetromaps.maps.MapModelModification;
import org.openmetromaps.maps.graph.NetworkLine;
import org.openmetromaps.maps.graph.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class SelectionPanel extends JPanel
{

	final static Logger logger = LoggerFactory.getLogger(SelectionPanel.class);

	private static final long serialVersionUID = 1L;

	private MapEditor mapEditor;

	private Set<Node> nodes;

	private Map<Node, Map<NetworkLine, Boolean>> selectedLinesForNodes;

	public SelectionPanel(MapEditor mapEditor)
	{
		super(new GridLayout(0, 1));
		this.mapEditor = mapEditor;
		this.nodes = new HashSet<>();
		this.selectedLinesForNodes = new HashMap<>();

		setupLayout();

		mapEditor.addDataChangeListener(this::refresh);
	}

	private void setupLayout()
	{
		JPanel panel = new JPanel(new GridBagLayout());

		GridBagConstraintsEditor ce = new GridBagConstraintsEditor();
		GridBagConstraints c = ce.getConstraints();

		ce.fill(GridBagConstraints.BOTH);
		ce.weight(0, 0);
		c.insets = new Insets(0, 4, 4, 4);

		int lineCount = 0;
		for(Node node : nodes) {
			JLabel stationLabel = new JLabel(node.station.getName());
			ce.gridPos(0, lineCount++);
			panel.add(stationLabel, c);

			Set<NetworkLine> lines = new HashSet<>();
			node.edges.forEach(e -> lines.addAll(e.lines));
			JPanel linesPanel = new JPanel(new GridLayout(0, lines.size(), 4, 2));
			linesPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
			for(NetworkLine line : lines) {
				JLabel lineLabel = new JLabel(line.line.getName(), SwingConstants.CENTER);
				lineLabel.setOpaque(true);
				lineLabel.setBackground(Color.decode(line.line.getColor()));
				linesPanel.add(lineLabel);
			}
			if (nodes.size() == 1) {
				for(NetworkLine line : lines) {
					JCheckBox lineCheckbox = new JCheckBox();
					lineCheckbox.setSelected(selectedLinesForNodes.get(node).get(line));
					lineCheckbox.addActionListener(a -> {
						selectedLinesForNodes.get(node).put(line, lineCheckbox.isSelected());
						mapEditor.triggerDataChanged();
					});
					linesPanel.add(lineCheckbox);
				}
			}
			ce.gridPos(0, lineCount++);
			panel.add(linesPanel, c);
		}

		JPanel buttonPanel = new JPanel(new GridLayout(0, 4, 2, 0));

		JButton mergeButton = new JButton("Merge");
		mergeButton.setEnabled(nodes.size() == 2);
		mergeButton.addActionListener(e -> {
			Iterator<Node> iterator = nodes.iterator();
			MapModelModification.mergeStations(mapEditor.getModel().getData(), iterator.next().station, iterator.next().station);
			mapEditor.triggerMapChanged();
		});
		ce.gridPos(0, lineCount);
		buttonPanel.add(mergeButton);

		JButton deleteButton = new JButton("Delete");
		deleteButton.setEnabled(nodes.size() == 1);
		deleteButton.addActionListener(e -> {
			MapModelModification.deleteStation(mapEditor.getModel().getData(), nodes.iterator().next().station);
			mapEditor.triggerMapChanged();
		});
		ce.gridPos(1, lineCount);
		buttonPanel.add(deleteButton);

		JButton splitButton = new JButton("Split");
		splitButton.setEnabled(splitButtonEnabled());
		splitButton.addActionListener(e -> {
			Node node = nodes.iterator().next();
			MapModelModification.splitStation(
					mapEditor.getModel().getData(), node.station,
					selectedLinesForNodes.get(node).entrySet().stream()
							.filter(Map.Entry::getValue)
							.map(Map.Entry::getKey)
							.map(nl -> nl.line)
							.collect(Collectors.toSet())
			);
			mapEditor.triggerMapChanged();
		});
		ce.gridPos(2, lineCount);
		buttonPanel.add(splitButton);

		JButton newButton = new JButton("New");
		newButton.setEnabled(newButtonEnabled());
		newButton.addActionListener(e -> {
			if(nodes.size() == 1) {
				MapModelModification.newStationAtEndOfLine(mapEditor.getModel().getData(), nodes.iterator().next().station);
			}
			else if(nodes.size() == 2) {
				Iterator<Node> iterator = nodes.iterator();
				MapModelModification.newStationBetweenTwoNeighbouringNodes(mapEditor.getModel().getData(), iterator.next().station, iterator.next().station);
			}
			mapEditor.triggerMapChanged();
		});
		ce.gridPos(3, lineCount);
		buttonPanel.add(newButton);

		ce.gridPos(0, lineCount++);
		panel.add(buttonPanel, c);

		ce.gridPos(0, lineCount);
		ce.weight(1, 1);
		panel.add(new JPanel(), c);

		add(new JScrollPane(panel));
	}

	public void setNodes(Set<Node> nodes)
	{
		this.nodes = nodes;
		selectedLinesForNodes = new HashMap<>();
		for(Node node : nodes) {
			Map<NetworkLine, Boolean> selectedLines = new HashMap<>();
			Set<NetworkLine> lines = new HashSet<>();
			node.edges.forEach(e -> lines.addAll(e.lines));
			for(NetworkLine line : lines) {
				selectedLines.put(line, false);
			}
			selectedLinesForNodes.put(node, selectedLines);
		}
		refresh();
	}

	protected void refresh()
	{
		removeAll();
		setupLayout();
		revalidate();
		repaint();
	}

	private boolean splitButtonEnabled() {
		if(nodes.size() != 1) return false;

		Map<NetworkLine, Boolean> selectedLines = selectedLinesForNodes.get(nodes.iterator().next());
		boolean firstEntry = selectedLines.entrySet().iterator().next().getValue();
		return selectedLines.entrySet().stream().anyMatch(e -> e.getValue() != firstEntry);
	}
	
	private boolean newButtonEnabled() {
		if(nodes.size() == 1) {
			return nodes.iterator().next().isLastStopOfALine;
		}
		else if(nodes.size() == 2) {
			Iterator<Node> nodeIterator = nodes.iterator();
			Node nodeA = nodeIterator.next();
			Node nodeB = nodeIterator.next();
			
			return nodeA.edges.stream().anyMatch(e -> e.n1 == nodeB || e.n2 == nodeB);
		}
		else {
			return false;
		}
	}
}
