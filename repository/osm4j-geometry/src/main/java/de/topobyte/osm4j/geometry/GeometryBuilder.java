package de.topobyte.osm4j.geometry;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import de.topobyte.osm4j.core.resolve.OsmEntityProvider;

public class GeometryBuilder extends AbstractGeometryBuilder {
   private NodeBuilder nodeBuilder;
   private WayBuilder wayBuilder;
   private RegionBuilder regionBuilder;
   private MissingEntitiesStrategy missingEntitiesStrategy = MissingEntitiesStrategy.THROW_EXCEPTION;
   private MissingWayNodeStrategy missingWayNodeStrategy = MissingWayNodeStrategy.OMIT_VERTEX_FROM_POLYLINE;

   public GeometryBuilder() {
      this(new GeometryFactory());
   }

   public GeometryBuilder(GeometryFactory factory) {
      super(factory);
      this.nodeBuilder = new NodeBuilder(factory);
      this.wayBuilder = new WayBuilder(factory);
      this.regionBuilder = new RegionBuilder(factory);
      this.wayBuilder.setMissingEntitiesStrategy(this.missingEntitiesStrategy);
      this.wayBuilder.setMissingWayNodeStrategy(this.missingWayNodeStrategy);
      this.regionBuilder.setMissingEntitiesStrategy(this.missingEntitiesStrategy);
      this.regionBuilder.setMissingWayNodeStrategy(this.missingWayNodeStrategy);
   }

   public NodeBuilder getNodeBuilder() {
      return this.nodeBuilder;
   }

   public WayBuilder getWayBuilder() {
      return this.wayBuilder;
   }

   public RegionBuilder getRegionBuilder() {
      return this.regionBuilder;
   }

   public MissingEntitiesStrategy getMissingEntitiesStrategy() {
      return this.missingEntitiesStrategy;
   }

   public void setMissingEntitiesStrategy(MissingEntitiesStrategy missingEntitiesStrategy) {
      this.missingEntitiesStrategy = missingEntitiesStrategy;
      this.wayBuilder.setMissingEntitiesStrategy(missingEntitiesStrategy);
      this.regionBuilder.setMissingEntitiesStrategy(missingEntitiesStrategy);
   }

   public MissingWayNodeStrategy getMissingWayNodeStrategy() {
      return this.missingWayNodeStrategy;
   }

   public void setMissingWayNodeStrategy(MissingWayNodeStrategy missingWayNodeStrategy) {
      this.missingWayNodeStrategy = missingWayNodeStrategy;
      this.wayBuilder.setMissingWayNodeStrategy(missingWayNodeStrategy);
      this.regionBuilder.setMissingWayNodeStrategy(missingWayNodeStrategy);
   }

   public Coordinate buildCoordinate(OsmNode node) {
      return this.nodeBuilder.buildCoordinate(node);
   }

   public Point build(OsmNode node) {
      return this.nodeBuilder.build(node);
   }

   public Geometry build(OsmWay way, OsmEntityProvider resolver) throws EntityNotFoundException {
      return this.wayBuilder.build(way, resolver).toGeometry(this.factory);
   }

   public Geometry build(OsmRelation relation, OsmEntityProvider resolver) throws EntityNotFoundException {
      return this.regionBuilder.build(relation, resolver).toGeometry(this.factory);
   }
}
