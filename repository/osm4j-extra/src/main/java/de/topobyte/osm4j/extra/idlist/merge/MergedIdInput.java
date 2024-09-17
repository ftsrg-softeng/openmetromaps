package de.topobyte.osm4j.extra.idlist.merge;

import de.topobyte.osm4j.extra.idlist.IdInput;
import java.io.EOFException;
import java.io.IOException;
import java.util.Collection;
import java.util.PriorityQueue;

public class MergedIdInput implements IdInput {
   private Collection<IdInput> inputs;
   private PriorityQueue<MergeInput> queue;
   private long last = 0L;

   public MergedIdInput(Collection<IdInput> inputs) throws IOException {
      this.inputs = inputs;
      this.queue = new PriorityQueue<>(inputs.size(), new MergeInputComparator());

      for (IdInput input : inputs) {
         try {
            MergeInput mergeInput = new MergeInput(input);
            this.queue.add(mergeInput);
         } catch (EOFException var5) {
         }
      }
   }

   @Override
   public long next() throws IOException {
      while (!this.queue.isEmpty()) {
         MergeInput input = this.queue.poll();
         long next = input.getNext();

         try {
            input.next();
            this.queue.add(input);
         } catch (EOFException var5) {
            input.close();
         }

         if (this.last != next) {
            this.last = next;
            return next;
         }
      }

      throw new EOFException();
   }

   @Override
   public void close() throws IOException {
      for (IdInput input : this.inputs) {
         input.close();
      }
   }
}
