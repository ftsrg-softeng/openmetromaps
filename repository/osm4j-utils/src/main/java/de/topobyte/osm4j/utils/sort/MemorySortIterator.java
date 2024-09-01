package de.topobyte.osm4j.utils.sort;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.dataset.sort.IdComparator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class MemorySortIterator implements OsmIterator {
   private OsmIterator input;
   private boolean ignoreDuplicates = true;
   private Comparator<? super OsmNode> comparatorNodes;
   private Comparator<? super OsmWay> comparatorWays;
   private Comparator<? super OsmRelation> comparatorRelations;
   private List<OsmNode> nodes = new ArrayList<>();
   private List<OsmWay> ways = new ArrayList<>();
   private List<OsmRelation> relations = new ArrayList<>();
   private MemorySortIterator.Mode mode = MemorySortIterator.Mode.START;
   private int index = -1;

   public MemorySortIterator(OsmIterator input) {
      this(input, new IdComparator());
   }

   public MemorySortIterator(OsmIterator input, Comparator<OsmEntity> comparator) {
      this(input, comparator, comparator, comparator);
   }

   public MemorySortIterator(
      OsmIterator input,
      Comparator<? super OsmNode> comparatorNodes,
      Comparator<? super OsmWay> comparatorWays,
      Comparator<? super OsmRelation> comparatorRelations
   ) {
      this.input = input;
      this.comparatorNodes = comparatorNodes;
      this.comparatorWays = comparatorWays;
      this.comparatorRelations = comparatorRelations;
   }

   public boolean isIgnoreDuplicates() {
      return this.ignoreDuplicates;
   }

   public void setIgnoreDuplicates(boolean ignoreDuplicates) {
      this.ignoreDuplicates = ignoreDuplicates;
   }

   public Iterator<EntityContainer> iterator() {
      return this;
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   public boolean hasBounds() {
      return this.input.hasBounds();
   }

   public OsmBounds getBounds() {
      return this.input.getBounds();
   }

   public boolean hasNext() {
      switch (this.mode) {
         case NODES:
         default:
            if (this.index < this.nodes.size()) {
               return true;
            }

            return !this.ways.isEmpty() || !this.relations.isEmpty() || this.input.hasNext();
         case WAYS:
            if (this.index < this.ways.size()) {
               return true;
            }

            return !this.relations.isEmpty() || this.input.hasNext();
         case RELATIONS:
            return this.index < this.relations.size();
         case START:
            return this.input.hasNext();
         case END:
            return false;
      }
   }

   public EntityContainer next() {
      EntityContainer container = this.nextOrSwitchMode();
      return container != null ? container : this.nextOrSwitchMode();
   }

   private EntityContainer nextOrSwitchMode() {
      switch (this.mode) {
         case NODES:
         default:
            if (this.index < this.nodes.size()) {
               OsmNode node = this.nodes.get(this.index++);
               this.skipDuplicates(this.nodes, node.getId());
               return new EntityContainer(EntityType.Node, node);
            }

            this.nodes.clear();
            this.setMode();
            this.read();
            break;
         case WAYS:
            if (this.index < this.ways.size()) {
               OsmWay way = this.ways.get(this.index++);
               this.skipDuplicates(this.ways, way.getId());
               return new EntityContainer(EntityType.Way, way);
            }

            this.ways.clear();
            this.setMode();
            this.read();
            break;
         case RELATIONS:
            if (this.index < this.relations.size()) {
               OsmRelation relation = this.relations.get(this.index++);
               this.skipDuplicates(this.relations, relation.getId());
               return new EntityContainer(EntityType.Relation, relation);
            }

            this.relations.clear();
            this.setMode();
            break;
         case START:
            this.init();
            break;
         case END:
            throw new NoSuchElementException();
      }

      return null;
   }

   private void init() {
      EntityContainer container = (EntityContainer)this.input.next();
      switch (container.getType()) {
         case Node:
         default:
            this.nodes.add((OsmNode)container.getEntity());
            break;
         case Way:
            this.ways.add((OsmWay)container.getEntity());
            break;
         case Relation:
            this.relations.add((OsmRelation)container.getEntity());
      }

      this.setMode();
      this.read();
   }

   private void setMode() {
      if (!this.nodes.isEmpty()) {
         this.mode = MemorySortIterator.Mode.NODES;
      } else if (!this.ways.isEmpty()) {
         this.mode = MemorySortIterator.Mode.WAYS;
      } else if (!this.relations.isEmpty()) {
         this.mode = MemorySortIterator.Mode.RELATIONS;
      } else {
         this.mode = MemorySortIterator.Mode.END;
      }
   }

   private void read() {
      switch (this.mode) {
         case NODES:
            while (this.input.hasNext()) {
               EntityContainer container = (EntityContainer)this.input.next();
               OsmEntity entity = container.getEntity();
               if (container.getType() == EntityType.Node) {
                  this.nodes.add((OsmNode)entity);
               } else if (container.getType() == EntityType.Way) {
                  this.ways.add((OsmWay)entity);
               } else if (container.getType() == EntityType.Relation) {
                  this.relations.add((OsmRelation)entity);
               }
            }

            Collections.sort(this.nodes, this.comparatorNodes);
            this.index = 0;
            break;
         case WAYS:
            while (this.input.hasNext()) {
               EntityContainer container = (EntityContainer)this.input.next();
               OsmEntity entity = container.getEntity();
               if (container.getType() == EntityType.Way) {
                  this.ways.add((OsmWay)entity);
               } else if (container.getType() == EntityType.Relation) {
                  this.relations.add((OsmRelation)entity);
               }
            }

            Collections.sort(this.ways, this.comparatorWays);
            this.index = 0;
            break;
         case RELATIONS:
            while (this.input.hasNext()) {
               EntityContainer container = (EntityContainer)this.input.next();
               OsmEntity entity = container.getEntity();
               if (container.getType() == EntityType.Relation) {
                  this.relations.add((OsmRelation)entity);
               }
            }

            Collections.sort(this.relations, this.comparatorRelations);
            this.index = 0;
         case START:
         case END:
      }
   }

   private <T extends OsmEntity> void skipDuplicates(List<T> elements, long id) {
      while (this.index < elements.size() && elements.get(this.index).getId() == id) {
         this.index++;
      }
   }

   private static enum Mode {
      START,
      NODES,
      WAYS,
      RELATIONS,
      END;
   }
}
