package de.topobyte.osm4j.pbf.seq;

import de.topobyte.osm4j.pbf.protobuf.Fileformat;
import de.topobyte.osm4j.pbf.protobuf.Osmformat;
import de.topobyte.osm4j.pbf.util.BlobHeader;
import de.topobyte.osm4j.pbf.util.BlockData;
import de.topobyte.osm4j.pbf.util.PbfUtil;
import java.io.IOException;

public abstract class BlockParser extends BlobParser {
   @Override
   protected void parse(BlobHeader header, Fileformat.Blob blob) throws IOException {
      BlockData blockData = PbfUtil.getBlockData(blob);
      String type = header.getType();
      if (type.equals("OSMData")) {
         Osmformat.PrimitiveBlock primBlock = Osmformat.PrimitiveBlock.parseFrom(blockData.getBlobData());
         this.parse(primBlock);
      } else {
         if (!type.equals("OSMHeader")) {
            throw new IOException("invalid PBF block");
         }

         Osmformat.HeaderBlock headerBlock = Osmformat.HeaderBlock.parseFrom(blockData.getBlobData());
         this.parse(headerBlock);
      }
   }

   protected abstract void parse(Osmformat.HeaderBlock var1) throws IOException;

   protected abstract void parse(Osmformat.PrimitiveBlock var1) throws IOException;
}
