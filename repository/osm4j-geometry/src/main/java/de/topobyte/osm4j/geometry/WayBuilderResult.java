package de.topobyte.osm4j.geometry;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import de.topobyte.jts.utils.GeometryGroup;
import java.util.ArrayList;
import java.util.List;

public class WayBuilderResult {
   private List<Coordinate> coordinates = new ArrayList<>();
   private List<LineString> lineStrings = new ArrayList<>();
   private LinearRing linearRing = null;

   public List<Coordinate> getCoordinates() {
      return this.coordinates;
   }

   public void setCoordinates(List<Coordinate> coordinates) {
      this.coordinates = coordinates;
   }

   public List<LineString> getLineStrings() {
      return this.lineStrings;
   }

   public void setLineStrings(List<LineString> lineStrings) {
      this.lineStrings = lineStrings;
   }

   public LinearRing getLinearRing() {
      return this.linearRing;
   }

   public void setLinearRing(LinearRing linearRing) {
      this.linearRing = linearRing;
   }

   public void clear() {
      this.coordinates.clear();
      this.lineStrings.clear();
      this.linearRing = null;
   }

   public Geometry toGeometry(GeometryFactory factory) {
      return this.linearRing == null
         ? GeometryUtil.createGeometry(this.coordinates, this.lineStrings, factory)
         : GeometryUtil.createGeometry(this.coordinates, this.lineStrings, this.linearRing, factory);
   }

   public GeometryGroup toGeometryGroup(GeometryFactory factory) {
      return this.linearRing == null
         ? GeometryUtil.createGeometryGroup(this.coordinates, this.lineStrings, factory)
         : GeometryUtil.createGeometryGroup(this.coordinates, this.lineStrings, this.linearRing, factory);
   }
}
