package de.topobyte.osm4j.core.util;

import com.slimjars.dist.gnu.trove.TLongCollection;
import com.slimjars.dist.gnu.trove.iterator.TLongIterator;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import java.util.Collection;

public class IdUtil {
   public static long lowestId(TLongCollection ids) {
      long lowest = Long.MAX_VALUE;
      TLongIterator iterator = ids.iterator();

      while (iterator.hasNext()) {
         lowest = Math.min(lowest, iterator.next());
      }

      return lowest;
   }

   public static long lowestId(Collection<? extends OsmEntity> elements) {
      long lowest = Long.MAX_VALUE;

      for (OsmEntity element : elements) {
         lowest = Math.min(lowest, element.getId());
      }

      return lowest;
   }
}
