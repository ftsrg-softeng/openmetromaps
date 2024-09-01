package de.topobyte.osm4j.tbo.io;

import de.topobyte.osm4j.tbo.data.FileBlock;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

public class Decompression {
   private static LZ4FastDecompressor lz4Decompressor = null;

   public static byte[] decompress(FileBlock block) throws IOException {
      byte[] compressed = block.getBuffer();
      byte[] uncompressed;
      switch (block.getCompression()) {
         case NONE:
         default:
            uncompressed = compressed;
            break;
         case DEFLATE:
            uncompressed = new byte[block.getUncompressedLength()];
            Inflater decompresser = new Inflater();
            decompresser.setInput(compressed);

            try {
               decompresser.inflate(uncompressed);
            } catch (DataFormatException var5) {
               throw new IOException("Error while decompressing gzipped data", var5);
            }

            decompresser.end();
            break;
         case LZ4:
            uncompressed = new byte[block.getUncompressedLength()];
            initLz4();
            lz4Decompressor.decompress(compressed, uncompressed);
      }

      return uncompressed;
   }

   private static void initLz4() {
      if (lz4Decompressor == null) {
         LZ4Factory factory = LZ4Factory.fastestInstance();
         lz4Decompressor = factory.fastDecompressor();
      }
   }
}
