package org.openmetromaps.gtfs4j.model;

public class Trip {
   private String routeId;
   private String serviceId;
   private String id;
   private String headsign;
   private String shortName;
   private String directionId;
   private String blockId;
   private String shapeId;
   private String wheelchairAccessible;
   private String bikesAllowed;

   public Trip(String routeId, String serviceId, String id) {
      this.routeId = routeId;
      this.serviceId = serviceId;
      this.id = id;
   }

   public String getRouteId() {
      return this.routeId;
   }

   public void setRouteId(String routeId) {
      this.routeId = routeId;
   }

   public String getServiceId() {
      return this.serviceId;
   }

   public void setServiceId(String serviceId) {
      this.serviceId = serviceId;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getHeadsign() {
      return this.headsign;
   }

   public void setHeadsign(String headsign) {
      this.headsign = headsign;
   }

   public String getShortName() {
      return this.shortName;
   }

   public void setShortName(String shortName) {
      this.shortName = shortName;
   }

   public String getDirectionId() {
      return this.directionId;
   }

   public void setDirectionId(String directionId) {
      this.directionId = directionId;
   }

   public String getBlockId() {
      return this.blockId;
   }

   public void setBlockId(String blockId) {
      this.blockId = blockId;
   }

   public String getShapeId() {
      return this.shapeId;
   }

   public void setShapeId(String shapeId) {
      this.shapeId = shapeId;
   }

   public String getWheelchairAccessible() {
      return this.wheelchairAccessible;
   }

   public void setWheelchairAccessible(String wheelchairAccessible) {
      this.wheelchairAccessible = wheelchairAccessible;
   }

   public String getBikesAllowed() {
      return this.bikesAllowed;
   }

   public void setBikesAllowed(String bikesAllowed) {
      this.bikesAllowed = bikesAllowed;
   }
}
