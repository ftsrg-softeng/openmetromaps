package de.topobyte.osm4j.core.dataset;

import com.slimjars.dist.gnu.trove.list.array.TLongArrayList;
import com.slimjars.dist.gnu.trove.map.TLongObjectMap;
import de.topobyte.osm4j.core.access.OsmHandler;
import de.topobyte.osm4j.core.access.OsmInputException;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmIteratorInput;
import de.topobyte.osm4j.core.access.OsmReader;
import de.topobyte.osm4j.core.access.OsmReaderInput;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.core.model.impl.Relation;
import de.topobyte.osm4j.core.model.impl.Way;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapDataSetLoader {
   public static InMemoryMapDataSet read(OsmIteratorInput iteratorInput, boolean keepNodeTags, boolean keepWayTags, boolean keepRelationTags) throws IOException {
      OsmIterator iterator = iteratorInput.getIterator();
      InMemoryMapDataSet data = read(iterator, keepNodeTags, keepWayTags, keepRelationTags);
      iteratorInput.close();
      return data;
   }

   public static InMemoryMapDataSet read(OsmReaderInput readerInput, boolean keepNodeTags, boolean keepWayTags, boolean keepRelationTags) throws IOException, OsmInputException {
      OsmReader reader = readerInput.getReader();
      InMemoryMapDataSet data = read(reader, keepNodeTags, keepWayTags, keepRelationTags);
      readerInput.close();
      return data;
   }

   public static InMemoryMapDataSet read(OsmIterator iterator, boolean keepNodeTags, boolean keepWayTags, boolean keepRelationTags) throws IOException {
      InMemoryMapDataSet dataSet = new InMemoryMapDataSet();
      TLongObjectMap<OsmNode> nodes = dataSet.getNodes();
      TLongObjectMap<OsmWay> ways = dataSet.getWays();
      TLongObjectMap<OsmRelation> relations = dataSet.getRelations();
      if (iterator.hasBounds()) {
         dataSet.setBounds(iterator.getBounds());
      }

      while (iterator.hasNext()) {
         EntityContainer container = iterator.next();
         switch (container.getType()) {
            case Node:
               OsmNode node = (OsmNode)container.getEntity();
               if (!keepNodeTags) {
                  node = new Node(node.getId(), node.getLongitude(), node.getLatitude());
               }

               nodes.put(node.getId(), node);
               break;
            case Way:
               OsmWay way = (OsmWay)container.getEntity();
               if (!keepWayTags) {
                  TLongArrayList ids = new TLongArrayList();

                  for (int i = 0; i < way.getNumberOfNodes(); i++) {
                     ids.add(way.getNodeId(i));
                  }

                  way = new Way(way.getId(), ids);
               }

               ways.put(way.getId(), way);
               break;
            case Relation:
               OsmRelation relation = (OsmRelation)container.getEntity();
               if (!keepRelationTags) {
                  List<OsmRelationMember> members = new ArrayList<>();

                  for (int i = 0; i < relation.getNumberOfMembers(); i++) {
                     members.add(relation.getMember(i));
                  }

                  relation = new Relation(relation.getId(), members);
               }

               relations.put(relation.getId(), relation);
         }
      }

      return dataSet;
   }

   public static InMemoryMapDataSet read(OsmReader reader, final boolean keepNodeTags, final boolean keepWayTags, final boolean keepRelationTags) throws OsmInputException {
      final InMemoryMapDataSet dataSet = new InMemoryMapDataSet();
      final TLongObjectMap<OsmNode> nodes = dataSet.getNodes();
      final TLongObjectMap<OsmWay> ways = dataSet.getWays();
      final TLongObjectMap<OsmRelation> relations = dataSet.getRelations();
      reader.setHandler(new OsmHandler() {
         @Override
         public void handle(OsmBounds bounds) throws IOException {
            dataSet.setBounds(bounds);
         }

         @Override
         public void handle(OsmNode node) throws IOException {
            if (!keepNodeTags) {
               node = new Node(node.getId(), node.getLongitude(), node.getLatitude());
            }

            nodes.put(node.getId(), node);
         }

         @Override
         public void handle(OsmWay way) throws IOException {
            if (!keepWayTags) {
               TLongArrayList ids = new TLongArrayList();

               for (int i = 0; i < way.getNumberOfNodes(); i++) {
                  ids.add(way.getNodeId(i));
               }

               way = new Way(way.getId(), ids);
            }

            ways.put(way.getId(), way);
         }

         @Override
         public void handle(OsmRelation relation) throws IOException {
            if (!keepRelationTags) {
               List<OsmRelationMember> members = new ArrayList<>();

               for (int i = 0; i < relation.getNumberOfMembers(); i++) {
                  members.add(relation.getMember(i));
               }

               relation = new Relation(relation.getId(), members);
            }

            relations.put(relation.getId(), relation);
         }

         @Override
         public void complete() throws IOException {
         }
      });
      reader.read();
      return dataSet;
   }
}
