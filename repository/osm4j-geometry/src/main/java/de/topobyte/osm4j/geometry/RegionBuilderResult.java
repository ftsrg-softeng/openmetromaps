package de.topobyte.osm4j.geometry;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import de.topobyte.jts.utils.GeometryGroup;
import java.util.ArrayList;
import java.util.List;

public class RegionBuilderResult {
   private List<Coordinate> coordinates = new ArrayList<>();
   private List<LineString> lineStrings = new ArrayList<>();
   private MultiPolygon multiPolygon = null;

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

   public MultiPolygon getMultiPolygon() {
      return this.multiPolygon;
   }

   public void setMultiPolygon(MultiPolygon multiPolygon) {
      this.multiPolygon = multiPolygon;
   }

   public void clear() {
      this.coordinates.clear();
      this.lineStrings.clear();
      this.multiPolygon = null;
   }

   public Geometry toGeometry(GeometryFactory factory) {
      return this.multiPolygon == null
         ? GeometryUtil.createGeometry(this.coordinates, this.lineStrings, factory)
         : GeometryUtil.createGeometry(this.coordinates, this.lineStrings, this.multiPolygon, factory);
   }

   public GeometryGroup toGeometryGroup(GeometryFactory factory) {
      return this.multiPolygon == null
         ? GeometryUtil.createGeometryGroup(this.coordinates, this.lineStrings, factory)
         : GeometryUtil.createGeometryGroup(this.coordinates, this.lineStrings, this.multiPolygon, factory);
   }
}
