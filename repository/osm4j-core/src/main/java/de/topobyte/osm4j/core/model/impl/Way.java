package de.topobyte.osm4j.core.model.impl;

import com.slimjars.dist.gnu.trove.list.TLongList;
import de.topobyte.osm4j.core.model.iface.OsmMetadata;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.util.List;

public class Way extends Entity implements OsmWay {
   private final TLongList nodes;

   public Way(long id, TLongList nodes) {
      super(id, null);
      this.nodes = nodes;
   }

   public Way(long id, TLongList nodes, OsmMetadata metadata) {
      super(id, metadata);
      this.nodes = nodes;
   }

   public Way(long id, TLongList nodes, List<? extends OsmTag> tags) {
      this(id, nodes, tags, null);
   }

   public Way(long id, TLongList nodes, List<? extends OsmTag> tags, OsmMetadata metadata) {
      super(id, tags, metadata);
      this.nodes = nodes;
   }

   public TLongList getNodes() {
      return this.nodes;
   }

   @Override
   public int getNumberOfNodes() {
      return this.nodes.size();
   }

   @Override
   public long getNodeId(int n) {
      return this.nodes.get(n);
   }
}
