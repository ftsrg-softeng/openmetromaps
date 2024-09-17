package de.topobyte.osm4j.tbo.access;

import de.topobyte.compactio.CompactWriter;
import de.topobyte.compactio.OutputStreamCompactWriter;
import de.topobyte.osm4j.tbo.ByteArrayOutputStream;
import de.topobyte.osm4j.tbo.Compression;
import de.topobyte.osm4j.tbo.data.FileBlock;
import de.topobyte.osm4j.tbo.writerhelper.Blockable;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;

public class BlockableWriter {
   private ByteArrayOutputStream baos;
   private boolean lowMemoryFootprint;
   private LZ4Compressor lz4Compressor = null;

   public BlockableWriter(boolean lowMemoryFootprint) {
      this.lowMemoryFootprint = lowMemoryFootprint;
      if (!lowMemoryFootprint) {
         this.baos = new ByteArrayOutputStream();
      }
   }

   public FileBlock writeBlock(Blockable blockable, int type, int count, Compression compression) throws IOException {
      if (this.lowMemoryFootprint) {
         this.baos = new ByteArrayOutputStream();
      } else {
         this.baos.reset();
      }

      CompactWriter bufferWriter = new OutputStreamCompactWriter(this.baos);
      blockable.write(bufferWriter);
      byte[] uncompressed = this.baos.toByteArray();
      byte[] compressed = null;
      int length = 0;
      this.baos.reset();
      switch (compression) {
         case NONE:
         default:
            compressed = uncompressed;
            length = uncompressed.length;
            break;
         case DEFLATE:
            DeflaterOutputStream out = new DeflaterOutputStream(this.baos);
            out.write(uncompressed);
            out.close();
            compressed = this.baos.toByteArray();
            length = compressed.length;
            break;
         case LZ4:
            this.initLz4();
            int estimate = this.lz4Compressor.maxCompressedLength(uncompressed.length);
            compressed = new byte[estimate];
            length = this.lz4Compressor.compress(uncompressed, compressed);
      }

      FileBlock block = new FileBlock(type, compression, uncompressed.length, count, compressed, length);
      if (this.lowMemoryFootprint) {
         this.baos = null;
      }

      return block;
   }

   private void initLz4() {
      if (this.lz4Compressor == null) {
         LZ4Factory factory = LZ4Factory.fastestInstance();
         this.lz4Compressor = factory.fastCompressor();
      }
   }
}
