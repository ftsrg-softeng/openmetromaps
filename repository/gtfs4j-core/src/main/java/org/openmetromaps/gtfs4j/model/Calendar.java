package org.openmetromaps.gtfs4j.model;

public class Calendar {
   private String serviceId;
   private String monday;
   private String tuesday;
   private String wednesday;
   private String thursday;
   private String friday;
   private String saturday;
   private String sunday;
   private String startDate;
   private String endDate;

   public Calendar(
      String serviceId,
      String monday,
      String tuesday,
      String wednesday,
      String thursday,
      String friday,
      String saturday,
      String sunday,
      String startDate,
      String endDate
   ) {
      this.serviceId = serviceId;
      this.monday = monday;
      this.tuesday = tuesday;
      this.wednesday = wednesday;
      this.thursday = thursday;
      this.friday = friday;
      this.saturday = saturday;
      this.sunday = sunday;
      this.startDate = startDate;
      this.endDate = endDate;
   }

   public String getServiceId() {
      return this.serviceId;
   }

   public void setServiceId(String serviceId) {
      this.serviceId = serviceId;
   }

   public String getMonday() {
      return this.monday;
   }

   public void setMonday(String monday) {
      this.monday = monday;
   }

   public String getTuesday() {
      return this.tuesday;
   }

   public void setTuesday(String tuesday) {
      this.tuesday = tuesday;
   }

   public String getWednesday() {
      return this.wednesday;
   }

   public void setWednesday(String wednesday) {
      this.wednesday = wednesday;
   }

   public String getThursday() {
      return this.thursday;
   }

   public void setThursday(String thursday) {
      this.thursday = thursday;
   }

   public String getFriday() {
      return this.friday;
   }

   public void setFriday(String friday) {
      this.friday = friday;
   }

   public String getSaturday() {
      return this.saturday;
   }

   public void setSaturday(String saturday) {
      this.saturday = saturday;
   }

   public String getSunday() {
      return this.sunday;
   }

   public void setSunday(String sunday) {
      this.sunday = sunday;
   }

   public String getStartDate() {
      return this.startDate;
   }

   public void setStartDate(String startDate) {
      this.startDate = startDate;
   }

   public String getEndDate() {
      return this.endDate;
   }

   public void setEndDate(String endDate) {
      this.endDate = endDate;
   }
}
