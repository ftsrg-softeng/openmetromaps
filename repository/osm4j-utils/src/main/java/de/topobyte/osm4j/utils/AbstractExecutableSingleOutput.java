package de.topobyte.osm4j.utils;

import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.pbf.seq.PbfWriter;
import de.topobyte.osm4j.tbo.access.TboWriter;
import de.topobyte.osm4j.utils.config.PbfConfig;
import de.topobyte.osm4j.utils.config.PbfOptions;
import de.topobyte.osm4j.utils.config.TboConfig;
import de.topobyte.osm4j.utils.config.TboOptions;
import de.topobyte.osm4j.xml.output.OsmXmlOutputStream;
import de.topobyte.utilities.apache.commons.cli.OptionHelper;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractExecutableSingleOutput extends AbstractExecutable {
   private static final String OPTION_OUTPUT = "output";
   private static final String OPTION_OUTPUT_FORMAT = "output-format";
   protected FileFormat outputFormat;
   protected PbfConfig pbfConfig;
   protected TboConfig tboConfig;
   protected String pathOutput = null;
   protected boolean writeMetadata = true;
   protected boolean closeOutput = true;
   protected OutputStream out;
   protected OsmOutputStream osmOutputStream;

   public AbstractExecutableSingleOutput() {
      OptionHelper.addL(this.options, "output", true, false, "the output file");
      OptionHelper.addL(this.options, "output-format", true, true, "the file format of the output");
      PbfOptions.add(this.options);
      TboOptions.add(this.options);
   }

   @Override
   protected void setup(String[] args) {
      super.setup(args);
      String outputFormatName = this.line.getOptionValue("output-format");
      this.outputFormat = FileFormat.parseFileFormat(outputFormatName);
      if (this.outputFormat == null) {
         System.out.println("invalid output format");
         System.out.println("please specify one of: " + FileFormat.getHumanReadableListOfSupportedFormats());
         System.exit(1);
      }

      this.pbfConfig = PbfOptions.parse(this.line);
      this.tboConfig = TboOptions.parse(this.line);
      this.pathOutput = this.line.getOptionValue("output");
   }

   protected void init() throws IOException {
      this.out = null;
      if (this.pathOutput == null) {
         this.closeOutput = false;
         this.out = new BufferedOutputStream(System.out);
      } else {
         this.closeOutput = true;
         File file = new File(this.pathOutput);
         FileOutputStream fos = new FileOutputStream(file);
         this.out = new BufferedOutputStream(fos);
      }

      switch (this.outputFormat) {
         case XML:
            this.osmOutputStream = new OsmXmlOutputStream(this.out, this.writeMetadata);
            break;
         case TBO:
            TboWriter tboWriter = new TboWriter(this.out, this.writeMetadata);
            tboWriter.setCompression(this.tboConfig.getCompression());
            this.osmOutputStream = tboWriter;
            break;
         case PBF:
            PbfWriter pbfWriter = new PbfWriter(this.out, this.writeMetadata);
            pbfWriter.setCompression(this.pbfConfig.getCompression());
            pbfWriter.setUseDense(this.pbfConfig.isUseDenseNodes());
            this.osmOutputStream = pbfWriter;
      }
   }

   protected void finish() throws IOException {
      this.out.flush();
      if (this.closeOutput) {
         this.out.close();
      }
   }
}
