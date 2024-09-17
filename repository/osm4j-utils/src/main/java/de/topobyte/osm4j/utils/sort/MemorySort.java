package de.topobyte.osm4j.utils.sort;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.dataset.sort.IdComparator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MemorySort {
   private OsmOutputStream output;
   private OsmIterator input;
   private boolean ignoreDuplicates = true;
   private Comparator<? super OsmNode> comparatorNodes;
   private Comparator<? super OsmWay> comparatorWays;
   private Comparator<? super OsmRelation> comparatorRelations;
   private List<OsmNode> nodes = new ArrayList<>();
   private List<OsmWay> ways = new ArrayList<>();
   private List<OsmRelation> relations = new ArrayList<>();
   private boolean hasMore = true;
   private EntityContainer next = null;

   public MemorySort(OsmOutputStream output, OsmIterator input) {
      this(output, input, new IdComparator());
   }

   public MemorySort(OsmOutputStream output, OsmIterator input, Comparator<OsmEntity> comparator) {
      this(output, input, comparator, comparator, comparator);
   }

   public MemorySort(
      OsmOutputStream output,
      OsmIterator input,
      Comparator<? super OsmNode> comparatorNodes,
      Comparator<? super OsmWay> comparatorWays,
      Comparator<? super OsmRelation> comparatorRelations
   ) {
      this.output = output;
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

   public void run() throws IOException {
      this.execute();
      this.output.complete();
   }

   private void advance() {
      this.hasMore = this.input.hasNext();
      if (this.hasMore) {
         this.next = (EntityContainer)this.input.next();
      } else {
         this.next = null;
      }
   }

   private void execute() throws IOException {
      if (this.input.hasBounds()) {
         this.output.write(this.input.getBounds());
      }

      this.advance();

      while (this.hasMore && this.next.getType() == EntityType.Node) {
         this.nodes.add((OsmNode)this.next.getEntity());
         this.advance();
      }

      Collections.sort(this.nodes, this.comparatorNodes);
      this.writeNodes();
      this.nodes.clear();

      while (this.hasMore && this.next.getType() == EntityType.Way) {
         this.ways.add((OsmWay)this.next.getEntity());
         this.advance();
      }

      Collections.sort(this.ways, this.comparatorWays);
      this.writeWays();
      this.ways.clear();

      while (this.hasMore && this.next.getType() == EntityType.Relation) {
         this.relations.add((OsmRelation)this.next.getEntity());
         this.advance();
      }

      Collections.sort(this.relations, this.comparatorRelations);
      this.writeRelations();
      this.relations.clear();
   }

   private void writeNodes() throws IOException {
      MemorySort.EntityWriter<OsmNode> writer = new MemorySort.EntityWriter<OsmNode>() {
         public void write(OsmNode node) throws IOException {
            MemorySort.this.output.write(node);
         }
      };
      writer.write(this.nodes);
   }

   private void writeWays() throws IOException {
      MemorySort.EntityWriter<OsmWay> writer = new MemorySort.EntityWriter<OsmWay>() {
         public void write(OsmWay way) throws IOException {
            MemorySort.this.output.write(way);
         }
      };
      writer.write(this.ways);
   }

   private void writeRelations() throws IOException {
      MemorySort.EntityWriter<OsmRelation> writer = new MemorySort.EntityWriter<OsmRelation>() {
         public void write(OsmRelation relation) throws IOException {
            MemorySort.this.output.write(relation);
         }
      };
      writer.write(this.relations);
   }

   private abstract class EntityWriter<T extends OsmEntity> {
      private EntityWriter() {
      }

      public abstract void write(T var1) throws IOException;

      public void write(List<T> elements) throws IOException {
         if (!MemorySort.this.ignoreDuplicates) {
            this.writeAll(elements);
         } else {
            this.writeUnique(elements);
         }
      }

      public void writeAll(List<T> elements) throws IOException {
         for (T element : elements) {
            this.write(element);
         }
      }

      private void writeUnique(List<T> elements) throws IOException {
         if (!elements.isEmpty()) {
            T element = elements.get(0);
            this.write(element);
            long last = element.getId();

            for (int i = 1; i < elements.size(); i++) {
               element = elements.get(i);
               if (last != element.getId()) {
                  this.write(element);
                  last = element.getId();
               }
            }
         }
      }
   }
}
