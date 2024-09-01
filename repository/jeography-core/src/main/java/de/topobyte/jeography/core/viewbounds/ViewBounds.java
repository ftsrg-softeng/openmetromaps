package de.topobyte.jeography.core.viewbounds;

public interface ViewBounds {
   BoundsInfo checkBounds(double var1, double var3);

   double fixLon(double var1);

   double fixLat(double var1);
}
