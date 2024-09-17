package de.topobyte.osm4j.utils;

import de.topobyte.largescalefileio.ClosingFileInputStreamFactory;
import de.topobyte.largescalefileio.SimpleClosingFileInputStreamFactory;
import de.topobyte.osm4j.core.access.OsmIdIterator;
import de.topobyte.osm4j.core.access.OsmIdIteratorInput;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmIteratorInput;
import de.topobyte.osm4j.core.access.OsmIteratorInputFactory;
import de.topobyte.osm4j.core.dataset.sort.IdComparator;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class OsmFileSetInput implements OsmIteratorInputFactory {
   private Collection<OsmFile> osmFiles;
   private Comparator<? super OsmNode> comparatorNodes;
   private Comparator<? super OsmWay> comparatorWays;
   private Comparator<? super OsmRelation> comparatorRelations;

   public OsmFileSetInput(Collection<OsmFile> osmFiles) {
      this(osmFiles, new IdComparator());
   }

   public OsmFileSetInput(Collection<OsmFile> osmFiles, Comparator<? super OsmEntity> comparator) {
      this(osmFiles, comparator, comparator, comparator);
   }

   public OsmFileSetInput(
      Collection<OsmFile> osmFiles,
      Comparator<? super OsmNode> comparatorNodes,
      Comparator<? super OsmWay> comparatorWays,
      Comparator<? super OsmRelation> comparatorRelations
   ) {
      this.osmFiles = osmFiles;
      this.comparatorNodes = comparatorNodes;
      this.comparatorWays = comparatorWays;
      this.comparatorRelations = comparatorRelations;
   }

   public OsmIteratorInput createIterator(boolean readTags, boolean readMetadata) throws IOException {
      Collection<InputStream> inputs = new ArrayList<>();
      Collection<OsmIterator> iterators = new ArrayList<>();
      ClosingFileInputStreamFactory factory = new SimpleClosingFileInputStreamFactory();

      for (OsmFile osmFile : this.osmFiles) {
         InputStream input = factory.create(osmFile.getPath().toFile());
         OsmIterator iterator = OsmIoUtils.setupOsmIterator(input, osmFile.getFileFormat(), readTags, readMetadata);
         inputs.add(input);
         iterators.add(iterator);
      }

      return new OsmMergeIteratorInput(inputs, iterators, this.comparatorNodes, this.comparatorWays, this.comparatorRelations);
   }

   public OsmIdIteratorInput createIdIterator() throws IOException {
      Collection<InputStream> inputs = new ArrayList<>();
      Collection<OsmIdIterator> iterators = new ArrayList<>();
      ClosingFileInputStreamFactory factory = new SimpleClosingFileInputStreamFactory();

      for (OsmFile osmFile : this.osmFiles) {
         InputStream input = factory.create(osmFile.getPath().toFile());
         OsmIdIterator iterator = OsmIoUtils.setupOsmIdIterator(input, osmFile.getFileFormat());
         inputs.add(input);
         iterators.add(iterator);
      }

      return new OsmMergeIdIteratorInput(inputs, iterators);
   }
}
