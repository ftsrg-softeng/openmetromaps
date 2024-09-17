package jama;

import java.io.Serializable;

public class LUDecomposition implements Serializable {
   private double[][] LU;
   private int m;
   private int n;
   private int pivsign;
   private int[] piv;
   private static final long serialVersionUID = 1L;

   public LUDecomposition(Matrix A) {
      this.LU = A.getArrayCopy();
      this.m = A.getRowDimension();
      this.n = A.getColumnDimension();
      this.piv = new int[this.m];
      int i = 0;

      while (i < this.m) {
         this.piv[i] = i++;
      }

      this.pivsign = 1;
      double[] LUcolj = new double[this.m];

      for (int j = 0; j < this.n; j++) {
         for (int ix = 0; ix < this.m; ix++) {
            LUcolj[ix] = this.LU[ix][j];
         }

         for (int ix = 0; ix < this.m; ix++) {
            double[] LUrowi = this.LU[ix];
            int kmax = Math.min(ix, j);
            double s = 0.0;

            for (int k = 0; k < kmax; k++) {
               s += LUrowi[k] * LUcolj[k];
            }

            LUrowi[j] = LUcolj[ix] -= s;
         }

         int p = j;

         for (int ix = j + 1; ix < this.m; ix++) {
            if (Math.abs(LUcolj[ix]) > Math.abs(LUcolj[p])) {
               p = ix;
            }
         }

         if (p != j) {
            for (int k = 0; k < this.n; k++) {
               double t = this.LU[p][k];
               this.LU[p][k] = this.LU[j][k];
               this.LU[j][k] = t;
            }

            int k = this.piv[p];
            this.piv[p] = this.piv[j];
            this.piv[j] = k;
            this.pivsign = -this.pivsign;
         }

         if (j < this.m & this.LU[j][j] != 0.0) {
            for (int ixx = j + 1; ixx < this.m; ixx++) {
               this.LU[ixx][j] = this.LU[ixx][j] / this.LU[j][j];
            }
         }
      }
   }

   public boolean isNonsingular() {
      for (int j = 0; j < this.n; j++) {
         if (this.LU[j][j] == 0.0) {
            return false;
         }
      }

      return true;
   }

   public Matrix getL() {
      Matrix X = new Matrix(this.m, this.n);
      double[][] L = X.getArray();

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            if (i > j) {
               L[i][j] = this.LU[i][j];
            } else if (i == j) {
               L[i][j] = 1.0;
            } else {
               L[i][j] = 0.0;
            }
         }
      }

      return X;
   }

   public Matrix getU() {
      Matrix X = new Matrix(this.n, this.n);
      double[][] U = X.getArray();

      for (int i = 0; i < this.n; i++) {
         for (int j = 0; j < this.n; j++) {
            if (i <= j) {
               U[i][j] = this.LU[i][j];
            } else {
               U[i][j] = 0.0;
            }
         }
      }

      return X;
   }

   public int[] getPivot() {
      int[] p = new int[this.m];

      for (int i = 0; i < this.m; i++) {
         p[i] = this.piv[i];
      }

      return p;
   }

   public double[] getDoublePivot() {
      double[] vals = new double[this.m];

      for (int i = 0; i < this.m; i++) {
         vals[i] = (double)this.piv[i];
      }

      return vals;
   }

   public double det() {
      if (this.m != this.n) {
         throw new IllegalArgumentException("Matrix must be square.");
      } else {
         double d = (double)this.pivsign;

         for (int j = 0; j < this.n; j++) {
            d *= this.LU[j][j];
         }

         return d;
      }
   }

   public Matrix solve(Matrix B) {
      if (B.getRowDimension() != this.m) {
         throw new IllegalArgumentException("Matrix row dimensions must agree.");
      } else if (!this.isNonsingular()) {
         throw new RuntimeException("Matrix is singular.");
      } else {
         int nx = B.getColumnDimension();
         Matrix Xmat = B.getMatrix(this.piv, 0, nx - 1);
         double[][] X = Xmat.getArray();

         for (int k = 0; k < this.n; k++) {
            for (int i = k + 1; i < this.n; i++) {
               for (int j = 0; j < nx; j++) {
                  X[i][j] = X[i][j] - X[k][j] * this.LU[i][k];
               }
            }
         }

         for (int k = this.n - 1; k >= 0; k--) {
            for (int j = 0; j < nx; j++) {
               X[k][j] = X[k][j] / this.LU[k][k];
            }

            for (int i = 0; i < k; i++) {
               for (int j = 0; j < nx; j++) {
                  X[i][j] = X[i][j] - X[k][j] * this.LU[i][k];
               }
            }
         }

         return Xmat;
      }
   }
}
