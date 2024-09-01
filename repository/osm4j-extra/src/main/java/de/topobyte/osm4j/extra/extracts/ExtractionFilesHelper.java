package de.topobyte.osm4j.extra.extracts;

import de.topobyte.utilities.apache.commons.cli.OptionHelper;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class ExtractionFilesHelper {
   private static final String OPTION_TREE = "tree";
   private static final String OPTION_SIMPLE_RELATIONS = "simple-relations";
   private static final String OPTION_COMPLEX_RELATIONS = "complex-relations";
   private static final String OPTION_SIMPLE_RELATIONS_BBOXES = "simple-relations-bboxes";
   private static final String OPTION_COMPLEX_RELATIONS_BBOXES = "complex-relations-bboxes";
   private static final String OPTION_FILE_NAMES_TREE_NODES = "tree-nodes";
   private static final String OPTION_FILE_NAMES_TREE_WAYS = "tree-ways";
   private static final String OPTION_FILE_NAMES_TREE_RELATIONS_SIMPLE = "tree-simple-relations";
   private static final String OPTION_FILE_NAMES_TREE_RELATIONS_COMPLEX = "tree-complex-relations";
   private static final String OPTION_FILE_NAMES_RELATION_NODES = "relation-nodes";
   private static final String OPTION_FILE_NAMES_RELATION_WAYS = "relation-ways";
   private static final String OPTION_FILE_NAMES_RELATION_RELATIONS = "relation-relations";

   public static void addOptions(Options options) {
      OptionHelper.addL(options, "tree", true, false, "relative path to the data tree");
      OptionHelper.addL(options, "simple-relations", true, false, "relative path to simple relation batches");
      OptionHelper.addL(options, "complex-relations", true, false, "relative path to complex relation batches");
      OptionHelper.addL(options, "simple-relations-bboxes", true, false, "relative path to simple relation batches bboxes");
      OptionHelper.addL(options, "complex-relations-bboxes", true, false, "relative path to complex relation batches bboxes");
      OptionHelper.addL(options, "tree-nodes", true, false, "name of node files in tree");
      OptionHelper.addL(options, "tree-ways", true, false, "name of way files in tree");
      OptionHelper.addL(options, "tree-simple-relations", true, false, "name of simple relations in tree");
      OptionHelper.addL(options, "tree-complex-relations", true, false, "name of complex relations in tree");
      OptionHelper.addL(options, "relation-nodes", true, false, "name of node files in relation batches");
      OptionHelper.addL(options, "relation-ways", true, false, "name of way files in relation batches");
      OptionHelper.addL(options, "relation-relations", true, false, "name of relation files in relation batches");
   }

   public static void parse(CommandLine line, ExtractionFileNames fileNames) {
      TreeFileNames treeNames = fileNames.getTreeNames();
      BatchFileNames relationNames = fileNames.getRelationNames();
      if (line.hasOption("tree")) {
         fileNames.setTree(line.getOptionValue("tree"));
      }

      if (line.hasOption("simple-relations")) {
         fileNames.setSimpleRelations(line.getOptionValue("simple-relations"));
      }

      if (line.hasOption("complex-relations")) {
         fileNames.setComplexRelations(line.getOptionValue("complex-relations"));
      }

      if (line.hasOption("simple-relations-bboxes")) {
         fileNames.setSimpleRelationsBboxes(line.getOptionValue("simple-relations-bboxes"));
      }

      if (line.hasOption("complex-relations-bboxes")) {
         fileNames.setComplexRelationsBboxes(line.getOptionValue("complex-relations-bboxes"));
      }

      if (line.hasOption("tree-nodes")) {
         treeNames.setNodes(line.getOptionValue("tree-nodes"));
      }

      if (line.hasOption("tree-ways")) {
         treeNames.setWays(line.getOptionValue("tree-ways"));
      }

      if (line.hasOption("tree-simple-relations")) {
         treeNames.setSimpleRelations(line.getOptionValue("tree-simple-relations"));
      }

      if (line.hasOption("tree-complex-relations")) {
         treeNames.setComplexRelations(line.getOptionValue("tree-complex-relations"));
      }

      if (line.hasOption("relation-nodes")) {
         relationNames.setNodes(line.getOptionValue("relation-nodes"));
      }

      if (line.hasOption("relation-ways")) {
         relationNames.setWays(line.getOptionValue("relation-ways"));
      }

      if (line.hasOption("relation-relations")) {
         relationNames.setRelations(line.getOptionValue("relation-relations"));
      }
   }
}
