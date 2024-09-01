package de.topobyte.osm4j.tbo.access;

import com.slimjars.dist.gnu.trove.list.TLongList;
import de.topobyte.compactio.CompactReader;
import de.topobyte.compactio.InputStreamCompactReader;
import de.topobyte.osm4j.core.access.OsmIdIterator;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.IdContainer;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.tbo.data.FileBlock;
import de.topobyte.osm4j.tbo.data.FileHeader;
import de.topobyte.osm4j.tbo.io.Decompression;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class TboIdIterator extends BlockReader implements OsmIdIterator {
   private FileHeader header;
   private int available = 0;
   private int pointer = 0;
   private boolean valid = true;
   private FileBlock block = null;
   private EntityType entityType = EntityType.Node;
   private TLongList ids = null;

   public TboIdIterator(InputStream input) throws IOException {
      this(new InputStreamCompactReader(input));
   }

   public TboIdIterator(CompactReader reader) throws IOException {
      super(reader);
      this.header = ReaderUtil.parseHeader(reader);
   }

   public boolean hasNext() {
      if (this.available == 0) {
         try {
            this.advanceBlock();
         } catch (IOException var2) {
            return false;
         }
      }

      return this.valid && this.available > 0;
   }

   public IdContainer next() {
      long id = this.ids.get(this.pointer);
      this.pointer++;
      this.available--;
      return new IdContainer(this.entityType, id);
   }

   public void remove() {
   }

   private void advanceBlock() throws IOException {
      this.block = this.readBlock();
      if (this.block == null) {
         this.valid = false;
      } else {
         this.pointer = 0;
         this.available = this.block.getNumObjects();
         byte[] uncompressed = Decompression.decompress(this.block);
         ByteArrayInputStream bais = new ByteArrayInputStream(uncompressed);
         CompactReader reader = new InputStreamCompactReader(bais);
         switch (this.block.getType()) {
            case 1:
               this.entityType = EntityType.Node;
               this.ids = ReaderUtil.parseNodeIds(reader, this.block);
               break;
            case 2:
               this.entityType = EntityType.Way;
               this.ids = ReaderUtil.parseWayIds(reader, this.block);
               break;
            case 3:
               this.entityType = EntityType.Relation;
               this.ids = ReaderUtil.parseRelationIds(reader, this.block);
         }
      }
   }

   public Iterator<IdContainer> iterator() {
      return this;
   }

   public boolean hasBounds() {
      return this.header.hasBounds();
   }

   public OsmBounds getBounds() {
      return this.header.getBounds();
   }
}
