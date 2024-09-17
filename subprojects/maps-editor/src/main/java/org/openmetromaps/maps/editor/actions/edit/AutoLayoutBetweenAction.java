package org.openmetromaps.maps.editor.actions.edit;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.openmetromaps.maps.editor.MapEditor;
import org.openmetromaps.maps.editor.actions.MapEditorAction;
import org.openmetromaps.maps.graph.LineConnectionResult;
import org.openmetromaps.maps.graph.LineNetwork;
import org.openmetromaps.maps.graph.LineNetworkUtil;
import org.openmetromaps.maps.graph.Node;
import org.openmetromaps.maps.graph.NodeConnectionResult;
import org.openmetromaps.maps.graph.NodesInBetweenResult;
import org.openmetromaps.maps.model.Line;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.lightgeom.lina.Point;
import de.topobyte.swing.util.EmptyIcon;

public class AutoLayoutBetweenAction  extends MapEditorAction
{

	final static Logger logger = LoggerFactory
			.getLogger(AutoLayoutBetweenAction.class);

	private static final long serialVersionUID = 1L;

	public AutoLayoutBetweenAction(MapEditor mapEditor)
	{
		super(mapEditor, "Auto Layout Between",
				"Apply auto layout between two selected stations");
		setIcon(new EmptyIcon(24));
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		List<Node> selectedNodes = new ArrayList<>(mapEditor.getMapViewStatus().getSelectedNodes());

        // Exactly 2 nodes must be selected
        if (selectedNodes.size() != 2) {
			JOptionPane.showMessageDialog(mapEditor.getFrame(), "Please select exactly two stations.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

        // The selected nodes, the start node is the anchor
        Node startNode = selectedNodes.get(0);
		Node endNode = selectedNodes.get(1);
        logger.debug(String.format("Trying to layout between: '%s' and '%s'", startNode.station.getName(), endNode.station.getName()));

        NodeConnectionResult connection = LineNetworkUtil.findConnection(startNode, endNode);

        // Nodes must be connected by a line
        if (!connection.isConnected()) {
			JOptionPane.showMessageDialog(
                mapEditor.getFrame(),
                "Please select two stations that are connected with a line.",
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
			return;
		}

        // One of the lines which connects start and end
        Line line = connection.getCommonLines().iterator().next();
		logger.debug("Common line: " + line.getName());

        LineConnectionResult lineConnection = LineNetworkUtil.findConnection(line, startNode, endNode);

        if (!lineConnection.isValid()) {
			JOptionPane.showMessageDialog(
                mapEditor.getFrame(),
                "Unable to determine connection between stations.", 
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
			return;
		}

        // Get the start and end coordinate
		LineNetwork lineNetwork = mapEditor.getMap().getLineNetwork();
        Point startPoint = startNode.location;
        Point endPoint = endNode.location;

        // Calculate the diffs
        double diffX = endPoint.getX() - startPoint.getX();
        double diffY = endPoint.getY() - startPoint.getY();

        // Calculate the angle between the two points
        //
        //                                            X (end)
        //                                    .       |
        //                             .              |
        //                      .                     |  diffY
        //               .                            |
        //        .      ) atan(diffY / diffX)        |
        //  X-----------------------------------------|
        //  (start-anchor)      diffX
        double angle = Math.toDegrees(Math.atan(diffY / diffX));

        // If the angle is less than 22.5, the line should be horizontal
        if(Math.abs(angle) <= 22.5) {
            endNode.location = new Point(endPoint.getX(), startPoint.getY());
        }
        // If the angle is between 22.5 and 67.5, the line should be 45 degrees
        else if(Math.abs(angle) > 22.5 && Math.abs(angle) < 67.5) {
            // Distance of start and end using Pithagorean theorem
            double length = Math.sqrt(diffX * diffX + diffY * diffY);

            // X and Y coordinate distance in 45 degree right triangle
            double coordDiff = Math.sqrt(2) * 0.5 * length;

            endNode.location = new Point(startPoint.getX() + Math.signum(diffX) * coordDiff, startPoint.getY() + Math.signum(diffY) * coordDiff);
        }
        // If the angle is bigger than 67.5, the line should be vertical
        else {
            endNode.location = new Point(startPoint.getX(), endPoint.getY());
        }

        // Distribute the rest of the nodes evenly between start and end
        NodesInBetweenResult nodesBetween = LineNetworkUtil.getNodesBetween(lineNetwork, line, lineConnection.getIdxNode1(), lineConnection.getIdxNode2());

        // Recalculate diffs
        endPoint = endNode.location;

        // Calculate the diffs
        diffX = endPoint.getX() - startPoint.getX();
        diffY = endPoint.getY() - startPoint.getY();

        List<Node> between = nodesBetween.getNodes();
        double dx = diffX / (between.size() + 1);
		double dy = diffY / (between.size() + 1);

		for (int i = 0; i < between.size(); i++) {
			Node node = between.get(i);
			double x = startPoint.x + dx * (i + 1);
			double y = startPoint.y + dy * (i + 1);
			node.location = new Point(x, y);
		}

		LineNetworkUtil.updateEdges(nodesBetween.getStart());
		LineNetworkUtil.updateEdges(nodesBetween.getEnd());
		for (Node node : between) {
			LineNetworkUtil.updateEdges(node);
		}

		mapEditor.getMap().repaint();
    }
}
