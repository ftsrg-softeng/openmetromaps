package de.topobyte.osm4j.extra.datatree;

import com.vividsolutions.jts.geom.Envelope;
import de.topobyte.adt.geo.BBox;
import de.topobyte.adt.geo.BBoxString;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataTreeOpener {
   public static DataTree open(File dir) throws IOException {
      File fileInfo = new File(dir, "tree.info");
      if (!fileInfo.exists()) {
         throw new FileNotFoundException("info file not found: " + fileInfo);
      } else {
         BBox bbox = null;
         BufferedReader reader = new BufferedReader(new FileReader(fileInfo));

         while (true) {
            String line = reader.readLine();
            if (line == null) {
               reader.close();
               if (bbox == null) {
                  throw new IOException("No bounding box found in info file");
               } else {
                  Envelope envelope = bbox.toEnvelope();
                  DataTree tree = new DataTree(envelope);
                  List<File> dataFiles = new ArrayList<>();
                  File[] files = dir.listFiles();

                  for (File file : files) {
                     if (file.isDirectory()) {
                        String name = file.getName();

                        try {
                           Long.parseLong(name, 16);
                           dataFiles.add(file);
                        } catch (NumberFormatException var23) {
                           System.out.println("Warning: unknown directory: " + file);
                        }
                     }
                  }

                  if (dataFiles.isEmpty()) {
                     throw new IOException("No data available");
                  } else {
                     Set<Long> hasChildren = new HashSet<>();
                     Map<Integer, Set<Long>> layerMap = new HashMap<>();

                     for (File filex : dataFiles) {
                        String name = filex.getName();
                        long path = Long.parseLong(name, 16);
                        int level = Long.toBinaryString(path).length() - 1;
                        Set<Long> layer = layerMap.get(level);
                        if (layer == null) {
                           layer = new HashSet<>();
                           layerMap.put(level, layer);
                        }

                        layer.add(path);
                     }

                     List<Integer> levels = new ArrayList<>(layerMap.keySet());
                     Collections.sort(levels);
                     int maxLevel = levels.get(levels.size() - 1);

                     for (int level = maxLevel; level > 0; level--) {
                        Set<Long> layer = layerMap.get(level);
                        Set<Long> above = layerMap.get(level - 1);
                        if (above == null) {
                           above = new HashSet<>();
                           layerMap.put(level - 1, above);
                        }

                        while (!layer.isEmpty()) {
                           Iterator<Long> iterator = layer.iterator();
                           long path = iterator.next();
                           iterator.remove();
                           long sibling = sibling(path);
                           boolean remove = layer.remove(sibling);
                           if (!remove) {
                              throw new IOException("Missing file for node: " + Long.toHexString(sibling));
                           }

                           long parent = parent(path);
                           if (above.contains(parent)) {
                              throw new IOException("Parent node shouldn't exists: " + Long.toHexString(parent));
                           }

                           above.add(parent);
                           hasChildren.add(parent);
                        }
                     }

                     Set<Node> work = new HashSet<>();
                     work.add(tree.getRoot());

                     while (!work.isEmpty()) {
                        Iterator<Node> iteratorx = work.iterator();
                        Node node = iteratorx.next();
                        iteratorx.remove();
                        if (hasChildren.contains(node.getPath())) {
                           node.split();
                           work.add(node.getLeft());
                           work.add(node.getRight());
                        }
                     }

                     return tree;
                  }
               }
            }

            if (bbox == null && line.startsWith("bbox:")) {
               String data = line.substring("bbox".length() + 1).trim();
               bbox = BBoxString.parse(data).toBbox();
            }
         }
      }
   }

   private static long parent(long path) {
      return path >> 1;
   }

   private static long sibling(long path) {
      long lastBit = path & 1L;
      long siblingLastBit = ~lastBit & 1L;
      return path & -2L | siblingLastBit;
   }
}
