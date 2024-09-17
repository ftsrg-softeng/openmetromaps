package de.topobyte.osm4j.pbf.seq;

import com.slimjars.dist.gnu.trove.list.array.TLongArrayList;
import de.topobyte.osm4j.core.access.OsmHandler;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmMetadata;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.model.impl.Metadata;
import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.core.model.impl.Relation;
import de.topobyte.osm4j.core.model.impl.RelationMember;
import de.topobyte.osm4j.core.model.impl.Tag;
import de.topobyte.osm4j.core.model.impl.Way;
import de.topobyte.osm4j.pbf.protobuf.Osmformat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrimParser {
   private int granularity;
   private long latOffset;
   private long lonOffset;
   private int dateGranularity;
   private String[] strings;
   private boolean fetchMetadata;

   public PrimParser(Osmformat.PrimitiveBlock block, boolean fetchMetadata) {
      this.fetchMetadata = fetchMetadata;
      Osmformat.StringTable stringTable = block.getStringtable();
      this.strings = new String[stringTable.getSCount()];

      for (int i = 0; i < this.strings.length; i++) {
         this.strings[i] = stringTable.getS(i).toStringUtf8();
      }

      this.granularity = block.getGranularity();
      this.latOffset = block.getLatOffset();
      this.lonOffset = block.getLonOffset();
      this.dateGranularity = block.getDateGranularity();
   }

   protected double parseLat(long degree) {
      return (double)((long)this.granularity * degree + this.latOffset) * 1.0E-9;
   }

   protected double parseLon(long degree) {
      return (double)((long)this.granularity * degree + this.lonOffset) * 1.0E-9;
   }

   public long getTimestamp(Osmformat.Info info) {
      return info.hasTimestamp() ? (long)this.dateGranularity * info.getTimestamp() : -1L;
   }

   public void parseNodes(List<Osmformat.Node> nodes, OsmHandler handler) throws IOException {
      for (Osmformat.Node n : nodes) {
         handler.handle(this.convert(n));
      }
   }

   public void parseWays(List<Osmformat.Way> ways, OsmHandler handler) throws IOException {
      for (Osmformat.Way w : ways) {
         handler.handle(this.convert(w));
      }
   }

   public void parseRelations(List<Osmformat.Relation> rels, OsmHandler handler) throws IOException {
      for (Osmformat.Relation r : rels) {
         handler.handle(this.convert(r));
      }
   }

   public OsmNode convert(Osmformat.Node n) {
      long id = n.getId();
      double lat = this.parseLat(n.getLat());
      double lon = this.parseLon(n.getLon());
      List<OsmTag> tags = new ArrayList<>();

      for (int j = 0; j < n.getKeysCount(); j++) {
         tags.add(new Tag(this.strings[n.getKeys(j)], this.strings[n.getVals(j)]));
      }

      OsmMetadata metadata = null;
      if (this.fetchMetadata && n.hasInfo()) {
         Osmformat.Info info = n.getInfo();
         metadata = this.convertMetadata(info);
      }

      return new Node(id, lon, lat, tags, metadata);
   }

   public OsmWay convert(Osmformat.Way w) {
      long id = w.getId();
      TLongArrayList nodes = new TLongArrayList();
      long lastId = 0L;

      for (long j : w.getRefsList()) {
         nodes.add(j + lastId);
         lastId += j;
      }

      List<OsmTag> tags = new ArrayList<>();

      for (int j = 0; j < w.getKeysCount(); j++) {
         tags.add(new Tag(this.strings[w.getKeys(j)], this.strings[w.getVals(j)]));
      }

      OsmMetadata metadata = null;
      if (this.fetchMetadata && w.hasInfo()) {
         Osmformat.Info info = w.getInfo();
         metadata = this.convertMetadata(info);
      }

      return new Way(id, nodes, tags, metadata);
   }

   public OsmRelation convert(Osmformat.Relation r) {
      long id = r.getId();
      long lastMid = 0L;
      List<OsmTag> tags = new ArrayList<>();

      for (int j = 0; j < r.getKeysCount(); j++) {
         tags.add(new Tag(this.strings[r.getKeys(j)], this.strings[r.getVals(j)]));
      }

      List<RelationMember> members = new ArrayList<>();

      for (int j = 0; j < r.getMemidsCount(); j++) {
         long mid = lastMid + r.getMemids(j);
         lastMid = mid;
         String role = this.strings[r.getRolesSid(j)];
         Osmformat.Relation.MemberType type = r.getTypes(j);
         EntityType t = this.getType(type);
         RelationMember member = new RelationMember(mid, t, role);
         members.add(member);
      }

      OsmMetadata metadata = null;
      if (this.fetchMetadata && r.hasInfo()) {
         Osmformat.Info info = r.getInfo();
         metadata = this.convertMetadata(info);
      }

      return new Relation(id, members, tags, metadata);
   }

   public OsmMetadata convertMetadata(Osmformat.Info info) {
      boolean visible = true;
      if (info.hasVisible() && !info.getVisible()) {
         visible = info.getVisible();
      }

      return new Metadata(info.getVersion(), this.getTimestamp(info), (long)info.getUid(), this.strings[info.getUserSid()], info.getChangeset(), visible);
   }

   public EntityType getType(Osmformat.Relation.MemberType type) {
      switch (type) {
         case NODE:
         default:
            return EntityType.Node;
         case WAY:
            return EntityType.Way;
         case RELATION:
            return EntityType.Relation;
      }
   }

   public void parseDense(Osmformat.DenseNodes nodes, OsmHandler handler) throws IOException {
      Osmformat.DenseInfo denseInfo = null;
      boolean hasVisible = false;
      if (this.fetchMetadata && nodes.hasDenseinfo()) {
         denseInfo = nodes.getDenseinfo();
         hasVisible = denseInfo.getVisibleCount() != 0;
      }

      long id = 0L;
      long lat = 0L;
      long lon = 0L;
      int version = 0;
      int uid = 0;
      int userSid = 0;
      long timestamp = 0L;
      long changeset = 0L;
      int j = 0;

      for (int i = 0; i < nodes.getIdCount(); i++) {
         id += nodes.getId(i);
         lat += nodes.getLat(i);
         lon += nodes.getLon(i);
         double latf = this.parseLat(lat);
         double lonf = this.parseLon(lon);
         List<OsmTag> tags = new ArrayList<>();
         OsmMetadata metadata = null;
         if (this.fetchMetadata && nodes.hasDenseinfo()) {
            version = denseInfo.getVersion(i);
            timestamp += denseInfo.getTimestamp(i);
            uid += denseInfo.getUid(i);
            userSid += denseInfo.getUserSid(i);
            changeset += denseInfo.getChangeset(i);
            boolean visible = true;
            if (hasVisible) {
               visible = denseInfo.getVisible(i);
            }

            metadata = new Metadata(version, timestamp * (long)this.dateGranularity, (long)uid, this.strings[userSid], changeset, visible);
         }

         if (nodes.getKeysValsCount() > 0) {
            while (nodes.getKeysVals(j) != 0) {
               int keyid = nodes.getKeysVals(j++);
               int valid = nodes.getKeysVals(j++);
               tags.add(new Tag(this.strings[keyid], this.strings[valid]));
            }

            j++;
         }

         Node node = new Node(id, lonf, latf, tags, metadata);
         handler.handle(node);
      }
   }

   public List<OsmNode> convert(Osmformat.DenseNodes nodes) {
      List<OsmNode> results = new ArrayList<>(nodes.getIdCount());
      Osmformat.DenseInfo denseInfo = null;
      boolean hasVisible = false;
      if (this.fetchMetadata && nodes.hasDenseinfo()) {
         denseInfo = nodes.getDenseinfo();
         hasVisible = denseInfo.getVisibleCount() != 0;
      }

      long id = 0L;
      long lat = 0L;
      long lon = 0L;
      int version = 0;
      int uid = 0;
      int userSid = 0;
      long timestamp = 0L;
      long changeset = 0L;
      int j = 0;

      for (int i = 0; i < nodes.getIdCount(); i++) {
         id += nodes.getId(i);
         lat += nodes.getLat(i);
         lon += nodes.getLon(i);
         double latf = this.parseLat(lat);
         double lonf = this.parseLon(lon);
         List<OsmTag> tags = new ArrayList<>();
         OsmMetadata metadata = null;
         if (this.fetchMetadata && nodes.hasDenseinfo()) {
            version = denseInfo.getVersion(i);
            timestamp += denseInfo.getTimestamp(i);
            uid += denseInfo.getUid(i);
            userSid += denseInfo.getUserSid(i);
            changeset += denseInfo.getChangeset(i);
            boolean visible = true;
            if (hasVisible) {
               visible = denseInfo.getVisible(i);
            }

            metadata = new Metadata(version, timestamp * (long)this.dateGranularity, (long)uid, this.strings[userSid], changeset, visible);
         }

         if (nodes.getKeysValsCount() > 0) {
            while (nodes.getKeysVals(j) != 0) {
               int keyid = nodes.getKeysVals(j++);
               int valid = nodes.getKeysVals(j++);
               tags.add(new Tag(this.strings[keyid], this.strings[valid]));
            }

            j++;
         }

         results.add(new Node(id, lonf, latf, tags, metadata));
      }

      return results;
   }
}
