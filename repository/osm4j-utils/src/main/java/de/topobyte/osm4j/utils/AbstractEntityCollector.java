package de.topobyte.osm4j.utils;

import com.slimjars.dist.gnu.trove.set.TLongSet;
import com.slimjars.dist.gnu.trove.set.hash.TLongHashSet;
import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmHandler;
import de.topobyte.osm4j.core.access.OsmInputException;
import de.topobyte.osm4j.core.access.OsmReader;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.utilities.apache.commons.cli.OptionHelper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public abstract class AbstractEntityCollector extends AbstractExecutableSingleInputStreamSingleOutput implements OsmHandler {
   private static final String OPTION_REFERENCES = "references";
   private static final String OPTION_REFERENCES_FORMAT = "references-format";
   private String pathReferences;
   private InputStream inRefs;
   protected Iterator<EntityContainer> iteratorReferences;
   protected TLongSet ids = new TLongHashSet();

   public AbstractEntityCollector() {
      OptionHelper.addL(this.options, "references", true, true, "the file to determine references from");
      OptionHelper.addL(this.options, "references-format", true, true, "the file format of the references file");
   }

   @Override
   protected void setup(String[] args) {
      super.setup(args);
      this.pathReferences = this.line.getOptionValue("references");
   }

   @Override
   protected void init() throws IOException {
      this.inRefs = StreamUtil.bufferedInputStream(this.pathReferences);
      this.iteratorReferences = OsmIoUtils.setupOsmIterator(this.inRefs, this.inputFormat, this.readMetadata);
      super.init();
   }

   protected void run() throws OsmInputException, IOException {
      this.readReferences();

      try {
         this.inRefs.close();
      } catch (IOException var2) {
         throw new OsmInputException("unable to close references input", var2);
      }

      OsmReader reader = this.createReader();
      reader.setHandler(this);
      reader.read();
   }

   public void handle(OsmBounds bounds) throws IOException {
   }

   public void handle(OsmNode node) throws IOException {
      if (this.take(node)) {
         this.osmOutputStream.write(node);
      }
   }

   public void handle(OsmWay way) throws IOException {
      if (this.take(way)) {
         this.osmOutputStream.write(way);
      }
   }

   public void handle(OsmRelation relation) throws IOException {
      if (this.take(relation)) {
         this.osmOutputStream.write(relation);
      }
   }

   public void complete() throws IOException {
      this.osmOutputStream.complete();
   }

   protected abstract void readReferences();

   protected abstract boolean take(OsmNode var1);

   protected abstract boolean take(OsmWay var1);

   protected abstract boolean take(OsmRelation var1);
}
