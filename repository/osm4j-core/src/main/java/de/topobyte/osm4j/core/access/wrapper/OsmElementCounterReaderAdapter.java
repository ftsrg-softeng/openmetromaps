package de.topobyte.osm4j.core.access.wrapper;

import de.topobyte.osm4j.core.access.OsmElementCounter;
import de.topobyte.osm4j.core.access.OsmHandler;
import de.topobyte.osm4j.core.access.OsmInputException;
import de.topobyte.osm4j.core.access.OsmReader;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;

public class OsmElementCounterReaderAdapter implements OsmElementCounter, OsmHandler {
   private OsmReader reader;
   private int numNodes = 0;
   private int numWays = 0;
   private int numRelations = 0;

   public OsmElementCounterReaderAdapter(OsmReader reader) {
      this.reader = reader;
      reader.setHandler(this);
   }

   @Override
   public void handle(OsmBounds bounds) throws IOException {
   }

   @Override
   public void handle(OsmNode node) throws IOException {
      this.numNodes++;
   }

   @Override
   public void handle(OsmWay way) throws IOException {
      this.numWays++;
   }

   @Override
   public void handle(OsmRelation relation) throws IOException {
      this.numRelations++;
   }

   @Override
   public void complete() throws IOException {
   }

   @Override
   public void count() throws OsmInputException {
      this.reader.read();
   }

   @Override
   public long getNumberOfNodes() {
      return (long)this.numNodes;
   }

   @Override
   public long getNumberOfWays() {
      return (long)this.numWays;
   }

   @Override
   public long getNumberOfRelations() {
      return (long)this.numRelations;
   }

   @Override
   public long getTotalNumberOfElements() {
      return (long)(this.numNodes + this.numWays + this.numRelations);
   }

   @Override
   public long getNumberOfElements(EntityType type) {
      switch (type) {
         case Node:
            return (long)this.numNodes;
         case Way:
            return (long)this.numWays;
         case Relation:
            return (long)this.numRelations;
         default:
            return 0L;
      }
   }
}
