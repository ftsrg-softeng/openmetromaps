package de.topobyte.osm4j.core.model.impl;

import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmMetadata;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import java.util.ArrayList;
import java.util.List;

public class Entity implements OsmEntity {
   private long id;
   private List<? extends OsmTag> tags;
   private OsmMetadata metadata;

   public Entity(long id, OsmMetadata metadata) {
      this.id = id;
      this.metadata = metadata;
      this.tags = new ArrayList<>();
   }

   public Entity(long id, List<? extends OsmTag> tags, OsmMetadata metadata) {
      this.id = id;
      this.tags = tags;
      this.metadata = metadata;
   }

   @Override
   public long getId() {
      return this.id;
   }

   public List<? extends OsmTag> getTags() {
      return this.tags;
   }

   public void setTags(List<? extends OsmTag> tags) {
      this.tags = tags;
   }

   @Override
   public int getNumberOfTags() {
      return this.tags.size();
   }

   @Override
   public OsmTag getTag(int n) {
      return this.tags.get(n);
   }

   @Override
   public OsmMetadata getMetadata() {
      return this.metadata;
   }

   public void setMetadata(OsmMetadata metadata) {
      this.metadata = metadata;
   }
}
