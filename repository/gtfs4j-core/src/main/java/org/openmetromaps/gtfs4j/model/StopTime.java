package org.openmetromaps.gtfs4j.model;

public class StopTime {
   private String tripId;
   private String arrivalTime;
   private String departureTime;
   private String stopId;
   private String stopSequence;
   private String stopHeadsign;
   private String pickupType;
   private String dropOffType;
   private String shapeDistTraveled;
   private String timepoint;

   public StopTime(String tripId, String arrivalTime, String departureTime, String stopId, String stopSequence) {
      this.tripId = tripId;
      this.arrivalTime = arrivalTime;
      this.departureTime = departureTime;
      this.stopId = stopId;
      this.stopSequence = stopSequence;
   }

   public String getTripId() {
      return this.tripId;
   }

   public void setTripId(String tripId) {
      this.tripId = tripId;
   }

   public String getArrivalTime() {
      return this.arrivalTime;
   }

   public void setArrivalTime(String arrivalTime) {
      this.arrivalTime = arrivalTime;
   }

   public String getDepartureTime() {
      return this.departureTime;
   }

   public void setDepartureTime(String departureTime) {
      this.departureTime = departureTime;
   }

   public String getStopId() {
      return this.stopId;
   }

   public void setStopId(String stopId) {
      this.stopId = stopId;
   }

   public String getStopSequence() {
      return this.stopSequence;
   }

   public void setStopSequence(String stopSequence) {
      this.stopSequence = stopSequence;
   }

   public String getStopHeadsign() {
      return this.stopHeadsign;
   }

   public void setStopHeadsign(String stopHeadsign) {
      this.stopHeadsign = stopHeadsign;
   }

   public String getPickupType() {
      return this.pickupType;
   }

   public void setPickupType(String pickupType) {
      this.pickupType = pickupType;
   }

   public String getDropOffType() {
      return this.dropOffType;
   }

   public void setDropOffType(String dropOffType) {
      this.dropOffType = dropOffType;
   }

   public String getShapeDistTraveled() {
      return this.shapeDistTraveled;
   }

   public void setShapeDistTraveled(String shapeDistTraveled) {
      this.shapeDistTraveled = shapeDistTraveled;
   }

   public String getTimepoint() {
      return this.timepoint;
   }

   public void setTimepoint(String timepoint) {
      this.timepoint = timepoint;
   }
}
