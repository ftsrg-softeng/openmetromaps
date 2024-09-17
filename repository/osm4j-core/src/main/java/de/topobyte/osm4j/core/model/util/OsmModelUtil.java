package de.topobyte.osm4j.core.model.util;

import com.slimjars.dist.gnu.trove.list.TLongList;
import com.slimjars.dist.gnu.trove.list.array.TLongArrayList;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OsmModelUtil {
   public static Map<String, String> getTagsAsMap(OsmEntity entity) {
      Map<String, String> map = new HashMap<>();

      for (int i = 0; i < entity.getNumberOfTags(); i++) {
         OsmTag tag = entity.getTag(i);
         map.put(tag.getKey(), tag.getValue());
      }

      return map;
   }

   public static List<? extends OsmTag> getTagsAsList(OsmEntity entity) {
      List<OsmTag> list = new ArrayList<>();

      for (int i = 0; i < entity.getNumberOfTags(); i++) {
         OsmTag tag = entity.getTag(i);
         list.add(tag);
      }

      return list;
   }

   public static TLongList nodesAsList(OsmWay way) {
      TLongList ids = new TLongArrayList();

      for (int i = 0; i < way.getNumberOfNodes(); i++) {
         ids.add(way.getNodeId(i));
      }

      return ids;
   }

   public static List<OsmRelationMember> membersAsList(OsmRelation relation) {
      List<OsmRelationMember> members = new ArrayList<>();

      for (int i = 0; i < relation.getNumberOfMembers(); i++) {
         members.add(relation.getMember(i));
      }

      return members;
   }

   public static boolean isClosed(OsmWay way) {
      int n = way.getNumberOfNodes();
      if (n < 2) {
         return true;
      } else {
         long id0 = way.getNodeId(0);
         long idN = way.getNodeId(n - 1);
         return id0 == idN;
      }
   }
}
