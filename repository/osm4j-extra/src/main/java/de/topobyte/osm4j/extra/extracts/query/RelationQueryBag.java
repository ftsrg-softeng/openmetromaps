package de.topobyte.osm4j.extra.extracts.query;

import com.slimjars.dist.gnu.trove.map.TLongObjectMap;
import com.slimjars.dist.gnu.trove.map.hash.TLongObjectHashMap;
import com.slimjars.dist.gnu.trove.set.TLongSet;
import com.slimjars.dist.gnu.trove.set.hash.TLongHashSet;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmWay;

class RelationQueryBag {
   OsmStreamOutput outRelations;
   TLongSet nodeIds;
   TLongSet wayIds;
   int nSimple = 0;
   int nComplex = 0;
   TLongObjectMap<OsmNode> additionalNodes = new TLongObjectHashMap();
   TLongObjectMap<OsmWay> additionalWays = new TLongObjectHashMap();

   public RelationQueryBag(OsmStreamOutput outRelations) {
      this.outRelations = outRelations;
      this.additionalNodes = new TLongObjectHashMap();
      this.additionalWays = new TLongObjectHashMap();
      this.nodeIds = new TLongHashSet();
      this.wayIds = new TLongHashSet();
   }

   public RelationQueryBag(
      OsmStreamOutput outRelations, TLongObjectMap<OsmNode> additionalNodes, TLongObjectMap<OsmWay> additionalWays, TLongSet nodeIds, TLongSet wayIds
   ) {
      this.outRelations = outRelations;
      this.additionalNodes = additionalNodes;
      this.additionalWays = additionalWays;
      this.nodeIds = nodeIds;
      this.wayIds = wayIds;
   }
}
