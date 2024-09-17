package org.openmetromaps.gtfs4j.model;

public class Route {
   private String id;
   private String agencyId;
   private String shortName;
   private String longName;
   private String desc;
   private String type;
   private String url;
   private String color;
   private String textColor;

   public Route(String id, String shortName, String longName, String type) {
      this.id = id;
      this.shortName = shortName;
      this.longName = longName;
      this.type = type;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getAgencyId() {
      return this.agencyId;
   }

   public void setAgencyId(String agencyId) {
      this.agencyId = agencyId;
   }

   public String getShortName() {
      return this.shortName;
   }

   public void setShortName(String shortName) {
      this.shortName = shortName;
   }

   public String getLongName() {
      return this.longName;
   }

   public void setLongName(String longName) {
      this.longName = longName;
   }

   public String getDesc() {
      return this.desc;
   }

   public void setDesc(String desc) {
      this.desc = desc;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getUrl() {
      return this.url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getColor() {
      return this.color;
   }

   public void setColor(String color) {
      this.color = color;
   }

   public String getTextColor() {
      return this.textColor;
   }

   public void setTextColor(String textColor) {
      this.textColor = textColor;
   }
}
