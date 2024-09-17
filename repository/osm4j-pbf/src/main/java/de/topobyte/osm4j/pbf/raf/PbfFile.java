package de.topobyte.osm4j.pbf.raf;

import de.topobyte.osm4j.pbf.protobuf.Fileformat;
import de.topobyte.osm4j.pbf.protobuf.Osmformat;
import de.topobyte.osm4j.pbf.util.BlobHeader;
import de.topobyte.osm4j.pbf.util.BlockData;
import de.topobyte.osm4j.pbf.util.PbfUtil;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class PbfFile {
   private RandomAccessFile file;
   private boolean blockIndexInitialized = false;
   private BlockInfo headerBlockInfo;
   private List<BlockInfo> dataBlockInfos = new ArrayList<>();

   public PbfFile(File file) throws FileNotFoundException {
      this(new RandomAccessFile(file, "r"));
   }

   public PbfFile(RandomAccessFile file) {
      this.file = file;
   }

   public void buildBlockIndex() throws IOException {
      this.headerBlockInfo = null;
      this.dataBlockInfos.clear();
      this.file.seek(0L);

      while (true) {
         try {
            long pos = this.file.getFilePointer();
            int headerSize = this.file.readInt();
            BlobHeader header = PbfUtil.parseHeader(this.file, headerSize);
            this.file.skipBytes(header.getDataLength());
            BlockInfo info = new BlockInfo(pos, headerSize, header.getDataLength());
            if (header.getType().equals("OSMData")) {
               this.dataBlockInfos.add(info);
            } else {
               if (!header.getType().equals("OSMHeader")) {
                  throw new IOException("invalid PBF block");
               }

               if (this.headerBlockInfo != null) {
                  throw new IOException("Multiple header blocks");
               }

               this.headerBlockInfo = info;
            }
         } catch (EOFException var6) {
            this.blockIndexInitialized = true;
            return;
         }
      }
   }

   public boolean isBlockIndexInitialized() {
      return this.blockIndexInitialized;
   }

   public boolean hasHeader() {
      return this.headerBlockInfo != null;
   }

   public int getNumberOfDataBlocks() {
      return this.dataBlockInfos.size();
   }

   public BlockInfo getDataBlockInfo(int i) {
      return this.dataBlockInfos.get(i);
   }

   public byte[] getRawHeaderBlockWithHeader() throws IOException {
      return this.getRawBlockWithHeader(this.headerBlockInfo);
   }

   public byte[] getRawDataBlockWithHeader(int i) throws IOException {
      BlockInfo info = this.dataBlockInfos.get(i);
      return this.getRawBlockWithHeader(info);
   }

   private byte[] getRawBlockWithHeader(BlockInfo info) throws IOException {
      this.file.seek(info.getPosition());
      int lengthTotal = info.getLengthHeader() + info.getLengthData();
      byte[] buf = new byte[lengthTotal];
      this.file.readFully(buf);
      return buf;
   }

   public Osmformat.HeaderBlock getHeaderBlock() throws IOException {
      Fileformat.Blob blob = this.getBlockBlob(this.headerBlockInfo);
      BlockData blockData = PbfUtil.getBlockData(blob);
      return Osmformat.HeaderBlock.parseFrom(blockData.getBlobData());
   }

   public BlobHeader getDataBlockHeader(int i) throws IOException {
      BlockInfo info = this.dataBlockInfos.get(i);
      this.file.seek(info.getPosition() + 4L);
      return PbfUtil.parseHeader(this.file, info.getLengthHeader());
   }

   public Fileformat.Blob getDataBlob(int i) throws IOException {
      BlockInfo info = this.dataBlockInfos.get(i);
      return this.getBlockBlob(info);
   }

   public Osmformat.PrimitiveBlock getDataBlock(int i) throws IOException {
      BlockInfo info = this.dataBlockInfos.get(i);
      Fileformat.Blob blob = this.getBlockBlob(info);
      BlockData blockData = PbfUtil.getBlockData(blob);
      return Osmformat.PrimitiveBlock.parseFrom(blockData.getBlobData());
   }

   private Fileformat.Blob getBlockBlob(BlockInfo info) throws IOException {
      this.file.seek(info.getPosition() + 4L + (long)info.getLengthHeader());
      return PbfUtil.parseBlock(this.file, info.getLengthData());
   }
}
