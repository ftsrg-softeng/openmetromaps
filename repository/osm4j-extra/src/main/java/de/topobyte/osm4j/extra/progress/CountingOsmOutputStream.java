package de.topobyte.osm4j.extra.progress;

import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;

public class CountingOsmOutputStream implements OsmOutputStream {
   private OsmOutputStream output;
   private long numNodes = 0L;
   private long numWays = 0L;
   private long numRelations = 0L;

   public CountingOsmOutputStream(OsmOutputStream output) {
      this.output = output;
   }

   public long getNumNodes() {
      return this.numNodes;
   }

   public long getNumWays() {
      return this.numWays;
   }

   public long getNumRelations() {
      return this.numRelations;
   }

   public void write(OsmBounds bounds) throws IOException {
      this.output.write(bounds);
   }

   public void write(OsmNode node) throws IOException {
      this.output.write(node);
      this.numNodes++;
   }

   public void write(OsmWay way) throws IOException {
      this.output.write(way);
      this.numWays++;
   }

   public void write(OsmRelation relation) throws IOException {
      this.output.write(relation);
      this.numRelations++;
   }

   public void complete() throws IOException {
      this.output.complete();
   }
}
