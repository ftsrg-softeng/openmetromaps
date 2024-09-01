package de.topobyte.osm4j.utils;

import de.topobyte.osm4j.core.access.OsmIdIterator;
import de.topobyte.osm4j.core.access.OsmIdIteratorInput;
import de.topobyte.osm4j.utils.merge.sorted.SortedIdMergeIterator;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class OsmMergeIdIteratorInput implements OsmIdIteratorInput {
   private Collection<InputStream> inputs;
   private Collection<OsmIdIterator> iterators;

   public OsmMergeIdIteratorInput(Collection<InputStream> inputs, Collection<OsmIdIterator> iterators) {
      this.inputs = inputs;
      this.iterators = iterators;
   }

   public void close() throws IOException {
      for (InputStream input : this.inputs) {
         input.close();
      }
   }

   public OsmIdIterator getIterator() throws IOException {
      return new SortedIdMergeIterator(this.iterators);
   }
}
