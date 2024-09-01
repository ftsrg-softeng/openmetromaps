package de.topobyte.jsi.intersectiontester;

import com.infomatiq.jsi.Rectangle;
import com.infomatiq.jsi.rtree.RTree;
import com.infomatiq.jsi.rtree.Traversal;
import com.infomatiq.jsi.rtree.TreeTraverser;
import com.slimjars.dist.gnu.trove.procedure.TIntProcedure;

public class RTreeIntersectionTester implements RectangleIntersectionTester {
   int counter = 1;
   RTree tree;
   boolean free = true;

   public RTreeIntersectionTester() {
      this(1, 10);
   }

   public RTreeIntersectionTester(int minNodeEntries, int maxNodeEntries) {
      this.tree = new RTree(minNodeEntries, maxNodeEntries);
   }

   public RTreeIntersectionTester(RTree tree) {
      this.tree = tree;
      Traversal traversal = new Traversal() {
         public void node(Rectangle rectangle) {
         }

         public void element(Rectangle rectangle, int nodeId) {
            if (nodeId >= RTreeIntersectionTester.this.counter) {
               RTreeIntersectionTester.this.counter = nodeId + 1;
            }
         }
      };
      TreeTraverser traverser = new TreeTraverser(tree, traversal);
      traverser.traverse();
   }

   @Override
   public void add(Rectangle r, boolean clone) {
      if (clone) {
         r = new Rectangle(r.minX, r.minY, r.maxX, r.maxY);
      }

      this.tree.add(r, this.counter++);
   }

   @Override
   public boolean isFree(Rectangle rectangle) {
      this.free = true;
      this.tree.intersects(rectangle, new TIntProcedure() {
         public boolean execute(int id) {
            RTreeIntersectionTester.this.free = false;
            return false;
         }
      });
      return this.free;
   }
}
