package de.topobyte.osm4j.utils;

import de.topobyte.utilities.apache.commons.cli.OptionHelper;

public abstract class AbstractAreaFilter extends AbstractExecutableSingleInputStreamSingleOutput {
   private static final String OPTION_ONLY_NODES = "nodes-only";
   protected boolean onlyNodes;

   public AbstractAreaFilter() {
      OptionHelper.addL(this.options, "nodes-only", false, false, "extract only nodes");
   }

   @Override
   protected void setup(String[] args) {
      super.setup(args);
      this.onlyNodes = false;
      this.onlyNodes = this.line.hasOption("nodes-only");
   }
}
