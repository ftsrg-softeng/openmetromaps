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
import org.apache.commons.lang3.tuple.Pair;
import org.openmetromaps.maps.Navigation;
import org.openmetromaps.maps.graph.Edge;
import org.openmetromaps.maps.graph.NetworkLine;
import org.openmetromaps.maps.graph.Node;
import org.openmetromaps.maps.model.Line;
import org.openmetromaps.maps.model.Stop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NavigationPanel extends JPanel {

    final static Logger logger = LoggerFactory.getLogger(NavigationPanel.class);

    private static final long serialVersionUID = 1L;

    private MapEditor mapEditor;

    private Set<Node> nodes;

    Navigation.RoutePreference preference = Navigation.RoutePreference.FEWER_STOPS;
    private Set<NetworkLine> excludedLines;

    public NavigationPanel(MapEditor mapEditor) {
        super(new GridLayout(0, 1));
        this.mapEditor = mapEditor;
        this.nodes = new HashSet<>();
        this.excludedLines = new HashSet<>();

        setupLayout();

        mapEditor.addDataChangeListener(this::refresh);
    }

    private void setupLayout() {
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraintsEditor ce = new GridBagConstraintsEditor();
        GridBagConstraints c = ce.getConstraints();

        ce.fill(GridBagConstraints.BOTH);
        ce.weight(0, 0);
        c.insets = new Insets(0, 2, 4, 4);

        int lineCount = 0;
        JPanel preferencePanel = new JPanel(new GridLayout(0, 2, 2, 0));

        ce.gridPos(0, lineCount++);
        panel.add(new JLabel(excludedLines.isEmpty()
                ? "No lines are excluded on the Lines Panel."
                : "%s lines are excluded.".formatted(excludedLines.size())), c);

        ButtonGroup bg = new ButtonGroup();
        JRadioButton fewerStopsButton = new JRadioButton("Fewer stops");
        JRadioButton fewerTransfersButton = new JRadioButton("Fewer transfers");
        fewerStopsButton.setSelected(preference == Navigation.RoutePreference.FEWER_STOPS);
        fewerTransfersButton.setSelected(preference == Navigation.RoutePreference.FEWER_TRANSFERS);
        ActionListener preferenceListener = a -> {
            if (fewerStopsButton.isSelected())
                preference = Navigation.RoutePreference.FEWER_STOPS;
            else if (fewerTransfersButton.isSelected())
                preference = Navigation.RoutePreference.FEWER_TRANSFERS;
        };
        fewerStopsButton.addActionListener(preferenceListener);
        fewerTransfersButton.addActionListener(preferenceListener);
        preferencePanel.add(fewerStopsButton);
        preferencePanel.add(fewerTransfersButton);
        bg.add(fewerStopsButton);
        bg.add(fewerTransfersButton);

        ce.gridPos(0, lineCount++);
        panel.add(preferencePanel, c);

        JButton navigateButton = new JButton("Navigate");
        navigateButton.setEnabled(nodes.size() == 2);
        navigateButton.addActionListener(e -> {
            var iter = nodes.iterator();
            var from = iter.next().station;
            var to = iter.next().station;
            List<Stop> path = Navigation.findPath(mapEditor.getModel().getData(), from, to, preference,
                    excludedLines.stream().map(nl -> nl.line).collect(Collectors.toSet()));

            List<Pair<Edge, NetworkLine>> edgeLines = IntStream.range(1, path.size())
                    .mapToObj(i -> Pair.of(path.get(i - 1), path.get(i)))
                    .filter(p -> p.getLeft().getLine() == p.getRight().getLine())
                    .map(p -> {
                        List<Edge> edges1 = mapEditor.getView().getLineNetwork().getStationToNode().get(p.getLeft().getStation()).edges;
                        List<Edge> edges2 = mapEditor.getView().getLineNetwork().getStationToNode().get(p.getRight().getStation()).edges;
                        return Pair.of(edges1.stream().filter(edges2::contains).findAny().get(),
                                mapEditor.getView().getLineNetwork().lines.stream().filter(nl -> nl.line == p.getLeft().getLine()).findAny().get());
                    }).toList();

            mapEditor.removeHighlight();
            mapEditor.highlightPath(path, edgeLines);
            mapEditor.triggerDataChanged();
        });

        ce.gridPos(0, lineCount++);
        panel.add(navigateButton, c);

        List<Stop> highlightedPath = mapEditor.getMapViewStatus().getHighlightedPath();
        if (highlightedPath.size() < 2) {
            ce.gridPos(0, lineCount++);
            panel.add(new JLabel("No route found."), c);
        } else {
            Line lastLine = null;
            for (Stop stop : highlightedPath) {
                Line line = stop.getLine();

                if (line == lastLine)
                    continue;
                lastLine = line;

                ce.gridPos(0, lineCount++);
                panel.add(new JLabel(stop.getStation().getName()), c);

                JLabel lineLabel = new JLabel(line.getName(), SwingConstants.CENTER);
                lineLabel.setOpaque(true);
                lineLabel.setBackground(Color.decode(line.getColor()));
                ce.gridPos(0, lineCount++);
                panel.add(lineLabel, c);
            }
            Stop lastStop = highlightedPath.get(highlightedPath.size() - 1);
            ce.gridPos(0, lineCount++);
            panel.add(new JLabel(lastStop.getStation().getName()), c);
        }

        ce.gridPos(0, lineCount);
        ce.weight(1, 1);
        panel.add(new JPanel(), c);

        add(new JScrollPane(panel));
    }

    public void setSelection(Set<Node> nodes, Set<NetworkLine> selectedLines) {
        this.nodes = nodes;
        this.excludedLines = selectedLines;
        refresh();
    }

    protected void refresh() {
        removeAll();
        setupLayout();
        revalidate();
        repaint();
    }
}
