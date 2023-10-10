package org.openmetromaps.maps.editor;

import de.topobyte.awt.util.GridBagConstraintsEditor;
import org.openmetromaps.maps.DataChangeListener;
import org.openmetromaps.maps.graph.NetworkLine;
import org.openmetromaps.maps.model.Line;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;

public class LinesPanel extends JPanel {
    final static Logger logger = LoggerFactory.getLogger(StationPanel.class);

    private static final long serialVersionUID = 1L;

    private MapEditor mapEditor;

    private Map<Line, Boolean> linesToShow;

    private Map<Line, Boolean> linesSelected;

    public LinesPanel(MapEditor mapEditor) {
        super(new GridLayout(0, 1));
        this.mapEditor = mapEditor;

        setupLayout();

        mapEditor.addDataChangeListener(this::refresh);
    }

    private void setupLayout() {
        if (this.linesToShow == null) {
            this.linesToShow = new HashMap<>();
            this.linesSelected = new HashMap<>();
            for (Line line : this.mapEditor.getModel().getData().lines) {
                this.linesToShow.put(line, true);
                this.linesSelected.put(line, false);
            }
        }

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraintsEditor ce = new GridBagConstraintsEditor();
        GridBagConstraints c = ce.getConstraints();

        ce.fill(GridBagConstraints.BOTH);
        ce.weight(0, 0);
        c.insets = new Insets(0, 4, 4, 4);

        int lineCount = 0;
        for (Line line : mapEditor.getModel().getData().lines) {
            JCheckBox lineShowCheckbox = new JCheckBox();
            lineShowCheckbox.setSelected(linesToShow.get(line));
            lineShowCheckbox.addActionListener(e -> {
                linesToShow.put(line, lineShowCheckbox.isSelected());
                NetworkLine networkLine = mapEditor.getView().getLineNetwork().getLines().stream().filter(nl -> nl.line.equals(line)).findAny().get();
                if (lineShowCheckbox.isSelected()) {
                    mapEditor.unhideLine(networkLine);
                }
                else {
                    mapEditor.hideLine(networkLine);
                }
            });
            ce.gridPos(0, lineCount);
            panel.add(lineShowCheckbox, c);

            JCheckBox lineSelectedCheckbox = new JCheckBox();
            lineSelectedCheckbox.setSelected(linesSelected.get(line));
            lineSelectedCheckbox.addActionListener(e -> {
                linesSelected.put(line, lineSelectedCheckbox.isSelected());
                NetworkLine networkLine = mapEditor.getView().getLineNetwork().getLines().stream().filter(nl -> nl.line.equals(line)).findAny().get();
                if (lineSelectedCheckbox.isSelected()) {
                    mapEditor.selectLine(networkLine);
                }
                else {
                    mapEditor.unselectLine(networkLine);
                }
            });
            ce.gridPos(1, lineCount);
            panel.add(lineSelectedCheckbox, c);

            JLabel lineLabel = new JLabel(line.getName(), SwingConstants.CENTER);
            lineLabel.setOpaque(true);
            lineLabel.setBackground(Color.decode(line.getColor()));
            ce.gridPos(2, lineCount);
            panel.add(lineLabel, c);

            JLabel terminusLabel = new JLabel(
                line.getStops().get(0).getStation().getName() +
                " - " +
                line.getStops().get(line.getStops().size() - 1).getStation().getName()
            );
            ce.gridPos(3, lineCount);
            panel.add(terminusLabel, c);

            lineCount++;
        }

        ce.gridPos(0, lineCount);
        ce.weight(1, 1);
        ce.gridWidth(4);
        panel.add(new JPanel(), c);

        add(new JScrollPane(panel));
    }

    protected void refresh() {
        removeAll();
        setupLayout();
    }
}
