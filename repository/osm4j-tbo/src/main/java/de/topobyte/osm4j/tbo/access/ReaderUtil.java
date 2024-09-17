package de.topobyte.osm4j.tbo.access;

import com.slimjars.dist.gnu.trove.list.TLongList;
import com.slimjars.dist.gnu.trove.list.array.TLongArrayList;
import de.topobyte.compactio.CompactReader;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmMetadata;
import de.topobyte.osm4j.core.model.impl.Bounds;
import de.topobyte.osm4j.core.model.impl.Entity;
import de.topobyte.osm4j.core.model.impl.Metadata;
import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.core.model.impl.Relation;
import de.topobyte.osm4j.core.model.impl.RelationMember;
import de.topobyte.osm4j.core.model.impl.Tag;
import de.topobyte.osm4j.core.model.impl.Way;
import de.topobyte.osm4j.tbo.data.BlockMetadataInfo;
import de.topobyte.osm4j.tbo.data.FileBlock;
import de.topobyte.osm4j.tbo.data.FileHeader;
import de.topobyte.osm4j.tbo.writerhelper.EntityTypeHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ReaderUtil {
   public static FileHeader parseHeader(CompactReader reader) throws IOException {
      byte[] magic = new byte[FileHeader.MAGIC.length];
      reader.readFully(magic);
      if (!Arrays.equals(magic, FileHeader.MAGIC)) {
         throw new IOException("Not a TBO file: wrong magic code");
      } else {
         int version = (int)reader.readVariableLengthUnsignedInteger();
         Map<String, String> tags = new TreeMap<>();
         int numTags = (int)reader.readVariableLengthUnsignedInteger();

         for (int i = 0; i < numTags; i++) {
            String key = reader.readString();
            String value = reader.readString();
            tags.put(key, value);
         }

         int flags = reader.readByte();
         boolean hasMetadata = (flags & 1) != 0;
         boolean hasBounds = (flags & 2) != 0;
         OsmBounds bounds = null;
         if (hasBounds) {
            double left = Double.longBitsToDouble(reader.readLong());
            double right = Double.longBitsToDouble(reader.readLong());
            double bottom = Double.longBitsToDouble(reader.readLong());
            double top = Double.longBitsToDouble(reader.readLong());
            bounds = new Bounds(left, right, top, bottom);
         }

         return new FileHeader(version, tags, hasMetadata, bounds);
      }
   }

   private static List<String> parsePool(CompactReader reader) throws IOException {
      List<String> pool = new ArrayList<>();
      long size = reader.readVariableLengthUnsignedInteger();

      for (int i = 0; (long)i < size; i++) {
         String string = reader.readString();
         pool.add(string);
      }

      return pool;
   }

   private static double fromLong(long value) {
      return (double)value * 1.0E-7;
   }

   private static List<Tag> parseTags(CompactReader reader, List<String> pool) throws IOException {
      int num = (int)reader.readVariableLengthUnsignedInteger();
      List<Tag> tags = new ArrayList<>();

      for (int i = 0; i < num; i++) {
         int k = (int)reader.readVariableLengthUnsignedInteger();
         int v = (int)reader.readVariableLengthUnsignedInteger();
         String key = pool.get(k);
         String value = pool.get(v);
         Tag tag = new Tag(key, value);
         tags.add(tag);
      }

      return tags;
   }

   public static List<Node> parseNodes(CompactReader reader, FileBlock block, boolean fetchTags, boolean hasMetadata, boolean fetchMetadata) throws IOException {
      long len = reader.readVariableLengthUnsignedInteger();
      List<String> poolTags = null;
      if (fetchTags) {
         poolTags = parsePool(reader);
      } else {
         reader.skip(len);
      }

      int n = block.getNumObjects();
      List<Node> nodes = new ArrayList<>(n);
      long idOffset = 0L;
      long latOffset = 0L;
      long lonOffset = 0L;
      long[] ids = new long[n];
      double[] lats = new double[n];
      double[] lons = new double[n];
      reader.readVariableLengthUnsignedInteger();

      for (int i = 0; i < n; i++) {
         long id = idOffset + reader.readVariableLengthSignedInteger();
         idOffset = id;
         ids[i] = id;
      }

      reader.readVariableLengthUnsignedInteger();

      for (int i = 0; i < n; i++) {
         long mlat = latOffset + reader.readVariableLengthSignedInteger();
         long mlon = lonOffset + reader.readVariableLengthSignedInteger();
         lats[i] = fromLong(mlat);
         lons[i] = fromLong(mlon);
         latOffset = mlat;
         lonOffset = mlon;
      }

      for (int i = 0; i < n; i++) {
         Node node = new Node(ids[i], lons[i], lats[i]);
         nodes.add(node);
      }

      len = reader.readVariableLengthUnsignedInteger();
      if (fetchTags) {
         for (int i = 0; i < n; i++) {
            List<Tag> tags = parseTags(reader, poolTags);
            nodes.get(i).setTags(tags);
         }
      } else {
         reader.skip(len);
      }

      if (hasMetadata && fetchMetadata) {
         reader.readVariableLengthUnsignedInteger();
         parseMetadata(reader, nodes);
      }

      return nodes;
   }

   public static TLongList parseNodeIds(CompactReader reader, FileBlock block) throws IOException {
      long len = reader.readVariableLengthUnsignedInteger();
      reader.skip(len);
      long idOffset = 0L;
      int n = block.getNumObjects();
      TLongList ids = new TLongArrayList(n);
      reader.readVariableLengthUnsignedInteger();

      for (int i = 0; i < n; i++) {
         long id = idOffset + reader.readVariableLengthSignedInteger();
         ids.add(id);
         idOffset = id;
      }

      return ids;
   }

   public static List<Way> parseWays(CompactReader reader, FileBlock block, boolean fetchTags, boolean hasMetadata, boolean fetchMetadata) throws IOException {
      long len = reader.readVariableLengthUnsignedInteger();
      List<String> poolTags = null;
      if (fetchTags) {
         poolTags = parsePool(reader);
      } else {
         reader.skip(len);
      }

      int n = block.getNumObjects();
      List<Way> ways = new ArrayList<>(n);
      long idOffset = 0L;
      long nidOffset = 0L;
      long[] ids = new long[n];
      reader.readVariableLengthUnsignedInteger();

      for (int i = 0; i < n; i++) {
         long id = idOffset + reader.readVariableLengthSignedInteger();
         idOffset = id;
         ids[i] = id;
      }

      reader.readVariableLengthUnsignedInteger();

      for (int i = 0; i < n; i++) {
         TLongArrayList nodeIds = new TLongArrayList();
         long numNodes = reader.readVariableLengthUnsignedInteger();

         for (int k = 0; (long)k < numNodes; k++) {
            long nid = nidOffset + reader.readVariableLengthSignedInteger();
            nodeIds.add(nid);
            nidOffset = nid;
         }

         Way way = new Way(ids[i], nodeIds);
         ways.add(way);
      }

      len = reader.readVariableLengthUnsignedInteger();
      if (fetchTags) {
         for (int i = 0; i < n; i++) {
            List<Tag> tags = parseTags(reader, poolTags);
            ways.get(i).setTags(tags);
         }
      } else {
         reader.skip(len);
      }

      if (hasMetadata && fetchMetadata) {
         reader.readVariableLengthUnsignedInteger();
         parseMetadata(reader, ways);
      }

      return ways;
   }

   public static TLongList parseWayIds(CompactReader reader, FileBlock block) throws IOException {
      long len = reader.readVariableLengthUnsignedInteger();
      reader.skip(len);
      long idOffset = 0L;
      int n = block.getNumObjects();
      TLongList ids = new TLongArrayList(n);
      reader.readVariableLengthUnsignedInteger();

      for (int i = 0; i < n; i++) {
         long id = idOffset + reader.readVariableLengthSignedInteger();
         ids.add(id);
         idOffset = id;
      }

      return ids;
   }

   public static List<Relation> parseRelations(CompactReader reader, FileBlock block, boolean fetchTags, boolean hasMetadata, boolean fetchMetadata) throws IOException {
      long len = reader.readVariableLengthUnsignedInteger();
      List<String> poolTags = null;
      if (fetchTags) {
         poolTags = parsePool(reader);
      } else {
         reader.skip(len);
      }

      reader.readVariableLengthUnsignedInteger();
      List<String> poolMembers = parsePool(reader);
      int n = block.getNumObjects();
      List<Relation> relations = new ArrayList<>(n);
      long idOffset = 0L;
      long midOffset = 0L;
      long[] ids = new long[n];
      reader.readVariableLengthUnsignedInteger();

      for (int i = 0; i < n; i++) {
         long id = idOffset + reader.readVariableLengthSignedInteger();
         idOffset = id;
         ids[i] = id;
      }

      reader.readVariableLengthUnsignedInteger();

      for (int i = 0; i < n; i++) {
         List<RelationMember> members = new ArrayList<>();
         long numMembers = reader.readVariableLengthUnsignedInteger();

         for (int k = 0; (long)k < numMembers; k++) {
            int typeByte = reader.readByte();
            long mid = midOffset + reader.readVariableLengthSignedInteger();
            midOffset = mid;
            int roleIndex = (int)reader.readVariableLengthUnsignedInteger();
            String role = poolMembers.get(roleIndex);
            EntityType type = EntityTypeHelper.getType(typeByte);
            members.add(new RelationMember(mid, type, role));
         }

         Relation relation = new Relation(ids[i], members);
         relations.add(relation);
      }

      len = reader.readVariableLengthUnsignedInteger();
      if (fetchTags) {
         for (int i = 0; i < n; i++) {
            List<Tag> tags = parseTags(reader, poolTags);
            relations.get(i).setTags(tags);
         }
      } else {
         reader.skip(len);
      }

      if (hasMetadata && fetchMetadata) {
         reader.readVariableLengthUnsignedInteger();
         parseMetadata(reader, relations);
      }

      return relations;
   }

   public static TLongList parseRelationIds(CompactReader reader, FileBlock block) throws IOException {
      long len = reader.readVariableLengthUnsignedInteger();
      reader.skip(len);
      len = reader.readVariableLengthUnsignedInteger();
      reader.skip(len);
      long idOffset = 0L;
      int n = block.getNumObjects();
      TLongList ids = new TLongArrayList(n);
      reader.readVariableLengthUnsignedInteger();

      for (int i = 0; i < n; i++) {
         long id = idOffset + reader.readVariableLengthSignedInteger();
         ids.add(id);
         idOffset = id;
      }

      return ids;
   }

   private static void parseMetadata(CompactReader reader, List<? extends Entity> elements) throws IOException {
      int situationByte = reader.readByte();
      BlockMetadataInfo situation = null;
      if (situationByte == 2) {
         situation = BlockMetadataInfo.ALL;
      } else if (situationByte == 1) {
         situation = BlockMetadataInfo.NONE;
      } else if (situationByte == 3) {
         situation = BlockMetadataInfo.MIXED;
      }

      if (situation != null && situation != BlockMetadataInfo.NONE) {
         List<String> poolUsernames = parsePool(reader);
         int numElements = elements.size();
         boolean[] hasMeta = null;
         int numMetaData;
         if (situation == BlockMetadataInfo.MIXED) {
            numMetaData = 0;
            hasMeta = new boolean[numElements];

            for (int i = 0; i < numElements; i++) {
               int flag = reader.readByte();
               boolean thisHasMeta = flag == 1;
               hasMeta[i] = thisHasMeta;
               if (thisHasMeta) {
                  numMetaData++;
               }
            }
         } else {
            numMetaData = elements.size();
         }

         int[] versions = parseDeltaInts(reader, numMetaData);
         long[] timestamps = parseDeltaLongs(reader, numMetaData);
         long[] changesets = parseDeltaLongs(reader, numMetaData);
         long[] userIds = parseDeltaLongs(reader, numMetaData);
         int[] userNameIds = parseInts(reader, numMetaData);
         if (situation == BlockMetadataInfo.ALL) {
            for (int ix = 0; ix < numElements; ix++) {
               Entity entity = elements.get(ix);
               String user = poolUsernames.get(userNameIds[ix]);
               OsmMetadata metadata = new Metadata(versions[ix], timestamps[ix], userIds[ix], user, changesets[ix]);
               entity.setMetadata(metadata);
            }
         } else {
            int ix = 0;

            for (int k = 0; k < numElements; k++) {
               if (hasMeta[k]) {
                  Entity entity = elements.get(k);
                  String user = poolUsernames.get(userNameIds[ix]);
                  OsmMetadata metadata = new Metadata(versions[ix], timestamps[ix], userIds[ix], user, changesets[ix]);
                  ix++;
                  entity.setMetadata(metadata);
               }
            }
         }
      }
   }

   private static int[] parseDeltaInts(CompactReader reader, int n) throws IOException {
      int[] values = new int[n];
      long offset = 0L;

      for (int i = 0; i < n; i++) {
         long delta = reader.readVariableLengthSignedInteger();
         long value = offset + delta;
         values[i] = (int)value;
         offset = value;
      }

      return values;
   }

   private static long[] parseDeltaLongs(CompactReader reader, int n) throws IOException {
      long[] values = new long[n];
      long offset = 0L;

      for (int i = 0; i < n; i++) {
         long delta = reader.readVariableLengthSignedInteger();
         long value = offset + delta;
         values[i] = value;
         offset = value;
      }

      return values;
   }

   private static int[] parseInts(CompactReader reader, int n) throws IOException {
      int[] values = new int[n];

      for (int i = 0; i < n; i++) {
         long v = reader.readVariableLengthUnsignedInteger();
         values[i] = (int)v;
      }

      return values;
   }
}
