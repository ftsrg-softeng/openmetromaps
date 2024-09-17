package de.topobyte.osm4j.extra.datatree;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import java.util.ArrayList;
import java.util.List;

public class DataTree {
   public static final String FILENAME_INFO = "tree.info";
   public static final String PROPERTY_BBOX = "bbox";
   private Node root;
   private List<Node> results = new ArrayList<>();

   public DataTree(Envelope envelope) {
      this.root = new Node(envelope, null, 1L, 0);
   }

   public Node getRoot() {
      return this.root;
   }

   public List<Node> getLeafs() {
      return this.getLeafs(this.root);
   }

   public List<Node> getLeafs(Node start) {
      List<Node> leafs = new ArrayList<>();
      this.getLeafs(start, leafs);
      return leafs;
   }

   private void getLeafs(Node node, List<Node> leafs) {
      if (node.isLeaf()) {
         leafs.add(node);
      } else {
         this.getLeafs(node.getLeft(), leafs);
         this.getLeafs(node.getRight(), leafs);
      }
   }

   public List<Node> getInner() {
      List<Node> inner = new ArrayList<>();
      this.getInner(this.root, inner);
      return inner;
   }

   public List<Node> getInner(Node start) {
      List<Node> inner = new ArrayList<>();
      this.getInner(start, inner);
      return inner;
   }

   private void getInner(Node node, List<Node> inner) {
      if (!node.isLeaf()) {
         inner.add(node);
         this.getInner(node.getLeft(), inner);
         this.getInner(node.getRight(), inner);
      }
   }

   public void print() {
      this.print(this.root);
   }

   private void print(Node node) {
      if (node.isLeaf()) {
         System.out.println(Long.toHexString(node.getPath()) + ": " + node.getEnvelope());
      } else {
         this.print(node.getLeft());
         this.print(node.getRight());
      }
   }

   public List<Node> query(double lon, double lat) {
      this.results.clear();
      if (this.root.getEnvelope().contains(lon, lat)) {
         this.root.query(this.results, lon, lat);
      }

      return this.results;
   }

   public List<Node> query(Coordinate coordinate) {
      return this.query(coordinate.x, coordinate.y);
   }

   public List<Node> query(Node start, double lon, double lat) {
      this.results.clear();
      start.query(this.results, lon, lat);
      return this.results;
   }

   public List<Node> query(Node start, Coordinate coordinate) {
      this.results.clear();
      start.query(this.results, coordinate.x, coordinate.y);
      return this.results;
   }

   public List<Node> query(Geometry geometry) {
      this.results.clear();
      this.root.query(this.results, geometry);
      return this.results;
   }

   public List<Node> query(Envelope envelope) {
      return this.query(new GeometryFactory().toGeometry(envelope));
   }
}
