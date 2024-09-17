package de.topobyte.osm4j.pbf.seq;

import de.topobyte.osm4j.core.access.OsmHandler;
import de.topobyte.osm4j.pbf.protobuf.Osmformat;
import de.topobyte.osm4j.pbf.util.PbfUtil;
import java.io.IOException;

public class PbfParser extends BlockParser {
   private OsmHandler handler;
   private boolean fetchMetadata;

   public PbfParser(OsmHandler handler, boolean fetchMetadata) {
      this.handler = handler;
      this.fetchMetadata = fetchMetadata;
   }

   @Override
   protected void parse(Osmformat.HeaderBlock block) throws IOException {
      Osmformat.HeaderBBox bbox = block.getBbox();
      this.handler.handle(PbfUtil.bounds(bbox));
   }

   @Override
   protected void parse(Osmformat.PrimitiveBlock block) throws IOException {
      PrimParser primParser = new PrimParser(block, this.fetchMetadata);

      for (Osmformat.PrimitiveGroup group : block.getPrimitivegroupList()) {
         primParser.parseNodes(group.getNodesList(), this.handler);
         primParser.parseWays(group.getWaysList(), this.handler);
         primParser.parseRelations(group.getRelationsList(), this.handler);
         if (group.hasDense()) {
            primParser.parseDense(group.getDense(), this.handler);
         }
      }
   }
}
