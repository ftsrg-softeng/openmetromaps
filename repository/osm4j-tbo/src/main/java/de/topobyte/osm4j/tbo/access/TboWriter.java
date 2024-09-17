package de.topobyte.osm4j.tbo.access;

import de.topobyte.compactio.CompactWriter;
import de.topobyte.compactio.OutputStreamCompactWriter;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.tbo.Compression;
import de.topobyte.osm4j.tbo.batching.BatchBuilder;
import de.topobyte.osm4j.tbo.batching.ElementCountBatchBuilder;
import de.topobyte.osm4j.tbo.batching.MemberCountBatchBuilder;
import de.topobyte.osm4j.tbo.batching.WayNodeCountBatchBuilder;
import de.topobyte.osm4j.tbo.data.FileBlock;
import de.topobyte.osm4j.tbo.data.FileHeader;
import de.topobyte.osm4j.tbo.writerhelper.NodeBatch;
import de.topobyte.osm4j.tbo.writerhelper.RelationBatch;
import de.topobyte.osm4j.tbo.writerhelper.WayBatch;
import java.io.IOException;
import java.io.OutputStream;

public class TboWriter implements OsmOutputStream {
   private BatchBuilder<OsmNode> batchBuilderNodes = new ElementCountBatchBuilder<>(4096);
   private BatchBuilder<OsmWay> batchBuilderWays = new WayNodeCountBatchBuilder(6144);
   private BatchBuilder<OsmRelation> batchBuilderRelations = new MemberCountBatchBuilder(8192);
   private BlockWriter blockWriter;
   private BlockableWriter blockableWriter;
   private Compression compression = Compression.NONE;
   private boolean writeMetadata;
   private NodeBatch nodeBatch;
   private WayBatch wayBatch;
   private RelationBatch relationBatch;
   private TboWriter.Mode mode = TboWriter.Mode.HEADER;
   private FileHeader header = null;

   public TboWriter(OutputStream output, boolean writeMetadata) {
      this(new OutputStreamCompactWriter(output), writeMetadata);
   }

   public TboWriter(OutputStream output, boolean writeMetadata, boolean lowMemoryFootPrint) {
      this(new OutputStreamCompactWriter(output), writeMetadata, lowMemoryFootPrint);
   }

   public TboWriter(CompactWriter writer, boolean writeMetadata) {
      this(writer, writeMetadata, false);
   }

   public TboWriter(CompactWriter writer, boolean writeMetadata, boolean lowMemoryFootPrint) {
      this(new DefaultBlockWriter(writer), writeMetadata, lowMemoryFootPrint);
   }

   public TboWriter(BlockWriter blockWriter, boolean writeMetadata, boolean lowMemoryFootPrint) {
      this(blockWriter, new BlockableWriter(lowMemoryFootPrint), writeMetadata);
   }

   public TboWriter(BlockWriter blockWriter, BlockableWriter blockableWriter, boolean writeMetadata) {
      this.blockWriter = blockWriter;
      this.blockableWriter = blockableWriter;
      this.writeMetadata = writeMetadata;
      this.nodeBatch = new NodeBatch(writeMetadata);
      this.wayBatch = new WayBatch(writeMetadata);
      this.relationBatch = new RelationBatch(writeMetadata);
   }

   public Compression getCompression() {
      return this.compression;
   }

   public void setCompression(Compression compression) {
      this.compression = compression;
   }

   public boolean isWriteMetadata() {
      return this.writeMetadata;
   }

   public void setWriteMetadata(boolean writeMetadata) {
      this.writeMetadata = writeMetadata;
   }

   public void setBatchSizeByElementCount(int batchSize) {
      this.batchBuilderNodes = new ElementCountBatchBuilder<>(batchSize);
      this.batchBuilderWays = new ElementCountBatchBuilder<>(batchSize);
      this.batchBuilderRelations = new ElementCountBatchBuilder<>(batchSize);
   }

   public void setBatchSizeByElementCount(int batchSizeNodes, int batchSizeWays, int batchSizeRelations) {
      this.batchBuilderNodes = new ElementCountBatchBuilder<>(batchSizeNodes);
      this.batchBuilderWays = new ElementCountBatchBuilder<>(batchSizeWays);
      this.batchBuilderRelations = new ElementCountBatchBuilder<>(batchSizeRelations);
   }

   public void setBatchSizeNodesByElementCount(int batchSize) {
      this.batchBuilderNodes = new ElementCountBatchBuilder<>(batchSize);
   }

   public void setBatchSizeWaysByElementCount(int batchSize) {
      this.batchBuilderWays = new ElementCountBatchBuilder<>(batchSize);
   }

   public void setBatchSizeRelationsByElementCount(int batchSize) {
      this.batchBuilderRelations = new ElementCountBatchBuilder<>(batchSize);
   }

   public void setBatchSizeWaysByNodes(int batchSize) {
      this.batchBuilderWays = new WayNodeCountBatchBuilder(batchSize);
   }

   public void setBatchSizeRelationsByMembers(int batchSize) {
      this.batchBuilderRelations = new MemberCountBatchBuilder(batchSize);
   }

   public void writeHeader(FileHeader header) throws IOException {
      this.header = header;
   }

   public void write(OsmBounds bounds) throws IOException {
      this.writeBounds(bounds);
   }

   public void write(OsmNode node) throws IOException {
      this.writeNode(node);
   }

   public void write(OsmWay way) throws IOException {
      this.writeWay(way);
   }

   public void write(OsmRelation relation) throws IOException {
      this.writeRelation(relation);
   }

   private void writeBounds(OsmBounds bounds) throws IOException {
      if (this.mode != TboWriter.Mode.HEADER) {
         throw new RuntimeException("wrong entity order while processing bounds");
      } else {
         if (this.header == null) {
            this.header = WriterUtil.createHeader(this.writeMetadata, bounds);
         } else if (!this.header.hasBounds()) {
            this.header.setBounds(bounds);
         }

         this.finishHeader();
      }
   }

   private void writeNode(OsmNode node) throws IOException {
      if (this.mode != TboWriter.Mode.NODE) {
         if (this.mode != TboWriter.Mode.HEADER) {
            throw new RuntimeException("wrong entity order while processing node");
         }

         this.finishHeader();
      }

      this.nodeBatch.put(node);
      if (checkBatch(node, this.batchBuilderNodes)) {
         this.writeNodeBatch();
      }
   }

   private void writeWay(OsmWay way) throws IOException {
      if (this.mode != TboWriter.Mode.WAY) {
         if (this.mode == TboWriter.Mode.HEADER) {
            this.finishHeader();
         } else {
            if (this.mode != TboWriter.Mode.NODE) {
               throw new RuntimeException("wrong entity order while processing way");
            }

            this.finishNodes();
         }
      }

      this.wayBatch.put(way);
      if (checkBatch(way, this.batchBuilderWays)) {
         this.writeWayBatch();
      }
   }

   private void writeRelation(OsmRelation relation) throws IOException {
      if (this.mode != TboWriter.Mode.RELATION) {
         if (this.mode == TboWriter.Mode.HEADER) {
            this.finishHeader();
         } else if (this.mode == TboWriter.Mode.NODE) {
            this.finishNodes();
            this.finishWays();
         } else if (this.mode == TboWriter.Mode.WAY) {
            this.finishWays();
         }
      }

      this.relationBatch.put(relation);
      if (checkBatch(relation, this.batchBuilderRelations)) {
         this.writeRelationBatch();
      }
   }

   private static <T extends OsmEntity> boolean checkBatch(T element, BatchBuilder<T> batchBuilder) throws IOException {
      batchBuilder.add(element);
      boolean full = batchBuilder.full();
      if (full) {
         batchBuilder.clear();
      }

      return full;
   }

   private void writeNodeBatch() throws IOException {
      FileBlock block = this.blockableWriter.writeBlock(this.nodeBatch, 1, this.nodeBatch.size(), this.compression);
      this.blockWriter.writeBlock(block);
      this.nodeBatch.clear();
   }

   private void writeWayBatch() throws IOException {
      FileBlock block = this.blockableWriter.writeBlock(this.wayBatch, 2, this.wayBatch.size(), this.compression);
      this.blockWriter.writeBlock(block);
      this.wayBatch.clear();
   }

   private void writeRelationBatch() throws IOException {
      FileBlock block = this.blockableWriter.writeBlock(this.relationBatch, 3, this.relationBatch.size(), this.compression);
      this.blockWriter.writeBlock(block);
      this.relationBatch.clear();
   }

   public void complete() throws IOException {
      this.finishHeader();
      this.finishNodes();
      this.finishWays();
      this.finishRelations();
   }

   private void finishHeader() throws IOException {
      if (this.mode == TboWriter.Mode.HEADER) {
         if (this.header == null) {
            this.header = WriterUtil.createHeader(this.writeMetadata, null);
         }

         this.blockWriter.writeHeader(this.header);
         this.mode = TboWriter.Mode.NODE;
      }
   }

   private void finishNodes() throws IOException {
      if (this.mode == TboWriter.Mode.NODE) {
         if (this.nodeBatch.size() > 0) {
            this.writeNodeBatch();
         }

         this.mode = TboWriter.Mode.WAY;
      }
   }

   private void finishWays() throws IOException {
      if (this.mode == TboWriter.Mode.WAY) {
         if (this.wayBatch.size() > 0) {
            this.writeWayBatch();
         }

         this.mode = TboWriter.Mode.RELATION;
      }
   }

   private void finishRelations() throws IOException {
      if (this.mode == TboWriter.Mode.RELATION && this.relationBatch.size() > 0) {
         this.writeRelationBatch();
      }
   }

   private static enum Mode {
      HEADER,
      NODE,
      WAY,
      RELATION;
   }
}
