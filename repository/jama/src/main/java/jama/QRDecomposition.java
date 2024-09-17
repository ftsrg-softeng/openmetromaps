package jama;

import jama.util.Maths;
import java.io.Serializable;

public class QRDecomposition implements Serializable {
   private double[][] QR;
   private int m;
   private int n;
   private double[] Rdiag;
   private static final long serialVersionUID = 1L;

   public QRDecomposition(Matrix A) {
      this.QR = A.getArrayCopy();
      this.m = A.getRowDimension();
      this.n = A.getColumnDimension();
      this.Rdiag = new double[this.n];

      for (int k = 0; k < this.n; k++) {
         double nrm = 0.0;

         for (int i = k; i < this.m; i++) {
            nrm = Maths.hypot(nrm, this.QR[i][k]);
         }

         if (nrm != 0.0) {
            if (this.QR[k][k] < 0.0) {
               nrm = -nrm;
            }

            for (int i = k; i < this.m; i++) {
               this.QR[i][k] = this.QR[i][k] / nrm;
            }

            this.QR[k][k]++;

            for (int j = k + 1; j < this.n; j++) {
               double s = 0.0;

               for (int i = k; i < this.m; i++) {
                  s += this.QR[i][k] * this.QR[i][j];
               }

               s = -s / this.QR[k][k];

               for (int i = k; i < this.m; i++) {
                  this.QR[i][j] = this.QR[i][j] + s * this.QR[i][k];
               }
            }
         }

         this.Rdiag[k] = -nrm;
      }
   }

   public boolean isFullRank() {
      for (int j = 0; j < this.n; j++) {
         if (this.Rdiag[j] == 0.0) {
            return false;
         }
      }

      return true;
   }

   public Matrix getH() {
      Matrix X = new Matrix(this.m, this.n);
      double[][] H = X.getArray();

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            if (i >= j) {
               H[i][j] = this.QR[i][j];
            } else {
               H[i][j] = 0.0;
            }
         }
      }

      return X;
   }

   public Matrix getR() {
      Matrix X = new Matrix(this.n, this.n);
      double[][] R = X.getArray();

      for (int i = 0; i < this.n; i++) {
         for (int j = 0; j < this.n; j++) {
            if (i < j) {
               R[i][j] = this.QR[i][j];
            } else if (i == j) {
               R[i][j] = this.Rdiag[i];
            } else {
               R[i][j] = 0.0;
            }
         }
      }

      return X;
   }

   public Matrix getQ() {
      Matrix X = new Matrix(this.m, this.n);
      double[][] Q = X.getArray();

      for (int k = this.n - 1; k >= 0; k--) {
         for (int i = 0; i < this.m; i++) {
            Q[i][k] = 0.0;
         }

         Q[k][k] = 1.0;

         for (int j = k; j < this.n; j++) {
            if (this.QR[k][k] != 0.0) {
               double s = 0.0;

               for (int i = k; i < this.m; i++) {
                  s += this.QR[i][k] * Q[i][j];
               }

               s = -s / this.QR[k][k];

               for (int i = k; i < this.m; i++) {
                  Q[i][j] = Q[i][j] + s * this.QR[i][k];
               }
            }
         }
      }

      return X;
   }

   public Matrix solve(Matrix B) {
      if (B.getRowDimension() != this.m) {
         throw new IllegalArgumentException("Matrix row dimensions must agree.");
      } else if (!this.isFullRank()) {
         throw new RuntimeException("Matrix is rank deficient.");
      } else {
         int nx = B.getColumnDimension();
         double[][] X = B.getArrayCopy();

         for (int k = 0; k < this.n; k++) {
            for (int j = 0; j < nx; j++) {
               double s = 0.0;

               for (int i = k; i < this.m; i++) {
                  s += this.QR[i][k] * X[i][j];
               }

               s = -s / this.QR[k][k];

               for (int i = k; i < this.m; i++) {
                  X[i][j] = X[i][j] + s * this.QR[i][k];
               }
            }
         }

         for (int k = this.n - 1; k >= 0; k--) {
            for (int j = 0; j < nx; j++) {
               X[k][j] = X[k][j] / this.Rdiag[k];
            }

            for (int i = 0; i < k; i++) {
               for (int j = 0; j < nx; j++) {
                  X[i][j] = X[i][j] - X[k][j] * this.QR[i][k];
               }
            }
         }

         return new Matrix(X, this.n, nx).getMatrix(0, this.n - 1, 0, nx - 1);
      }
   }
}
