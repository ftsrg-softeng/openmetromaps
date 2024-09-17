package de.topobyte.osm4j.geometry.relation;

import com.slimjars.dist.gnu.trove.list.TLongList;
import com.slimjars.dist.gnu.trove.list.array.TLongArrayList;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.CoordinateSequenceFactory;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import de.topobyte.osm4j.core.resolve.OsmEntityProvider;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChainOfWays {
   private static GeometryFactory factory = new GeometryFactory();
   private List<WaySegment> segments;
   private Set<OsmWay> waySet;
   private long first;
   private long last;
   private boolean closed = false;

   public ChainOfWays(OsmWay way) {
      if (way.getNumberOfNodes() < 2) {
         throw new IllegalArgumentException("Only ways with 2 or more nodes are allowed");
      } else {
         this.segments = new ArrayList<>();
         this.waySet = new HashSet<>();
         this.segments.add(new WaySegment(way, false));
         this.waySet.add(way);
         this.first = way.getNodeId(0);
         this.last = way.getNodeId(way.getNumberOfNodes() - 1);
         this.closed = this.first == this.last;
      }
   }

   public boolean isValidRing() {
      return !this.closed ? false : this.lengthIsZero() || !this.lengthIsLessThan(4);
   }

   public void addWay(OsmWay way) {
      if (way.getNumberOfNodes() < 2) {
         throw new IllegalArgumentException("Only ways with 2 or more nodes are allowed");
      } else {
         long id0 = way.getNodeId(0);
         long idN = way.getNodeId(way.getNumberOfNodes() - 1);
         boolean reverse = false;
         if (id0 == this.last) {
            this.last = idN;
         } else if (id0 == this.first) {
            this.first = idN;
            reverse = true;
         } else if (idN == this.last) {
            this.last = id0;
            reverse = true;
         } else if (idN == this.first) {
            this.first = id0;
         }

         this.segments.add(new WaySegment(way, reverse));
         this.waySet.add(way);
         if (this.first == this.last) {
            this.closed = true;
         }
      }
   }

   public long getFirst() {
      return this.first;
   }

   public long getLast() {
      return this.last;
   }

   public boolean isClosed() {
      return this.closed;
   }

   public List<WaySegment> getSegments() {
      return this.segments;
   }

   public Set<OsmWay> getWaySet() {
      return this.waySet;
   }

   public LineString toLineString(OsmEntityProvider resolver) throws EntityNotFoundException {
      CoordinateSequence points = this.toCoordinateSequence(resolver);
      return new LineString(points, factory);
   }

   public LinearRing toLinearRing(OsmEntityProvider resolver) throws EntityNotFoundException {
      int len = this.getLength();
      if (len < 4) {
         return new LinearRing(null, factory);
      } else {
         CoordinateSequence points = this.toCoordinateSequence(resolver);
         return new LinearRing(points, factory);
      }
   }

   public int getLength() {
      return this.segments.isEmpty() ? 0 : this.getLengthNonEmpty();
   }

   private int getLengthNonEmpty() {
      int len = 1;

      for (WaySegment segment : this.segments) {
         len += segment.getWay().getNumberOfNodes() - 1;
      }

      return len;
   }

   private boolean lengthIsZero() {
      return this.segments.isEmpty();
   }

   private boolean lengthIsLessThan(int maxLen) {
      if (this.segments.isEmpty()) {
         return true;
      } else {
         int len = 1;

         for (WaySegment segment : this.segments) {
            len += segment.getWay().getNumberOfNodes() - 1;
            if (len >= maxLen) {
               return false;
            }
         }

         return len < maxLen;
      }
   }

   private CoordinateSequence toCoordinateSequence(OsmEntityProvider resolver) throws EntityNotFoundException {
      CoordinateSequenceFactory csf = factory.getCoordinateSequenceFactory();
      int len = this.getLength();
      CoordinateSequence points = csf.create(len, 2);
      int n = 0;

      for (int i = 0; i < this.segments.size(); i++) {
         WaySegment segment = this.segments.get(i);
         OsmWay way = segment.getWay();

         for (int k = 0; k < way.getNumberOfNodes(); k++) {
            if (k > 0 || i == 0) {
               OsmNode node = resolver.getNode(segment.getNodeId(k));
               points.setOrdinate(n, 0, node.getLongitude());
               points.setOrdinate(n, 1, node.getLatitude());
               n++;
            }
         }
      }

      return points;
   }

   public ChainOfNodes toSegmentRing() {
      if (this.segments.isEmpty()) {
         return new ChainOfNodes(new TLongArrayList());
      } else {
         int len = this.getLengthNonEmpty();
         TLongList ids = new TLongArrayList(len);

         for (int i = 0; i < this.segments.size(); i++) {
            WaySegment segment = this.segments.get(i);
            OsmWay way = segment.getWay();

            for (int k = 0; k < way.getNumberOfNodes(); k++) {
               if (k > 0 || i == 0) {
                  ids.add(segment.getNodeId(k));
               }
            }
         }

         return new ChainOfNodes(ids);
      }
   }
}
