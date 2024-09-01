package de.topobyte.osm4j.utils;

import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.utilities.apache.commons.cli.OptionHelper;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractExecutableSingleInputStreamSingleOutput extends AbstractExecutableSingleInputStreamOutput {
   private static final String OPTION_OUTPUT = "output";
   protected String pathOutput = null;
   protected boolean closeOutput = true;
   protected OutputStream out;
   protected OsmOutputStream osmOutputStream;

   public AbstractExecutableSingleInputStreamSingleOutput() {
      OptionHelper.addL(this.options, "output", true, false, "the output file");
   }

   @Override
   protected void setup(String[] args) {
      super.setup(args);
      this.pathOutput = this.line.getOptionValue("output");
   }

   @Override
   protected void init() throws IOException {
      super.init();
      this.out = null;
      if (this.pathOutput == null) {
         this.closeOutput = false;
         this.out = new BufferedOutputStream(System.out);
      } else {
         this.closeOutput = true;
         this.out = StreamUtil.bufferedOutputStream(this.pathOutput);
      }

      this.osmOutputStream = OsmIoUtils.setupOsmOutput(this.out, this.outputFormat, this.writeMetadata, this.pbfConfig, this.tboConfig);
   }

   @Override
   protected void finish() throws IOException {
      super.finish();
      this.out.flush();
      if (this.closeOutput) {
         this.out.close();
      }
   }
}
