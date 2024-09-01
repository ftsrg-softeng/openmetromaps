package de.topobyte.osm4j.pbf.raf;

import de.topobyte.osm4j.core.model.iface.EntityType;

public class FileStructure {
   private Interval blocksNodes = null;
   private Interval blocksWays = null;
   private Interval blocksRelations = null;

   public FileStructure(Interval blocksNodes, Interval blocksWays, Interval blocksRelations) {
      this.blocksNodes = blocksNodes;
      this.blocksWays = blocksWays;
      this.blocksRelations = blocksRelations;
   }

   public boolean hasType(EntityType type) {
      switch (type) {
         case Node:
            return this.hasNodes();
         case Way:
            return this.hasWays();
         case Relation:
            return this.hasRelations();
         default:
            return false;
      }
   }

   public Interval getBlocks(EntityType type) {
      switch (type) {
         case Node:
            return this.blocksNodes;
         case Way:
            return this.blocksWays;
         case Relation:
            return this.blocksRelations;
         default:
            return null;
      }
   }

   public boolean hasNodes() {
      return this.blocksNodes != null;
   }

   public boolean hasWays() {
      return this.blocksWays != null;
   }

   public boolean hasRelations() {
      return this.blocksRelations != null;
   }

   public Interval getBlocksNodes() {
      return this.blocksNodes;
   }

   public void setBlocksNodes(Interval blocksNodes) {
      this.blocksNodes = blocksNodes;
   }

   public Interval getBlocksWays() {
      return this.blocksWays;
   }

   public void setBlocksWays(Interval blocksWays) {
      this.blocksWays = blocksWays;
   }

   public Interval getBlocksRelations() {
      return this.blocksRelations;
   }

   public void setBlocksRelations(Interval blocksRelations) {
      this.blocksRelations = blocksRelations;
   }
}
