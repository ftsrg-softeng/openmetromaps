package de.topobyte.osm4j.extra.relations;

import com.slimjars.dist.gnu.trove.set.TLongSet;
import com.slimjars.dist.gnu.trove.set.hash.TLongHashSet;
import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.model.util.OsmModelUtil;
import de.topobyte.osm4j.extra.idlist.IdListOutputStream;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmIoUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WayMemberNodeIdsExtractor {
   private Path[] dirsData;
   private String fileNamesWays;
   private String fileNamesNodeIds;
   private FileFormat inputFormat;
   private List<Path> subdirs;

   public WayMemberNodeIdsExtractor(Path[] dirsData, String fileNamesWays, String fileNamesNodeIds, FileFormat inputFormat) {
      this.dirsData = dirsData;
      this.fileNamesWays = fileNamesWays;
      this.fileNamesNodeIds = fileNamesNodeIds;
      this.inputFormat = inputFormat;
   }

   public void execute() throws IOException {
      this.init();
      int i = 0;

      for (Path path : this.subdirs) {
         System.out.println(String.format("Processing directory %d of %d", ++i, this.subdirs.size()));
         this.extract(path);
      }
   }

   private void init() throws IOException {
      for (Path dirData : this.dirsData) {
         if (!Files.isDirectory(dirData)) {
            System.out.println("Data path is not a directory: " + dirData);
            System.exit(1);
         }
      }

      this.subdirs = new ArrayList<>();

      for (Path dirDatax : this.dirsData) {
         File[] subs = dirDatax.toFile().listFiles();

         for (File sub : subs) {
            if (sub.isDirectory()) {
               Path subPath = sub.toPath();
               Path ways = subPath.resolve(this.fileNamesWays);
               if (Files.exists(ways)) {
                  this.subdirs.add(subPath);
               }
            }
         }
      }
   }

   private void extract(Path path) throws IOException {
      Path pathWays = path.resolve(this.fileNamesWays);
      Path pathNodeIds = path.resolve(this.fileNamesNodeIds);
      InputStream input = StreamUtil.bufferedInputStream(pathWays.toFile());
      OsmIterator osmIterator = OsmIoUtils.setupOsmIterator(input, this.inputFormat, false);
      OutputStream outputNodeIds = StreamUtil.bufferedOutputStream(pathNodeIds.toFile());
      IdListOutputStream idOutputNodeIds = new IdListOutputStream(outputNodeIds);
      TLongSet nodeIdsSet = new TLongHashSet();

      for (EntityContainer container : osmIterator) {
         if (container.getType() == EntityType.Way) {
            OsmWay way = (OsmWay)container.getEntity();

            for (long id : OsmModelUtil.nodesAsList(way).toArray()) {
               nodeIdsSet.add(id);
            }
         }
      }

      input.close();
      long[] nodesIds = nodeIdsSet.toArray();
      Arrays.sort(nodesIds);

      for (long id : nodesIds) {
         idOutputNodeIds.write(id);
      }

      idOutputNodeIds.close();
   }
}
