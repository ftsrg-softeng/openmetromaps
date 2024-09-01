package de.topobyte.osm4j.tbo.access;

import de.topobyte.compactio.CompactReader;
import de.topobyte.compactio.InputStreamCompactReader;
import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.core.model.impl.Relation;
import de.topobyte.osm4j.core.model.impl.Way;
import de.topobyte.osm4j.tbo.data.FileBlock;
import de.topobyte.osm4j.tbo.data.FileHeader;
import de.topobyte.osm4j.tbo.io.Decompression;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Reader extends BlockReader {
   private boolean fetchTags;
   private boolean hasMetadata;
   private boolean fetchMetadata;
   private final Handler handler;

   public Reader(InputStream is, Handler handler, boolean fetchTags, boolean fetchMetadata) {
      this(new InputStreamCompactReader(is), handler, fetchTags, fetchMetadata);
   }

   public Reader(CompactReader reader, Handler handler, boolean fetchTags, boolean fetchMetadata) {
      super(reader);
      this.handler = handler;
      this.fetchTags = fetchTags;
      this.fetchMetadata = fetchMetadata;
   }

   public void run() throws IOException {
      FileHeader header = ReaderUtil.parseHeader(this.reader);
      this.hasMetadata = header.hasMetadata();
      this.handler.handle(header);
      long notifysize = 104857600L;
      long processed = 0L;
      long lastMessage = 0L;

      while (true) {
         FileBlock block = this.readBlock();
         if (block == null) {
            this.handler.complete();
            return;
         }

         int blockSize = 3 + block.getBuffer().length;
         processed += (long)blockSize;
         long message = processed / notifysize;
         if (message > lastMessage) {
            lastMessage = message;
            System.err.println(String.format("%.3f MiB", (double)processed / 1024.0 / 1024.0));
         }

         this.parseBlock(block);
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
