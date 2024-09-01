package de.topobyte.osm4j.extra.nodearray;

import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.extra.io.ra.BufferedRandomAccessFile;
import de.topobyte.osm4j.extra.io.ra.NormalRandomAccessFile;
import de.topobyte.osm4j.extra.io.ra.RandomAccess;
import java.io.File;
import java.io.IOException;

public class NodeArrayFloat implements NodeArray {
   static final int NULL = Integer.MAX_VALUE;
   private RandomAccess f;

   public NodeArrayFloat(RandomAccess f) {
      this.f = f;
   }

   public NodeArrayFloat(File file) throws IOException {
      this.f = new NormalRandomAccessFile(file);
   }

   public NodeArrayFloat(File file, int pageSize, int cacheSize) throws IOException {
      this.f = new BufferedRandomAccessFile(file, pageSize, cacheSize);
   }

   @Override
   public void close() throws IOException {
      this.f.close();
   }

   @Override
   public OsmNode get(long id) throws IOException {
      this.f.seek(id * 8L);
      double lon = (double)this.f.readFloat();
      double lat = (double)this.f.readFloat();
      return new Node(id, lon, lat);
   }

   @Override
   public boolean supportsContainment() {
      return true;
   }

   @Override
   public boolean contains(long id) throws IOException {
      this.f.seek(id * 8L);
      int lon = this.f.readInt();
      int lat = this.f.readInt();
      return lon != Integer.MAX_VALUE && lat != Integer.MAX_VALUE;
   }

   @Override
   public int bytesPerRecord() {
      return 8;
   }
}
