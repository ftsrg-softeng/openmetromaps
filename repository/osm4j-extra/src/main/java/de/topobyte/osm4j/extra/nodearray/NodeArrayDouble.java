package de.topobyte.osm4j.extra.nodearray;

import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.extra.io.ra.BufferedRandomAccessFile;
import de.topobyte.osm4j.extra.io.ra.NormalRandomAccessFile;
import de.topobyte.osm4j.extra.io.ra.RandomAccess;
import java.io.File;
import java.io.IOException;

public class NodeArrayDouble implements NodeArray {
   static final long NULL = Long.MAX_VALUE;
   private RandomAccess f;

   public NodeArrayDouble(RandomAccess f) {
      this.f = f;
   }

   public NodeArrayDouble(File file) throws IOException {
      this.f = new NormalRandomAccessFile(file);
   }

   public NodeArrayDouble(File file, int pageSize, int cacheSize) throws IOException {
      this.f = new BufferedRandomAccessFile(file, pageSize, cacheSize);
   }

   @Override
   public void close() throws IOException {
      this.f.close();
   }

   @Override
   public OsmNode get(long id) throws IOException {
      this.f.seek(id * 16L);
      double lon = this.f.readDouble();
      double lat = this.f.readDouble();
      return new Node(id, lon, lat);
   }

   @Override
   public boolean supportsContainment() {
      return true;
   }

   @Override
   public boolean contains(long id) throws IOException {
      this.f.seek(id * 16L);
      long lon = this.f.readLong();
      long lat = this.f.readLong();
      return lon != Long.MAX_VALUE && lat != Long.MAX_VALUE;
   }

   @Override
   public int bytesPerRecord() {
      return 16;
   }
}
