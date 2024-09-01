package de.topobyte.osm4j.extra.relations;

import com.vividsolutions.jts.geom.Envelope;
import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.dataset.InMemoryMapDataSet;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.resolve.CompositeOsmEntityProvider;
import de.topobyte.osm4j.core.resolve.EntityFinder;
import de.topobyte.osm4j.core.resolve.EntityFinders;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import de.topobyte.osm4j.core.resolve.EntityNotFoundStrategy;
import de.topobyte.osm4j.core.resolve.NullOsmEntityProvider;
import de.topobyte.osm4j.core.resolve.OsmEntityProvider;
import de.topobyte.osm4j.core.util.RelationIterator;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.extra.idbboxlist.IdBboxEntry;
import de.topobyte.osm4j.geometry.BboxBuilder;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleRelationsDistributor extends RelationsDistributorBase {
   public SimpleRelationsDistributor(
      Path pathTree,
      Path pathData,
      Path pathOutputEmpty,
      Path pathOutputNonTree,
      Path pathOutputBboxes,
      String fileNamesRelations,
      String fileNamesWays,
      String fileNamesNodes,
      String fileNamesTreeRelations,
      FileFormat inputFormat,
      OsmOutputConfig outputConfig
   ) {
      super(
         pathTree,
         pathData,
         pathOutputEmpty,
         pathOutputNonTree,
         pathOutputBboxes,
         fileNamesRelations,
         fileNamesWays,
         fileNamesNodes,
         fileNamesTreeRelations,
         inputFormat,
         outputConfig
      );
   }

   public void execute() throws IOException {
      this.init();
      this.run();
      this.finish();
   }

   @Override
   protected void build(Path path) throws IOException {
      Path pathRelations = path.resolve(this.fileNamesRelations);
      Path pathWays = path.resolve(this.fileNamesWays);
      Path pathNodes = path.resolve(this.fileNamesNodes);
      InMemoryMapDataSet dataWays = this.read(pathWays, false, false);
      InMemoryMapDataSet dataNodes = this.read(pathNodes, false, false);
      OsmEntityProvider entityProvider = new CompositeOsmEntityProvider(dataNodes, dataWays, new NullOsmEntityProvider());
      InputStream input = StreamUtil.bufferedInputStream(pathRelations.toFile());
      OsmIterator osmIterator = OsmIoUtils.setupOsmIterator(input, this.inputFormat, this.outputConfig.isWriteMetadata());
      RelationIterator relationIterator = new RelationIterator(osmIterator);
      EntityFinder finder = EntityFinders.create(entityProvider, EntityNotFoundStrategy.IGNORE);

      for (OsmRelation relation : relationIterator) {
         Set<OsmNode> nodes = new HashSet<>();

         try {
            finder.findMemberNodesAndWayNodes(relation, nodes);
         } catch (EntityNotFoundException var17) {
            continue;
         }

         if (nodes.size() == 0) {
            this.nWrittenEmpty++;
            this.write(relation, this.outputEmpty);
         } else {
            Envelope envelope = BboxBuilder.box(nodes);
            List<Node> leafs = this.tree.query(envelope);
            if (leafs.size() == 1) {
               this.nWrittenToTree++;
               this.write(relation, this.outputs.get(leafs.get(0)));
            } else {
               this.nRemaining++;
               this.write(relation, this.outputNonTree);
               this.outputBboxes.write(new IdBboxEntry(relation.getId(), envelope, nodes.size()));
            }
         }
      }
   }

   private void write(OsmRelation relation, OsmStreamOutput output) throws IOException {
      output.getOsmOutput().write(relation);
   }
}
