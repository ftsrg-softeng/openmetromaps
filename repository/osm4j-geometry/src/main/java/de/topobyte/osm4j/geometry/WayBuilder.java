package de.topobyte.osm4j.geometry;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.GeometryFactory;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.model.util.OsmModelUtil;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import de.topobyte.osm4j.core.resolve.OsmEntityProvider;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WayBuilder extends AbstractGeometryBuilder {
   static final Logger logger = LoggerFactory.getLogger(WayBuilder.class);
   private NodeBuilder nodeBuilder;
   private MissingEntitiesStrategy missingEntitiesStrategy = MissingEntitiesStrategy.THROW_EXCEPTION;
   private MissingWayNodeStrategy missingWayNodeStrategy = MissingWayNodeStrategy.OMIT_VERTEX_FROM_POLYLINE;
   private boolean includePuntal = true;
   private boolean log = false;
   private LogLevel logLevel = LogLevel.WARN;

   public WayBuilder() {
      this(new GeometryFactory());
   }

   public WayBuilder(GeometryFactory factory) {
      super(factory);
      this.nodeBuilder = new NodeBuilder(factory);
   }

   public MissingEntitiesStrategy getMissingEntitiesStrategy() {
      return this.missingEntitiesStrategy;
   }

   public void setMissingEntitiesStrategy(MissingEntitiesStrategy missingEntitiesStrategy) {
      this.missingEntitiesStrategy = missingEntitiesStrategy;
   }

   public MissingWayNodeStrategy getMissingWayNodeStrategy() {
      return this.missingWayNodeStrategy;
   }

   public void setMissingWayNodeStrategy(MissingWayNodeStrategy missingWayNodeStrategy) {
      this.missingWayNodeStrategy = missingWayNodeStrategy;
   }

   public boolean isIncludePuntal() {
      return this.includePuntal;
   }

   public void setIncludePuntal(boolean includePuntal) {
      this.includePuntal = includePuntal;
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

   public WayBuilderResult build(OsmWay way, OsmEntityProvider resolver) throws EntityNotFoundException {
      switch (this.missingEntitiesStrategy) {
         case THROW_EXCEPTION:
         default:
            return this.buildThrowExceptionIfNodeMissing(way, resolver);
         case BUILD_EMPTY:
            return this.buildReturnEmptyIfNodeMissing(way, resolver);
         case BUILD_PARTIAL:
            switch (this.missingWayNodeStrategy) {
               case OMIT_VERTEX_FROM_POLYLINE:
               default:
                  return this.buildOmitVertexIfNodeMissing(way, resolver);
               case SPLIT_POLYLINE:
                  return this.buildSplitIfNodeMissing(way, resolver);
            }
      }
   }

   public WayBuilderResult buildThrowExceptionIfNodeMissing(OsmWay way, OsmEntityProvider resolver) throws EntityNotFoundException {
      WayBuilderResult result = new WayBuilderResult();
      int numNodes = way.getNumberOfNodes();
      if (numNodes == 0) {
         return result;
      } else {
         if (numNodes == 1) {
            if (!this.includePuntal) {
               return result;
            }

            OsmNode node = resolver.getNode(way.getNodeId(0));
            result.getCoordinates().add(this.nodeBuilder.buildCoordinate(node));
         }

         CoordinateSequence cs = this.factory.getCoordinateSequenceFactory().create(numNodes, 2);

         for (int i = 0; i < numNodes; i++) {
            OsmNode node = resolver.getNode(way.getNodeId(i));
            cs.setOrdinate(i, 0, node.getLongitude());
            cs.setOrdinate(i, 1, node.getLatitude());
         }

         this.createLine(result, cs, OsmModelUtil.isClosed(way));
         return result;
      }
   }

   public WayBuilderResult buildReturnEmptyIfNodeMissing(OsmWay way, OsmEntityProvider resolver) {
      WayBuilderResult result = new WayBuilderResult();
      int numNodes = way.getNumberOfNodes();
      if (numNodes == 0) {
         return result;
      } else {
         label27:
         if (numNodes != 1) {
            CoordinateSequence cs = this.factory.getCoordinateSequenceFactory().create(numNodes, 2);

            for (int i = 0; i < way.getNumberOfNodes(); i++) {
               OsmNode node;
               try {
                  node = resolver.getNode(way.getNodeId(i));
               } catch (EntityNotFoundException var9) {
                  result.clear();
                  return result;
               }

               cs.setOrdinate(i, 0, node.getLongitude());
               cs.setOrdinate(i, 1, node.getLatitude());
            }

            this.createLine(result, cs, OsmModelUtil.isClosed(way));
            return result;
         } else if (!this.includePuntal) {
            return result;
         } else {
            try {
               OsmNode node = resolver.getNode(way.getNodeId(0));
               result.getCoordinates().add(this.nodeBuilder.buildCoordinate(node));
               break label27;
            } catch (EntityNotFoundException var10) {
               return result;
            }
         }
      }
      throw new RuntimeException("Need to return something here"); // Decompiler missed return statement, maybe not needed?
   }

   public WayBuilderResult buildOmitVertexIfNodeMissing(OsmWay way, OsmEntityProvider resolver) {
      WayBuilderResult result = new WayBuilderResult();
      boolean closed = OsmModelUtil.isClosed(way);
      boolean firstMissing = false;
      List<Coordinate> coords = new ArrayList<>();

      for (int i = 0; i < way.getNumberOfNodes(); i++) {
         OsmNode node;
         try {
            node = resolver.getNode(way.getNodeId(i));
         } catch (EntityNotFoundException var10) {
            if (this.log) {
               this.logMissingNode(way.getNodeId(i));
            }

            if (i == 0) {
               firstMissing = true;
            }
            continue;
         }

         coords.add(new Coordinate(node.getLongitude(), node.getLatitude()));
      }

      if (coords.size() == 0) {
         return result;
      } else if (coords.size() == 1) {
         if (!this.includePuntal) {
            return result;
         } else {
            result.getCoordinates().add(coords.get(0));
            return result;
         }
      } else {
         if (closed && firstMissing && coords.size() > 2) {
            coords.add(coords.get(0));
         }

         CoordinateSequence cs = this.factory.getCoordinateSequenceFactory().create(coords.toArray(new Coordinate[0]));
         this.createLine(result, cs, closed);
         return result;
      }
   }

   public WayBuilderResult buildSplitIfNodeMissing(OsmWay way, OsmEntityProvider resolver) {
      boolean closed = OsmModelUtil.isClosed(way);
      boolean firstMissing = false;
      CoordinateSequencesBuilder builder = new CoordinateSequencesBuilder();
      builder.beginNewSequence();

      for (int i = 0; i < way.getNumberOfNodes(); i++) {
         OsmNode node;
         try {
            node = resolver.getNode(way.getNodeId(i));
         } catch (EntityNotFoundException var9) {
            if (this.log) {
               this.logMissingNode(way.getNodeId(i));
            }

            if (i == 0) {
               firstMissing = true;
            }

            builder.beginNewSequence();
            continue;
         }

         builder.add(new Coordinate(node.getLongitude(), node.getLatitude()));
      }

      builder.finishSequence();
      return builder.createWayBuilderResult(this.factory, this.includePuntal, closed, firstMissing);
   }

   private void logMissingNode(long nodeId) {
      String message = String.format("Node not found: %d", nodeId);
      this.log(message);
   }

   private void log(String message) {
      switch (this.logLevel) {
         case INFO:
         default:
            logger.info(message);
            break;
         case DEBUG:
            logger.debug(message);
            break;
         case WARN:
            logger.warn(message);
      }
   }

   private void createLine(WayBuilderResult result, CoordinateSequence cs, boolean close) {
      if (close && cs.size() > 3) {
         result.setLinearRing(this.factory.createLinearRing(cs));
      } else {
         result.getLineStrings().add(this.factory.createLineString(cs));
      }
   }
}
