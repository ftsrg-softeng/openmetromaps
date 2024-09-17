package de.topobyte.osm4j.extra.datatree;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import java.util.List;

public class Node {
   boolean isLeaf = true;
   private Envelope envelope;
   private Node parent;
   private Node left;
   private Node right;
   private long path;
   private int level;
   private Direction direction;
   private double splitPoint = 0.0;
   private Geometry box;

   Node(Envelope envelope, Node parent, long path, int level) {
      this.envelope = envelope;
      this.parent = parent;
      this.path = path;
      this.level = level;
      if (envelope.getWidth() >= envelope.getHeight()) {
         this.direction = Direction.HORIZONTAL;
      } else {
         this.direction = Direction.VERTICAL;
      }

      this.box = new GeometryFactory().toGeometry(envelope);
   }

   public Envelope getEnvelope() {
      return this.envelope;
   }

   public Node getParent() {
      return this.parent;
   }

   public Node getSibling() {
      if (this.parent == null) {
         return null;
      } else {
         return this.parent.getLeft() != this ? this.parent.getLeft() : this.parent.getRight();
      }
   }

   public long getPath() {
      return this.path;
   }

   public int getLevel() {
      return this.level;
   }

   public boolean isLeaf() {
      return this.isLeaf;
   }

   public Node getLeft() {
      return this.left;
   }

   public Node getRight() {
      return this.right;
   }

   public Direction getSplitDirection() {
      return this.direction;
   }

   public void melt() {
      this.isLeaf = true;
      this.left = null;
      this.right = null;
   }

   public void split() {
      Direction direction = this.getSplitDirection();
      long pathL = this.path << 1 | 0L;
      long pathR = this.path << 1 | 1L;
      Envelope envLeft;
      Envelope envRight;
      if (direction == Direction.HORIZONTAL) {
         double x1 = this.envelope.getMinX();
         double x3 = this.envelope.getMaxX();
         double x2 = (x1 + x3) / 2.0;
         double y1 = this.envelope.getMinY();
         double y2 = this.envelope.getMaxY();
         envLeft = new Envelope(x1, x2, y1, y2);
         envRight = new Envelope(x2, x3, y1, y2);
         this.splitPoint = x2;
      } else {
         double x1 = this.envelope.getMinX();
         double x2 = this.envelope.getMaxX();
         double y1 = this.envelope.getMinY();
         double y3 = this.envelope.getMaxY();
         double y2 = (y1 + y3) / 2.0;
         envLeft = new Envelope(x1, x2, y1, y2);
         envRight = new Envelope(x1, x2, y2, y3);
         this.splitPoint = y2;
      }

      this.isLeaf = false;
      this.left = new Node(envLeft, this, pathL, this.level + 1);
      this.right = new Node(envRight, this, pathR, this.level + 1);
   }

   public void split(int depth) {
      if (depth >= 1) {
         this.split();
      }

      if (depth > 1) {
         this.left.split(depth - 1);
         this.right.split(depth - 1);
      }
   }

   public void query(List<Node> nodes, double lon, double lat) {
      if (this.isLeaf()) {
         nodes.add(this);
      } else {
         if (this.direction == Direction.HORIZONTAL) {
            if (lon < this.splitPoint) {
               this.left.query(nodes, lon, lat);
            } else if (lon > this.splitPoint) {
               this.right.query(nodes, lon, lat);
            } else {
               this.left.query(nodes, lon, lat);
               this.right.query(nodes, lon, lat);
            }
         } else if (lat < this.splitPoint) {
            this.left.query(nodes, lon, lat);
         } else if (lat > this.splitPoint) {
            this.right.query(nodes, lon, lat);
         } else {
            this.left.query(nodes, lon, lat);
            this.right.query(nodes, lon, lat);
         }
      }
   }

   public Side side(double lon, double lat) {
      if (this.direction == Direction.HORIZONTAL) {
         if (lon < this.splitPoint) {
            return Side.LEFT;
         } else {
            return lon > this.splitPoint ? Side.RIGHT : Side.ON;
         }
      } else if (lat < this.splitPoint) {
         return Side.LEFT;
      } else {
         return lat > this.splitPoint ? Side.RIGHT : Side.ON;
      }
   }

   public void query(List<Node> nodes, Geometry geometry) {
      if (geometry.intersects(this.box)) {
         if (this.isLeaf()) {
            nodes.add(this);
         } else {
            this.left.query(nodes, geometry);
            this.right.query(nodes, geometry);
         }
      }
   }
}
