package com.infomatiq.jsi.rtree;

import com.infomatiq.jsi.Point;
import com.infomatiq.jsi.PriorityQueue;
import com.infomatiq.jsi.Rectangle;
import com.infomatiq.jsi.SpatialIndex;
import com.slimjars.dist.gnu.trove.list.array.TIntArrayList;
import com.slimjars.dist.gnu.trove.map.hash.TIntObjectHashMap;
import com.slimjars.dist.gnu.trove.procedure.TIntProcedure;
import com.slimjars.dist.gnu.trove.stack.TIntStack;
import com.slimjars.dist.gnu.trove.stack.array.TIntArrayStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RTree implements SpatialIndex {
   private static final Logger log = LoggerFactory.getLogger(RTree.class);
   private static final Logger deleteLog = LoggerFactory.getLogger(RTree.class.getName() + "-delete");
   private static final int DEFAULT_MAX_NODE_ENTRIES = 50;
   private static final int DEFAULT_MIN_NODE_ENTRIES = 20;
   int maxNodeEntries;
   int minNodeEntries;
   private TIntObjectHashMap<Node> nodeMap = new TIntObjectHashMap();
   private static final boolean INTERNAL_CONSISTENCY_CHECKING = false;
   private static final int ENTRY_STATUS_ASSIGNED = 0;
   private static final int ENTRY_STATUS_UNASSIGNED = 1;
   private byte[] entryStatus = null;
   private byte[] initialEntryStatus = null;
   private TIntStack parents = new TIntArrayStack();
   private TIntStack parentsEntry = new TIntArrayStack();
   int treeHeight = 1;
   int rootNodeId = 0;
   int size = 0;
   private int highestUsedNodeId = this.rootNodeId;
   private TIntStack deletedNodeIds = new TIntArrayStack();
   private TIntArrayList nearestIds = new TIntArrayList();
   private TIntArrayList savedValues = new TIntArrayList();
   private float savedPriority = 0.0F;
   private SortedList nearestNIds = new SortedList();
   private PriorityQueue distanceQueue = new PriorityQueue(true);

   public RTree() {
      this(20, 50);
   }

   public RTree(int minNodeEntries, int maxNodeEntries) {
      this.init(minNodeEntries, maxNodeEntries);
   }

   private void init(int minNodeEntries, int maxNodeEntries) {
      this.minNodeEntries = minNodeEntries;
      this.maxNodeEntries = maxNodeEntries;
      if (maxNodeEntries < 2) {
         log.warn("Invalid MaxNodeEntries = " + maxNodeEntries + " Resetting to default value of " + 50);
         maxNodeEntries = 50;
      }

      if (minNodeEntries < 1 || minNodeEntries > maxNodeEntries / 2) {
         log.warn("MinNodeEntries must be between 1 and MaxNodeEntries / 2");
         minNodeEntries = maxNodeEntries / 2;
      }

      this.entryStatus = new byte[maxNodeEntries];
      this.initialEntryStatus = new byte[maxNodeEntries];

      for (int i = 0; i < maxNodeEntries; i++) {
         this.initialEntryStatus[i] = 1;
      }

      Node root = new Node(this.rootNodeId, 1, maxNodeEntries);
      this.nodeMap.put(this.rootNodeId, root);
      log.debug("init()  MaxNodeEntries = " + maxNodeEntries + ", MinNodeEntries = " + minNodeEntries);
   }

   @Override
   public void add(Rectangle r, int id) {
      if (log.isDebugEnabled()) {
         log.debug("Adding rectangle " + r + ", id " + id);
      }

      this.add(r.minX, r.minY, r.maxX, r.maxY, id, 1);
      this.size++;
   }

   private void add(float minX, float minY, float maxX, float maxY, int id, int level) {
      Node n = this.chooseNode(minX, minY, maxX, maxY, level);
      Node newLeaf = null;
      if (n.entryCount < this.maxNodeEntries) {
         n.addEntry(minX, minY, maxX, maxY, id);
      } else {
         newLeaf = this.splitNode(n, minX, minY, maxX, maxY, id);
      }

      Node newNode = this.adjustTree(n, newLeaf);
      if (newNode != null) {
         int oldRootNodeId = this.rootNodeId;
         Node oldRoot = this.getNode(oldRootNodeId);
         this.rootNodeId = this.getNextNodeId();
         this.treeHeight++;
         Node root = new Node(this.rootNodeId, this.treeHeight, this.maxNodeEntries);
         root.addEntry(newNode.mbrMinX, newNode.mbrMinY, newNode.mbrMaxX, newNode.mbrMaxY, newNode.nodeId);
         root.addEntry(oldRoot.mbrMinX, oldRoot.mbrMinY, oldRoot.mbrMaxX, oldRoot.mbrMaxY, oldRoot.nodeId);
         this.nodeMap.put(this.rootNodeId, root);
      }
   }

   @Override
   public boolean delete(Rectangle r, int id) {
      this.parents.clear();
      this.parents.push(this.rootNodeId);
      this.parentsEntry.clear();
      this.parentsEntry.push(-1);
      Node n = null;
      int foundIndex = -1;

      while (foundIndex == -1 && this.parents.size() > 0) {
         n = this.getNode(this.parents.peek());
         int startIndex = this.parentsEntry.peek() + 1;
         if (!n.isLeaf()) {
            deleteLog.debug("searching node " + n.nodeId + ", from index " + startIndex);
            boolean contains = false;

            for (int i = startIndex; i < n.entryCount; i++) {
               if (Rectangle.contains(n.entriesMinX[i], n.entriesMinY[i], n.entriesMaxX[i], n.entriesMaxY[i], r.minX, r.minY, r.maxX, r.maxY)) {
                  this.parents.push(n.ids[i]);
                  this.parentsEntry.pop();
                  this.parentsEntry.push(i);
                  this.parentsEntry.push(-1);
                  contains = true;
                  break;
               }
            }

            if (contains) {
               continue;
            }
         } else {
            foundIndex = n.findEntry(r.minX, r.minY, r.maxX, r.maxY, id);
         }

         this.parents.pop();
         this.parentsEntry.pop();
      }

      if (foundIndex != -1 && n != null) {
         n.deleteEntry(foundIndex);
         this.condenseTree(n);
         this.size--;
      }

      Node root;
      for (root = this.getNode(this.rootNodeId); root.entryCount == 1 && this.treeHeight > 1; root = this.getNode(this.rootNodeId)) {
         this.deletedNodeIds.push(this.rootNodeId);
         root.entryCount = 0;
         this.rootNodeId = root.ids[0];
         this.treeHeight--;
      }

      if (this.size == 0) {
         root.mbrMinX = Float.MAX_VALUE;
         root.mbrMinY = Float.MAX_VALUE;
         root.mbrMaxX = -Float.MAX_VALUE;
         root.mbrMaxY = -Float.MAX_VALUE;
      }

      return foundIndex != -1;
   }

   @Override
   public void nearest(Point p, TIntProcedure v, float furthestDistance) {
      Node rootNode = this.getNode(this.rootNodeId);
      float furthestDistanceSq = furthestDistance * furthestDistance;
      this.nearest(p, rootNode, furthestDistanceSq);
      this.nearestIds.forEach(v);
      this.nearestIds.reset();
   }

   private void createNearestNDistanceQueue(Point p, int count, float furthestDistance) {
      this.distanceQueue.reset();
      this.distanceQueue.setSortOrder(false);
      if (count > 0) {
         this.parents.clear();
         this.parents.push(this.rootNodeId);
         this.parentsEntry.clear();
         this.parentsEntry.push(-1);
         float furthestDistanceSq = furthestDistance * furthestDistance;

         while (this.parents.size() > 0) {
            Node n = this.getNode(this.parents.peek());
            int startIndex = this.parentsEntry.peek() + 1;
            if (!n.isLeaf()) {
               boolean near = false;

               for (int i = startIndex; i < n.entryCount; i++) {
                  if (Rectangle.distanceSq(n.entriesMinX[i], n.entriesMinY[i], n.entriesMaxX[i], n.entriesMaxY[i], p.x, p.y) <= furthestDistanceSq) {
                     this.parents.push(n.ids[i]);
                     this.parentsEntry.pop();
                     this.parentsEntry.push(i);
                     this.parentsEntry.push(-1);
                     near = true;
                     break;
                  }
               }

               if (near) {
                  continue;
               }
            } else {
               for (int ix = 0; ix < n.entryCount; ix++) {
                  float entryDistanceSq = Rectangle.distanceSq(n.entriesMinX[ix], n.entriesMinY[ix], n.entriesMaxX[ix], n.entriesMaxY[ix], p.x, p.y);
                  int entryId = n.ids[ix];
                  if (entryDistanceSq <= furthestDistanceSq) {
                     this.distanceQueue.insert(entryId, entryDistanceSq);

                     while (this.distanceQueue.size() > count) {
                        int value = this.distanceQueue.getValue();
                        float distanceSq = this.distanceQueue.getPriority();
                        this.distanceQueue.pop();
                        if (distanceSq == this.distanceQueue.getPriority()) {
                           this.savedValues.add(value);
                           this.savedPriority = distanceSq;
                        } else {
                           this.savedValues.reset();
                        }
                     }

                     if (this.savedValues.size() > 0 && this.savedPriority == this.distanceQueue.getPriority()) {
                        for (int svi = 0; svi < this.savedValues.size(); svi++) {
                           this.distanceQueue.insert(this.savedValues.get(svi), this.savedPriority);
                        }

                        this.savedValues.reset();
                     }

                     if (this.distanceQueue.getPriority() < furthestDistanceSq && this.distanceQueue.size() >= count) {
                        furthestDistanceSq = this.distanceQueue.getPriority();
                     }
                  }
               }
            }

            this.parents.pop();
            this.parentsEntry.pop();
         }
      }
   }

   @Override
   public void nearestNUnsorted(Point p, TIntProcedure v, int count, float furthestDistance) {
      this.createNearestNDistanceQueue(p, count, furthestDistance);

      while (this.distanceQueue.size() > 0) {
         v.execute(this.distanceQueue.getValue());
         this.distanceQueue.pop();
      }
   }

   @Override
   public void nearestN(Point p, TIntProcedure v, int count, float furthestDistance) {
      this.createNearestNDistanceQueue(p, count, furthestDistance);
      this.distanceQueue.setSortOrder(true);

      while (this.distanceQueue.size() > 0) {
         v.execute(this.distanceQueue.getValue());
         this.distanceQueue.pop();
      }
   }

   @Deprecated
   public void nearestN_orig(Point p, TIntProcedure v, int count, float furthestDistance) {
      if (count > 0) {
         this.parents.clear();
         this.parents.push(this.rootNodeId);
         this.parentsEntry.clear();
         this.parentsEntry.push(-1);
         this.nearestNIds.init(count);
         float furthestDistanceSq = furthestDistance * furthestDistance;

         while (true) {
            boolean near;
            do {
               if (this.parents.size() <= 0) {
                  this.nearestNIds.forEachId(v);
                  return;
               }

               Node n = this.getNode(this.parents.peek());
               int startIndex = this.parentsEntry.peek() + 1;
               if (n.isLeaf()) {
                  for (int i = 0; i < n.entryCount; i++) {
                     float entryDistanceSq = Rectangle.distanceSq(n.entriesMinX[i], n.entriesMinY[i], n.entriesMaxX[i], n.entriesMaxY[i], p.x, p.y);
                     int entryId = n.ids[i];
                     if (entryDistanceSq <= furthestDistanceSq) {
                        this.nearestNIds.add(entryId, -entryDistanceSq);
                        float tempFurthestDistanceSq = -this.nearestNIds.getLowestPriority();
                        if (tempFurthestDistanceSq < furthestDistanceSq) {
                           furthestDistanceSq = tempFurthestDistanceSq;
                        }
                     }
                  }
                  break;
               }

               near = false;

               for (int ix = startIndex; ix < n.entryCount; ix++) {
                  if (Rectangle.distanceSq(n.entriesMinX[ix], n.entriesMinY[ix], n.entriesMaxX[ix], n.entriesMaxY[ix], p.x, p.y) <= furthestDistanceSq) {
                     this.parents.push(n.ids[ix]);
                     this.parentsEntry.pop();
                     this.parentsEntry.push(ix);
                     this.parentsEntry.push(-1);
                     near = true;
                     break;
                  }
               }
            } while (near);

            this.parents.pop();
            this.parentsEntry.pop();
         }
      }
   }

   @Override
   public void intersects(Rectangle r, TIntProcedure v) {
      Node rootNode = this.getNode(this.rootNodeId);
      this.intersects(r, v, rootNode);
   }

   @Override
   public void contains(Rectangle r, TIntProcedure v) {
      this.parents.clear();
      this.parents.push(this.rootNodeId);
      this.parentsEntry.clear();
      this.parentsEntry.push(-1);

      while (true) {
         boolean intersects;
         do {
            if (this.parents.size() <= 0) {
               return;
            }

            Node n = this.getNode(this.parents.peek());
            int startIndex = this.parentsEntry.peek() + 1;
            if (n.isLeaf()) {
               for (int i = 0; i < n.entryCount; i++) {
                  if (Rectangle.contains(r.minX, r.minY, r.maxX, r.maxY, n.entriesMinX[i], n.entriesMinY[i], n.entriesMaxX[i], n.entriesMaxY[i])
                     && !v.execute(n.ids[i])) {
                     return;
                  }
               }
               break;
            }

            intersects = false;

            for (int ix = startIndex; ix < n.entryCount; ix++) {
               if (Rectangle.intersects(r.minX, r.minY, r.maxX, r.maxY, n.entriesMinX[ix], n.entriesMinY[ix], n.entriesMaxX[ix], n.entriesMaxY[ix])) {
                  this.parents.push(n.ids[ix]);
                  this.parentsEntry.pop();
                  this.parentsEntry.push(ix);
                  this.parentsEntry.push(-1);
                  intersects = true;
                  break;
               }
            }
         } while (intersects);

         this.parents.pop();
         this.parentsEntry.pop();
      }
   }

   @Override
   public int size() {
      return this.size;
   }

   @Override
   public Rectangle getBounds() {
      Rectangle bounds = null;
      Node n = this.getNode(this.getRootNodeId());
      if (n != null && n.entryCount > 0) {
         bounds = new Rectangle();
         bounds.minX = n.mbrMinX;
         bounds.minY = n.mbrMinY;
         bounds.maxX = n.mbrMaxX;
         bounds.maxY = n.mbrMaxY;
      }

      return bounds;
   }

   private int getNextNodeId() {
      int nextNodeId = 0;
      if (this.deletedNodeIds.size() > 0) {
         nextNodeId = this.deletedNodeIds.pop();
      } else {
         nextNodeId = 1 + this.highestUsedNodeId++;
      }

      return nextNodeId;
   }

   public Node getNode(int id) {
      return (Node)this.nodeMap.get(id);
   }

   public int getHighestUsedNodeId() {
      return this.highestUsedNodeId;
   }

   public int getRootNodeId() {
      return this.rootNodeId;
   }

   private Node splitNode(Node n, float newRectMinX, float newRectMinY, float newRectMaxX, float newRectMaxY, int newId) {
      float initialArea = 0.0F;
      if (log.isDebugEnabled()) {
         float unionMinX = Math.min(n.mbrMinX, newRectMinX);
         float unionMinY = Math.min(n.mbrMinY, newRectMinY);
         float unionMaxX = Math.max(n.mbrMaxX, newRectMaxX);
         float unionMaxY = Math.max(n.mbrMaxY, newRectMaxY);
         initialArea = (unionMaxX - unionMinX) * (unionMaxY - unionMinY);
      }

      System.arraycopy(this.initialEntryStatus, 0, this.entryStatus, 0, this.maxNodeEntries);
      Node newNode = null;
      newNode = new Node(this.getNextNodeId(), n.level, this.maxNodeEntries);
      this.nodeMap.put(newNode.nodeId, newNode);
      this.pickSeeds(n, newRectMinX, newRectMinY, newRectMaxX, newRectMaxY, newId, newNode);

      while (n.entryCount + newNode.entryCount < this.maxNodeEntries + 1) {
         if (this.maxNodeEntries + 1 - newNode.entryCount == this.minNodeEntries) {
            for (int i = 0; i < this.maxNodeEntries; i++) {
               if (this.entryStatus[i] == 1) {
                  this.entryStatus[i] = 0;
                  if (n.entriesMinX[i] < n.mbrMinX) {
                     n.mbrMinX = n.entriesMinX[i];
                  }

                  if (n.entriesMinY[i] < n.mbrMinY) {
                     n.mbrMinY = n.entriesMinY[i];
                  }

                  if (n.entriesMaxX[i] > n.mbrMaxX) {
                     n.mbrMaxX = n.entriesMaxX[i];
                  }

                  if (n.entriesMaxY[i] > n.mbrMaxY) {
                     n.mbrMaxY = n.entriesMaxY[i];
                  }

                  n.entryCount++;
               }
            }
            break;
         }

         if (this.maxNodeEntries + 1 - n.entryCount == this.minNodeEntries) {
            for (int ix = 0; ix < this.maxNodeEntries; ix++) {
               if (this.entryStatus[ix] == 1) {
                  this.entryStatus[ix] = 0;
                  newNode.addEntry(n.entriesMinX[ix], n.entriesMinY[ix], n.entriesMaxX[ix], n.entriesMaxY[ix], n.ids[ix]);
                  n.ids[ix] = -1;
               }
            }
            break;
         }

         this.pickNext(n, newNode);
      }

      n.reorganize(this);
      if (log.isDebugEnabled()) {
         float newArea = Rectangle.area(n.mbrMinX, n.mbrMinY, n.mbrMaxX, n.mbrMaxY)
            + Rectangle.area(newNode.mbrMinX, newNode.mbrMinY, newNode.mbrMaxX, newNode.mbrMaxY);
         float percentageIncrease = 100.0F * (newArea - initialArea) / initialArea;
         log.debug("Node " + n.nodeId + " split. New area increased by " + percentageIncrease + "%");
      }

      return newNode;
   }

   private void pickSeeds(Node n, float newRectMinX, float newRectMinY, float newRectMaxX, float newRectMaxY, int newId, Node newNode) {
      float maxNormalizedSeparation = -1.0F;
      int highestLowIndex = -1;
      int lowestHighIndex = -1;
      if (newRectMinX < n.mbrMinX) {
         n.mbrMinX = newRectMinX;
      }

      if (newRectMinY < n.mbrMinY) {
         n.mbrMinY = newRectMinY;
      }

      if (newRectMaxX > n.mbrMaxX) {
         n.mbrMaxX = newRectMaxX;
      }

      if (newRectMaxY > n.mbrMaxY) {
         n.mbrMaxY = newRectMaxY;
      }

      float mbrLenX = n.mbrMaxX - n.mbrMinX;
      float mbrLenY = n.mbrMaxY - n.mbrMinY;
      if (log.isDebugEnabled()) {
         log.debug("pickSeeds(): NodeId = " + n.nodeId);
      }

      float tempHighestLow = newRectMinX;
      int tempHighestLowIndex = -1;
      float tempLowestHigh = newRectMaxX;
      int tempLowestHighIndex = -1;

      for (int i = 0; i < n.entryCount; i++) {
         float tempLow = n.entriesMinX[i];
         if (tempLow >= tempHighestLow) {
            tempHighestLow = tempLow;
            tempHighestLowIndex = i;
         } else {
            float tempHigh = n.entriesMaxX[i];
            if (tempHigh <= tempLowestHigh) {
               tempLowestHigh = tempHigh;
               tempLowestHighIndex = i;
            }
         }

         float normalizedSeparation = mbrLenX == 0.0F ? 1.0F : (tempHighestLow - tempLowestHigh) / mbrLenX;
         if (normalizedSeparation > 1.0F || normalizedSeparation < -1.0F) {
            log.error("Invalid normalized separation X");
         }

         if (log.isDebugEnabled()) {
            log.debug(
               "Entry "
                  + i
                  + ", dimension X: HighestLow = "
                  + tempHighestLow
                  + " (index "
                  + tempHighestLowIndex
                  + ")"
                  + ", LowestHigh = "
                  + tempLowestHigh
                  + " (index "
                  + tempLowestHighIndex
                  + ", NormalizedSeparation = "
                  + normalizedSeparation
            );
         }

         if (normalizedSeparation >= maxNormalizedSeparation) {
            highestLowIndex = tempHighestLowIndex;
            lowestHighIndex = tempLowestHighIndex;
            maxNormalizedSeparation = normalizedSeparation;
         }
      }

      tempHighestLow = newRectMinY;
      tempHighestLowIndex = -1;
      tempLowestHigh = newRectMaxY;
      tempLowestHighIndex = -1;

      for (int i = 0; i < n.entryCount; i++) {
         float tempLowx = n.entriesMinY[i];
         if (tempLowx >= tempHighestLow) {
            tempHighestLow = tempLowx;
            tempHighestLowIndex = i;
         } else {
            float tempHigh = n.entriesMaxY[i];
            if (tempHigh <= tempLowestHigh) {
               tempLowestHigh = tempHigh;
               tempLowestHighIndex = i;
            }
         }

         float normalizedSeparationx = mbrLenY == 0.0F ? 1.0F : (tempHighestLow - tempLowestHigh) / mbrLenY;
         if (normalizedSeparationx > 1.0F || normalizedSeparationx < -1.0F) {
            log.error("Invalid normalized separation Y");
         }

         if (log.isDebugEnabled()) {
            log.debug(
               "Entry "
                  + i
                  + ", dimension Y: HighestLow = "
                  + tempHighestLow
                  + " (index "
                  + tempHighestLowIndex
                  + ")"
                  + ", LowestHigh = "
                  + tempLowestHigh
                  + " (index "
                  + tempLowestHighIndex
                  + ", NormalizedSeparation = "
                  + normalizedSeparationx
            );
         }

         if (normalizedSeparationx >= maxNormalizedSeparation) {
            highestLowIndex = tempHighestLowIndex;
            lowestHighIndex = tempLowestHighIndex;
            maxNormalizedSeparation = normalizedSeparationx;
         }
      }

      if (highestLowIndex == lowestHighIndex) {
         highestLowIndex = -1;
         float tempMinY = newRectMinY;
         lowestHighIndex = 0;
         float tempMaxX = n.entriesMaxX[0];

         for (int i = 1; i < n.entryCount; i++) {
            if (n.entriesMinY[i] < tempMinY) {
               tempMinY = n.entriesMinY[i];
               highestLowIndex = i;
            } else if (n.entriesMaxX[i] > tempMaxX) {
               tempMaxX = n.entriesMaxX[i];
               lowestHighIndex = i;
            }
         }
      }

      if (highestLowIndex == -1) {
         newNode.addEntry(newRectMinX, newRectMinY, newRectMaxX, newRectMaxY, newId);
      } else {
         newNode.addEntry(
            n.entriesMinX[highestLowIndex],
            n.entriesMinY[highestLowIndex],
            n.entriesMaxX[highestLowIndex],
            n.entriesMaxY[highestLowIndex],
            n.ids[highestLowIndex]
         );
         n.ids[highestLowIndex] = -1;
         n.entriesMinX[highestLowIndex] = newRectMinX;
         n.entriesMinY[highestLowIndex] = newRectMinY;
         n.entriesMaxX[highestLowIndex] = newRectMaxX;
         n.entriesMaxY[highestLowIndex] = newRectMaxY;
         n.ids[highestLowIndex] = newId;
      }

      if (lowestHighIndex == -1) {
         lowestHighIndex = highestLowIndex;
      }

      this.entryStatus[lowestHighIndex] = 0;
      n.entryCount = 1;
      n.mbrMinX = n.entriesMinX[lowestHighIndex];
      n.mbrMinY = n.entriesMinY[lowestHighIndex];
      n.mbrMaxX = n.entriesMaxX[lowestHighIndex];
      n.mbrMaxY = n.entriesMaxY[lowestHighIndex];
   }

   private int pickNext(Node n, Node newNode) {
      float maxDifference = Float.NEGATIVE_INFINITY;
      int next = 0;
      int nextGroup = 0;
      maxDifference = Float.NEGATIVE_INFINITY;
      if (log.isDebugEnabled()) {
         log.debug("pickNext()");
      }

      for (int i = 0; i < this.maxNodeEntries; i++) {
         if (this.entryStatus[i] == 1) {
            if (n.ids[i] == -1) {
               log.error("Error: Node " + n.nodeId + ", entry " + i + " is null");
            }

            float nIncrease = Rectangle.enlargement(
               n.mbrMinX, n.mbrMinY, n.mbrMaxX, n.mbrMaxY, n.entriesMinX[i], n.entriesMinY[i], n.entriesMaxX[i], n.entriesMaxY[i]
            );
            float newNodeIncrease = Rectangle.enlargement(
               newNode.mbrMinX, newNode.mbrMinY, newNode.mbrMaxX, newNode.mbrMaxY, n.entriesMinX[i], n.entriesMinY[i], n.entriesMaxX[i], n.entriesMaxY[i]
            );
            float difference = Math.abs(nIncrease - newNodeIncrease);
            if (difference > maxDifference) {
               next = i;
               if (nIncrease < newNodeIncrease) {
                  nextGroup = 0;
               } else if (newNodeIncrease < nIncrease) {
                  nextGroup = 1;
               } else if (Rectangle.area(n.mbrMinX, n.mbrMinY, n.mbrMaxX, n.mbrMaxY)
                  < Rectangle.area(newNode.mbrMinX, newNode.mbrMinY, newNode.mbrMaxX, newNode.mbrMaxY)) {
                  nextGroup = 0;
               } else if (Rectangle.area(newNode.mbrMinX, newNode.mbrMinY, newNode.mbrMaxX, newNode.mbrMaxY)
                  < Rectangle.area(n.mbrMinX, n.mbrMinY, n.mbrMaxX, n.mbrMaxY)) {
                  nextGroup = 1;
               } else if (newNode.entryCount < this.maxNodeEntries / 2) {
                  nextGroup = 0;
               } else {
                  nextGroup = 1;
               }

               maxDifference = difference;
            }

            if (log.isDebugEnabled()) {
               log.debug(
                  "Entry "
                     + i
                     + " group0 increase = "
                     + nIncrease
                     + ", group1 increase = "
                     + newNodeIncrease
                     + ", diff = "
                     + difference
                     + ", MaxDiff = "
                     + maxDifference
                     + " (entry "
                     + next
                     + ")"
               );
            }
         }
      }

      this.entryStatus[next] = 0;
      if (nextGroup == 0) {
         if (n.entriesMinX[next] < n.mbrMinX) {
            n.mbrMinX = n.entriesMinX[next];
         }

         if (n.entriesMinY[next] < n.mbrMinY) {
            n.mbrMinY = n.entriesMinY[next];
         }

         if (n.entriesMaxX[next] > n.mbrMaxX) {
            n.mbrMaxX = n.entriesMaxX[next];
         }

         if (n.entriesMaxY[next] > n.mbrMaxY) {
            n.mbrMaxY = n.entriesMaxY[next];
         }

         n.entryCount++;
      } else {
         newNode.addEntry(n.entriesMinX[next], n.entriesMinY[next], n.entriesMaxX[next], n.entriesMaxY[next], n.ids[next]);
         n.ids[next] = -1;
      }

      return next;
   }

   private float nearest(Point p, Node n, float furthestDistanceSq) {
      for (int i = 0; i < n.entryCount; i++) {
         float tempDistanceSq = Rectangle.distanceSq(n.entriesMinX[i], n.entriesMinY[i], n.entriesMaxX[i], n.entriesMaxY[i], p.x, p.y);
         if (n.isLeaf()) {
            if (tempDistanceSq < furthestDistanceSq) {
               furthestDistanceSq = tempDistanceSq;
               this.nearestIds.reset();
            }

            if (tempDistanceSq <= furthestDistanceSq) {
               this.nearestIds.add(n.ids[i]);
            }
         } else if (tempDistanceSq <= furthestDistanceSq) {
            furthestDistanceSq = this.nearest(p, this.getNode(n.ids[i]), furthestDistanceSq);
         }
      }

      return furthestDistanceSq;
   }

   private boolean intersects(Rectangle r, TIntProcedure v, Node n) {
      for (int i = 0; i < n.entryCount; i++) {
         if (Rectangle.intersects(r.minX, r.minY, r.maxX, r.maxY, n.entriesMinX[i], n.entriesMinY[i], n.entriesMaxX[i], n.entriesMaxY[i])) {
            if (n.isLeaf()) {
               if (!v.execute(n.ids[i])) {
                  return false;
               }
            } else {
               Node childNode = this.getNode(n.ids[i]);
               if (!this.intersects(r, v, childNode)) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private void condenseTree(Node l) {
      Node n = l;
      Node parent = null;
      int parentEntry = 0;
      TIntStack eliminatedNodeIds = new TIntArrayStack();

      while (n.level != this.treeHeight) {
         parent = this.getNode(this.parents.pop());
         parentEntry = this.parentsEntry.pop();
         if (n.entryCount < this.minNodeEntries) {
            parent.deleteEntry(parentEntry);
            eliminatedNodeIds.push(n.nodeId);
         } else if (n.mbrMinX != parent.entriesMinX[parentEntry]
            || n.mbrMinY != parent.entriesMinY[parentEntry]
            || n.mbrMaxX != parent.entriesMaxX[parentEntry]
            || n.mbrMaxY != parent.entriesMaxY[parentEntry]) {
            float deletedMinX = parent.entriesMinX[parentEntry];
            float deletedMinY = parent.entriesMinY[parentEntry];
            float deletedMaxX = parent.entriesMaxX[parentEntry];
            float deletedMaxY = parent.entriesMaxY[parentEntry];
            parent.entriesMinX[parentEntry] = n.mbrMinX;
            parent.entriesMinY[parentEntry] = n.mbrMinY;
            parent.entriesMaxX[parentEntry] = n.mbrMaxX;
            parent.entriesMaxY[parentEntry] = n.mbrMaxY;
            parent.recalculateMBRIfInfluencedBy(deletedMinX, deletedMinY, deletedMaxX, deletedMaxY);
         }

         n = parent;
      }

      while (eliminatedNodeIds.size() > 0) {
         Node e = this.getNode(eliminatedNodeIds.pop());

         for (int j = 0; j < e.entryCount; j++) {
            this.add(e.entriesMinX[j], e.entriesMinY[j], e.entriesMaxX[j], e.entriesMaxY[j], e.ids[j], e.level);
            e.ids[j] = -1;
         }

         e.entryCount = 0;
         this.deletedNodeIds.push(e.nodeId);
      }
   }

   private Node chooseNode(float minX, float minY, float maxX, float maxY, int level) {
      Node n = this.getNode(this.rootNodeId);
      this.parents.clear();
      this.parentsEntry.clear();

      while (true) {
         if (n == null) {
            log.error("Could not get root node (" + this.rootNodeId + ")");
         }

         if (n.level == level) {
            return n;
         }

         float leastEnlargement = Rectangle.enlargement(n.entriesMinX[0], n.entriesMinY[0], n.entriesMaxX[0], n.entriesMaxY[0], minX, minY, maxX, maxY);
         int index = 0;

         for (int i = 1; i < n.entryCount; i++) {
            float tempMinX = n.entriesMinX[i];
            float tempMinY = n.entriesMinY[i];
            float tempMaxX = n.entriesMaxX[i];
            float tempMaxY = n.entriesMaxY[i];
            float tempEnlargement = Rectangle.enlargement(tempMinX, tempMinY, tempMaxX, tempMaxY, minX, minY, maxX, maxY);
            if (tempEnlargement < leastEnlargement
               || tempEnlargement == leastEnlargement
                  && Rectangle.area(tempMinX, tempMinY, tempMaxX, tempMaxY)
                     < Rectangle.area(n.entriesMinX[index], n.entriesMinY[index], n.entriesMaxX[index], n.entriesMaxY[index])) {
               index = i;
               leastEnlargement = tempEnlargement;
            }
         }

         this.parents.push(n.nodeId);
         this.parentsEntry.push(index);
         n = this.getNode(n.ids[index]);
      }
   }

   private Node adjustTree(Node n, Node nn) {
      while (n.level != this.treeHeight) {
         Node parent = this.getNode(this.parents.pop());
         int entry = this.parentsEntry.pop();
         if (parent.ids[entry] != n.nodeId) {
            log.error(
               "Error: entry " + entry + " in node " + parent.nodeId + " should point to node " + n.nodeId + "; actually points to node " + parent.ids[entry]
            );
         }

         if (parent.entriesMinX[entry] != n.mbrMinX
            || parent.entriesMinY[entry] != n.mbrMinY
            || parent.entriesMaxX[entry] != n.mbrMaxX
            || parent.entriesMaxY[entry] != n.mbrMaxY) {
            parent.entriesMinX[entry] = n.mbrMinX;
            parent.entriesMinY[entry] = n.mbrMinY;
            parent.entriesMaxX[entry] = n.mbrMaxX;
            parent.entriesMaxY[entry] = n.mbrMaxY;
            parent.recalculateMBR();
         }

         Node newNode = null;
         if (nn != null) {
            if (parent.entryCount < this.maxNodeEntries) {
               parent.addEntry(nn.mbrMinX, nn.mbrMinY, nn.mbrMaxX, nn.mbrMaxY, nn.nodeId);
            } else {
               newNode = this.splitNode(parent, nn.mbrMinX, nn.mbrMinY, nn.mbrMaxX, nn.mbrMaxY, nn.nodeId);
            }
         }

         n = parent;
         nn = newNode;
         Node var6 = null;
         newNode = null;
      }

      return nn;
   }

   public boolean checkConsistency() {
      return this.checkConsistency(this.rootNodeId, this.treeHeight, null);
   }

   private boolean checkConsistency(int nodeId, int expectedLevel, Rectangle expectedMBR) {
      Node n = this.getNode(nodeId);
      if (n == null) {
         log.error("Error: Could not read node " + nodeId);
         return false;
      } else if (nodeId == this.rootNodeId && this.size() == 0 && n.level != 1) {
         log.error("Error: tree is empty but root node is not at level 1");
         return false;
      } else if (n.level != expectedLevel) {
         log.error("Error: Node " + nodeId + ", expected level " + expectedLevel + ", actual level " + n.level);
         return false;
      } else {
         Rectangle calculatedMBR = this.calculateMBR(n);
         Rectangle actualMBR = new Rectangle();
         actualMBR.minX = n.mbrMinX;
         actualMBR.minY = n.mbrMinY;
         actualMBR.maxX = n.mbrMaxX;
         actualMBR.maxY = n.mbrMaxY;
         if (!actualMBR.equals(calculatedMBR)) {
            log.error("Error: Node " + nodeId + ", calculated MBR does not equal stored MBR");
            if (actualMBR.minX != n.mbrMinX) {
               log.error("  actualMinX=" + actualMBR.minX + ", calc=" + calculatedMBR.minX);
            }

            if (actualMBR.minY != n.mbrMinY) {
               log.error("  actualMinY=" + actualMBR.minY + ", calc=" + calculatedMBR.minY);
            }

            if (actualMBR.maxX != n.mbrMaxX) {
               log.error("  actualMaxX=" + actualMBR.maxX + ", calc=" + calculatedMBR.maxX);
            }

            if (actualMBR.maxY != n.mbrMaxY) {
               log.error("  actualMaxY=" + actualMBR.maxY + ", calc=" + calculatedMBR.maxY);
            }

            return false;
         } else if (expectedMBR != null && !actualMBR.equals(expectedMBR)) {
            log.error("Error: Node " + nodeId + ", expected MBR (from parent) does not equal stored MBR");
            return false;
         } else if (expectedMBR != null && actualMBR.sameObject(expectedMBR)) {
            log.error("Error: Node " + nodeId + " MBR using same rectangle object as parent's entry");
            return false;
         } else {
            for (int i = 0; i < n.entryCount; i++) {
               if (n.ids[i] == -1) {
                  log.error("Error: Node " + nodeId + ", Entry " + i + " is null");
                  return false;
               }

               if (n.level > 1
                  && !this.checkConsistency(n.ids[i], n.level - 1, new Rectangle(n.entriesMinX[i], n.entriesMinY[i], n.entriesMaxX[i], n.entriesMaxY[i]))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private Rectangle calculateMBR(Node n) {
      Rectangle mbr = new Rectangle();

      for (int i = 0; i < n.entryCount; i++) {
         if (n.entriesMinX[i] < mbr.minX) {
            mbr.minX = n.entriesMinX[i];
         }

         if (n.entriesMinY[i] < mbr.minY) {
            mbr.minY = n.entriesMinY[i];
         }

         if (n.entriesMaxX[i] > mbr.maxX) {
            mbr.maxX = n.entriesMaxX[i];
         }

         if (n.entriesMaxY[i] > mbr.maxY) {
            mbr.maxY = n.entriesMaxY[i];
         }
      }

      return mbr;
   }
}
