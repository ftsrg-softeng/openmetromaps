package de.topobyte.interactiveview;

public interface InteractiveView {
   void move(float var1, float var2);

   void zoomIn(double var1);

   void zoomOut(double var1);

   void zoomInToPosition(int var1, int var2, double var3);

   void zoomOutToPosition(int var1, int var2, double var3);
}
