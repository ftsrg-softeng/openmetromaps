package de.topobyte.osm4j.extra.idextract;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ExtractionItem {
   private List<Path> pathsIds;
   private Path pathOutput;

   public ExtractionItem(Path pathIds, Path pathOutput) {
      this.pathsIds = new ArrayList<>();
      this.pathsIds.add(pathIds);
      this.pathOutput = pathOutput;
   }

   public ExtractionItem(List<Path> pathIds, Path pathOutput) {
      this.pathsIds = pathIds;
      this.pathOutput = pathOutput;
   }

   public List<Path> getPathsIds() {
      return this.pathsIds;
   }

   public Path getPathOutput() {
      return this.pathOutput;
   }
}
