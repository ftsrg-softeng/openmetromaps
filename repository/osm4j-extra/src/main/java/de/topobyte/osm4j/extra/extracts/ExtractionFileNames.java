package de.topobyte.osm4j.extra.extracts;

import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmIoUtils;

public class ExtractionFileNames {
   private String splitNodes;
   private String splitWays;
   private String splitRelations;
   private String tree = "tree";
   private String waysByNodes = "waysbynodes";
   private String simpleRelations = "relations.simple.sorted";
   private String complexRelations = "relations.complex.sorted";
   private String simpleRelationsBboxes = "relations.simple.sorted.bboxlist";
   private String complexRelationsBboxes = "relations.complex.sorted.bboxlist";
   private String simpleRelationsEmpty;
   private String complexRelationsEmpty;
   private TreeFileNames treeNames;
   private BatchFileNames relationNames;

   public ExtractionFileNames(FileFormat outputFormat) {
      String extension = OsmIoUtils.extension(outputFormat);
      this.splitNodes = "nodes" + extension;
      this.splitWays = "ways" + extension;
      this.splitRelations = "relations" + extension;
      this.treeNames = new TreeFileNames(outputFormat);
      this.relationNames = new BatchFileNames(outputFormat);
      this.simpleRelationsEmpty = "relations.simple.empty" + extension;
      this.complexRelationsEmpty = "relations.complex.empty" + extension;
   }

   public String getSplitNodes() {
      return this.splitNodes;
   }

   public void setSplitNodes(String splitNodes) {
      this.splitNodes = splitNodes;
   }

   public String getSplitWays() {
      return this.splitWays;
   }

   public void setSplitWays(String splitWays) {
      this.splitWays = splitWays;
   }

   public String getSplitRelations() {
      return this.splitRelations;
   }

   public void setSplitRelations(String splitRelations) {
      this.splitRelations = splitRelations;
   }

   public String getTree() {
      return this.tree;
   }

   public void setTree(String tree) {
      this.tree = tree;
   }

   public String getWaysByNodes() {
      return this.waysByNodes;
   }

   public void setWaysByNodes(String waysByNodes) {
      this.waysByNodes = waysByNodes;
   }

   public String getSimpleRelations() {
      return this.simpleRelations;
   }

   public void setSimpleRelations(String simpleRelations) {
      this.simpleRelations = simpleRelations;
   }

   public String getComplexRelations() {
      return this.complexRelations;
   }

   public void setComplexRelations(String complexRelations) {
      this.complexRelations = complexRelations;
   }

   public String getSimpleRelationsBboxes() {
      return this.simpleRelationsBboxes;
   }

   public void setSimpleRelationsBboxes(String simpleRelationsBboxes) {
      this.simpleRelationsBboxes = simpleRelationsBboxes;
   }

   public String getComplexRelationsBboxes() {
      return this.complexRelationsBboxes;
   }

   public void setComplexRelationsBboxes(String complexRelationsBboxes) {
      this.complexRelationsBboxes = complexRelationsBboxes;
   }

   public String getSimpleRelationsEmpty() {
      return this.simpleRelationsEmpty;
   }

   public void setSimpleRelationsEmpty(String simpleRelationsEmpty) {
      this.simpleRelationsEmpty = simpleRelationsEmpty;
   }

   public String getComplexRelationsEmpty() {
      return this.complexRelationsEmpty;
   }

   public void setComplexRelationsEmpty(String complexRelationsEmpty) {
      this.complexRelationsEmpty = complexRelationsEmpty;
   }

   public TreeFileNames getTreeNames() {
      return this.treeNames;
   }

   public void setTreeNames(TreeFileNames treeNames) {
      this.treeNames = treeNames;
   }

   public BatchFileNames getRelationNames() {
      return this.relationNames;
   }

   public void setRelationNames(BatchFileNames relationNames) {
      this.relationNames = relationNames;
   }
}
