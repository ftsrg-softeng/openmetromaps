package de.topobyte.osm4j.testing;

import com.slimjars.dist.gnu.trove.list.TLongList;
import com.slimjars.dist.gnu.trove.list.array.TLongArrayList;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.testing.model.TestEntity;
import de.topobyte.osm4j.testing.model.TestMetadata;
import de.topobyte.osm4j.testing.model.TestNode;
import de.topobyte.osm4j.testing.model.TestRelation;
import de.topobyte.osm4j.testing.model.TestRelationMember;
import de.topobyte.osm4j.testing.model.TestTag;
import de.topobyte.osm4j.testing.model.TestWay;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityGenerator {
   private int idSpan;
   private boolean generateMetadata;
   private Random random = new Random();
   private long lastNodeId = 0L;
   private long lastWayId = 0L;
   private long lastRelationId = 0L;
   private double minLon = -180.0;
   private double maxLon = 180.0;
   private double minLat = -90.0;
   private double maxLat = 90.0;
   private int minNodes = 2;
   private int maxNodes = 50;
   private int minMembers = 2;
   private int maxMembers = 50;
   private int minLengthRoles = 3;
   private int maxLengthRoles = 12;
   private int minLengthUsernames = 5;
   private int maxLengthUsernames = 15;
   private int minLengthKeys = 3;
   private int maxLengthKeys = 12;
   private int minLengthValues = 6;
   private int maxLengthValues = 20;
   private int minTags = 0;
   private int maxTags = 10;
   private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

   public EntityGenerator(int idSpan, boolean generateMetadata) {
      this.idSpan = idSpan;
      this.generateMetadata = generateMetadata;
   }

   public int getIdSpan() {
      return this.idSpan;
   }

   public void setIdSpan(int idSpan) {
      this.idSpan = idSpan;
   }

   public boolean isGenerateMetadata() {
      return this.generateMetadata;
   }

   public void setGenerateMetadata(boolean generateMetadata) {
      this.generateMetadata = generateMetadata;
   }

   public TestNode generateNode() {
      long id = this.nodeId();
      double lon = this.lon();
      double lat = this.lat();
      TestNode node = new TestNode(id, lon, lat);
      this.generateTags(node);
      if (this.generateMetadata) {
         this.generateMetadata(node);
      }

      return node;
   }

   public TestWay generateWay() {
      long id = this.wayId();
      TLongList nodes = new TLongArrayList();
      int numNodes = this.minNodes + this.random.nextInt(this.maxNodes - this.minNodes);

      for (int i = 0; i < numNodes; i++) {
         long node = this.positiveLong();
         nodes.add(node);
      }

      TestWay way = new TestWay(id, nodes);
      this.generateTags(way);
      if (this.generateMetadata) {
         this.generateMetadata(way);
      }

      return way;
   }

   public TestRelation generateRelation() {
      long id = this.relationId();
      List<TestRelationMember> members = new ArrayList<>();
      int numMembers = this.minMembers + this.random.nextInt(this.maxMembers - this.minMembers);

      for (int i = 0; i < numMembers; i++) {
         long member = this.positiveLong();
         EntityType type = this.type();
         String role = this.role();
         members.add(new TestRelationMember(member, type, role));
      }

      TestRelation relation = new TestRelation(id, members);
      this.generateTags(relation);
      if (this.generateMetadata) {
         this.generateMetadata(relation);
      }

      return relation;
   }

   private long nodeId() {
      long id = this.lastNodeId + 1L + (long)this.random.nextInt(this.idSpan);
      this.lastNodeId = id;
      return id;
   }

   private long wayId() {
      long id = this.lastWayId + 1L + (long)this.random.nextInt(this.idSpan);
      this.lastWayId = id;
      return id;
   }

   private long relationId() {
      long id = this.lastRelationId + 1L + (long)this.random.nextInt(this.idSpan);
      this.lastRelationId = id;
      return id;
   }

   private double lon() {
      double v = this.random.nextDouble();
      return this.minLon + (this.maxLon - this.minLon) * v;
   }

   private double lat() {
      double v = this.random.nextDouble();
      return this.minLat + (this.maxLat - this.minLat) * v;
   }

   private EntityType type() {
      int v = this.random.nextInt(3);
      switch (v) {
         case 0:
         default:
            return EntityType.Node;
         case 1:
            return EntityType.Way;
         case 2:
            return EntityType.Relation;
      }
   }

   private String role() {
      return this.string(this.minLengthRoles, this.maxLengthRoles);
   }

   private String username() {
      return this.string(this.minLengthUsernames, this.maxLengthUsernames);
   }

   private String key() {
      return this.string(this.minLengthKeys, this.maxLengthKeys);
   }

   private String value() {
      return this.string(this.minLengthValues, this.maxLengthValues);
   }

   private String string(int minLength, int maxLength) {
      StringBuilder builder = new StringBuilder();
      long length = (long)(minLength + this.random.nextInt(maxLength - minLength));

      for (int i = 0; (long)i < length; i++) {
         char c = this.character();
         builder.append(c);
      }

      return builder.toString();
   }

   private char character() {
      return "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
         .charAt(this.random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".length()));
   }

   private void generateTags(TestEntity entity) {
      List<TestTag> tags = new ArrayList<>();
      long num = (long)(this.minTags + this.random.nextInt(this.maxTags - this.minTags));

      for (int i = 0; (long)i < num; i++) {
         tags.add(new TestTag(this.key(), this.value()));
      }

      entity.setTags(tags);
   }

   private void generateMetadata(TestEntity entity) {
      int version = this.random.nextInt();
      long timestamp = this.positiveLong() * 1000L;
      long uid = this.positiveLong();
      String user = this.username();
      long changeset = this.positiveLong();
      TestMetadata metadata = new TestMetadata(version, timestamp, uid, user, changeset);
      entity.setMetadata(metadata);
   }

   private long positiveLong() {
      return (long)(this.random.nextInt(2147483645) + 1);
   }
}
