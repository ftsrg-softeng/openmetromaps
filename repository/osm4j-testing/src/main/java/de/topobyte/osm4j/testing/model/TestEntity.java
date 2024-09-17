package de.topobyte.osm4j.testing.model;

import de.topobyte.osm4j.core.model.iface.OsmEntity;
import java.util.ArrayList;
import java.util.List;

public class TestEntity implements OsmEntity {
   private long id;
   private List<TestTag> tags;
   private TestMetadata metadata;

   public TestEntity(long id, TestMetadata metadata) {
      this.id = id;
      this.metadata = metadata;
      this.tags = new ArrayList<>();
   }

   public TestEntity(long id, List<TestTag> tags, TestMetadata metadata) {
      this.id = id;
      this.tags = tags;
      this.metadata = metadata;
   }

   public long getId() {
      return this.id;
   }

   public List<TestTag> getTags() {
      return this.tags;
   }

   public void setTags(List<TestTag> tags) {
      this.tags = tags;
   }

   public int getNumberOfTags() {
      return this.tags.size();
   }

   public TestTag getTag(int n) {
      return this.tags.get(n);
   }

   public TestMetadata getMetadata() {
      return this.metadata;
   }

   public void setMetadata(TestMetadata metadata) {
      this.metadata = metadata;
   }
}
