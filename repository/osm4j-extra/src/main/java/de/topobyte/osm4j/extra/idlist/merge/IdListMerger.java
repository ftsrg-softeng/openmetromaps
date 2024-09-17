package de.topobyte.osm4j.extra.idlist.merge;

import de.topobyte.osm4j.extra.idlist.IdInput;
import de.topobyte.osm4j.extra.idlist.IdListOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Collection;

public class IdListMerger {
   private IdListOutputStream output;
   private Collection<IdInput> inputs;

   public IdListMerger(IdListOutputStream output, Collection<IdInput> inputs) {
      this.output = output;
      this.inputs = inputs;
   }

   public void execute() throws IOException {
      IdInput idInput = new MergedIdInput(this.inputs);

      while (true) {
         try {
            long next = idInput.next();
            this.output.write(next);
         } catch (EOFException var4) {
            this.output.close();
            return;
         }
      }
   }
}
