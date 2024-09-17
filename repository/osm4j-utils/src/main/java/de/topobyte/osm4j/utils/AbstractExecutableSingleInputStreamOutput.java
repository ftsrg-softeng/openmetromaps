package de.topobyte.osm4j.utils;

import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmReader;
import de.topobyte.utilities.apache.commons.cli.OptionHelper;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractExecutableSingleInputStreamOutput extends AbstractExecutableInputOutput {
   private static final String OPTION_INPUT = "input";
   protected String pathInput = null;
   protected boolean closeInput = true;
   protected OsmInputStream osmStream;

   public AbstractExecutableSingleInputStreamOutput() {
      OptionHelper.addL(this.options, "input", true, false, "the input file");
   }

   @Override
   protected void setup(String[] args) {
      super.setup(args);
      this.pathInput = this.line.getOptionValue("input");
   }

   protected void init() throws IOException {
      InputStream in = null;
      if (this.pathInput == null) {
         this.closeInput = false;
         in = new BufferedInputStream(System.in);
      } else {
         this.closeInput = true;
         File file = new File(this.pathInput);
         in = StreamUtil.bufferedInputStream(file);
      }

      this.osmStream = new OsmInputStream(in, this.inputFormat);
   }

   protected OsmIterator createIterator() throws IOException {
      OsmStreamInput input = new OsmStreamInput(this.osmStream);
      return input.createIterator(this.readTags, this.readMetadata).getIterator();
   }

   protected OsmReader createReader() throws IOException {
      OsmStreamInput input = new OsmStreamInput(this.osmStream);
      return input.createReader(this.readTags, this.readMetadata).getReader();
   }

   protected void finish() throws IOException {
      if (this.closeInput) {
         this.osmStream.getInputStream().close();
      }
   }
}
