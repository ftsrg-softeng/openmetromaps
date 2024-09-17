package de.topobyte.osm4j.extra.nodearray;

import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import de.topobyte.osm4j.core.resolve.OsmEntityProvider;
import java.io.IOException;

public class NodeArrayEntityProvider implements OsmEntityProvider {
   private static final String MESSAGE_NON_NODE = "This provider contains only nodes";
   private NodeArray nodeArray;

   public NodeArrayEntityProvider(NodeArray nodeArray) {
      this.nodeArray = nodeArray;
   }

   public OsmNode getNode(long id) throws EntityNotFoundException {
      try {
         return this.nodeArray.get(id);
      } catch (IOException var4) {
         throw new EntityNotFoundException("Node not found due to IOException " + var4.getMessage());
      }
   }

   public OsmWay getWay(long id) throws EntityNotFoundException {
      throw new EntityNotFoundException("This provider contains only nodes");
   }

   public OsmRelation getRelation(long id) throws EntityNotFoundException {
      throw new EntityNotFoundException("This provider contains only nodes");
   }
}
