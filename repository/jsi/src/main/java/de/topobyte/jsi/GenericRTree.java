package de.topobyte.jsi;

import com.infomatiq.jsi.Point;
import com.infomatiq.jsi.Rectangle;
import com.infomatiq.jsi.rtree.RTree;
import com.slimjars.dist.gnu.trove.map.TIntObjectMap;
import com.slimjars.dist.gnu.trove.map.TObjectIntMap;
import com.slimjars.dist.gnu.trove.map.hash.TIntObjectHashMap;
import com.slimjars.dist.gnu.trove.map.hash.TObjectIntHashMap;
import com.slimjars.dist.gnu.trove.procedure.TIntProcedure;
import com.slimjars.dist.gnu.trove.procedure.TObjectProcedure;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GenericRTree<T> implements GenericSpatialIndex<T>, Externalizable {
   RTree rtree;
   int indexer = 0;
   TIntObjectMap<T> idToThing;
   TObjectIntMap<T> thingToId;
   Map<T, Rectangle> thingToRect;

   public GenericRTree(int minNodes, int maxNodes) {
      this.rtree = new RTree(minNodes, maxNodes);
      this.idToThing = new TIntObjectHashMap();
      this.thingToId = new TObjectIntHashMap();
      this.thingToRect = new HashMap<>();
   }

   public GenericRTree() {
      this(1, 10);
   }

   @Override
   public void add(Rectangle r, T thing) {
      int index = this.indexer++;
      this.add(r, thing, index);
   }

   private void add(Rectangle r, T thing, int index) {
      this.rtree.add(r, index);
      this.idToThing.put(index, thing);
      this.thingToId.put(thing, index);
      this.thingToRect.put(thing, r);
   }

   @Override
   public boolean delete(Rectangle r, T thing) {
      int id = this.thingToId.get(thing);
      boolean success = this.rtree.delete(r, id);
      if (success) {
         this.thingToId.remove(thing);
         this.idToThing.remove(id);
         this.thingToRect.remove(thing);
      }

      return success;
   }

   @Override
   public void contains(Rectangle r, final TObjectProcedure<T> procedure) {
      this.rtree.contains(r, new TIntProcedure() {
         public boolean execute(int id) {
            T thing = (T)GenericRTree.this.idToThing.get(id);
            return procedure.execute(thing);
         }
      });
   }

   @Override
   public Set<T> contains(Rectangle r) {
      final Set<T> results = new HashSet<>();
      this.contains(r, new TObjectProcedure<T>() {
         public boolean execute(T thing) {
            results.add(thing);
            return true;
         }
      });
      return results;
   }

   @Override
   public void intersects(Rectangle r, final TObjectProcedure<T> procedure) {
      this.rtree.intersects(r, new TIntProcedure() {
         public boolean execute(int id) {
            T thing = (T)GenericRTree.this.idToThing.get(id);
            return procedure.execute(thing);
         }
      });
   }

   @Override
   public Set<T> intersects(Rectangle r) {
      final Set<T> results = new HashSet<>();
      this.intersects(r, new TObjectProcedure<T>() {
         public boolean execute(T thing) {
            results.add(thing);
            return true;
         }
      });
      return results;
   }

   @Override
   public List<T> intersectionsAsList(Rectangle r) {
      final List<T> results = new ArrayList<>();
      this.intersects(r, new TObjectProcedure<T>() {
         public boolean execute(T thing) {
            results.add(thing);
            return true;
         }
      });
      return results;
   }

   @Override
   public void nearest(Point p, final TObjectProcedure<T> procedure, float distance) {
      this.rtree.nearest(p, new TIntProcedure() {
         public boolean execute(int id) {
            T thing = (T)GenericRTree.this.idToThing.get(id);
            return procedure.execute(thing);
         }
      }, distance);
   }

   @Override
   public Set<T> nearest(Point p, float distance) {
      final Set<T> results = new HashSet<>();
      this.nearest(p, new TObjectProcedure<T>() {
         public boolean execute(T thing) {
            results.add(thing);
            return true;
         }
      }, distance);
      return results;
   }

   @Override
   public int size() {
      return this.rtree.size();
   }

   public Rectangle getBounds() {
      return this.rtree.getBounds();
   }

   @Override
   public void writeExternal(ObjectOutput oo) throws IOException {
      oo.writeInt(this.indexer);
      oo.writeInt(this.idToThing.size());
      int[] keys = this.idToThing.keys();

      for (int i : keys) {
         T thing = (T)this.idToThing.get(i);
         Rectangle rect = this.thingToRect.get(thing);
         oo.writeInt(i);
         oo.writeObject(thing);
         oo.writeFloat(rect.minX);
         oo.writeFloat(rect.maxX);
         oo.writeFloat(rect.minY);
         oo.writeFloat(rect.maxY);
      }
   }

   @Override
   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.indexer = oi.readInt();
      int size = oi.readInt();

      for (int i = 0; i < size; i++) {
         int id = oi.readInt();
         T thing = (T)oi.readObject();
         float minX = oi.readFloat();
         float maxX = oi.readFloat();
         float minY = oi.readFloat();
         float maxY = oi.readFloat();
         Rectangle r = new Rectangle(minX, minY, maxX, maxY);
         this.add(r, thing, id);
      }
   }
}
