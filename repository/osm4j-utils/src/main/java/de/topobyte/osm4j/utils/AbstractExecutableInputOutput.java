package de.topobyte.osm4j.utils;

import de.topobyte.osm4j.utils.config.PbfConfig;
import de.topobyte.osm4j.utils.config.PbfOptions;
import de.topobyte.osm4j.utils.config.TboConfig;
import de.topobyte.osm4j.utils.config.TboOptions;
import de.topobyte.utilities.apache.commons.cli.OptionHelper;

public abstract class AbstractExecutableInputOutput extends AbstractExecutable {
   private static final String OPTION_INPUT_FORMAT = "input-format";
   private static final String OPTION_OUTPUT_FORMAT = "output-format";
   protected FileFormat inputFormat;
   protected FileFormat outputFormat;
   protected PbfConfig pbfConfig;
   protected TboConfig tboConfig;
   protected boolean readTags = true;
   protected boolean readMetadata = true;
   protected boolean writeMetadata = true;

   public AbstractExecutableInputOutput() {
      OptionHelper.addL(this.options, "input-format", true, true, "the file format of the input");
      OptionHelper.addL(this.options, "output-format", true, true, "the file format of the output");
      PbfOptions.add(this.options);
      TboOptions.add(this.options);
   }

   @Override
   protected void setup(String[] args) {
      super.setup(args);
      String inputFormatName = this.line.getOptionValue("input-format");
      this.inputFormat = FileFormat.parseFileFormat(inputFormatName);
      if (this.inputFormat == null) {
         System.out.println("invalid input format");
         System.out.println("please specify one of: " + FileFormat.getHumanReadableListOfSupportedFormats());
         System.exit(1);
      }

      String outputFormatName = this.line.getOptionValue("output-format");
      this.outputFormat = FileFormat.parseFileFormat(outputFormatName);
      if (this.outputFormat == null) {
         System.out.println("invalid output format");
         System.out.println("please specify one of: " + FileFormat.getHumanReadableListOfSupportedFormats());
         System.exit(1);
      }

      this.pbfConfig = PbfOptions.parse(this.line);
      this.tboConfig = TboOptions.parse(this.line);
   }
}
