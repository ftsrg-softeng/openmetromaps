package de.topobyte.osm4j.core.dataset;

import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;

public class ListDataSetOutputStream implements OsmOutputStream {
   private InMemoryListDataSet data = new InMemoryListDataSet();

   public InMemoryListDataSet getData() {
      return this.data;
   }

   @Override
   public void write(OsmBounds bounds) throws IOException {
      this.data.setBounds(bounds);
   }

   @Override
   public void write(OsmNode node) throws IOException {
      this.data.getNodes().add(node);
   }

   @Override
   public void write(OsmWay way) throws IOException {
      this.data.getWays().add(way);
   }

   @Override
   public void write(OsmRelation relation) throws IOException {
      this.data.getRelations().add(relation);
   }

   @Override
   public void complete() throws IOException {
   }
}
