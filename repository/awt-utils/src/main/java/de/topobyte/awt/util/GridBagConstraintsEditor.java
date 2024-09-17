package de.topobyte.awt.util;

import java.awt.GridBagConstraints;

public class GridBagConstraintsEditor {
   private GridBagConstraints c;

   public GridBagConstraintsEditor() {
      this.c = new GridBagConstraints();
   }

   public GridBagConstraintsEditor(GridBagConstraints c) {
      this.c = c;
   }

   public GridBagConstraints getConstraints() {
      return this.c;
   }

   public GridBagConstraintsEditor gridX(int gridX) {
      this.c.gridx = gridX;
      return this;
   }

   public GridBagConstraintsEditor gridY(int gridY) {
      this.c.gridy = gridY;
      return this;
   }

   public GridBagConstraintsEditor gridPos(int gridX, int gridY) {
      this.c.gridx = gridX;
      this.c.gridy = gridY;
      return this;
   }

   public GridBagConstraintsEditor gridWidth(int gridWidth) {
      this.c.gridwidth = gridWidth;
      return this;
   }

   public GridBagConstraintsEditor gridHeight(int gridHeight) {
      this.c.gridheight = gridHeight;
      return this;
   }

   public GridBagConstraintsEditor gridSize(int gridWidth, int gridHeight) {
      this.c.gridwidth = gridWidth;
      this.c.gridheight = gridHeight;
      return this;
   }

   public GridBagConstraintsEditor weightX(double weightX) {
      this.c.weightx = weightX;
      return this;
   }

   public GridBagConstraintsEditor weightY(double weightY) {
      this.c.weighty = weightY;
      return this;
   }

   public GridBagConstraintsEditor weight(double weightX, double weightY) {
      this.c.weightx = weightX;
      this.c.weighty = weightY;
      return this;
   }

   public GridBagConstraintsEditor anchor(int anchor) {
      this.c.anchor = anchor;
      return this;
   }

   public GridBagConstraintsEditor fill(int fill) {
      this.c.fill = fill;
      return this;
   }

   public GridBagConstraintsEditor ipadX(int ipadX) {
      this.c.ipadx = ipadX;
      return this;
   }

   public GridBagConstraintsEditor ipadY(int ipadY) {
      this.c.ipady = ipadY;
      return this;
   }

   public GridBagConstraintsEditor ipad(int ipadX, int ipadY) {
      this.c.ipadx = ipadX;
      this.c.ipady = ipadY;
      return this;
   }
}
