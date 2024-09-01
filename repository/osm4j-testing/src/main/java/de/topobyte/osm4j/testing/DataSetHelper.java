package de.topobyte.osm4j.testing;

import de.topobyte.osm4j.core.access.OsmHandler;
import de.topobyte.osm4j.core.access.OsmInputException;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.access.OsmReader;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmMetadata;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.model.util.OsmModelUtil;
import de.topobyte.osm4j.testing.model.TestNode;
import de.topobyte.osm4j.testing.model.TestRelation;
import de.topobyte.osm4j.testing.model.TestWay;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSetHelper {
   static final Logger logger = LoggerFactory.getLogger(DataSetHelper.class);

   public static void write(TestDataSet data, OsmOutputStream output) throws IOException {
      for (OsmNode node : data.getNodes()) {
         output.write(node);
      }

      for (OsmWay way : data.getWays()) {
         output.write(way);
      }

      for (OsmRelation relation : data.getRelations()) {
         output.write(relation);
      }
   }

   public static TestDataSet read(OsmIterator iterator) throws IOException {
      TestDataSet dataSet = new TestDataSet();
      if (iterator.hasBounds()) {
         dataSet.setBounds(EntityHelper.clone(iterator.getBounds()));
      }

      while (iterator.hasNext()) {
         EntityContainer container = (EntityContainer)iterator.next();
         switch (container.getType()) {
            case Node:
               OsmNode node = (OsmNode)container.getEntity();
               dataSet.getNodes().add(EntityHelper.clone(node));
               break;
            case Way:
               OsmWay way = (OsmWay)container.getEntity();
               dataSet.getWays().add(EntityHelper.clone(way));
               break;
            case Relation:
               OsmRelation relation = (OsmRelation)container.getEntity();
               dataSet.getRelations().add(EntityHelper.clone(relation));
         }
      }

      return dataSet;
   }

   public static TestDataSet read(OsmReader reader) throws IOException, OsmInputException {
      final TestDataSet dataSet = new TestDataSet();
      final List<TestNode> nodes = dataSet.getNodes();
      final List<TestWay> ways = dataSet.getWays();
      final List<TestRelation> relations = dataSet.getRelations();
      reader.setHandler(new OsmHandler() {
         public void handle(OsmBounds bounds) throws IOException {
            dataSet.setBounds(EntityHelper.clone(bounds));
         }

         public void handle(OsmNode node) throws IOException {
            nodes.add(EntityHelper.clone(node));
         }

         public void handle(OsmWay way) throws IOException {
            ways.add(EntityHelper.clone(way));
         }

         public void handle(OsmRelation relation) throws IOException {
            relations.add(EntityHelper.clone(relation));
         }

         public void complete() throws IOException {
         }
      });
      reader.read();
      return dataSet;
   }

   public static boolean equals(TestDataSet a, TestDataSet b) {
      if (!nodesEqual(a.getNodes(), b.getNodes())) {
         return false;
      } else {
         return !waysEqual(a.getWays(), b.getWays()) ? false : relationsEqual(a.getRelations(), b.getRelations());
      }
   }

   public static boolean nodesEqual(List<? extends OsmNode> nodes1, List<? extends OsmNode> nodes2) {
      if (nodes1.size() != nodes2.size()) {
         return false;
      } else {
         int n = nodes1.size();

         for (int i = 0; i < n; i++) {
            OsmNode a = nodes1.get(i);
            OsmNode b = nodes2.get(i);
            if (!equals(a, b)) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean waysEqual(List<? extends OsmWay> ways1, List<? extends OsmWay> ways2) {
      if (ways1.size() != ways2.size()) {
         return false;
      } else {
         int n = ways1.size();

         for (int i = 0; i < n; i++) {
            OsmWay a = ways1.get(i);
            OsmWay b = ways2.get(i);
            if (!equals(a, b)) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean relationsEqual(List<? extends OsmRelation> relations1, List<? extends OsmRelation> relations2) {
      if (relations1.size() != relations2.size()) {
         return false;
      } else {
         int n = relations1.size();

         for (int i = 0; i < n; i++) {
            OsmRelation a = relations1.get(i);
            OsmRelation b = relations2.get(i);
            if (!equals(a, b)) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean equals(OsmNode a, OsmNode b) {
      if (a.getId() != b.getId()) {
         logger.debug("Node ids not equal");
         return false;
      } else if (Math.abs(a.getLongitude() - b.getLongitude()) > 1.0E-4) {
         logger.debug("Longitudes not equal");
         return false;
      } else if (Math.abs(a.getLatitude() - b.getLatitude()) > 1.0E-4) {
         logger.debug("Latitudes not equal");
         return false;
      } else if (!tagsEqual(a, b)) {
         logger.debug("Node tags not equal");
         return false;
      } else {
         return metadataEqual(a, b);
      }
   }

   public static boolean equals(OsmWay a, OsmWay b) {
      if (a.getId() != b.getId()) {
         logger.debug("Way ids not equal");
         return false;
      } else if (!nodeRefsEqual(a, b)) {
         logger.debug("Node references not equal");
         return false;
      } else if (!tagsEqual(a, b)) {
         logger.debug("Way tags not equal");
         return false;
      } else {
         return metadataEqual(a, b);
      }
   }

   public static boolean equals(OsmRelation a, OsmRelation b) {
      if (a.getId() != b.getId()) {
         logger.debug("Relation ids not equal");
         return false;
      } else if (!membersEqual(a, b)) {
         logger.debug("Members not equal");
         return false;
      } else if (!tagsEqual(a, b)) {
         logger.debug("Relation tags not equal");
         return false;
      } else {
         return metadataEqual(a, b);
      }
   }

   public static boolean tagsEqual(OsmEntity a, OsmEntity b) {
      Map<String, String> tagsA = OsmModelUtil.getTagsAsMap(a);
      Map<String, String> tagsB = OsmModelUtil.getTagsAsMap(b);
      if (tagsA.size() != tagsB.size()) {
         return false;
      } else {
         Set<String> keysA = tagsA.keySet();
         Set<String> keysB = tagsB.keySet();
         if (!keysA.equals(keysB)) {
            return false;
         } else {
            for (String key : keysA) {
               String valA = tagsA.get(key);
               String valB = tagsB.get(key);
               if (!valA.equals(valB)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public static boolean metadataEqual(OsmEntity a, OsmEntity b) {
      OsmMetadata ma = a.getMetadata();
      OsmMetadata mb = b.getMetadata();
      if (ma == null && mb == null) {
         return true;
      } else {
         return ma != null && mb != null
            ? ma.getChangeset() == mb.getChangeset()
               && ma.getTimestamp() == mb.getTimestamp()
               && ma.getUid() == mb.getUid()
               && ma.getUser().equals(mb.getUser())
               && ma.getVersion() == mb.getVersion()
            : false;
      }
   }

   public static boolean nodeRefsEqual(OsmWay a, OsmWay b) {
      if (a.getNumberOfNodes() != b.getNumberOfNodes()) {
         logger.debug("Number of node references not equal");
         return false;
      } else {
         int n = a.getNumberOfNodes();

         for (int i = 0; i < n; i++) {
            if (a.getNodeId(i) != b.getNodeId(i)) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean membersEqual(OsmRelation a, OsmRelation b) {
      if (a.getNumberOfMembers() != b.getNumberOfMembers()) {
         return false;
      } else {
         int n = a.getNumberOfMembers();

         for (int i = 0; i < n; i++) {
            OsmRelationMember member1 = a.getMember(i);
            OsmRelationMember member2 = b.getMember(i);
            if (!membersEqual(member1, member2)) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean membersEqual(OsmRelationMember a, OsmRelationMember b) {
      if (a.getId() != b.getId()) {
         return false;
      } else {
         return a.getType() != b.getType() ? false : a.getRole().equals(b.getRole());
      }
   }
}
