package de.topobyte.osm4j.tbo.writerhelper;

import de.topobyte.compactio.CompactWriter;
import de.topobyte.compactio.OutputStreamCompactWriter;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;
import de.topobyte.osm4j.tbo.ByteArrayOutputStream;
import de.topobyte.osm4j.tbo.data.StringPool;
import de.topobyte.osm4j.tbo.data.StringPoolBuilder;
import java.io.IOException;

public class RelationBatch extends EntityBatch<OsmRelation> {
   protected StringPool stringPoolMembers;
   private long midOffset = 0L;

   public RelationBatch(boolean writeMetadata) {
      super(writeMetadata);
   }

   @Override
   public void write(CompactWriter writer) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      CompactWriter bwriter = new OutputStreamCompactWriter(baos);
      this.writeTagStringPool(bwriter);
      this.writeAndReset(writer, baos);
      this.writeMemberStringPool(bwriter);
      this.writeAndReset(writer, baos);
      this.writeIds(bwriter);
      this.writeAndReset(writer, baos);
      this.writeMembers(bwriter);
      this.writeAndReset(writer, baos);
      this.writeTags(bwriter);
      this.writeAndReset(writer, baos);
      this.writeMetadata(bwriter);
      this.writeAndReset(writer, baos);
   }

   private void writeMembers(CompactWriter writer) throws IOException {
      for (OsmRelation relation : this.elements) {
         int nMembers = relation.getNumberOfMembers();
         writer.writeVariableLengthUnsignedInteger((long)nMembers);

         for (int i = 0; i < nMembers; i++) {
            OsmRelationMember member = relation.getMember(i);
            long mid = member.getId();
            EntityType type = member.getType();
            int t = EntityTypeHelper.getByte(type);
            int index = this.stringPoolMembers.getId(member.getRole());
            writer.writeByte(t);
            writer.writeVariableLengthSignedInteger(mid - this.midOffset);
            writer.writeVariableLengthUnsignedInteger((long)index);
            this.midOffset = mid;
         }
      }
   }

   public void writeMemberStringPool(CompactWriter writer) throws IOException {
      StringPoolBuilder poolBuilder = new StringPoolBuilder();

      for (OsmRelation object : this.elements) {
         int nMembers = object.getNumberOfMembers();

         for (int i = 0; i < nMembers; i++) {
            OsmRelationMember member = object.getMember(i);
            poolBuilder.add(member.getRole());
         }
      }

      this.stringPoolMembers = poolBuilder.buildStringPool();
      this.writePool(writer, this.stringPoolMembers);
   }

   @Override
   public void clear() {
      super.clear();
      this.midOffset = 0L;
   }
}
