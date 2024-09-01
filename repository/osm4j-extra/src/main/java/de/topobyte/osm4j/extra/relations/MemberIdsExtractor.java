package de.topobyte.osm4j.extra.relations;

import com.slimjars.dist.gnu.trove.set.TLongSet;
import com.slimjars.dist.gnu.trove.set.hash.TLongHashSet;
import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;
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

public class MemberIdsExtractor {
   private Path[] dirsData;
   private String fileNamesRelations;
   private String fileNamesNodeIds;
   private String fileNamesWayIds;
   private FileFormat inputFormat;
   private List<Path> subdirs;

   public MemberIdsExtractor(Path[] dirsData, String fileNamesRelations, String fileNamesNodeIds, String fileNamesWayIds, FileFormat inputFormat) {
      this.dirsData = dirsData;
      this.fileNamesRelations = fileNamesRelations;
      this.fileNamesNodeIds = fileNamesNodeIds;
      this.fileNamesWayIds = fileNamesWayIds;
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
               Path relations = subPath.resolve(this.fileNamesRelations);
               if (Files.exists(relations)) {
                  this.subdirs.add(subPath);
               }
            }
         }
      }
   }

   private void extract(Path path) throws IOException {
      Path pathRelations = path.resolve(this.fileNamesRelations);
      Path pathNodeIds = path.resolve(this.fileNamesNodeIds);
      Path pathWayIds = path.resolve(this.fileNamesWayIds);
      InputStream input = StreamUtil.bufferedInputStream(pathRelations.toFile());
      OsmIterator osmIterator = OsmIoUtils.setupOsmIterator(input, this.inputFormat, false);
      OutputStream outputNodeIds = StreamUtil.bufferedOutputStream(pathNodeIds.toFile());
      IdListOutputStream idOutputNodeIds = new IdListOutputStream(outputNodeIds);
      TLongSet nodeIdsSet = new TLongHashSet();
      TLongSet wayIdsSet = new TLongHashSet();

      for (EntityContainer container : osmIterator) {
         if (container.getType() == EntityType.Relation) {
            OsmRelation relation = (OsmRelation)container.getEntity();

            for (OsmRelationMember member : OsmModelUtil.membersAsList(relation)) {
               if (member.getType() == EntityType.Node) {
                  nodeIdsSet.add(member.getId());
               } else if (member.getType() == EntityType.Way) {
                  wayIdsSet.add(member.getId());
               }
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
      OutputStream outputWayIds = StreamUtil.bufferedOutputStream(pathWayIds.toFile());
      IdListOutputStream idOutputWayIds = new IdListOutputStream(outputWayIds);
      long[] wayIds = wayIdsSet.toArray();
      Arrays.sort(wayIds);

      for (long id : wayIds) {
         idOutputWayIds.write(id);
      }

      idOutputWayIds.close();
   }
}
