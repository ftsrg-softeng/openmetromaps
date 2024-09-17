package de.topobyte.osm4j.core.dataset;

import com.slimjars.dist.gnu.trove.set.TLongSet;
import de.topobyte.osm4j.core.access.OsmHandler;
import de.topobyte.osm4j.core.access.OsmIdHandler;
import de.topobyte.osm4j.core.access.OsmIdIterator;
import de.topobyte.osm4j.core.access.OsmIdReader;
import de.topobyte.osm4j.core.access.OsmInputException;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmReader;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.IdContainer;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;

public class IdDataSetReader {
   public static InMemorySetIdDataSet read(OsmIdIterator iterator) throws IOException {
      InMemorySetIdDataSet dataSet = new InMemorySetIdDataSet();
      TLongSet nodeIds = dataSet.getNodeIds();
      TLongSet wayIds = dataSet.getWayIds();
      TLongSet relationIds = dataSet.getRelationIds();
      if (iterator.hasBounds()) {
         dataSet.setBounds(iterator.getBounds());
      }

      while (iterator.hasNext()) {
         IdContainer container = iterator.next();
         switch (container.getType()) {
            case Node:
               nodeIds.add(container.getId());
               break;
            case Way:
               wayIds.add(container.getId());
               break;
            case Relation:
               relationIds.add(container.getId());
         }
      }

      return dataSet;
   }

   public static InMemorySetIdDataSet read(OsmIterator iterator) throws IOException {
      InMemorySetIdDataSet dataSet = new InMemorySetIdDataSet();
      TLongSet nodeIds = dataSet.getNodeIds();
      TLongSet wayIds = dataSet.getWayIds();
      TLongSet relationIds = dataSet.getRelationIds();
      if (iterator.hasBounds()) {
         dataSet.setBounds(iterator.getBounds());
      }

      while (iterator.hasNext()) {
         EntityContainer container = iterator.next();
         switch (container.getType()) {
            case Node:
               nodeIds.add(container.getEntity().getId());
               break;
            case Way:
               wayIds.add(container.getEntity().getId());
               break;
            case Relation:
               relationIds.add(container.getEntity().getId());
         }
      }

      return dataSet;
   }

   public static InMemorySetIdDataSet read(OsmIdReader reader) throws OsmInputException {
      final InMemorySetIdDataSet dataSet = new InMemorySetIdDataSet();
      final TLongSet nodeIds = dataSet.getNodeIds();
      final TLongSet wayIds = dataSet.getWayIds();
      final TLongSet relationIds = dataSet.getRelationIds();
      reader.setIdHandler(new OsmIdHandler() {
         @Override
         public void handle(OsmBounds bounds) throws IOException {
            dataSet.setBounds(bounds);
         }

         @Override
         public void handleNode(long id) throws IOException {
            nodeIds.add(id);
         }

         @Override
         public void handleWay(long id) throws IOException {
            wayIds.add(id);
         }

         @Override
         public void handleRelation(long id) throws IOException {
            relationIds.add(id);
         }

         @Override
         public void complete() throws IOException {
         }
      });
      reader.read();
      return dataSet;
   }

   public static InMemorySetIdDataSet read(OsmReader reader) throws OsmInputException {
      final InMemorySetIdDataSet dataSet = new InMemorySetIdDataSet();
      final TLongSet nodeIds = dataSet.getNodeIds();
      final TLongSet wayIds = dataSet.getWayIds();
      final TLongSet relationIds = dataSet.getRelationIds();
      reader.setHandler(new OsmHandler() {
         @Override
         public void handle(OsmBounds bounds) throws IOException {
            dataSet.setBounds(bounds);
         }

         @Override
         public void handle(OsmNode node) throws IOException {
            nodeIds.add(node.getId());
         }

         @Override
         public void handle(OsmWay way) throws IOException {
            wayIds.add(way.getId());
         }

         @Override
         public void handle(OsmRelation relation) throws IOException {
            relationIds.add(relation.getId());
         }

         @Override
         public void complete() throws IOException {
         }
      });
      reader.read();
      return dataSet;
   }
}
