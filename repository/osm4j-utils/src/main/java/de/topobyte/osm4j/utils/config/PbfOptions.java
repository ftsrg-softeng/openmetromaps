package de.topobyte.osm4j.utils.config;

import de.topobyte.osm4j.pbf.Compression;
import de.topobyte.utilities.apache.commons.cli.OptionHelper;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class PbfOptions {
   public static final String POSSIBLE_COMPRESSION_ARGUMENTS = "none, deflate, lz4";
   private static final String OPTION_PBF_COMPRESSION = "pbf-compression";
   private static final String OPTION_PBF_NONE_DENSE = "pbf-none-dense";

   public static void add(Options options) {
      OptionHelper.addL(options, "pbf-compression", true, false, "PBF output compression. One of none, deflate, lz4");
      OptionHelper.addL(options, "pbf-none-dense", false, false, "Disable dense node packing");
   }

   public static PbfConfig parse(CommandLine line) {
      PbfConfig config = new PbfConfig();
      if (line.hasOption("pbf-compression")) {
         String compressionArg = line.getOptionValue("pbf-compression");
         if (compressionArg.equals("none")) {
            config.setCompression(Compression.NONE);
         } else if (compressionArg.equals("deflate")) {
            config.setCompression(Compression.DEFLATE);
         } else if (compressionArg.equals("lz4")) {
            config.setCompression(Compression.LZ4);
         } else {
            System.out.println("invalid compression value");
            System.out.println("please specify one of: none, deflate, lz4");
            System.exit(1);
         }
      }

      if (line.hasOption("pbf-none-dense")) {
         config.setUseDenseNodes(false);
      }

      return config;
   }
}
