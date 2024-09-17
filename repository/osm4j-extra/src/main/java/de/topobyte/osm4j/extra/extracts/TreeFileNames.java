package de.topobyte.osm4j.extra.extracts;

import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmIoUtils;

public class TreeFileNames {
   private String nodes;
   private String ways;
   private String simpleRelations;
   private String complexRelations;

   public TreeFileNames(FileFormat outputFormat) {
      String extension = OsmIoUtils.extension(outputFormat);
      this.nodes = "nodes" + extension;
      this.ways = "ways" + extension;
      this.simpleRelations = "relations.simple" + extension;
      this.complexRelations = "relations.complex" + extension;
   }

   public String getNodes() {
      return this.nodes;
   }

   public void setNodes(String nodes) {
      this.nodes = nodes;
   }

   public String getWays() {
      return this.ways;
   }

   public void setWays(String ways) {
      this.ways = ways;
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
}
