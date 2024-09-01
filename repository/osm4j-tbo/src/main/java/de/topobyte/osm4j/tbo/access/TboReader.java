package de.topobyte.osm4j.tbo.access;

import de.topobyte.compactio.CompactReader;
import de.topobyte.compactio.InputStreamCompactReader;
import de.topobyte.osm4j.core.access.OsmHandler;
import de.topobyte.osm4j.core.access.OsmInputException;
import de.topobyte.osm4j.core.access.OsmReader;
import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.core.model.impl.Relation;
import de.topobyte.osm4j.core.model.impl.Way;
import de.topobyte.osm4j.tbo.data.FileBlock;
import de.topobyte.osm4j.tbo.data.FileHeader;
import de.topobyte.osm4j.tbo.io.Decompression;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TboReader extends BlockReader implements OsmReader {
   private boolean fetchTags;
   private boolean hasMetadata;
   private boolean fetchMetadata;
   private OsmHandler handler;

   public TboReader(InputStream is, boolean fetchTags, boolean fetchMetadata) {
      this(new InputStreamCompactReader(is), fetchTags, fetchMetadata);
   }

   public TboReader(CompactReader reader, boolean fetchTags, boolean fetchMetadata) {
      super(reader);
      this.fetchTags = fetchTags;
      this.fetchMetadata = fetchMetadata;
   }

   public void setHandler(OsmHandler handler) {
      this.handler = handler;
   }

   public void read() throws OsmInputException {
      try {
         FileHeader header = ReaderUtil.parseHeader(this.reader);
         if (header.hasBounds()) {
            this.handler.handle(header.getBounds());
         }

         this.hasMetadata = header.hasMetadata();
      } catch (IOException var15) {
         throw new OsmInputException("error while reading header", var15);
      }

      long notifysize = 104857600L;
      long processed = 0L;
      long lastMessage = 0L;

      while (true) {
         FileBlock block;
         try {
            block = this.readBlock();
         } catch (IOException var13) {
            throw new OsmInputException("error while reading block", var13);
         }

         if (block == null) {
            try {
               this.handler.complete();
               return;
            } catch (IOException var12) {
               throw new OsmInputException("error while completing handler", var12);
            }
         }

         int blockSize = 3 + block.getBuffer().length;
         processed += (long)blockSize;
         long message = processed / notifysize;
         if (message > lastMessage) {
            lastMessage = message;
            System.err.println(String.format("%.3f MiB", (double)processed / 1024.0 / 1024.0));
         }

         try {
            this.parseBlock(block);
         } catch (IOException var14) {
            throw new OsmInputException("error while parsing block", var14);
         }
      }
   }

   private void parseBlock(FileBlock block) throws IOException {
      byte[] uncompressed = Decompression.decompress(block);
      ByteArrayInputStream bais = new ByteArrayInputStream(uncompressed);
      CompactReader compactReader = new InputStreamCompactReader(bais);
      this.parseBlock(compactReader, block);
   }

   private void parseBlock(CompactReader reader, FileBlock block) throws IOException {
      if (block.getType() == 1) {
         for (Node node : ReaderUtil.parseNodes(reader, block, this.fetchTags, this.hasMetadata, this.fetchMetadata)) {
            this.handler.handle(node);
         }
      } else if (block.getType() == 2) {
         for (Way way : ReaderUtil.parseWays(reader, block, this.fetchTags, this.hasMetadata, this.fetchMetadata)) {
            this.handler.handle(way);
         }
      } else if (block.getType() == 3) {
         for (Relation relation : ReaderUtil.parseRelations(reader, block, this.fetchTags, this.hasMetadata, this.fetchMetadata)) {
            this.handler.handle(relation);
         }
      }
   }
}
