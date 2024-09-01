package de.topobyte.lightgeom.convexhull;

import de.topobyte.lightgeom.lina.Point;
import java.util.ArrayList;
import java.util.List;

public class ConvexHull {
   private final IntArray quicksortStack = new IntArray();
   private float[] sortedPoints;
   private final PointArray hull = new PointArray();
   private final IntArray indices = new IntArray();
   private final ShortArray originalIndices = new ShortArray(false, 0);

   public List<Point> computePolygon(List<Point> points) {
      int nPoints = points.size();
      float[] values = new float[nPoints * 2];

      for (int i = 0; i < nPoints; i++) {
         Point point = points.get(i);
         values[2 * i] = (float)point.x;
         values[2 * i + 1] = (float)point.y;
      }

      List<Point> result = new ArrayList<>();
      PointArray polygon = this.computePolygon(values, false);
      int nResultPoints = polygon.size / 2;

      for (int i = 0; i < nResultPoints; i++) {
         float x = polygon.items[2 * i];
         float y = polygon.items[2 * i + 1];
         result.add(new Point((double)x, (double)y));
      }

      return result;
   }

   public PointArray computePolygon(FloatArray points, boolean sorted) {
      return this.computePolygon(points.items, 0, points.size, sorted);
   }

   public PointArray computePolygon(float[] polygon, boolean sorted) {
      return this.computePolygon(polygon, 0, polygon.length, sorted);
   }

   public PointArray computePolygon(float[] points, int offset, int count, boolean sorted) {
      int end = offset + count;
      if (!sorted) {
         if (this.sortedPoints == null || this.sortedPoints.length < count) {
            this.sortedPoints = new float[count];
         }

         System.arraycopy(points, offset, this.sortedPoints, 0, count);
         points = this.sortedPoints;
         offset = 0;
         this.sort(points, count);
      }

      PointArray hull = this.hull;
      hull.clear();

      for (int i = offset; i < end; i += 2) {
         float x = points[i];
         float y = points[i + 1];

         while (hull.size >= 4 && this.ccw(x, y) <= 0.0F) {
            hull.size -= 2;
         }

         hull.add(x);
         hull.add(y);
      }

      int i = end - 4;

      for (int t = hull.size + 2; i >= offset; i -= 2) {
         float x = points[i];
         float y = points[i + 1];

         while (hull.size >= t && this.ccw(x, y) <= 0.0F) {
            hull.size -= 2;
         }

         hull.add(x);
         hull.add(y);
      }

      return hull;
   }

   public IntArray computeIndices(FloatArray points, boolean sorted, boolean yDown) {
      return this.computeIndices(points.items, 0, points.size, sorted, yDown);
   }

   public IntArray computeIndices(float[] polygon, boolean sorted, boolean yDown) {
      return this.computeIndices(polygon, 0, polygon.length, sorted, yDown);
   }

   public IntArray computeIndices(float[] points, int offset, int count, boolean sorted, boolean yDown) {
      int end = offset + count;
      if (!sorted) {
         if (this.sortedPoints == null || this.sortedPoints.length < count) {
            this.sortedPoints = new float[count];
         }

         System.arraycopy(points, offset, this.sortedPoints, 0, count);
         points = this.sortedPoints;
         offset = 0;
         this.sortWithIndices(points, count, yDown);
      }

      IntArray indices = this.indices;
      indices.clear();
      FloatArray hull = this.hull;
      hull.clear();
      int i = offset;

      for (int index = offset / 2; i < end; index++) {
         float x = points[i];

         float y;
         for (y = points[i + 1]; hull.size >= 4 && this.ccw(x, y) <= 0.0F; indices.size--) {
            hull.size -= 2;
         }

         hull.add(x);
         hull.add(y);
         indices.add(index);
         i += 2;
      }

      i = end - 4;
      int index = i / 2;

      for (int t = hull.size + 2; i >= offset; index--) {
         float x = points[i];

         float y;
         for (y = points[i + 1]; hull.size >= t && this.ccw(x, y) <= 0.0F; indices.size--) {
            hull.size -= 2;
         }

         hull.add(x);
         hull.add(y);
         indices.add(index);
         i -= 2;
      }

      if (!sorted) {
         short[] originalIndicesArray = this.originalIndices.items;
         int[] indicesArray = indices.items;
         int ix = 0;

         for (int n = indices.size; ix < n; ix++) {
            indicesArray[ix] = originalIndicesArray[indicesArray[ix]];
         }
      }

      return indices;
   }

   private float ccw(float p3x, float p3y) {
      FloatArray hull = this.hull;
      int size = hull.size;
      float p1x = hull.get(size - 4);
      float p1y = hull.get(size - 3);
      float p2x = hull.get(size - 2);
      float p2y = hull.peek();
      return (p2x - p1x) * (p3y - p1y) - (p2y - p1y) * (p3x - p1x);
   }

   private void sort(float[] values, int count) {
      int lower = 0;
      int upper = count - 1;
      IntArray stack = this.quicksortStack;
      stack.add(lower);
      stack.add(upper - 1);

      while (stack.size > 0) {
         upper = stack.pop();
         lower = stack.pop();
         if (upper > lower) {
            int i = this.quicksortPartition(values, lower, upper);
            if (i - lower > upper - i) {
               stack.add(lower);
               stack.add(i - 2);
            }

            stack.add(i + 2);
            stack.add(upper);
            if (upper - i >= i - lower) {
               stack.add(lower);
               stack.add(i - 2);
            }
         }
      }
   }

   private int quicksortPartition(float[] values, int lower, int upper) {
      float x = values[lower];
      float y = values[lower + 1];
      int up = upper;
      int down = lower;

      while (down < up) {
         while (down < up && values[down] <= x) {
            down += 2;
         }

         while (values[up] > x || values[up] == x && values[up + 1] < y) {
            up -= 2;
         }

         if (down < up) {
            float temp = values[down];
            values[down] = values[up];
            values[up] = temp;
            temp = values[down + 1];
            values[down + 1] = values[up + 1];
            values[up + 1] = temp;
         }
      }

      values[lower] = values[up];
      values[up] = x;
      values[lower + 1] = values[up + 1];
      values[up + 1] = y;
      return up;
   }

   private void sortWithIndices(float[] values, int count, boolean yDown) {
      int pointCount = count / 2;
      this.originalIndices.clear();
      this.originalIndices.ensureCapacity(pointCount);
      short[] originalIndicesArray = this.originalIndices.items;
      short i = (short)0;

      while (i < pointCount) {
         originalIndicesArray[i] = i++;
      }

      i = (short)0;
      int upper = count - 1;
      IntArray stack = this.quicksortStack;
      stack.add(i);
      stack.add(upper - 1);

      while (stack.size > 0) {
         upper = stack.pop();
         i = (short)stack.pop();
         if (upper > i) {
            int ix = this.quicksortPartitionWithIndices(values, i, upper, yDown, originalIndicesArray);
            if (ix - i > upper - ix) {
               stack.add(i);
               stack.add(ix - 2);
            }

            stack.add(ix + 2);
            stack.add(upper);
            if (upper - ix >= ix - i) {
               stack.add(i);
               stack.add(ix - 2);
            }
         }
      }
   }

   private int quicksortPartitionWithIndices(float[] values, int lower, int upper, boolean yDown, short[] originalIndices) {
      float x = values[lower];
      float y = values[lower + 1];
      int up = upper;
      int down = lower;

      while (down < up) {
         while (down < up && values[down] <= x) {
            down += 2;
         }

         if (yDown) {
            while (values[up] > x || values[up] == x && values[up + 1] < y) {
               up -= 2;
            }
         } else {
            while (values[up] > x || values[up] == x && values[up + 1] > y) {
               up -= 2;
            }
         }

         if (down < up) {
            float temp = values[down];
            values[down] = values[up];
            values[up] = temp;
            temp = values[down + 1];
            values[down + 1] = values[up + 1];
            values[up + 1] = temp;
            short tempIndex = originalIndices[down / 2];
            originalIndices[down / 2] = originalIndices[up / 2];
            originalIndices[up / 2] = tempIndex;
         }
      }

      values[lower] = values[up];
      values[up] = x;
      values[lower + 1] = values[up + 1];
      values[up + 1] = y;
      short tempIndex = originalIndices[lower / 2];
      originalIndices[lower / 2] = originalIndices[up / 2];
      originalIndices[up / 2] = tempIndex;
      return up;
   }
}
