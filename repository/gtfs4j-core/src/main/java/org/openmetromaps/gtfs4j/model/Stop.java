package org.openmetromaps.gtfs4j.model;

public class Stop {
   private String id;
   private String code;
   private String name;
   private String desc;
   private String lat;
   private String lon;
   private String zoneId;
   private String url;
   private String locationType;
   private String parentStation;
   private String timezone;
   private String wheelchairBoarding;

   public Stop(String id, String name, String lat, String lon) {
      this.id = id;
      this.name = name;
      this.lat = lat;
      this.lon = lon;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getCode() {
      return this.code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDesc() {
      return this.desc;
   }

   public void setDesc(String desc) {
      this.desc = desc;
   }

   public String getLat() {
      return this.lat;
   }

   public void setLat(String lat) {
      this.lat = lat;
   }

   public String getLon() {
      return this.lon;
   }

   public void setLon(String lon) {
      this.lon = lon;
   }

   public String getZoneId() {
      return this.zoneId;
   }

   public void setZoneId(String zoneId) {
      this.zoneId = zoneId;
   }

   public String getUrl() {
      return this.url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getLocationType() {
      return this.locationType;
   }

   public void setLocationType(String locationType) {
      this.locationType = locationType;
   }

   public String getParentStation() {
      return this.parentStation;
   }

   public void setParentStation(String parentStation) {
      this.parentStation = parentStation;
   }

   public String getTimezone() {
      return this.timezone;
   }

   public void setTimezone(String timezone) {
      this.timezone = timezone;
   }

   public String getWheelchairBoarding() {
      return this.wheelchairBoarding;
   }

   public void setWheelchairBoarding(String wheelchairBoarding) {
      this.wheelchairBoarding = wheelchairBoarding;
   }
}
