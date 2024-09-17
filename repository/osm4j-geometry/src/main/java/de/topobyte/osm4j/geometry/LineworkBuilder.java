package de.topobyte.osm4j.geometry;

import com.vividsolutions.jts.geom.GeometryFactory;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.resolve.EntityFinder;
import de.topobyte.osm4j.core.resolve.EntityFinders;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import de.topobyte.osm4j.core.resolve.EntityNotFoundStrategy;
import de.topobyte.osm4j.core.resolve.OsmEntityProvider;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class LineworkBuilder extends AbstractGeometryBuilder {
   private NodeBuilder nodeBuilder;
   private WayBuilder wayBuilder;
   private MissingEntitiesStrategy missingEntitiesStrategy = MissingEntitiesStrategy.THROW_EXCEPTION;
   private MissingWayNodeStrategy missingWayNodeStrategy = MissingWayNodeStrategy.OMIT_VERTEX_FROM_POLYLINE;
   private boolean log = false;
   private LogLevel logLevel = LogLevel.WARN;

   public LineworkBuilder() {
      this(new GeometryFactory());
   }

   public LineworkBuilder(GeometryFactory factory) {
      super(factory);
      this.nodeBuilder = new NodeBuilder(factory);
      this.wayBuilder = new WayBuilder(factory);
      this.wayBuilder.setMissingEntitiesStrategy(this.missingEntitiesStrategy);
      this.wayBuilder.setMissingWayNodeStrategy(this.missingWayNodeStrategy);
   }

   public boolean isLog() {
      return this.log;
   }

   public void setLog(boolean log) {
      this.log = log;
   }

   public LogLevel getLogLevel() {
      return this.logLevel;
   }

   public void setLogLevel(LogLevel logLevel) {
      this.logLevel = logLevel;
   }

   public MissingEntitiesStrategy getMissingEntitiesStrategy() {
      return this.missingEntitiesStrategy;
   }

   public void setMissingEntitiesStrategy(MissingEntitiesStrategy missingEntitiesStrategy) {
      this.missingEntitiesStrategy = missingEntitiesStrategy;
      this.wayBuilder.setMissingEntitiesStrategy(missingEntitiesStrategy);
   }

   public MissingWayNodeStrategy getMissingWayNodeStrategy() {
      return this.missingWayNodeStrategy;
   }

   public void setMissingWayNodeStrategy(MissingWayNodeStrategy missingWayNodeStrategy) {
      this.missingWayNodeStrategy = missingWayNodeStrategy;
      this.wayBuilder.setMissingWayNodeStrategy(missingWayNodeStrategy);
   }

   public LineworkBuilderResult build(OsmRelation relation, OsmEntityProvider provider) throws EntityNotFoundException {
      EntityNotFoundStrategy enfs = Util.strategy(this.missingEntitiesStrategy, this.log, this.logLevel);
      EntityFinder finder = EntityFinders.create(provider, enfs);
      Set<OsmNode> nodes = new HashSet<>();
      Set<OsmWay> ways = new HashSet<>();

      try {
         finder.findMemberNodesAndWays(relation, nodes, ways);
      } catch (EntityNotFoundException var8) {
         switch (this.missingEntitiesStrategy) {
            case THROW_EXCEPTION:
            default:
               throw var8;
            case BUILD_EMPTY:
               return new LineworkBuilderResult();
            case BUILD_PARTIAL:
         }
      }

      return this.build(nodes, ways, provider);
   }

   public LineworkBuilderResult build(Collection<OsmRelation> relations, OsmEntityProvider provider) throws EntityNotFoundException {
      EntityNotFoundStrategy enfs = Util.strategy(this.missingEntitiesStrategy, this.log, this.logLevel);
      EntityFinder finder = EntityFinders.create(provider, enfs);
      Set<OsmNode> nodes = new HashSet<>();
      Set<OsmWay> ways = new HashSet<>();

      try {
         finder.findMemberNodesAndWays(relations, nodes, ways);
      } catch (EntityNotFoundException var8) {
         switch (this.missingEntitiesStrategy) {
            case THROW_EXCEPTION:
            default:
               throw var8;
            case BUILD_EMPTY:
               return new LineworkBuilderResult();
            case BUILD_PARTIAL:
         }
      }

      return this.build(nodes, ways, provider);
   }

   public LineworkBuilderResult build(Collection<OsmNode> nodes, Collection<OsmWay> ways, OsmEntityProvider provider) throws EntityNotFoundException {
      LineworkBuilderResult result = new LineworkBuilderResult();
      GeometryUtil.buildNodes(this.nodeBuilder, nodes, result.getCoordinates());

      for (OsmWay way : ways) {
         WayBuilderResult wbr = this.wayBuilder.build(way, provider);
         result.getCoordinates().addAll(wbr.getCoordinates());
         result.getLineStrings().addAll(wbr.getLineStrings());
         if (wbr.getLinearRing() != null) {
            result.getLineStrings().add(wbr.getLinearRing());
         }
      }

      return result;
   }
}
