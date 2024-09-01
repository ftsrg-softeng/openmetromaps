package de.topobyte.osm4j.extra.extracts;

import java.nio.file.Path;

public class ExtractionPaths {
   private Path splitNodes;
   private Path splitWays;
   private Path splitRelations;
   private Path tree;
   private Path waysByNodes;
   private Path simpleRelations;
   private Path complexRelations;
   private Path simpleRelationsBboxes;
   private Path complexRelationsBboxes;
   private Path simpleRelationsEmpty;
   private Path complexRelationsEmpty;

   public ExtractionPaths(Path base, ExtractionFileNames fileNames) {
      this.splitNodes = base.resolve(fileNames.getSplitNodes());
      this.splitWays = base.resolve(fileNames.getSplitWays());
      this.splitRelations = base.resolve(fileNames.getSplitRelations());
      this.tree = base.resolve(fileNames.getTree());
      this.simpleRelations = base.resolve(fileNames.getSimpleRelations());
      this.complexRelations = base.resolve(fileNames.getComplexRelations());
      this.simpleRelationsBboxes = base.resolve(fileNames.getSimpleRelationsBboxes());
      this.complexRelationsBboxes = base.resolve(fileNames.getComplexRelationsBboxes());
      this.simpleRelationsEmpty = base.resolve(fileNames.getSimpleRelationsEmpty());
      this.complexRelationsEmpty = base.resolve(fileNames.getComplexRelationsEmpty());
   }

   public Path getSplitNodes() {
      return this.splitNodes;
   }

   public void setSplitNodes(Path splitNodes) {
      this.splitNodes = splitNodes;
   }

   public Path getSplitWays() {
      return this.splitWays;
   }

   public void setSplitWays(Path splitWays) {
      this.splitWays = splitWays;
   }

   public Path getSplitRelations() {
      return this.splitRelations;
   }

   public void setSplitRelations(Path splitRelations) {
      this.splitRelations = splitRelations;
   }

   public Path getTree() {
      return this.tree;
   }

   public void setTree(Path tree) {
      this.tree = tree;
   }

   public Path getWaysByNodes() {
      return this.waysByNodes;
   }

   public void setWaysByNodes(Path waysByNodes) {
      this.waysByNodes = waysByNodes;
   }

   public Path getSimpleRelations() {
      return this.simpleRelations;
   }

   public void setSimpleRelations(Path simpleRelations) {
      this.simpleRelations = simpleRelations;
   }

   public Path getComplexRelations() {
      return this.complexRelations;
   }

   public void setComplexRelations(Path complexRelations) {
      this.complexRelations = complexRelations;
   }

   public Path getSimpleRelationsBboxes() {
      return this.simpleRelationsBboxes;
   }

   public void setSimpleRelationsBboxes(Path simpleRelationsBboxes) {
      this.simpleRelationsBboxes = simpleRelationsBboxes;
   }

   public Path getComplexRelationsBboxes() {
      return this.complexRelationsBboxes;
   }

   public void setComplexRelationsBboxes(Path complexRelationsBboxes) {
      this.complexRelationsBboxes = complexRelationsBboxes;
   }

   public Path getSimpleRelationsEmpty() {
      return this.simpleRelationsEmpty;
   }

   public void setSimpleRelationsEmpty(Path simpleRelationsEmpty) {
      this.simpleRelationsEmpty = simpleRelationsEmpty;
   }

   public Path getComplexRelationsEmpty() {
      return this.complexRelationsEmpty;
   }

   public void setComplexRelationsEmpty(Path complexRelationsEmpty) {
      this.complexRelationsEmpty = complexRelationsEmpty;
   }
}
