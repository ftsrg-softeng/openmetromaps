package de.topobyte.osm4j.extra.datatree;

import com.vividsolutions.jts.geom.Envelope;
import de.topobyte.adt.geo.BBox;
import java.io.File;
import java.io.IOException;

public class EmptyDataTreeFromOtherCreator {
   private File dirInputTree;
   private File dirOutputTree;

   public EmptyDataTreeFromOtherCreator(File dirInputTree, File dirOutputTree) {
      this.dirInputTree = dirInputTree;
      this.dirOutputTree = dirOutputTree;
   }

   public void execute() throws IOException {
      System.out.println("Opening data tree: " + this.dirInputTree);
      DataTree tree = DataTreeOpener.open(this.dirInputTree);
      System.out.println("Creating new data tree: " + this.dirOutputTree);
      this.dirOutputTree.mkdirs();
      if (!this.dirOutputTree.isDirectory()) {
         System.out.println("Unable to create output directory");
         System.exit(1);
      }

      if (this.dirOutputTree.listFiles().length != 0) {
         System.out.println("Output directory not empty");
         System.exit(1);
      }

      Envelope envelope = tree.getRoot().getEnvelope();
      BBox bbox = new BBox(envelope);
      DataTreeUtil.writeTreeInfo(this.dirOutputTree, bbox);

      for (Node leaf : tree.getLeafs()) {
         String subdirName = Long.toHexString(leaf.getPath());
         File subdir = new File(this.dirOutputTree, subdirName);
         subdir.mkdir();
      }
   }
}
