package de.topobyte.osm4j.pbf.raf;

import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.pbf.util.PbfMeta;
import java.io.IOException;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileStructureAnalyzer {
   static final Logger logger = LoggerFactory.getLogger(FileStructureAnalyzer.class);
   private PbfFile pbfFile;
   private int maxN = -1;
   private int minW = -1;
   private int maxW = -1;
   private int minR = -1;
   private boolean done = false;
   private boolean containsNodes = true;
   private boolean containsWays = true;
   private boolean containsRelations = true;
   private boolean foundAnyWay = false;
   private boolean foundLastNode = false;
   private boolean foundLastWay = false;

   public static FileStructure analyze(PbfFile pbfFile) throws IOException {
      if (!pbfFile.isBlockIndexInitialized()) {
         pbfFile.buildBlockIndex();
      }

      FileStructureAnalyzer analyzer = new FileStructureAnalyzer(pbfFile);
      analyzer.execute();
      analyzer.logEntityBounds();
      Interval blocksNodes = null;
      Interval blocksWays = null;
      Interval blocksRelations = null;
      if (analyzer.containsNodes) {
         blocksNodes = new Interval(0, analyzer.maxN);
      }

      if (analyzer.containsWays) {
         blocksWays = new Interval(analyzer.minW, analyzer.maxW);
      }

      if (analyzer.containsRelations) {
         blocksRelations = new Interval(analyzer.minR, pbfFile.getNumberOfDataBlocks() - 1);
      }

      return new FileStructure(blocksNodes, blocksWays, blocksRelations);
   }

   private FileStructureAnalyzer(PbfFile pbfFile) {
      this.pbfFile = pbfFile;
   }

   private void logEntityBounds() {
      logger.debug(String.format("maxN: %d, minW: %d, maxW: %d, minR: %d", this.maxN, this.minW, this.maxW, this.minR));
   }

   private void execute() throws IOException {
      int numDataBlocks = this.pbfFile.getNumberOfDataBlocks();
      logger.debug("num blocks: " + numDataBlocks);
      this.checkFirst();
      if (!this.done) {
         this.checkLast();
         if (!this.done) {
            if (this.minW == 0 && this.maxW == numDataBlocks - 1) {
               this.done = true;
            } else if (!this.containsNodes) {
               logger.debug("no nodes. finding last way");
               this.logEntityBounds();
               this.findLastWay();
               this.done = true;
            } else if (!this.containsRelations) {
               logger.debug("no relations. finding last node");
               this.logEntityBounds();
               this.findLastNode();
               this.done = true;
            } else {
               logger.debug("trying to find any way");

               while (this.containsWays && !this.foundAnyWay) {
                  if (this.minR - this.maxN == 1) {
                     this.containsWays = false;
                     this.foundLastNode = true;
                     this.foundLastWay = true;
                     this.done = true;
                     break;
                  }

                  this.contractNodeRelationInterval();
               }

               if (!this.done) {
                  this.logEntityBounds();
                  logger.debug("file contains all element types");
                  this.findLastNode();
                  this.findLastWay();
               }
            }
         }
      }
   }

   private void findLastNode() throws IOException {
      logger.debug("finding last node");

      while (!this.foundLastNode) {
         if (this.minW - this.maxN == 1) {
            this.foundLastNode = true;
         }

         this.contractNodeWayInterval();
      }
   }

   private void findLastWay() throws IOException {
      logger.debug("finding last way");

      while (!this.foundLastWay) {
         if (this.minR - this.maxW == 1) {
            this.foundLastWay = true;
            break;
         }

         this.contractWayRelationInterval();
      }
   }

   private void checkFirst() throws IOException {
      Set<EntityType> types = this.getTypes(0);
      logger.debug("first: " + types);
      if (types.contains(EntityType.Relation)) {
         this.done = true;
         this.foundLastNode = true;
         this.foundLastWay = true;
         this.containsNodes = types.contains(EntityType.Node);
         this.containsWays = types.contains(EntityType.Way);
         this.minR = 0;
         if (this.containsNodes) {
            this.maxN = 0;
         }

         if (this.containsWays) {
            this.minW = 0;
            this.maxW = 0;
         }
      } else if (this.contains(types, EntityType.Node, EntityType.Way)) {
         this.foundAnyWay = true;
         this.foundLastNode = true;
         this.maxN = 0;
         this.minW = 0;
      } else if (types.contains(EntityType.Node)) {
         this.maxN = 0;
      } else if (types.contains(EntityType.Way)) {
         this.containsNodes = false;
         this.foundLastNode = true;
         this.foundAnyWay = true;
         this.minW = 0;
         this.maxW = 0;
      }
   }

   private void checkLast() throws IOException {
      int last = this.pbfFile.getNumberOfDataBlocks() - 1;
      Set<EntityType> types = this.getTypes(last);
      logger.debug("last: " + types);
      if (types.contains(EntityType.Node)) {
         this.done = true;
         this.foundLastNode = true;
         this.foundLastWay = true;
         this.containsWays = this.contains(types, EntityType.Way);
         this.containsRelations = this.contains(types, EntityType.Relation);
         this.maxN = last;
         if (this.containsWays) {
            this.foundAnyWay = true;
            this.minW = last;
            this.maxW = last;
         }

         if (this.containsRelations) {
            this.minR = last;
         }
      } else if (types.contains(EntityType.Way)) {
         this.containsRelations = this.contains(types, EntityType.Relation);
         if (this.containsRelations) {
            this.minR = last;
         }

         if (!this.foundAnyWay) {
            this.foundAnyWay = true;
            this.minW = last;
         }

         this.foundLastWay = true;
         this.maxW = last;
      } else if (types.contains(EntityType.Relation)) {
         this.minR = last;
      }
   }

   private void contractNodeRelationInterval() throws IOException {
      int l = this.maxN;
      int r = this.minR;
      int p = (l + r) / 2;
      logger.debug("[" + l + "," + r + "] -> " + p);
      Set<EntityType> types = this.getTypes(p);
      logger.debug("types: " + types);
      if (types.contains(EntityType.Way)) {
         this.minW = p;
         this.maxW = p;
         this.foundAnyWay = true;
         if (types.contains(EntityType.Node)) {
            this.maxN = p;
            this.foundLastNode = true;
         }

         if (types.contains(EntityType.Relation)) {
            this.minR = p;
            this.foundLastWay = true;
         }
      } else if (this.contains(types, EntityType.Node, EntityType.Relation)) {
         this.maxN = p;
         this.minR = p;
         this.containsWays = false;
         this.foundLastNode = true;
         this.foundLastWay = true;
         this.done = true;
      } else if (types.contains(EntityType.Node)) {
         this.maxN = p;
      } else if (types.contains(EntityType.Relation)) {
         this.minR = p;
      }
   }

   private void contractNodeWayInterval() throws IOException {
      int l = this.maxN;
      int r = this.minW;
      int p = (l + r) / 2;
      logger.debug("[" + l + "," + r + "] -> " + p);
      Set<EntityType> types = this.getTypes(p);
      logger.debug("types: " + types);
      if (this.contains(types, EntityType.Node, EntityType.Way)) {
         this.maxN = p;
         this.minW = p;
         this.foundLastNode = true;
      } else if (types.contains(EntityType.Node)) {
         this.maxN = p;
      } else if (types.contains(EntityType.Way)) {
         this.minW = p;
      }
   }

   private void contractWayRelationInterval() throws IOException {
      int l = this.maxW;
      int r = this.minR;
      int p = (l + r) / 2;
      logger.debug("[" + l + "," + r + "] -> " + p);
      Set<EntityType> types = this.getTypes(p);
      logger.debug("types: " + types);
      if (this.contains(types, EntityType.Way, EntityType.Relation)) {
         this.maxW = p;
         this.minR = p;
         this.foundLastWay = true;
      } else if (types.contains(EntityType.Way)) {
         this.maxW = p;
      } else if (types.contains(EntityType.Relation)) {
         this.minR = p;
      }
   }

   private Set<EntityType> getTypes(int p) throws IOException {
      return PbfMeta.getContentTypes(this.pbfFile.getDataBlock(p));
   }

   private boolean contains(Set<EntityType> blockTypes, EntityType... types) throws IOException {
      boolean allFound = true;

      for (EntityType type : types) {
         allFound &= blockTypes.contains(type);
      }

      return allFound;
   }
}
