package de.topobyte.osm4j.testing;

import com.slimjars.dist.gnu.trove.list.TLongList;
import com.slimjars.dist.gnu.trove.list.array.TLongArrayList;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmMetadata;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.model.util.OsmModelUtil;
import de.topobyte.osm4j.testing.model.TestBounds;
import de.topobyte.osm4j.testing.model.TestMetadata;
import de.topobyte.osm4j.testing.model.TestNode;
import de.topobyte.osm4j.testing.model.TestRelation;
import de.topobyte.osm4j.testing.model.TestRelationMember;
import de.topobyte.osm4j.testing.model.TestTag;
import de.topobyte.osm4j.testing.model.TestWay;
import java.util.ArrayList;
import java.util.List;

public class EntityHelper {
   public static TestBounds clone(OsmBounds bounds) {
      return new TestBounds(bounds.getLeft(), bounds.getRight(), bounds.getTop(), bounds.getBottom());
   }

   public static TestNode clone(OsmNode node) {
      List<TestTag> tags = cloneTags(node);
      TestMetadata metadata = cloneMetadata(node);
      return node(node, tags, metadata);
   }

   public static TestWay clone(OsmWay way) {
      List<TestTag> tags = cloneTags(way);
      TLongList nodes = cloneNodeReferences(way);
      TestMetadata metadata = cloneMetadata(way);
      return new TestWay(way.getId(), nodes, tags, metadata);
   }

   public static TestRelation clone(OsmRelation relation) {
      List<TestTag> tags = cloneTags(relation);
      List<TestRelationMember> members = cloneMembers(relation);
      TestMetadata metadata = cloneMetadata(relation);
      return new TestRelation(relation.getId(), members, tags, metadata);
   }

   private static TestNode node(OsmNode node, List<TestTag> tags, TestMetadata metadata) {
      return new TestNode(node.getId(), node.getLongitude(), node.getLatitude(), tags, metadata);
   }

   private static TestMetadata cloneMetadata(OsmEntity entity) {
      OsmMetadata metadata = entity.getMetadata();
      return metadata == null
         ? null
         : new TestMetadata(metadata.getVersion(), metadata.getTimestamp(), metadata.getUid(), metadata.getUser(), metadata.getChangeset());
   }

   private static List<TestTag> cloneTags(OsmEntity entity) {
      List<TestTag> copy = new ArrayList<>();

      for (OsmTag tag : OsmModelUtil.getTagsAsList(entity)) {
         copy.add(new TestTag(tag.getKey(), tag.getValue()));
      }

      return copy;
   }

   private static TLongList cloneNodeReferences(OsmWay way) {
      TLongList nodes = new TLongArrayList(way.getNumberOfNodes());

      for (int i = 0; i < way.getNumberOfNodes(); i++) {
         nodes.add(way.getNodeId(i));
      }

      return nodes;
   }

   private static List<TestRelationMember> cloneMembers(OsmRelation relation) {
      List<TestRelationMember> members = new ArrayList<>(relation.getNumberOfMembers());

      for (int i = 0; i < relation.getNumberOfMembers(); i++) {
         OsmRelationMember member = relation.getMember(i);
         members.add(new TestRelationMember(member.getId(), member.getType(), member.getRole()));
      }

      return members;
   }
}
