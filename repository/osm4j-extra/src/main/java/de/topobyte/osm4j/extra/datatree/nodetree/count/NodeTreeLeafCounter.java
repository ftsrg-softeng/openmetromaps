package de.topobyte.osm4j.extra.datatree.nodetree.count;

import com.slimjars.dist.gnu.trove.map.TLongLongMap;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.extra.threading.Task;

public interface NodeTreeLeafCounter extends Task {
   Node getHead();

   TLongLongMap getCounters();
}
