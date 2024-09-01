package de.topobyte.adt.geo;

import com.vividsolutions.jts.geom.Envelope;

public class BBox {
   private double lon1;
   private double lon2;
   private double lat1;
   private double lat2;

   public BBox(double lon1, double lat1, double lon2, double lat2) {
      this.lon1 = lon1;
      this.lat1 = lat1;
      this.lon2 = lon2;
      this.lat2 = lat2;
      this.validate();
   }

   public void set(double lon1, double lat1, double lon2, double lat2) {
      this.lon1 = lon1;
      this.lat1 = lat1;
      this.lon2 = lon2;
      this.lat2 = lat2;
      this.validate();
   }

   public BBox(BBox other) {
      this.lon1 = other.lon1;
      this.lon2 = other.lon2;
      this.lat1 = other.lat1;
      this.lat2 = other.lat2;
   }

   public BBox(Envelope envelope) {
      this(envelope.getMinX(), envelope.getMaxY(), envelope.getMaxX(), envelope.getMinY());
   }

   public double getLon1() {
      return this.lon1;
   }

   public double getLon2() {
      return this.lon2;
   }

   public double getLat1() {
      return this.lat1;
   }

   public double getLat2() {
      return this.lat2;
   }

   public void setLon1(double lon1) {
      this.lon1 = lon1;
      this.validate();
   }

   public void setLon2(double lon2) {
      this.lon2 = lon2;
      this.validate();
   }

   public void setLat1(double lat1) {
      this.lat1 = lat1;
      this.validate();
   }

   public void setLat2(double lat2) {
      this.lat2 = lat2;
      this.validate();
   }

   private void validate() {
      if (this.lon1 > this.lon2) {
         double tmp = this.lon1;
         this.lon1 = this.lon2;
         this.lon2 = tmp;
      }

      if (this.lat1 < this.lat2) {
         double tmp = this.lat1;
         this.lat1 = this.lat2;
         this.lat2 = tmp;
      }
   }

   @Override
   public String toString() {
      return String.format("%f,%f:%f,%f", this.lon1, this.lat1, this.lon2, this.lat2);
   }

   public Envelope toEnvelope() {
      return new Envelope(this.lon1, this.lon2, this.lat1, this.lat2);
   }

   public void toEnvelope(Envelope envelope) {
      envelope.init(this.lon1, this.lon2, this.lat1, this.lat2);
   }

   @Override
   public boolean equals(Object other) {
      if (!(other instanceof BBox)) {
         return false;
      } else {
         BBox otherBox = (BBox)other;
         return otherBox.lon1 == this.lon1 && otherBox.lon2 == this.lon2 && otherBox.lat1 == this.lat1 && otherBox.lat2 == this.lat2;
      }
   }
}
