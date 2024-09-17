package de.topobyte.osm4j.pbf.util;

import com.google.protobuf.ByteString;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.impl.Bounds;
import de.topobyte.osm4j.pbf.Compression;
import de.topobyte.osm4j.pbf.protobuf.Fileformat;
import de.topobyte.osm4j.pbf.protobuf.Osmformat;
import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

public class PbfUtil {
   private static LZ4FastDecompressor lz4Decompressor = null;

   public static Osmformat.HeaderBlock createHeader(String writingProgram, boolean requiresDense, OsmBounds bound) {
      Osmformat.HeaderBlock.Builder headerblock = Osmformat.HeaderBlock.newBuilder();
      if (bound != null) {
         Osmformat.HeaderBBox.Builder bbox = Osmformat.HeaderBBox.newBuilder();
         bbox.setLeft(bboxDegreesToLong(bound.getLeft()));
         bbox.setBottom(bboxDegreesToLong(bound.getBottom()));
         bbox.setRight(bboxDegreesToLong(bound.getRight()));
         bbox.setTop(bboxDegreesToLong(bound.getTop()));
         headerblock.setBbox(bbox);
      }

      headerblock.setWritingprogram(writingProgram);
      headerblock.addRequiredFeatures("OsmSchema-V0.6");
      if (requiresDense) {
         headerblock.addRequiredFeatures("DenseNodes");
      }

      return headerblock.build();
   }

   private static long bboxDegreesToLong(double value) {
      return (long)(value / 1.0E-9);
   }

   public static double bboxLongToDegrees(long value) {
      return (double)value * 1.0E-9;
   }

   public static BlobHeader parseHeader(DataInput input) throws IOException {
      int lengthHeader = input.readInt();

      try {
         return parseHeader(input, lengthHeader);
      } catch (EOFException var3) {
         throw new IOException("Unable to parse blob header", var3);
      }
   }

   public static BlobHeader parseHeader(DataInput input, int lengthHeader) throws IOException {
      byte[] buf = new byte[lengthHeader];
      input.readFully(buf);
      Fileformat.BlobHeader header = Fileformat.BlobHeader.parseFrom(buf);
      return new BlobHeader(header.getType(), header.getDatasize(), header.getIndexdata());
   }

   public static Fileformat.Blob parseBlock(DataInput data, int lengthData) throws IOException {
      byte[] buf = new byte[lengthData];
      data.readFully(buf);
      return Fileformat.Blob.parseFrom(buf);
   }

   private static void initLz4() {
      if (lz4Decompressor == null) {
         LZ4Factory factory = LZ4Factory.fastestInstance();
         lz4Decompressor = factory.fastDecompressor();
      }
   }

   public static BlockData getBlockData(Fileformat.Blob blob) throws IOException {
      ByteString blobData;
      Compression compression;
      if (blob.hasRaw()) {
         compression = Compression.NONE;
         blobData = blob.getRaw();
      } else if (blob.hasZlibData()) {
         compression = Compression.DEFLATE;
         byte[] uncompressed = new byte[blob.getRawSize()];
         Inflater decompresser = new Inflater();
         decompresser.setInput(blob.getZlibData().toByteArray());

         try {
            decompresser.inflate(uncompressed);
         } catch (DataFormatException var6) {
            throw new IOException("Error while decompressing gzipped data", var6);
         }

         decompresser.end();
         blobData = ByteString.copyFrom(uncompressed);
      } else {
         if (!blob.hasLz4Data()) {
            throw new IOException("Encountered block without data");
         }

         compression = Compression.LZ4;
         byte[] uncompressed = new byte[blob.getRawSize()];
         initLz4();
         lz4Decompressor.decompress(blob.getLz4Data().toByteArray(), 0, uncompressed, 0, blob.getRawSize());
         blobData = ByteString.copyFrom(uncompressed);
      }

      return new BlockData(blobData, compression);
   }

   public static OsmBounds bounds(Osmformat.HeaderBBox bbox) {
      double left = bboxLongToDegrees(bbox.getLeft());
      double right = bboxLongToDegrees(bbox.getRight());
      double top = bboxLongToDegrees(bbox.getTop());
      double bottom = bboxLongToDegrees(bbox.getBottom());
      return new Bounds(left, right, top, bottom);
   }
}
