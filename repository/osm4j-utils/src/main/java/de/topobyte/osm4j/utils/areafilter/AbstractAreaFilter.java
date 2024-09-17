package de.topobyte.osm4j.utils.areafilter;

import com.slimjars.dist.gnu.trove.set.hash.TLongHashSet;
import com.vividsolutions.jts.geom.Coordinate;
import de.topobyte.jts.utils.predicate.PredicateEvaluator;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.access.ProgressMonitor;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;

public abstract class AbstractAreaFilter {
   private OsmOutputStream output;
   private OsmIterator input;
   private boolean onlyNodes;
   private ProgressMonitor monitor = new ProgressMonitor("area filter");
   protected PredicateEvaluator test;
   private TLongHashSet nodeIds = new TLongHashSet();
   private TLongHashSet wayIds = new TLongHashSet();

   public AbstractAreaFilter(OsmOutputStream output, OsmIterator input, boolean onlyNodes) {
      this.output = output;
      this.input = input;
      this.onlyNodes = onlyNodes;
   }

   public void run() throws IOException {
      for (EntityContainer entityContainer : this.input) {
         switch (entityContainer.getType()) {
            case Node:
               this.handle((OsmNode)entityContainer.getEntity());
               break;
            case Way:
               this.handle((OsmWay)entityContainer.getEntity());
               break;
            case Relation:
               this.handle((OsmRelation)entityContainer.getEntity());
         }
      }

      this.output.complete();
   }

   private void handle(OsmNode node) throws IOException {
      this.monitor.nodeProcessed();
      Coordinate coordinate = new Coordinate(node.getLongitude(), node.getLatitude());
      if (this.test.covers(coordinate)) {
         if (!this.onlyNodes) {
            this.nodeIds.add(node.getId());
         }

         this.output.write(node);
      }
   }

   private void handle(OsmWay way) throws IOException {
      this.monitor.wayProcessed();
      if (!this.onlyNodes) {
         boolean take = false;

         for (int i = 0; i < way.getNumberOfNodes(); i++) {
            if (this.nodeIds.contains(way.getNodeId(i))) {
               take = true;
               break;
            }
         }

         if (take) {
            this.wayIds.add(way.getId());
            this.output.write(way);
         }
      }
   }

   private void handle(OsmRelation relation) throws IOException {
      this.monitor.relationProcessed();
      if (!this.onlyNodes) {
         boolean take = false;

         for (int i = 0; i < relation.getNumberOfMembers(); i++) {
            OsmRelationMember member = relation.getMember(i);
            if (member.getType() == EntityType.Node) {
               if (this.nodeIds.contains(member.getId())) {
                  take = true;
                  break;
               }
            } else if (member.getType() == EntityType.Way && this.wayIds.contains(member.getId())) {
               take = true;
               break;
            }
         }

         if (take) {
            this.output.write(relation);
         }
      }
   }
}
