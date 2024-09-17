package jama;

import java.io.Serializable;

public class CholeskyDecomposition implements Serializable {
   private double[][] L;
   private int n;
   private boolean isspd;
   private static final long serialVersionUID = 1L;

   public CholeskyDecomposition(Matrix Arg) {
      double[][] A = Arg.getArray();
      this.n = Arg.getRowDimension();
      this.L = new double[this.n][this.n];
      this.isspd = Arg.getColumnDimension() == this.n;

      for (int j = 0; j < this.n; j++) {
         double[] Lrowj = this.L[j];
         double d = 0.0;

         for (int k = 0; k < j; k++) {
            double[] Lrowk = this.L[k];
            double s = 0.0;

            for (int i = 0; i < k; i++) {
               s += Lrowk[i] * Lrowj[i];
            }

            double var14;
            Lrowj[k] = var14 = (A[j][k] - s) / this.L[k][k];
            d += var14 * var14;
            this.isspd = this.isspd & A[k][j] == A[j][k];
         }

         d = A[j][j] - d;
         this.isspd &= d > 0.0;
         this.L[j][j] = Math.sqrt(Math.max(d, 0.0));

         for (int k = j + 1; k < this.n; k++) {
            this.L[j][k] = 0.0;
         }
      }
   }

   public boolean isSPD() {
      return this.isspd;
   }

   public Matrix getL() {
      return new Matrix(this.L, this.n, this.n);
   }

   public Matrix solve(Matrix B) {
      if (B.getRowDimension() != this.n) {
         throw new IllegalArgumentException("Matrix row dimensions must agree.");
      } else if (!this.isspd) {
         throw new RuntimeException("Matrix is not symmetric positive definite.");
      } else {
         double[][] X = B.getArrayCopy();
         int nx = B.getColumnDimension();

         for (int k = 0; k < this.n; k++) {
            for (int j = 0; j < nx; j++) {
               for (int i = 0; i < k; i++) {
                  X[k][j] = X[k][j] - X[i][j] * this.L[k][i];
               }

               X[k][j] = X[k][j] / this.L[k][k];
            }
         }

         for (int k = this.n - 1; k >= 0; k--) {
            for (int j = 0; j < nx; j++) {
               for (int i = k + 1; i < this.n; i++) {
                  X[k][j] = X[k][j] - X[i][j] * this.L[i][k];
               }

               X[k][j] = X[k][j] / this.L[k][k];
            }
         }

         return new Matrix(X, this.n, nx);
      }
   }
}
