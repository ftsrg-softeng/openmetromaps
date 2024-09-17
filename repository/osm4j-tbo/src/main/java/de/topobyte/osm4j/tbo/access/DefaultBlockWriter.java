package de.topobyte.osm4j.tbo.access;

import de.topobyte.compactio.CompactWriter;
import de.topobyte.compactio.OutputStreamCompactWriter;
import de.topobyte.osm4j.tbo.ByteArrayOutputStream;
import de.topobyte.osm4j.tbo.Compression;
import de.topobyte.osm4j.tbo.data.FileBlock;
import de.topobyte.osm4j.tbo.data.FileHeader;
import java.io.IOException;

public class DefaultBlockWriter implements BlockWriter {
   protected final CompactWriter writer;

   public DefaultBlockWriter(CompactWriter writer) {
      this.writer = writer;
   }

   @Override
   public void writeHeader(FileHeader header) throws IOException {
      header.write(this.writer);
   }

   @Override
   public void writeBlock(FileBlock block) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      this.writeBlockInfo(new OutputStreamCompactWriter(baos), block);
      int total = baos.size() + block.getLength();
      this.writer.writeByte(block.getType());
      this.writer.writeVariableLengthUnsignedInteger((long)total);
      this.writeBlockInfo(this.writer, block);
      this.writer.write(block.getBuffer(), 0, block.getLength());
   }

   private void writeBlockInfo(CompactWriter writer, FileBlock block) throws IOException {
      writer.writeByte(block.getCompression().getId());
      if (block.getCompression() != Compression.NONE) {
         writer.writeVariableLengthUnsignedInteger((long)block.getUncompressedLength());
      }

      writer.writeVariableLengthUnsignedInteger((long)block.getNumObjects());
   }
}
