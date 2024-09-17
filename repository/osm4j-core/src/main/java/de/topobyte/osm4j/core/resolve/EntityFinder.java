package de.topobyte.osm4j.core.resolve;

import com.slimjars.dist.gnu.trove.TLongCollection;
import de.topobyte.adt.multicollections.MultiSet;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface EntityFinder {
   List<OsmNode> findNodes(TLongCollection var1) throws EntityNotFoundException;

   List<OsmWay> findWays(TLongCollection var1) throws EntityNotFoundException;

   List<OsmRelation> findRelations(TLongCollection var1) throws EntityNotFoundException;

   void findWayNodes(OsmWay var1, Collection<OsmNode> var2) throws EntityNotFoundException;

   void findWayNodes(Collection<OsmWay> var1, Collection<OsmNode> var2) throws EntityNotFoundException;

   void findMemberNodes(OsmRelation var1, Set<OsmNode> var2) throws EntityNotFoundException;

   void findMemberNodes(Collection<OsmRelation> var1, Set<OsmNode> var2) throws EntityNotFoundException;

   void findMemberWays(OsmRelation var1, Set<OsmWay> var2) throws EntityNotFoundException;

   void findMemberWays(Collection<OsmRelation> var1, Set<OsmWay> var2) throws EntityNotFoundException;

   void findMemberWays(OsmRelation var1, MultiSet<OsmWay> var2) throws EntityNotFoundException;

   void findMemberWays(Collection<OsmRelation> var1, MultiSet<OsmWay> var2) throws EntityNotFoundException;

   void findMemberRelations(OsmRelation var1, Set<OsmRelation> var2) throws EntityNotFoundException;

   void findMemberRelations(Collection<OsmRelation> var1, Set<OsmRelation> var2) throws EntityNotFoundException;

   void findMemberRelationsRecursively(OsmRelation var1, Set<OsmRelation> var2) throws EntityNotFoundException;

   void findMemberRelationsRecursively(Collection<OsmRelation> var1, Set<OsmRelation> var2) throws EntityNotFoundException;

   void findMemberNodesAndWays(OsmRelation var1, Set<OsmNode> var2, Set<OsmWay> var3) throws EntityNotFoundException;

   void findMemberNodesAndWays(Collection<OsmRelation> var1, Set<OsmNode> var2, Set<OsmWay> var3) throws EntityNotFoundException;

   void findMemberNodesAndWayNodes(OsmRelation var1, Set<OsmNode> var2) throws EntityNotFoundException;

   void findMemberNodesAndWayNodes(Collection<OsmRelation> var1, Set<OsmNode> var2) throws EntityNotFoundException;
}
