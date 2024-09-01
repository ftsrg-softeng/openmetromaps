package de.topobyte.osm4j.extra;

import com.slimjars.dist.gnu.trove.list.TIntList;
import com.slimjars.dist.gnu.trove.list.array.TIntArrayList;
import java.util.ArrayList;
import java.util.List;

public class MissingEntityCounter {
   private int numNodes = 0;
   private int numWays = 0;
   private int numWayNodes = 0;

   public void addNodes(int n) {
      this.numNodes += n;
   }

   public void addWays(int n) {
      this.numWays += n;
   }

   public void addWayNodes(int n) {
      this.numWayNodes += n;
   }

   public int getNumNodes() {
      return this.numNodes;
   }

   public int getNumWays() {
      return this.numWays;
   }

   public int getNumWayNodes() {
      return this.numWayNodes;
   }

   public boolean nonZero() {
      return this.numNodes != 0 || this.numWays != 0 || this.numWayNodes != 0;
   }

   public String toMessage() {
      TIntList nums = new TIntArrayList();
      List<String> names = new ArrayList<>();
      if (this.numNodes != 0) {
         names.add("nodes");
         nums.add(this.numNodes);
      }

      if (this.numWays != 0) {
         names.add("ways");
         nums.add(this.numWays);
      }

      if (this.numWayNodes != 0) {
         names.add("waynodes");
         nums.add(this.numWayNodes);
      }

      int vals = nums.size();
      if (vals == 0) {
         return null;
      } else {
         StringBuilder buffer = new StringBuilder();
         buffer.append(nums.get(0));
         buffer.append(" ");
         buffer.append(names.get(0));

         for (int i = 1; i < vals - 1; i++) {
            buffer.append(", ");
            buffer.append(nums.get(i));
            buffer.append(" ");
            buffer.append(names.get(i));
         }

         if (vals > 1) {
            buffer.append(" and ");
            buffer.append(nums.get(vals - 1));
            buffer.append(" ");
            buffer.append(names.get(vals - 1));
         }

         return buffer.toString();
      }
   }
}
