package de.topobyte.osm4j.pbf.seq;

import de.topobyte.osm4j.pbf.protobuf.Fileformat;
import de.topobyte.osm4j.pbf.util.BlobHeader;
import de.topobyte.osm4j.pbf.util.PbfUtil;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public abstract class BlobParser {
   public void parse(InputStream input) throws IOException {
      DataInputStream data = new DataInputStream(input);

      while (true) {
         try {
            this.parseBlob(data);
         } catch (EOFException var4) {
            return;
         }
      }
   }

   private void parseBlob(DataInput data) throws IOException {
      BlobHeader header = PbfUtil.parseHeader(data);
      Fileformat.Blob blob = PbfUtil.parseBlock(data, header.getDataLength());
      this.parse(header, blob);
   }

   protected abstract void parse(BlobHeader var1, Fileformat.Blob var2) throws IOException;
}
