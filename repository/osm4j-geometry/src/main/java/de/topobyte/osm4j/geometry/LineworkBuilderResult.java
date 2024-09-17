package de.topobyte.osm4j.geometry;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import de.topobyte.jts.utils.GeometryGroup;
import java.util.ArrayList;
import java.util.List;

public class LineworkBuilderResult {
   private List<Coordinate> coordinates = new ArrayList<>();
   private List<LineString> lineStrings = new ArrayList<>();

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

   public void clear() {
      this.coordinates.clear();
      this.lineStrings.clear();
   }

   public Geometry toGeometry(GeometryFactory factory) {
      return GeometryUtil.createGeometry(this.coordinates, this.lineStrings, factory);
   }

   public GeometryGroup toGeometryGroup(GeometryFactory factory) {
      return GeometryUtil.createGeometryGroup(this.coordinates, this.lineStrings, factory);
   }
}
