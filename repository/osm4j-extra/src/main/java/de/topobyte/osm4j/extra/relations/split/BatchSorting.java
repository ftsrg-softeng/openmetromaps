package de.topobyte.osm4j.extra.relations.split;

import com.vividsolutions.jts.geom.Envelope;
import de.topobyte.osm4j.extra.batch.BatchBuilder;
import de.topobyte.osm4j.extra.idbboxlist.IdBboxEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BatchSorting {
   public static List<List<IdBboxEntry>> sort(List<IdBboxEntry> bboxes, int maxMembers) {
      List<List<IdBboxEntry>> batches = new ArrayList<>();
      long size = 0L;

      for (IdBboxEntry entry : bboxes) {
         size += (long)entry.getSize();
      }

      System.out.println("num boxes: " + bboxes.size());
      System.out.println("total size: " + size);
      int maxSlice = (int)Math.ceil(Math.sqrt((double)(size * (long)maxMembers)));
      System.out.println("max nodes per slice: " + maxSlice);
      sortX(bboxes);
      BatchBuilder<IdBboxEntry> sliceBuilder = new BatchBuilder<>(new IdBboxEntryBatch(maxSlice));
      sliceBuilder.addAll(bboxes);
      sliceBuilder.finish();

      for (List<IdBboxEntry> slice : sliceBuilder.getResults()) {
         sortY(slice);
         BatchBuilder<IdBboxEntry> batchBuilder = new BatchBuilder<>(new IdBboxEntryBatch(maxMembers));
         batchBuilder.addAll(slice);
         batchBuilder.finish();
         batches.addAll(batchBuilder.getResults());
      }

      return batches;
   }

   private static void sortX(List<IdBboxEntry> bboxes) {
      Collections.sort(bboxes, new Comparator<IdBboxEntry>() {
         public int compare(IdBboxEntry o1, IdBboxEntry o2) {
            Envelope e1 = o1.getEnvelope();
            Envelope e2 = o2.getEnvelope();
            double x1 = e1.getMaxX() + e1.getMinX() / 2.0;
            double x2 = e2.getMaxX() + e2.getMinX() / 2.0;
            return Double.compare(x1, x2);
         }
      });
   }

   private static void sortY(List<IdBboxEntry> bboxes) {
      Collections.sort(bboxes, new Comparator<IdBboxEntry>() {
         public int compare(IdBboxEntry o1, IdBboxEntry o2) {
            Envelope e1 = o1.getEnvelope();
            Envelope e2 = o2.getEnvelope();
            double y1 = e1.getMaxY() + e1.getMinY() / 2.0;
            double y2 = e2.getMaxY() + e2.getMinY() / 2.0;
            return Double.compare(y1, y2);
         }
      });
   }
}
