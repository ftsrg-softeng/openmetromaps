package de.topobyte.osm4j.testing;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.testing.model.TestNode;
import de.topobyte.osm4j.testing.model.TestRelation;
import de.topobyte.osm4j.testing.model.TestWay;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class TestDataSetIterator implements OsmIterator {
   private TestDataSet data;
   private int index = 0;

   public TestDataSetIterator(TestDataSet data) {
      this.data = data;
   }

   public Iterator<EntityContainer> iterator() {
      return this;
   }

   public boolean hasBounds() {
      return this.data.hasBounds();
   }

   public OsmBounds getBounds() {
      return this.data.getBounds();
   }

   public boolean hasNext() {
      List<TestNode> nodes = this.data.getNodes();
      List<TestWay> ways = this.data.getWays();
      List<TestRelation> relations = this.data.getRelations();
      return this.index < nodes.size() + ways.size() + relations.size();
   }

   public EntityContainer next() {
      List<TestNode> nodes = this.data.getNodes();
      List<TestWay> ways = this.data.getWays();
      List<TestRelation> relations = this.data.getRelations();
      if (this.index < nodes.size()) {
         return new EntityContainer(EntityType.Node, nodes.get(this.index++));
      } else {
         int wayIndex = this.index - nodes.size();
         if (wayIndex < ways.size()) {
            this.index++;
            return new EntityContainer(EntityType.Way, ways.get(wayIndex));
         } else {
            int relationIndex = wayIndex - ways.size();
            if (relationIndex < relations.size()) {
               this.index++;
               return new EntityContainer(EntityType.Relation, relations.get(relationIndex));
            } else {
               throw new NoSuchElementException();
            }
         }
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
