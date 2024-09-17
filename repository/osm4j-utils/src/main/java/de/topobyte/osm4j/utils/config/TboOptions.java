package de.topobyte.osm4j.utils.config;

import de.topobyte.osm4j.tbo.Compression;
import de.topobyte.osm4j.utils.config.limit.ElementCountLimit;
import de.topobyte.osm4j.utils.config.limit.RelationMemberLimit;
import de.topobyte.osm4j.utils.config.limit.WayNodeLimit;
import de.topobyte.utilities.apache.commons.cli.OptionHelper;
import de.topobyte.utilities.apache.commons.cli.parsing.ArgumentHelper;
import de.topobyte.utilities.apache.commons.cli.parsing.ArgumentParseException;
import de.topobyte.utilities.apache.commons.cli.parsing.IntegerOption;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class TboOptions {
   public static final String POSSIBLE_COMPRESSION_ARGUMENTS = "none, deflate, lz4";
   private static final String OPTION_TBO_COMPRESSION = "tbo-compression";
   private static final String OPTION_TBO_MAX_ELEMENTS_PER_BLOCK = "tbo-max-elements-per-block";
   private static final String OPTION_TBO_MAX_NODES_PER_BLOCK = "tbo-max-nodes-per-block";
   private static final String OPTION_TBO_MAX_WAYS_PER_BLOCK = "tbo-max-ways-per-block";
   private static final String OPTION_TBO_MAX_RELATIONS_PER_BLOCK = "tbo-max-relations-per-block";
   private static final String OPTION_TBO_MAX_WAY_NODES_PER_BLOCK = "tbo-max-way-nodes-per-block";
   private static final String OPTION_TBO_MAX_RELATION_MEMBERS_PER_BLOCK = "tbo-max-relation-members-per-block";

   public static void add(Options options) {
      OptionHelper.addL(options, "tbo-compression", true, false, "TBO output compression. One of none, deflate, lz4");
      OptionHelper.addL(options, "tbo-max-elements-per-block", true, false, "TBO: max number of elements per block");
      OptionHelper.addL(options, "tbo-max-nodes-per-block", true, false, "TBO: max number of nodes per block");
      OptionHelper.addL(options, "tbo-max-ways-per-block", true, false, "TBO: max number of ways per block");
      OptionHelper.addL(options, "tbo-max-relations-per-block", true, false, "TBO: max number of relations per block");
      OptionHelper.addL(options, "tbo-max-way-nodes-per-block", true, false, "TBO: max number of way nodes per block");
      OptionHelper.addL(options, "tbo-max-relation-members-per-block", true, false, "TBO: max number of relation members per block");
   }

   public static TboConfig parse(CommandLine line) {
      TboConfig config = new TboConfig();
      if (line.hasOption("tbo-compression")) {
         String compressionArg = line.getOptionValue("tbo-compression");
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

      try {
         IntegerOption maxElementsPerBlock = parseInteger(line, "tbo-max-elements-per-block");
         IntegerOption maxNodesPerBlock = parseInteger(line, "tbo-max-nodes-per-block");
         IntegerOption maxWaysPerBlock = parseInteger(line, "tbo-max-ways-per-block");
         IntegerOption maxRelationsPerBlock = parseInteger(line, "tbo-max-relations-per-block");
         IntegerOption maxWayNodesPerBlock = parseInteger(line, "tbo-max-way-nodes-per-block");
         IntegerOption maxRelationMembersPerBlock = parseInteger(line, "tbo-max-relation-members-per-block");
         if (maxElementsPerBlock.hasValue()) {
            config.setLimitNodes(new ElementCountLimit(maxElementsPerBlock.getValue()));
            config.setLimitWays(new ElementCountLimit(maxElementsPerBlock.getValue()));
            config.setLimitRelations(new ElementCountLimit(maxElementsPerBlock.getValue()));
         }

         if (maxNodesPerBlock.hasValue()) {
            config.setLimitNodes(new ElementCountLimit(maxNodesPerBlock.getValue()));
         }

         if (maxWaysPerBlock.hasValue()) {
            config.setLimitWays(new ElementCountLimit(maxWaysPerBlock.getValue()));
         }

         if (maxRelationsPerBlock.hasValue()) {
            config.setLimitRelations(new ElementCountLimit(maxRelationsPerBlock.getValue()));
         }

         if (maxWayNodesPerBlock.hasValue()) {
            config.setLimitWays(new WayNodeLimit(maxWayNodesPerBlock.getValue()));
         }

         if (maxRelationMembersPerBlock.hasValue()) {
            config.setLimitRelations(new RelationMemberLimit(maxRelationMembersPerBlock.getValue()));
         }
      } catch (ArgumentParseException var8) {
         System.out.println("Error while parsing options: " + var8.getMessage());
         System.exit(1);
      }

      return config;
   }

   private static IntegerOption parseInteger(CommandLine line, String option) throws ArgumentParseException {
      IntegerOption integer;
      try {
         integer = ArgumentHelper.getInteger(line, option);
      } catch (ArgumentParseException var4) {
         throw new ArgumentParseException(String.format("Unable to parse option '%s'", option));
      }

      if (integer.hasValue() && integer.getValue() < 1) {
         throw new ArgumentParseException(String.format("Option '%s' must be >= 1", option));
      } else {
         return integer;
      }
   }
}
