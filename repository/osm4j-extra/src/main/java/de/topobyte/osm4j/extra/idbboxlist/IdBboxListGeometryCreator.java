package de.topobyte.osm4j.extra.idbboxlist;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.WKTWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class IdBboxListGeometryCreator {
   private File fileInput;
   private File fileOutput;

   public IdBboxListGeometryCreator(File fileInput, File fileOutput) {
      this.fileInput = fileInput;
      this.fileOutput = fileOutput;
   }

   public void execute() throws IOException {
      System.out.println("Opening file: " + this.fileInput);
      List<Geometry> boxList = IdBboxUtil.readBoxes(this.fileInput);
      GeometryCollection geometry = new GeometryFactory().createGeometryCollection(boxList.toArray(new Geometry[0]));
      System.out.println("Writing output to: " + this.fileOutput);
      FileWriter writer = new FileWriter(this.fileOutput);
      new WKTWriter().write(geometry, writer);
      writer.close();
   }
}
