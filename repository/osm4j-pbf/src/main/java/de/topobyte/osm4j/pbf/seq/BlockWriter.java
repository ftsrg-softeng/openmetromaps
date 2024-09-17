package de.topobyte.osm4j.pbf.seq;

import com.google.protobuf.ByteString;
import de.topobyte.osm4j.pbf.Compression;
import de.topobyte.osm4j.pbf.protobuf.Fileformat;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.zip.Deflater;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;

public class BlockWriter {
   private DataOutputStream output;
   private LZ4Compressor lz4Compressor = null;

   public BlockWriter(OutputStream output) {
      this.output = new DataOutputStream(output);
   }

   public void write(String type, ByteString indexData, Compression compression, ByteString data) throws IOException {
      Fileformat.Blob.Builder blobBuilder = Fileformat.Blob.newBuilder();
      switch (compression) {
         case NONE:
         default:
            blobBuilder.setRaw(data);
            blobBuilder.setRawSize(data.size());
            break;
         case DEFLATE: {
            blobBuilder.setRawSize(data.size());
            ByteArray compressed = this.deflate(data);
            ByteString zlibData = ByteString.copyFrom(compressed.getData(), 0, compressed.getLength());
            blobBuilder.setZlibData(zlibData);
            break;
         }
         case LZ4: {
            blobBuilder.setRawSize(data.size());
            ByteArray compressed = this.lz4(data);
            ByteString lz4Data = ByteString.copyFrom(compressed.getData(), 0, compressed.getLength());
            blobBuilder.setLz4Data(lz4Data);
         }
      }

      Fileformat.Blob blob = blobBuilder.build();
      this.write(type, indexData, blob);
   }

   public void write(String type, ByteString indexData, Fileformat.Blob blob) throws IOException {
      Fileformat.BlobHeader.Builder headerBuilder = Fileformat.BlobHeader.newBuilder();
      if (indexData != null) {
         headerBuilder.setIndexdata(indexData);
      }

      headerBuilder.setType(type);
      headerBuilder.setDatasize(blob.getSerializedSize());
      Fileformat.BlobHeader header = headerBuilder.build();
      int size = header.getSerializedSize();
      this.output.writeInt(size);
      header.writeTo(this.output);
      blob.writeTo(this.output);
   }

   protected ByteArray deflate(ByteString data) {
      int size = data.size();
      Deflater deflater = new Deflater();
      deflater.setInput(data.toByteArray());
      deflater.finish();
      byte[] out = new byte[size];
      deflater.deflate(out);
      if (!deflater.finished()) {
         out = Arrays.copyOf(out, size + size / 64 + 16);
         deflater.deflate(out, deflater.getTotalOut(), out.length - deflater.getTotalOut());
         if (!deflater.finished()) {
            throw new Error("Internal error in compressor");
         }
      }

      int length = deflater.getTotalOut();
      deflater.end();
      return new ByteArray(out, length);
   }

   private void initLz4() {
      if (this.lz4Compressor == null) {
         LZ4Factory factory = LZ4Factory.fastestInstance();
         this.lz4Compressor = factory.fastCompressor();
      }
   }

   protected ByteArray lz4(ByteString data) {
      this.initLz4();
      int size = data.size();
      int estimate = this.lz4Compressor.maxCompressedLength(size);
      byte[] out = new byte[estimate];
      int length = this.lz4Compressor.compress(data.toByteArray(), 0, size, out, 0, estimate);
      return new ByteArray(out, length);
   }
}
