package de.topobyte.osm4j.utils;

import de.topobyte.utilities.apache.commons.cli.OptionHelper;
import java.nio.file.Paths;

public abstract class AbstractExecutableSingleInputFileOutput extends AbstractExecutableInputOutput {
   private static final String OPTION_INPUT = "input";
   protected String pathInput;

   public AbstractExecutableSingleInputFileOutput() {
      OptionHelper.addL(this.options, "input", true, true, "the input file");
   }

   @Override
   protected void setup(String[] args) {
      super.setup(args);
      this.pathInput = this.line.getOptionValue("input");
   }

   protected OsmFile getOsmFile() {
      return new OsmFile(Paths.get(this.pathInput), this.inputFormat);
   }

   protected OsmFileInput getOsmFileInput() {
      return new OsmFileInput(this.getOsmFile());
   }
}
