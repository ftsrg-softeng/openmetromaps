package de.topobyte.osm4j.tbo.access;

import com.slimjars.dist.gnu.trove.iterator.TLongIterator;
import com.slimjars.dist.gnu.trove.list.TLongList;
import de.topobyte.compactio.CompactReader;
import de.topobyte.compactio.InputStreamCompactReader;
import de.topobyte.osm4j.core.access.OsmIdHandler;
import de.topobyte.osm4j.core.access.OsmIdReader;
import de.topobyte.osm4j.core.access.OsmInputException;
import de.topobyte.osm4j.tbo.data.FileBlock;
import de.topobyte.osm4j.tbo.data.FileHeader;
import de.topobyte.osm4j.tbo.io.Decompression;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TboIdReader extends BlockReader implements OsmIdReader {
   private OsmIdHandler handler;

   public TboIdReader(InputStream is) {
      this(new InputStreamCompactReader(is));
   }

   public TboIdReader(CompactReader reader) {
      super(reader);
   }

   public void setIdHandler(OsmIdHandler handler) {
      this.handler = handler;
   }

   public void read() throws OsmInputException {
      try {
         FileHeader header = ReaderUtil.parseHeader(this.reader);
         if (header.hasBounds()) {
            this.handler.handle(header.getBounds());
         }
      } catch (IOException var6) {
         throw new OsmInputException("error while reading header", var6);
      }

      while (true) {
         FileBlock block;
         try {
            block = this.readBlock();
         } catch (IOException var4) {
            throw new OsmInputException("error while reading block", var4);
         }

         if (block == null) {
            try {
               this.handler.complete();
               return;
            } catch (IOException var3) {
               throw new OsmInputException("error while completing handler", var3);
            }
         }

         try {
            this.parseBlock(block);
         } catch (IOException var5) {
            throw new OsmInputException("error while parsing block", var5);
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
         TLongList nodes = ReaderUtil.parseNodeIds(reader, block);
         TLongIterator iterator = nodes.iterator();

         while (iterator.hasNext()) {
            this.handler.handleNode(iterator.next());
         }
      } else if (block.getType() == 2) {
         TLongList ways = ReaderUtil.parseWayIds(reader, block);
         TLongIterator iterator = ways.iterator();

         while (iterator.hasNext()) {
            this.handler.handleWay(iterator.next());
         }
      } else if (block.getType() == 3) {
         TLongList relations = ReaderUtil.parseRelationIds(reader, block);
         TLongIterator iterator = relations.iterator();

         while (iterator.hasNext()) {
            this.handler.handleRelation(iterator.next());
         }
      }
   }
}
