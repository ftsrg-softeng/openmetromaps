package de.topobyte.osm4j.extra.nodearray;

import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.extra.io.ra.BufferedRandomAccessFile;
import de.topobyte.osm4j.extra.io.ra.NormalRandomAccessFile;
import de.topobyte.osm4j.extra.io.ra.RandomAccess;
import java.io.File;
import java.io.IOException;

public class NodeArrayShort implements NodeArray {
   static final short NULL = Coding.SHORT_NULL;
   private RandomAccess f;

   public NodeArrayShort(RandomAccess f) {
      this.f = f;
   }

   public NodeArrayShort(File file) throws IOException {
      this.f = new NormalRandomAccessFile(file);
   }

   public NodeArrayShort(File file, int pageSize, int cacheSize) throws IOException {
      this.f = new BufferedRandomAccessFile(file, pageSize, cacheSize);
   }

   @Override
   public void close() throws IOException {
      this.f.close();
   }

   @Override
   public OsmNode get(long id) throws IOException {
      this.f.seek(id * 4L);
      double lon = Coding.decodeLonFromShort(this.f.readShort());
      double lat = Coding.decodeLatFromShort(this.f.readShort());
      return new Node(id, lon, lat);
   }

   @Override
   public boolean supportsContainment() {
      return true;
   }

   @Override
   public boolean contains(long id) throws IOException {
      this.f.seek(id * 4L);
      short lon = this.f.readShort();
      short lat = this.f.readShort();
      return lon != NULL && lat != NULL;
   }

   @Override
   public int bytesPerRecord() {
      return 4;
   }
}
