package de.topobyte.osm4j.extra.datatree;

import com.slimjars.dist.gnu.trove.map.TLongLongMap;
import com.slimjars.dist.gnu.trove.map.TObjectLongMap;
import com.slimjars.dist.gnu.trove.map.hash.TObjectLongHashMap;
import com.vividsolutions.jts.geom.Envelope;
import de.topobyte.adt.geo.BBox;
import de.topobyte.adt.geo.BBoxString;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataTreeUtil {
   public static void writeTreeInfo(File dir, BBox bbox) throws FileNotFoundException {
      File file = new File(dir, "tree.info");
      PrintWriter pw = new PrintWriter(file);
      pw.println("bbox: " + BBoxString.create(bbox));
      pw.close();
   }

   public static DataTree openExistingTree(Path dirOutput) throws IOException {
      if (!Files.exists(dirOutput)) {
         throw new IOException("Output path does not exist");
      } else if (!Files.isDirectory(dirOutput)) {
         throw new IOException("Output path is not a directory");
      } else {
         return DataTreeOpener.open(dirOutput.toFile());
      }
   }

   public static DataTree initNewTree(Path dirOutput, OsmBounds bounds) throws IOException {
      Envelope envelope = new Envelope(bounds.getLeft(), bounds.getRight(), bounds.getBottom(), bounds.getTop());
      BBox bbox = new BBox(envelope);
      return initNewTree(dirOutput, bbox);
   }

   public static DataTree initNewTree(Path dirOutput, BBox bbox) throws IOException {
      if (!Files.exists(dirOutput)) {
         System.out.println("Creating output directory");
         Files.createDirectories(dirOutput);
      }

      if (!Files.isDirectory(dirOutput)) {
         throw new IOException("Output path is not a directory");
      } else if (dirOutput.toFile().list().length != 0) {
         throw new IOException("Output directory is not empty");
      } else {
         writeTreeInfo(dirOutput.toFile(), bbox);
         return new DataTree(bbox.toEnvelope());
      }
   }

   public static void mergeUnderfilledSiblings(DataTree tree, Node head, int maxNodes, TLongLongMap counters) {
      List<Node> inner = tree.getInner(head);
      List<Node> leafs = tree.getLeafs(head);
      System.out.println("Before merging underfilled siblings:");
      System.out.println("inner nodes: " + inner.size());
      System.out.println("leafs: " + leafs.size());
      TObjectLongMap<Node> counts = new TObjectLongHashMap();

      for (Node leaf : leafs) {
         long count = counters.get(leaf.getPath());
         counts.put(leaf, count);
      }

      List<Node> check = new ArrayList<>(inner);
      Collections.sort(check, new Comparator<Node>() {
         public int compare(Node o1, Node o2) {
            return Integer.compare(o2.getLevel(), o1.getLevel());
         }
      });

      for (Node node : check) {
         if (node.getLeft().isLeaf() && node.getRight().isLeaf()) {
            long sum = counts.get(node.getLeft()) + counts.get(node.getRight());
            if (sum < (long)maxNodes) {
               node.melt();
               counts.put(node, sum);
            }
         }
      }

      System.out.println("After:");
      System.out.println("inner nodes: " + tree.getInner(head).size());
      System.out.println("leafs: " + tree.getLeafs(head).size());
   }
}
