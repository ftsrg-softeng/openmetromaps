package de.topobyte.osm4j.extra.relations.split;

import com.slimjars.dist.gnu.trove.map.TLongObjectMap;
import com.slimjars.dist.gnu.trove.map.hash.TLongObjectHashMap;
import com.slimjars.dist.gnu.trove.set.TLongSet;
import de.topobyte.osm4j.core.access.OsmIteratorInput;
import de.topobyte.osm4j.core.access.OsmIteratorInputFactory;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.util.RelationIterator;
import de.topobyte.osm4j.extra.relations.Group;
import de.topobyte.osm4j.extra.relations.RelationGraph;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ComplexRelationGrouper {
   private OsmIteratorInputFactory iteratorFactory;
   private RelationGraph relationGraph;
   private List<Group> groups;
   private TLongObjectMap<OsmRelation> groupRelations;

   public ComplexRelationGrouper(OsmIteratorInputFactory iteratorFactory, boolean storeSimpleRelations, boolean undirected) {
      this.iteratorFactory = iteratorFactory;
      this.relationGraph = new RelationGraph(storeSimpleRelations, undirected);
   }

   public List<Group> getGroups() {
      return this.groups;
   }

   public TLongObjectMap<OsmRelation> getGroupRelations() {
      return this.groupRelations;
   }

   public void buildGroups() throws IOException {
      OsmIteratorInput iteratorInput = this.iteratorFactory.createIterator(false, false);
      this.relationGraph.build(iteratorInput.getIterator());
      iteratorInput.close();
      System.out.println("Number of relations without relation members: " + this.relationGraph.getNumNoChildren());
      System.out.println("Number of relations with relation members: " + this.relationGraph.getIdsHasChildRelations().size());
      System.out.println("Number of child relations: " + this.relationGraph.getIdsIsChildRelation().size());
      this.groups = this.relationGraph.buildGroups();
   }

   public void readGroupRelations(boolean readMetadata) throws FileNotFoundException, IOException {
      TLongSet idsHasChildRelations = this.relationGraph.getIdsHasChildRelations();
      TLongSet idsIsChildRelation = this.relationGraph.getIdsIsChildRelation();
      OsmIteratorInput iteratorInput = this.iteratorFactory.createIterator(true, readMetadata);
      RelationIterator relations = new RelationIterator(iteratorInput.getIterator());
      this.groupRelations = new TLongObjectHashMap();

      for (OsmRelation relation : relations) {
         if (idsHasChildRelations.contains(relation.getId()) || idsIsChildRelation.contains(relation.getId())) {
            this.groupRelations.put(relation.getId(), relation);
         }
      }

      iteratorInput.close();
   }
}
