package de.topobyte.osm4j.utils.config;

import de.topobyte.osm4j.tbo.Compression;
import de.topobyte.osm4j.utils.config.limit.ElementCountLimit;
import de.topobyte.osm4j.utils.config.limit.NodeLimit;
import de.topobyte.osm4j.utils.config.limit.RelationLimit;
import de.topobyte.osm4j.utils.config.limit.RelationMemberLimit;
import de.topobyte.osm4j.utils.config.limit.WayLimit;
import de.topobyte.osm4j.utils.config.limit.WayNodeLimit;

public class TboConfig {
   private Compression compression = Compression.LZ4;
   private NodeLimit limitNodes = new ElementCountLimit(4096);
   private WayLimit limitWays = new WayNodeLimit(6144);
   private RelationLimit limitRelations = new RelationMemberLimit(8192);

   public Compression getCompression() {
      return this.compression;
   }

   public void setCompression(Compression compression) {
      this.compression = compression;
   }

   public NodeLimit getLimitNodes() {
      return this.limitNodes;
   }

   public void setLimitNodes(NodeLimit limitNodes) {
      this.limitNodes = limitNodes;
   }

   public WayLimit getLimitWays() {
      return this.limitWays;
   }

   public void setLimitWays(WayLimit limitWays) {
      this.limitWays = limitWays;
   }

   public RelationLimit getLimitRelations() {
      return this.limitRelations;
   }

   public void setLimitRelations(RelationLimit limitRelations) {
      this.limitRelations = limitRelations;
   }
}
