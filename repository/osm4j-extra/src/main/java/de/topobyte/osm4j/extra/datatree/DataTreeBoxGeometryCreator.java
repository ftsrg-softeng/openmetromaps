package de.topobyte.osm4j.extra.datatree;

import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.io.WKTWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataTreeBoxGeometryCreator {
   private File dirTree;
   private File fileOutput;

   public DataTreeBoxGeometryCreator(File dirTree, File fileOutput) {
      this.dirTree = dirTree;
      this.fileOutput = fileOutput;
   }

   public void execute() throws IOException {
      System.out.println("Opening data tree: " + this.dirTree);
      DataTree tree = DataTreeOpener.open(this.dirTree);
      GeometryCollection geometry = BoxUtil.createBoxesGeometry(tree, BoxUtil.WORLD_BOUNDS);
      System.out.println("Writing output to: " + this.fileOutput);
      FileWriter writer = new FileWriter(this.fileOutput);
      new WKTWriter().write(geometry, writer);
      writer.close();
   }
}
