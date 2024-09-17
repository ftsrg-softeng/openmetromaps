package org.openmetromaps.gtfs4j.model;

public class Agency {
   private String id;
   private String name;
   private String url;
   private String timezone;
   private String lang;
   private String phone;
   private String fareUrl;
   private String email;

   public Agency(String name, String url, String timezone) {
      this.name = name;
      this.url = url;
      this.timezone = timezone;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getUrl() {
      return this.url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getTimezone() {
      return this.timezone;
   }

   public void setTimezone(String timezone) {
      this.timezone = timezone;
   }

   public String getLang() {
      return this.lang;
   }

   public void setLang(String lang) {
      this.lang = lang;
   }

   public String getPhone() {
      return this.phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getFareUrl() {
      return this.fareUrl;
   }

   public void setFareUrl(String fareUrl) {
      this.fareUrl = fareUrl;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }
}
