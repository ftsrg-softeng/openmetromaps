package de.topobyte.osm4j.testing;

import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;

public class TestDataSetOutputStream implements OsmOutputStream {
   private TestDataSet data = new TestDataSet();

   public TestDataSet getData() {
      return this.data;
   }

   public void write(OsmBounds bounds) throws IOException {
      this.data.setBounds(EntityHelper.clone(bounds));
   }

   public void write(OsmNode node) throws IOException {
      this.data.getNodes().add(EntityHelper.clone(node));
   }

   public void write(OsmWay way) throws IOException {
      this.data.getWays().add(EntityHelper.clone(way));
   }

   public void write(OsmRelation relation) throws IOException {
      this.data.getRelations().add(EntityHelper.clone(relation));
   }

   public void complete() throws IOException {
   }
}
