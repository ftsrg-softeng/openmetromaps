package de.topobyte.osm4j.extra.datatree.ways;

import com.slimjars.dist.gnu.trove.map.TLongObjectMap;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.resolve.OsmEntityProvider;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.nio.file.Path;

public class SimpleWaysDistributor extends AbstractWaysDistributor {
   public SimpleWaysDistributor(
      Path pathTree,
      String fileNamesNodes1,
      String fileNamesNodes2,
      String fileNamesWays,
      String fileNamesOutputWays,
      String fileNamesOutputNodes,
      FileFormat inputFormatNodes,
      FileFormat inputFormatWays,
      OsmOutputConfig outputConfig
   ) {
      super(
         pathTree, fileNamesNodes1, fileNamesNodes2, fileNamesWays, fileNamesOutputWays, fileNamesOutputNodes, inputFormatNodes, inputFormatWays, outputConfig
      );
   }

   @Override
   protected void leafData(LeafData leafData) throws IOException {
      OsmEntityProvider entityProvider = leafData.getNodeProvider();

      for (OsmWay way : leafData.getDataWays().getWays()) {
         this.build(leafData.getLeaf(), way, entityProvider);
      }
   }

   @Override
   protected void write(Node leaf, OsmWay way, TLongObjectMap<OsmNode> nodes) throws IOException {
      OsmStreamOutput wayOutput = this.outputsWays.get(leaf);
      OsmStreamOutput nodeOutput = this.outputsNodes.get(leaf);
      wayOutput.getOsmOutput().write(way);

      for (OsmNode node : nodes.valueCollection()) {
         nodeOutput.getOsmOutput().write(node);
      }
   }
}
