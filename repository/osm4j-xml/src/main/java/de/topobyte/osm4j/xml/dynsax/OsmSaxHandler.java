package de.topobyte.osm4j.xml.dynsax;

import com.slimjars.dist.gnu.trove.list.array.TLongArrayList;
import de.topobyte.osm4j.core.access.OsmHandler;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmMetadata;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import de.topobyte.osm4j.core.model.impl.Bounds;
import de.topobyte.osm4j.core.model.impl.Entity;
import de.topobyte.osm4j.core.model.impl.Metadata;
import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.core.model.impl.Relation;
import de.topobyte.osm4j.core.model.impl.RelationMember;
import de.topobyte.osm4j.core.model.impl.Tag;
import de.topobyte.osm4j.core.model.impl.Way;
import de.topobyte.xml.dynsax.Child;
import de.topobyte.xml.dynsax.ChildType;
import de.topobyte.xml.dynsax.Data;
import de.topobyte.xml.dynsax.DynamicSaxHandler;
import de.topobyte.xml.dynsax.Element;
import de.topobyte.xml.dynsax.ParsingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

class OsmSaxHandler extends DynamicSaxHandler {
   private OsmHandler handler;
   private boolean parseMetadata;
   private DateParser dateParser;
   private Element root;
   private Element bounds;
   private Element bound;
   private Element node;
   private Element way;
   private Element relation;
   private static final String NAME_OSM = "osm";
   private static final String NAME_BOUNDS = "bounds";
   private static final String NAME_BOUND = "bound";
   private static final String NAME_NODE = "node";
   private static final String NAME_WAY = "way";
   private static final String NAME_RELATION = "relation";
   private static final String NAME_TAG = "tag";
   private static final String NAME_ND = "nd";
   private static final String NAME_MEMBER = "member";
   private static final String ATTR_MIN_LAT = "minlat";
   private static final String ATTR_MAX_LAT = "maxlat";
   private static final String ATTR_MIN_LON = "minlon";
   private static final String ATTR_MAX_LON = "maxlon";
   private static final String ATTR_BOX = "box";
   private static final String ATTR_ID = "id";
   private static final String ATTR_K = "k";
   private static final String ATTR_V = "v";
   private static final String ATTR_LON = "lon";
   private static final String ATTR_LAT = "lat";
   private static final String ATTR_REF = "ref";
   private static final String ATTR_TYPE = "type";
   private static final String ATTR_ROLE = "role";
   private static final String ATTR_VERSION = "version";
   private static final String ATTR_TIMESTAMP = "timestamp";
   private static final String ATTR_UID = "uid";
   private static final String ATTR_USER = "user";
   private static final String ATTR_CHANGESET = "changeset";
   private static final String ATTR_VISIBLE = "visible";

   static OsmSaxHandler createInstance(boolean parseMetadata) {
      return new OsmSaxHandler(null, parseMetadata);
   }

   static OsmSaxHandler createInstance(OsmHandler handler, boolean parseMetadata) {
      return new OsmSaxHandler(handler, parseMetadata);
   }

   private OsmSaxHandler(OsmHandler handler, boolean parseMetadata) {
      this.handler = handler;
      this.parseMetadata = parseMetadata;
      if (parseMetadata) {
         this.dateParser = new DateParser();
      }

      this.setRoot(this.createRoot(), true);
   }

   void setHandler(OsmHandler handler) {
      this.handler = handler;
   }

   private Element createRoot() {
      this.root = new Element("osm", false);
      this.bounds = new Element("bounds", false);
      this.bounds.addAttribute("minlon");
      this.bounds.addAttribute("maxlon");
      this.bounds.addAttribute("minlat");
      this.bounds.addAttribute("maxlat");
      this.bound = new Element("bound", false);
      this.bound.addAttribute("box");
      this.node = new Element("node", false);
      this.node.addAttribute("id");
      this.node.addAttribute("lon");
      this.node.addAttribute("lat");
      this.way = new Element("way", false);
      this.way.addAttribute("id");
      this.relation = new Element("relation", false);
      this.relation.addAttribute("id");
      List<Element> entities = new ArrayList<>();
      entities.add(this.node);
      entities.add(this.way);
      entities.add(this.relation);
      this.root.addChild(new Child(this.bounds, ChildType.IGNORE, true));
      this.root.addChild(new Child(this.bound, ChildType.IGNORE, true));
      this.root.addChild(new Child(this.node, ChildType.IGNORE, true));
      this.root.addChild(new Child(this.way, ChildType.IGNORE, true));
      this.root.addChild(new Child(this.relation, ChildType.IGNORE, true));
      Element tag = new Element("tag", false);
      tag.addAttribute("k");
      tag.addAttribute("v");

      for (Element element : entities) {
         element.addChild(new Child(tag, ChildType.LIST, false));
      }

      if (this.parseMetadata) {
         for (Element element : entities) {
            element.addAttribute("version");
            element.addAttribute("timestamp");
            element.addAttribute("uid");
            element.addAttribute("user");
            element.addAttribute("changeset");
            element.addAttribute("visible");
         }
      }

      Element nd = new Element("nd", false);
      nd.addAttribute("ref");
      this.way.addChild(new Child(nd, ChildType.LIST, false));
      Element member = new Element("member", false);
      member.addAttribute("type");
      member.addAttribute("ref");
      member.addAttribute("role");
      this.relation.addChild(new Child(member, ChildType.LIST, false));
      return this.root;
   }

   public void emit(Data data) throws ParsingException {
      if (data.getElement() == this.bounds) {
         String aMinLon = data.getAttribute("minlon");
         String aMaxLon = data.getAttribute("maxlon");
         String aMinLat = data.getAttribute("minlat");
         String aMaxLat = data.getAttribute("maxlat");
         double minLon = Double.parseDouble(aMinLon);
         double minLat = Double.parseDouble(aMinLat);
         double maxLon = Double.parseDouble(aMaxLon);
         double maxLat = Double.parseDouble(aMaxLat);

         try {
            this.handler.handle(new Bounds(minLon, maxLon, maxLat, minLat));
         } catch (IOException var21) {
            throw new ParsingException("while handling bounds", var21);
         }
      }

      if (data.getElement() == this.bound) {
         String aBox = data.getAttribute("box");
         String[] parts = aBox.split(",");
         if (parts.length == 4) {
            double minLat = Double.parseDouble(parts[0]);
            double minLon = Double.parseDouble(parts[1]);
            double maxLat = Double.parseDouble(parts[2]);
            double maxLon = Double.parseDouble(parts[3]);

            try {
               this.handler.handle(new Bounds(minLon, maxLon, maxLat, minLat));
            } catch (IOException var20) {
               throw new ParsingException("while handling bounds", var20);
            }
         }
      }

      OsmMetadata metadata = null;
      if (this.parseMetadata) {
         String aVersion = data.getAttribute("version");
         String aTimestamp = data.getAttribute("timestamp");
         String aUid = data.getAttribute("uid");
         String user = data.getAttribute("user");
         String aChangeset = data.getAttribute("changeset");
         String aVisible = data.getAttribute("visible");
         long uid = -1L;
         if (aUid != null) {
            uid = Long.parseLong(aUid);
         }

         if (user == null) {
            user = "";
         }

         int version = -1;
         if (aVersion != null) {
            version = Integer.parseInt(aVersion);
         }

         long changeset = -1L;
         if (aChangeset != null) {
            changeset = Long.parseLong(aChangeset);
         }

         long timestamp = -1L;
         if (aTimestamp != null) {
            DateTime date = this.dateParser.parse(aTimestamp);
            timestamp = date.getMillis();
         }

         boolean visible = true;
         if (aVisible != null && aVisible.equals("false")) {
            visible = false;
         }

         metadata = new Metadata(version, timestamp, uid, user, changeset, visible);
      }

      if (data.getElement() == this.node) {
         String aId = data.getAttribute("id");
         String aLon = data.getAttribute("lon");
         String aLat = data.getAttribute("lat");
         long id = Long.parseLong(aId);
         double lon = Double.parseDouble(aLon);
         double lat = Double.parseDouble(aLat);
         Node node = new Node(id, lon, lat, metadata);
         this.fillTags(node, data);

         try {
            this.handler.handle(node);
         } catch (IOException var19) {
            throw new ParsingException("while handling node", var19);
         }
      } else if (data.getElement() == this.way) {
         String aId = data.getAttribute("id");
         long id = Long.parseLong(aId);
         TLongArrayList nodes = new TLongArrayList();
         List<Data> nds = data.getList("nd");
         if (nds != null) {
            for (Data nd : nds) {
               String aRef = nd.getAttribute("ref");
               long ref = Long.parseLong(aRef);
               nodes.add(ref);
            }
         }

         Way way = new Way(id, nodes, metadata);
         this.fillTags(way, data);

         try {
            this.handler.handle(way);
         } catch (IOException var18) {
            throw new ParsingException("while handling way", var18);
         }
      } else if (data.getElement() == this.relation) {
         String aId = data.getAttribute("id");
         long id = Long.parseLong(aId);
         List<OsmRelationMember> members = new ArrayList<>();
         List<Data> memberDs = data.getList("member");
         if (memberDs != null) {
            for (Data memberD : memberDs) {
               String aType = memberD.getAttribute("type");
               String aRef = memberD.getAttribute("ref");
               String role = memberD.getAttribute("role");
               long ref = Long.parseLong(aRef);
               EntityType type = null;
               if (aType.equals("node")) {
                  type = EntityType.Node;
               } else if (aType.equals("way")) {
                  type = EntityType.Way;
               } else if (aType.equals("relation")) {
                  type = EntityType.Relation;
               }

               RelationMember member = new RelationMember(ref, type, role);
               members.add(member);
            }
         }

         Relation relation = new Relation(id, members, metadata);
         this.fillTags(relation, data);

         try {
            this.handler.handle(relation);
         } catch (IOException var17) {
            throw new ParsingException("while handling relation", var17);
         }
      }
   }

   private void fillTags(Entity entity, Data data) {
      List<Data> list = data.getList("tag");
      if (list != null) {
         List<OsmTag> tags = new ArrayList<>();

         for (Data child : list) {
            String k = child.getAttribute("k");
            String v = child.getAttribute("v");
            tags.add(new Tag(k, v));
         }

         entity.setTags(tags);
      }
   }
}
