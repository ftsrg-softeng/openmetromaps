package de.topobyte.osm4j.extra.relations.split;

import de.topobyte.osm4j.extra.batch.SizeBatch;
import de.topobyte.osm4j.extra.relations.Group;

class GroupBatch extends SizeBatch<Group> {
   GroupBatch(int maxMembers) {
      super(maxMembers);
   }

   protected int size(Group element) {
      return element.getNumMembers();
   }
}
