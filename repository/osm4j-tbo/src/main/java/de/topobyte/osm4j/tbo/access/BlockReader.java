package de.topobyte.osm4j.tbo.access;

import com.slimjars.dist.gnu.trove.map.TIntObjectMap;
import com.slimjars.dist.gnu.trove.map.hash.TIntObjectHashMap;
import de.topobyte.compactio.CompactReader;
import de.topobyte.compactio.CompactWriter;
import de.topobyte.compactio.InputStreamCompactReader;
import de.topobyte.osm4j.tbo.Compression;
import de.topobyte.osm4j.tbo.data.FileBlock;
import de.topobyte.osm4j.tbo.data.FileHeader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class BlockReader {
   protected final CompactReader reader;
   private static TIntObjectMap<Compression> compressions = new TIntObjectHashMap();

   public BlockReader(InputStream input) {
      this(new InputStreamCompactReader(input));
   }

   public BlockReader(CompactReader reader) {
      this.reader = reader;
   }

   public FileHeader parseHeader() throws IOException {
      return ReaderUtil.parseHeader(this.reader);
   }

   public FileBlock readBlock() throws IOException {
      int typeByte = 0;

      try {
         typeByte = this.reader.readByte();
      } catch (EOFException var10) {
         return null;
      }

      int length = (int)this.reader.readVariableLengthUnsignedInteger();
      int lengthMeta = 0;
      int compressionByte = this.reader.readByte();
      lengthMeta++;
      Compression compression = (Compression)compressions.get(compressionByte);
      if (compression == null) {
         throw new IOException("Unsupported compression method");
      } else {
         int uncompressedLength = 0;
         if (compression != Compression.NONE) {
            uncompressedLength = (int)this.reader.readVariableLengthUnsignedInteger();
            lengthMeta += CompactWriter.getNumberOfBytesUnsigned((long)uncompressedLength);
         }

         int numObjects = (int)this.reader.readVariableLengthUnsignedInteger();
         lengthMeta += CompactWriter.getNumberOfBytesUnsigned((long)numObjects);
         int compressedLength = length - lengthMeta;
         if (compression == Compression.NONE) {
            uncompressedLength = compressedLength;
         }

         byte[] buffer = new byte[compressedLength];
         this.reader.readFully(buffer);
         return new FileBlock(typeByte, compression, uncompressedLength, numObjects, buffer, compressedLength);
      }
   }

   static {
      for (Compression compression : Compression.values()) {
         compressions.put(compression.getId(), compression);
      }
   }
}
