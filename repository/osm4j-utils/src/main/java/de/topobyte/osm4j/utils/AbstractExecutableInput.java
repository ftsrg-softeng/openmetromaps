package de.topobyte.osm4j.utils;

import de.topobyte.utilities.apache.commons.cli.OptionHelper;

public abstract class AbstractExecutableInput extends AbstractExecutable {
   private static final String OPTION_INPUT_FORMAT = "input-format";
   protected FileFormat inputFormat;
   protected boolean readTags = true;
   protected boolean readMetadata = true;

   public AbstractExecutableInput() {
      OptionHelper.addL(this.options, "input-format", true, true, "the file format of the input");
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
   }
}
