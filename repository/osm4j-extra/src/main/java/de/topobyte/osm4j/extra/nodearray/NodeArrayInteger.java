package de.topobyte.osm4j.extra.nodearray;

import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.extra.io.ra.BufferedRandomAccessFile;
import de.topobyte.osm4j.extra.io.ra.NormalRandomAccessFile;
import de.topobyte.osm4j.extra.io.ra.RandomAccess;
import java.io.File;
import java.io.IOException;

public class NodeArrayInteger implements NodeArray {
   static final int NULL = Coding.INT_NULL;
   private RandomAccess f;

   public NodeArrayInteger(RandomAccess f) {
      this.f = f;
   }

   public NodeArrayInteger(File file) throws IOException {
      this.f = new NormalRandomAccessFile(file);
   }

   public NodeArrayInteger(File file, int pageSize, int cacheSize) throws IOException {
      this.f = new BufferedRandomAccessFile(file, pageSize, cacheSize);
   }

   @Override
   public void close() throws IOException {
      this.f.close();
   }

   @Override
   public OsmNode get(long id) throws IOException {
      this.f.seek(id * 8L);
      double lon = Coding.decodeLonFromInt(this.f.readInt());
      double lat = Coding.decodeLatFromInt(this.f.readInt());
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
      return lon != NULL && lat != NULL;
   }

   @Override
   public int bytesPerRecord() {
      return 8;
   }
}
