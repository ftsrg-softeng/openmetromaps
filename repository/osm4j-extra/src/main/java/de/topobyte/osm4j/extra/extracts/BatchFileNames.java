package de.topobyte.osm4j.extra.extracts;

import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmIoUtils;

public class BatchFileNames {
   private String nodes;
   private String ways;
   private String relations;

   public BatchFileNames(FileFormat outputFormat) {
      String extension = OsmIoUtils.extension(outputFormat);
      this.nodes = "nodes" + extension;
      this.ways = "ways" + extension;
      this.relations = "relations" + extension;
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

   public String getRelations() {
      return this.relations;
   }

   public void setRelations(String relations) {
      this.relations = relations;
   }
}
