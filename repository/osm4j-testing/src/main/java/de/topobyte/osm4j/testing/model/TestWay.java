package de.topobyte.osm4j.testing.model;

import com.slimjars.dist.gnu.trove.list.TLongList;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.util.List;

public class TestWay extends TestEntity implements OsmWay {
   private final TLongList nodes;

   public TestWay(long id, TLongList nodes) {
      super(id, null);
      this.nodes = nodes;
   }

   public TestWay(long id, TLongList nodes, TestMetadata metadata) {
      super(id, metadata);
      this.nodes = nodes;
   }

   public TestWay(long id, TLongList nodes, List<TestTag> tags) {
      this(id, nodes, tags, null);
   }

   public TestWay(long id, TLongList nodes, List<TestTag> tags, TestMetadata metadata) {
      super(id, tags, metadata);
      this.nodes = nodes;
   }

   public TLongList getNodes() {
      return this.nodes;
   }

   public int getNumberOfNodes() {
      return this.nodes.size();
   }

   public long getNodeId(int n) {
      return this.nodes.get(n);
   }
}
