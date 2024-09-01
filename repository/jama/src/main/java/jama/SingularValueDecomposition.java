package jama;

import jama.util.Maths;
import java.io.Serializable;

public class SingularValueDecomposition implements Serializable {
   private double[][] U;
   private double[][] V;
   private double[] s;
   private int m;
   private int n;
   private static final long serialVersionUID = 1L;

   public SingularValueDecomposition(Matrix Arg) {
      double[][] A = Arg.getArrayCopy();
      this.m = Arg.getRowDimension();
      this.n = Arg.getColumnDimension();
      int nu = Math.min(this.m, this.n);
      this.s = new double[Math.min(this.m + 1, this.n)];
      this.U = new double[this.m][nu];
      this.V = new double[this.n][this.n];
      double[] e = new double[this.n];
      double[] work = new double[this.m];
      boolean wantu = true;
      boolean wantv = true;
      int nct = Math.min(this.m - 1, this.n);
      int nrt = Math.max(0, Math.min(this.n - 2, this.m));

      for (int k = 0; k < Math.max(nct, nrt); k++) {
         if (k < nct) {
            this.s[k] = 0.0;

            for (int i = k; i < this.m; i++) {
               this.s[k] = Maths.hypot(this.s[k], A[i][k]);
            }

            if (this.s[k] != 0.0) {
               if (A[k][k] < 0.0) {
                  this.s[k] = -this.s[k];
               }

               for (int i = k; i < this.m; i++) {
                  A[i][k] = A[i][k] / this.s[k];
               }

               A[k][k]++;
            }

            this.s[k] = -this.s[k];
         }

         for (int j = k + 1; j < this.n; j++) {
            if (k < nct & this.s[k] != 0.0) {
               double t = 0.0;

               for (int i = k; i < this.m; i++) {
                  t += A[i][k] * A[i][j];
               }

               t = -t / A[k][k];

               for (int i = k; i < this.m; i++) {
                  A[i][j] = A[i][j] + t * A[i][k];
               }
            }

            e[j] = A[k][j];
         }

         if (wantu & k < nct) {
            for (int i = k; i < this.m; i++) {
               this.U[i][k] = A[i][k];
            }
         }

         if (k < nrt) {
            e[k] = 0.0;

            for (int i = k + 1; i < this.n; i++) {
               e[k] = Maths.hypot(e[k], e[i]);
            }

            if (e[k] != 0.0) {
               if (e[k + 1] < 0.0) {
                  e[k] = -e[k];
               }

               for (int i = k + 1; i < this.n; i++) {
                  e[i] /= e[k];
               }

               e[k + 1]++;
            }

            e[k] = -e[k];
            if (k + 1 < this.m & e[k] != 0.0) {
               for (int i = k + 1; i < this.m; i++) {
                  work[i] = 0.0;
               }

               for (int j = k + 1; j < this.n; j++) {
                  for (int i = k + 1; i < this.m; i++) {
                     work[i] += e[j] * A[i][j];
                  }
               }

               for (int j = k + 1; j < this.n; j++) {
                  double t = -e[j] / e[k + 1];

                  for (int i = k + 1; i < this.m; i++) {
                     A[i][j] = A[i][j] + t * work[i];
                  }
               }
            }

            if (wantv) {
               for (int i = k + 1; i < this.n; i++) {
                  this.V[i][k] = e[i];
               }
            }
         }
      }

      int p = Math.min(this.n, this.m + 1);
      if (nct < this.n) {
         this.s[nct] = A[nct][nct];
      }

      if (this.m < p) {
         this.s[p - 1] = 0.0;
      }

      if (nrt + 1 < p) {
         e[nrt] = A[nrt][p - 1];
      }

      e[p - 1] = 0.0;
      if (wantu) {
         for (int j = nct; j < nu; j++) {
            for (int i = 0; i < this.m; i++) {
               this.U[i][j] = 0.0;
            }

            this.U[j][j] = 1.0;
         }

         for (int k = nct - 1; k >= 0; k--) {
            if (this.s[k] != 0.0) {
               for (int j = k + 1; j < nu; j++) {
                  double t = 0.0;

                  for (int i = k; i < this.m; i++) {
                     t += this.U[i][k] * this.U[i][j];
                  }

                  t = -t / this.U[k][k];

                  for (int i = k; i < this.m; i++) {
                     this.U[i][j] = this.U[i][j] + t * this.U[i][k];
                  }
               }

               for (int i = k; i < this.m; i++) {
                  this.U[i][k] = -this.U[i][k];
               }

               this.U[k][k]++;

               for (int i = 0; i < k - 1; i++) {
                  this.U[i][k] = 0.0;
               }
            } else {
               for (int i = 0; i < this.m; i++) {
                  this.U[i][k] = 0.0;
               }

               this.U[k][k] = 1.0;
            }
         }
      }

      if (wantv) {
         for (int kx = this.n - 1; kx >= 0; kx--) {
            if (kx < nrt & e[kx] != 0.0) {
               for (int j = kx + 1; j < nu; j++) {
                  double t = 0.0;

                  for (int i = kx + 1; i < this.n; i++) {
                     t += this.V[i][kx] * this.V[i][j];
                  }

                  t = -t / this.V[kx + 1][kx];

                  for (int i = kx + 1; i < this.n; i++) {
                     this.V[i][j] = this.V[i][j] + t * this.V[i][kx];
                  }
               }
            }

            for (int i = 0; i < this.n; i++) {
               this.V[i][kx] = 0.0;
            }

            this.V[kx][kx] = 1.0;
         }
      }

      int pp = p - 1;
      int iter = 0;
      double eps = Math.pow(2.0, -52.0);
      double tiny = Math.pow(2.0, -966.0);

      while (p > 0) {
         int kx;
         for (kx = p - 2; kx >= -1 && kx != -1; kx--) {
            if (Math.abs(e[kx]) <= tiny + eps * (Math.abs(this.s[kx]) + Math.abs(this.s[kx + 1]))) {
               e[kx] = 0.0;
               break;
            }
         }

         int kase;
         if (kx == p - 2) {
            kase = 4;
         } else {
            int ks;
            for (ks = p - 1; ks >= kx && ks != kx; ks--) {
               double t = (ks != p ? Math.abs(e[ks]) : 0.0) + (ks != kx + 1 ? Math.abs(e[ks - 1]) : 0.0);
               if (Math.abs(this.s[ks]) <= tiny + eps * t) {
                  this.s[ks] = 0.0;
                  break;
               }
            }

            if (ks == kx) {
               kase = 3;
            } else if (ks == p - 1) {
               kase = 1;
            } else {
               kase = 2;
               kx = ks;
            }
         }

         kx++;
         switch (kase) {
            case 1:
               double f = e[p - 2];
               e[p - 2] = 0.0;
               int j = p - 2;

               for (; j >= kx; j--) {
                  double t = Maths.hypot(this.s[j], f);
                  double cs = this.s[j] / t;
                  double sn = f / t;
                  this.s[j] = t;
                  if (j != kx) {
                     f = -sn * e[j - 1];
                     e[j - 1] = cs * e[j - 1];
                  }

                  if (wantv) {
                     for (int i = 0; i < this.n; i++) {
                        t = cs * this.V[i][j] + sn * this.V[i][p - 1];
                        this.V[i][p - 1] = -sn * this.V[i][j] + cs * this.V[i][p - 1];
                        this.V[i][j] = t;
                     }
                  }
               }
               break;
            case 2:
               double f2 = e[kx - 1];
               e[kx - 1] = 0.0;

               for (int j2 = kx; j2 < p; j2++) {
                  double txx = Maths.hypot(this.s[j2], f2);
                  double csxx = this.s[j2] / txx;
                  double snxx = f2 / txx;
                  this.s[j2] = txx;
                  f2 = -snxx * e[j2];
                  e[j2] = csxx * e[j2];
                  if (wantu) {
                     for (int i = 0; i < this.m; i++) {
                        txx = csxx * this.U[i][j2] + snxx * this.U[i][kx - 1];
                        this.U[i][kx - 1] = -snxx * this.U[i][j2] + csxx * this.U[i][kx - 1];
                        this.U[i][j2] = txx;
                     }
                  }
               }
               break;
            case 3:
               double scale = Math.max(
                  Math.max(Math.max(Math.max(Math.abs(this.s[p - 1]), Math.abs(this.s[p - 2])), Math.abs(e[p - 2])), Math.abs(this.s[kx])), Math.abs(e[kx])
               );
               double sp = this.s[p - 1] / scale;
               double spm1 = this.s[p - 2] / scale;
               double epm1 = e[p - 2] / scale;
               double sk = this.s[kx] / scale;
               double ek = e[kx] / scale;
               double b = ((spm1 + sp) * (spm1 - sp) + epm1 * epm1) / 2.0;
               double c = sp * epm1 * sp * epm1;
               double shift = 0.0;
               if (b != 0.0 | c != 0.0) {
                  shift = Math.sqrt(b * b + c);
                  if (b < 0.0) {
                     shift = -shift;
                  }

                  shift = c / (b + shift);
               }

               double f3 = (sk + sp) * (sk - sp) + shift;
               double g = sk * ek;

               for (int j3 = kx; j3 < p - 1; j3++) {
                  double tx = Maths.hypot(f3, g);
                  double csx = f3 / tx;
                  double snx = g / tx;
                  if (j3 != kx) {
                     e[j3 - 1] = tx;
                  }

                  f3 = csx * this.s[j3] + snx * e[j3];
                  e[j3] = csx * e[j3] - snx * this.s[j3];
                  g = snx * this.s[j3 + 1];
                  this.s[j3 + 1] = csx * this.s[j3 + 1];
                  if (wantv) {
                     for (int i = 0; i < this.n; i++) {
                        tx = csx * this.V[i][j3] + snx * this.V[i][j3 + 1];
                        this.V[i][j3 + 1] = -snx * this.V[i][j3] + csx * this.V[i][j3 + 1];
                        this.V[i][j3] = tx;
                     }
                  }

                  tx = Maths.hypot(f3, g);
                  csx = f3 / tx;
                  snx = g / tx;
                  this.s[j3] = tx;
                  f3 = csx * e[j3] + snx * this.s[j3 + 1];
                  this.s[j3 + 1] = -snx * e[j3] + csx * this.s[j3 + 1];
                  g = snx * e[j3 + 1];
                  e[j3 + 1] = csx * e[j3 + 1];
                  if (wantu && j3 < this.m - 1) {
                     for (int i = 0; i < this.m; i++) {
                        tx = csx * this.U[i][j3] + snx * this.U[i][j3 + 1];
                        this.U[i][j3 + 1] = -snx * this.U[i][j3] + csx * this.U[i][j3 + 1];
                        this.U[i][j3] = tx;
                     }
                  }
               }

               e[p - 2] = f3;
               iter++;
               break;
            case 4:
               if (this.s[kx] <= 0.0) {
                  this.s[kx] = this.s[kx] < 0.0 ? -this.s[kx] : 0.0;
                  if (wantv) {
                     for (int i = 0; i <= pp; i++) {
                        this.V[i][kx] = -this.V[i][kx];
                     }
                  }
               }

               for (; kx < pp && !(this.s[kx] >= this.s[kx + 1]); kx++) {
                  double txx = this.s[kx];
                  this.s[kx] = this.s[kx + 1];
                  this.s[kx + 1] = txx;
                  if (wantv && kx < this.n - 1) {
                     for (int i = 0; i < this.n; i++) {
                        txx = this.V[i][kx + 1];
                        this.V[i][kx + 1] = this.V[i][kx];
                        this.V[i][kx] = txx;
                     }
                  }

                  if (wantu && kx < this.m - 1) {
                     for (int i = 0; i < this.m; i++) {
                        txx = this.U[i][kx + 1];
                        this.U[i][kx + 1] = this.U[i][kx];
                        this.U[i][kx] = txx;
                     }
                  }
               }

               iter = 0;
               p--;
         }
      }
   }

   public Matrix getU() {
      return new Matrix(this.U, this.m, Math.min(this.m + 1, this.n));
   }

   public Matrix getV() {
      return new Matrix(this.V, this.n, this.n);
   }

   public double[] getSingularValues() {
      return this.s;
   }

   public Matrix getS() {
      Matrix X = new Matrix(this.n, this.n);
      double[][] S = X.getArray();

      for (int i = 0; i < this.n; i++) {
         for (int j = 0; j < this.n; j++) {
            S[i][j] = 0.0;
         }

         S[i][i] = this.s[i];
      }

      return X;
   }

   public double norm2() {
      return this.s[0];
   }

   public double cond() {
      return this.s[0] / this.s[Math.min(this.m, this.n) - 1];
   }

   public int rank() {
      double eps = Math.pow(2.0, -52.0);
      double tol = (double)Math.max(this.m, this.n) * this.s[0] * eps;
      int r = 0;

      for (int i = 0; i < this.s.length; i++) {
         if (this.s[i] > tol) {
            r++;
         }
      }

      return r;
   }
}
