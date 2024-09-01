package de.topobyte.osm4j.utils;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmIteratorInput;
import de.topobyte.osm4j.core.dataset.sort.IdComparator;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.utils.merge.sorted.SortedMergeIterator;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Comparator;

public class OsmMergeIteratorInput implements OsmIteratorInput {
   private Collection<InputStream> inputs;
   private Collection<OsmIterator> iterators;
   private Comparator<? super OsmNode> comparatorNodes;
   private Comparator<? super OsmWay> comparatorWays;
   private Comparator<? super OsmRelation> comparatorRelations;

   public OsmMergeIteratorInput(Collection<InputStream> inputs, Collection<OsmIterator> iterators) {
      this(inputs, iterators, new IdComparator());
   }

   public OsmMergeIteratorInput(Collection<InputStream> inputs, Collection<OsmIterator> iterators, Comparator<? super OsmEntity> comparator) {
      this(inputs, iterators, comparator, comparator, comparator);
   }

   public OsmMergeIteratorInput(
      Collection<InputStream> inputs,
      Collection<OsmIterator> iterators,
      Comparator<? super OsmNode> comparatorNodes,
      Comparator<? super OsmWay> comparatorWays,
      Comparator<? super OsmRelation> comparatorRelations
   ) {
      this.inputs = inputs;
      this.iterators = iterators;
      this.comparatorNodes = comparatorNodes;
      this.comparatorWays = comparatorWays;
      this.comparatorRelations = comparatorRelations;
   }

   public void close() throws IOException {
      for (InputStream input : this.inputs) {
         input.close();
      }
   }

   public OsmIterator getIterator() throws IOException {
      return new SortedMergeIterator(this.iterators, this.comparatorNodes, this.comparatorWays, this.comparatorRelations);
   }
}
