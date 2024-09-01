package de.topobyte.osm4j.extra.relations;

import com.slimjars.dist.gnu.trove.set.TLongSet;
import com.slimjars.dist.gnu.trove.set.hash.TLongHashSet;
import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmInputAccessFactory;
import de.topobyte.osm4j.core.access.OsmIteratorInput;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;
import de.topobyte.osm4j.core.util.RelationIterator;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

public class RelationsSeparator {
   private OsmInputAccessFactory inputFactory;
   private Path pathOutputSimple;
   private Path pathOutputComplex;
   private OsmOutputConfig outputConfig;
   private TLongSet idsHasRelationMembers = new TLongHashSet();
   private TLongSet idsIsRelationMember = new TLongHashSet();

   public RelationsSeparator(OsmInputAccessFactory inputFactory, Path pathOutputSimple, Path pathOutputComplex, OsmOutputConfig outputConfig) {
      this.inputFactory = inputFactory;
      this.pathOutputSimple = pathOutputSimple;
      this.pathOutputComplex = pathOutputComplex;
      this.outputConfig = outputConfig;
   }

   public void execute() throws IOException {
      this.findComplexRelations();
      this.separateRelations();
   }

   private void findComplexRelations() throws IOException {
      OsmIteratorInput input = this.inputFactory.createIterator(false, false);

      for (OsmRelation relation : new RelationIterator(input.getIterator())) {
         boolean hasRelationMembers = false;

         for (int i = 0; i < relation.getNumberOfMembers(); i++) {
            OsmRelationMember member = relation.getMember(i);
            if (member.getType() == EntityType.Relation) {
               hasRelationMembers = true;
               this.idsIsRelationMember.add(member.getId());
            }
         }

         if (hasRelationMembers) {
            this.idsHasRelationMembers.add(relation.getId());
         }
      }

      input.close();
   }

   private void separateRelations() throws IOException {
      OutputStream outSimple = StreamUtil.bufferedOutputStream(this.pathOutputSimple);
      OutputStream outComplex = StreamUtil.bufferedOutputStream(this.pathOutputComplex);
      OsmOutputStream osmOutputSimple = OsmIoUtils.setupOsmOutput(outSimple, this.outputConfig);
      OsmOutputStream osmOutputComplex = OsmIoUtils.setupOsmOutput(outComplex, this.outputConfig);
      OsmIteratorInput input = this.inputFactory.createIterator(true, this.outputConfig.isWriteMetadata());

      for (OsmRelation relation : new RelationIterator(input.getIterator())) {
         long id = relation.getId();
         if (!this.idsIsRelationMember.contains(id) && !this.idsHasRelationMembers.contains(id)) {
            osmOutputSimple.write(relation);
         } else {
            osmOutputComplex.write(relation);
         }
      }

      osmOutputSimple.complete();
      osmOutputComplex.complete();
      outSimple.close();
      outComplex.close();
      input.close();
   }
}
