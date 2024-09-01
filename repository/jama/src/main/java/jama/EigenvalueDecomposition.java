package jama;

import jama.util.Maths;
import java.io.Serializable;

public class EigenvalueDecomposition implements Serializable {
   private int n;
   private boolean issymmetric;
   private double[] d;
   private double[] e;
   private double[][] V;
   private double[][] H;
   private double[] ort;
   private transient double cdivr;
   private transient double cdivi;
   private static final long serialVersionUID = 1L;

   private void tred2() {
      for (int j = 0; j < this.n; j++) {
         this.d[j] = this.V[this.n - 1][j];
      }

      for (int i = this.n - 1; i > 0; i--) {
         double scale = 0.0;
         double h = 0.0;

         for (int k = 0; k < i; k++) {
            scale += Math.abs(this.d[k]);
         }

         if (scale == 0.0) {
            this.e[i] = this.d[i - 1];

            for (int j = 0; j < i; j++) {
               this.d[j] = this.V[i - 1][j];
               this.V[i][j] = 0.0;
               this.V[j][i] = 0.0;
            }
         } else {
            for (int k = 0; k < i; k++) {
               this.d[k] = this.d[k] / scale;
               h += this.d[k] * this.d[k];
            }

            double f = this.d[i - 1];
            double g = Math.sqrt(h);
            if (f > 0.0) {
               g = -g;
            }

            this.e[i] = scale * g;
            h -= f * g;
            this.d[i - 1] = f - g;

            for (int j = 0; j < i; j++) {
               this.e[j] = 0.0;
            }

            for (int j = 0; j < i; j++) {
               f = this.d[j];
               this.V[j][i] = f;
               g = this.e[j] + this.V[j][j] * f;

               for (int k = j + 1; k <= i - 1; k++) {
                  g += this.V[k][j] * this.d[k];
                  this.e[k] = this.e[k] + this.V[k][j] * f;
               }

               this.e[j] = g;
            }

            f = 0.0;

            for (int j = 0; j < i; j++) {
               this.e[j] = this.e[j] / h;
               f += this.e[j] * this.d[j];
            }

            double hh = f / (h + h);

            for (int j = 0; j < i; j++) {
               this.e[j] = this.e[j] - hh * this.d[j];
            }

            for (int j = 0; j < i; j++) {
               f = this.d[j];
               g = this.e[j];

               for (int k = j; k <= i - 1; k++) {
                  this.V[k][j] = this.V[k][j] - (f * this.e[k] + g * this.d[k]);
               }

               this.d[j] = this.V[i - 1][j];
               this.V[i][j] = 0.0;
            }
         }

         this.d[i] = h;
      }

      for (int i = 0; i < this.n - 1; i++) {
         this.V[this.n - 1][i] = this.V[i][i];
         this.V[i][i] = 1.0;
         double h = this.d[i + 1];
         if (h != 0.0) {
            for (int k = 0; k <= i; k++) {
               this.d[k] = this.V[k][i + 1] / h;
            }

            for (int j = 0; j <= i; j++) {
               double g = 0.0;

               for (int k = 0; k <= i; k++) {
                  g += this.V[k][i + 1] * this.V[k][j];
               }

               for (int k = 0; k <= i; k++) {
                  this.V[k][j] = this.V[k][j] - g * this.d[k];
               }
            }
         }

         for (int k = 0; k <= i; k++) {
            this.V[k][i + 1] = 0.0;
         }
      }

      for (int j = 0; j < this.n; j++) {
         this.d[j] = this.V[this.n - 1][j];
         this.V[this.n - 1][j] = 0.0;
      }

      this.V[this.n - 1][this.n - 1] = 1.0;
      this.e[0] = 0.0;
   }

   private void tql2() {
      for (int i = 1; i < this.n; i++) {
         this.e[i - 1] = this.e[i];
      }

      this.e[this.n - 1] = 0.0;
      double f = 0.0;
      double tst1 = 0.0;
      double eps = Math.pow(2.0, -52.0);

      for (int l = 0; l < this.n; l++) {
         tst1 = Math.max(tst1, Math.abs(this.d[l]) + Math.abs(this.e[l]));
         int m = l;

         while (m < this.n && !(Math.abs(this.e[m]) <= eps * tst1)) {
            m++;
         }

         if (m > l) {
            int iter = 0;

            do {
               iter++;
               double g = this.d[l];
               double p = (this.d[l + 1] - g) / (2.0 * this.e[l]);
               double r = Maths.hypot(p, 1.0);
               if (p < 0.0) {
                  r = -r;
               }

               this.d[l] = this.e[l] / (p + r);
               this.d[l + 1] = this.e[l] * (p + r);
               double dl1 = this.d[l + 1];
               double h = g - this.d[l];

               for (int i = l + 2; i < this.n; i++) {
                  this.d[i] = this.d[i] - h;
               }

               f += h;
               p = this.d[m];
               double c = 1.0;
               double c2 = c;
               double c3 = c;
               double el1 = this.e[l + 1];
               double s = 0.0;
               double s2 = 0.0;

               for (int i = m - 1; i >= l; i--) {
                  c3 = c2;
                  c2 = c;
                  s2 = s;
                  g = c * this.e[i];
                  h = c * p;
                  r = Maths.hypot(p, this.e[i]);
                  this.e[i + 1] = s * r;
                  s = this.e[i] / r;
                  c = p / r;
                  p = c * this.d[i] - s * g;
                  this.d[i + 1] = h + s * (c * g + s * this.d[i]);

                  for (int k = 0; k < this.n; k++) {
                     h = this.V[k][i + 1];
                     this.V[k][i + 1] = s * this.V[k][i] + c * h;
                     this.V[k][i] = c * this.V[k][i] - s * h;
                  }
               }

               p = -s * s2 * c3 * el1 * this.e[l] / dl1;
               this.e[l] = s * p;
               this.d[l] = c * p;
            } while (Math.abs(this.e[l]) > eps * tst1);
         }

         this.d[l] = this.d[l] + f;
         this.e[l] = 0.0;
      }

      for (int i = 0; i < this.n - 1; i++) {
         int k = i;
         double p = this.d[i];

         for (int j = i + 1; j < this.n; j++) {
            if (this.d[j] < p) {
               k = j;
               p = this.d[j];
            }
         }

         if (k != i) {
            this.d[k] = this.d[i];
            this.d[i] = p;

            for (int jx = 0; jx < this.n; jx++) {
               p = this.V[jx][i];
               this.V[jx][i] = this.V[jx][k];
               this.V[jx][k] = p;
            }
         }
      }
   }

   private void orthes() {
      int low = 0;
      int high = this.n - 1;

      for (int m = low + 1; m <= high - 1; m++) {
         double scale = 0.0;

         for (int i = m; i <= high; i++) {
            scale += Math.abs(this.H[i][m - 1]);
         }

         if (scale != 0.0) {
            double h = 0.0;

            for (int i = high; i >= m; i--) {
               this.ort[i] = this.H[i][m - 1] / scale;
               h += this.ort[i] * this.ort[i];
            }

            double g = Math.sqrt(h);
            if (this.ort[m] > 0.0) {
               g = -g;
            }

            h -= this.ort[m] * g;
            this.ort[m] = this.ort[m] - g;

            for (int j = m; j < this.n; j++) {
               double f = 0.0;

               for (int i = high; i >= m; i--) {
                  f += this.ort[i] * this.H[i][j];
               }

               f /= h;

               for (int i = m; i <= high; i++) {
                  this.H[i][j] = this.H[i][j] - f * this.ort[i];
               }
            }

            for (int i = 0; i <= high; i++) {
               double f = 0.0;

               for (int j = high; j >= m; j--) {
                  f += this.ort[j] * this.H[i][j];
               }

               f /= h;

               for (int j = m; j <= high; j++) {
                  this.H[i][j] = this.H[i][j] - f * this.ort[j];
               }
            }

            this.ort[m] = scale * this.ort[m];
            this.H[m][m - 1] = scale * g;
         }
      }

      for (int i = 0; i < this.n; i++) {
         for (int j = 0; j < this.n; j++) {
            this.V[i][j] = i == j ? 1.0 : 0.0;
         }
      }

      for (int m = high - 1; m >= low + 1; m--) {
         if (this.H[m][m - 1] != 0.0) {
            for (int i = m + 1; i <= high; i++) {
               this.ort[i] = this.H[i][m - 1];
            }

            for (int j = m; j <= high; j++) {
               double g = 0.0;

               for (int i = m; i <= high; i++) {
                  g += this.ort[i] * this.V[i][j];
               }

               g = g / this.ort[m] / this.H[m][m - 1];

               for (int i = m; i <= high; i++) {
                  this.V[i][j] = this.V[i][j] + g * this.ort[i];
               }
            }
         }
      }
   }

   private void cdiv(double xr, double xi, double yr, double yi) {
      if (Math.abs(yr) > Math.abs(yi)) {
         double r = yi / yr;
         double d = yr + r * yi;
         this.cdivr = (xr + r * xi) / d;
         this.cdivi = (xi - r * xr) / d;
      } else {
         double r = yr / yi;
         double d = yi + r * yr;
         this.cdivr = (r * xr + xi) / d;
         this.cdivi = (r * xi - xr) / d;
      }
   }

   private void hqr2() {
      int nn = this.n;
      int n = nn - 1;
      int low = 0;
      int high = nn - 1;
      double eps = Math.pow(2.0, -52.0);
      double exshift = 0.0;
      double p = 0.0;
      double q = 0.0;
      double r = 0.0;
      double s = 0.0;
      double z = 0.0;
      double norm = 0.0;

      for (int i = 0; i < nn; i++) {
         if (i < low | i > high) {
            this.d[i] = this.H[i][i];
            this.e[i] = 0.0;
         }

         for (int j = Math.max(i - 1, 0); j < nn; j++) {
            norm += Math.abs(this.H[i][j]);
         }
      }

      int iter = 0;

      while (n >= low) {
         int l;
         for (l = n; l > low; l--) {
            s = Math.abs(this.H[l - 1][l - 1]) + Math.abs(this.H[l][l]);
            if (s == 0.0) {
               s = norm;
            }

            if (Math.abs(this.H[l][l - 1]) < eps * s) {
               break;
            }
         }

         if (l == n) {
            this.H[n][n] = this.H[n][n] + exshift;
            this.d[n] = this.H[n][n];
            this.e[n] = 0.0;
            n--;
            iter = 0;
         } else if (l == n - 1) {
            double w = this.H[n][n - 1] * this.H[n - 1][n];
            p = (this.H[n - 1][n - 1] - this.H[n][n]) / 2.0;
            q = p * p + w;
            z = Math.sqrt(Math.abs(q));
            this.H[n][n] = this.H[n][n] + exshift;
            this.H[n - 1][n - 1] = this.H[n - 1][n - 1] + exshift;
            double x = this.H[n][n];
            if (q >= 0.0) {
               if (p >= 0.0) {
                  z += p;
               } else {
                  z = p - z;
               }

               this.d[n - 1] = x + z;
               this.d[n] = this.d[n - 1];
               if (z != 0.0) {
                  this.d[n] = x - w / z;
               }

               this.e[n - 1] = 0.0;
               this.e[n] = 0.0;
               x = this.H[n][n - 1];
               s = Math.abs(x) + Math.abs(z);
               p = x / s;
               q = z / s;
               r = Math.sqrt(p * p + q * q);
               p /= r;
               q /= r;

               for (int j = n - 1; j < nn; j++) {
                  z = this.H[n - 1][j];
                  this.H[n - 1][j] = q * z + p * this.H[n][j];
                  this.H[n][j] = q * this.H[n][j] - p * z;
               }

               for (int i = 0; i <= n; i++) {
                  z = this.H[i][n - 1];
                  this.H[i][n - 1] = q * z + p * this.H[i][n];
                  this.H[i][n] = q * this.H[i][n] - p * z;
               }

               for (int i = low; i <= high; i++) {
                  z = this.V[i][n - 1];
                  this.V[i][n - 1] = q * z + p * this.V[i][n];
                  this.V[i][n] = q * this.V[i][n] - p * z;
               }
            } else {
               this.d[n - 1] = x + p;
               this.d[n] = x + p;
               this.e[n - 1] = z;
               this.e[n] = -z;
            }

            n -= 2;
            iter = 0;
         } else {
            double x = this.H[n][n];
            double y = 0.0;
            double w = 0.0;
            if (l < n) {
               y = this.H[n - 1][n - 1];
               w = this.H[n][n - 1] * this.H[n - 1][n];
            }

            if (iter == 10) {
               exshift += x;

               for (int i = low; i <= n; i++) {
                  this.H[i][i] = this.H[i][i] - x;
               }

               s = Math.abs(this.H[n][n - 1]) + Math.abs(this.H[n - 1][n - 2]);
               x = y = 0.75 * s;
               w = -0.4375 * s * s;
            }

            if (iter == 30) {
               s = (y - x) / 2.0;
               s = s * s + w;
               if (s > 0.0) {
                  s = Math.sqrt(s);
                  if (y < x) {
                     s = -s;
                  }

                  s = x - w / ((y - x) / 2.0 + s);

                  for (int i = low; i <= n; i++) {
                     this.H[i][i] = this.H[i][i] - s;
                  }

                  exshift += s;
                  w = 0.964;
                  y = 0.964;
                  x = 0.964;
               }
            }

            iter++;

            int m;
            for (m = n - 2; m >= l; m--) {
               z = this.H[m][m];
               r = x - z;
               double var53 = y - z;
               p = (r * var53 - w) / this.H[m + 1][m] + this.H[m][m + 1];
               q = this.H[m + 1][m + 1] - z - r - var53;
               r = this.H[m + 2][m + 1];
               s = Math.abs(p) + Math.abs(q) + Math.abs(r);
               p /= s;
               q /= s;
               r /= s;
               if (m == l
                  || Math.abs(this.H[m][m - 1]) * (Math.abs(q) + Math.abs(r))
                     < eps * Math.abs(p) * (Math.abs(this.H[m - 1][m - 1]) + Math.abs(z) + Math.abs(this.H[m + 1][m + 1]))) {
                  break;
               }
            }

            for (int i = m + 2; i <= n; i++) {
               this.H[i][i - 2] = 0.0;
               if (i > m + 2) {
                  this.H[i][i - 3] = 0.0;
               }
            }

            for (int k = m; k <= n - 1; k++) {
               boolean notlast = k != n - 1;
               if (k != m) {
                  p = this.H[k][k - 1];
                  q = this.H[k + 1][k - 1];
                  r = notlast ? this.H[k + 2][k - 1] : 0.0;
                  x = Math.abs(p) + Math.abs(q) + Math.abs(r);
                  if (x == 0.0) {
                     continue;
                  }

                  p /= x;
                  q /= x;
                  r /= x;
               }

               s = Math.sqrt(p * p + q * q + r * r);
               if (p < 0.0) {
                  s = -s;
               }

               if (s != 0.0) {
                  if (k != m) {
                     this.H[k][k - 1] = -s * x;
                  } else if (l != m) {
                     this.H[k][k - 1] = -this.H[k][k - 1];
                  }

                  p += s;
                  x = p / s;
                  y = q / s;
                  z = r / s;
                  q /= p;
                  r /= p;

                  for (int j = k; j < nn; j++) {
                     p = this.H[k][j] + q * this.H[k + 1][j];
                     if (notlast) {
                        p += r * this.H[k + 2][j];
                        this.H[k + 2][j] = this.H[k + 2][j] - p * z;
                     }

                     this.H[k][j] = this.H[k][j] - p * x;
                     this.H[k + 1][j] = this.H[k + 1][j] - p * y;
                  }

                  for (int ix = 0; ix <= Math.min(n, k + 3); ix++) {
                     p = x * this.H[ix][k] + y * this.H[ix][k + 1];
                     if (notlast) {
                        p += z * this.H[ix][k + 2];
                        this.H[ix][k + 2] = this.H[ix][k + 2] - p * r;
                     }

                     this.H[ix][k] = this.H[ix][k] - p;
                     this.H[ix][k + 1] = this.H[ix][k + 1] - p * q;
                  }

                  for (int ix = low; ix <= high; ix++) {
                     p = x * this.V[ix][k] + y * this.V[ix][k + 1];
                     if (notlast) {
                        p += z * this.V[ix][k + 2];
                        this.V[ix][k + 2] = this.V[ix][k + 2] - p * r;
                     }

                     this.V[ix][k] = this.V[ix][k] - p;
                     this.V[ix][k + 1] = this.V[ix][k + 1] - p * q;
                  }
               }
            }
         }
      }

      if (norm != 0.0) {
         for (int var41 = nn - 1; var41 >= 0; var41--) {
            p = this.d[var41];
            q = this.e[var41];
            if (q == 0.0) {
               int lx = var41;
               this.H[var41][var41] = 1.0;

               for (int ix = var41 - 1; ix >= 0; ix--) {
                  double wx = this.H[ix][ix] - p;
                  r = 0.0;

                  for (int j = lx; j <= var41; j++) {
                     r += this.H[ix][j] * this.H[j][var41];
                  }

                  if (this.e[ix] < 0.0) {
                     z = wx;
                     s = r;
                  } else {
                     lx = ix;
                     if (this.e[ix] == 0.0) {
                        if (wx != 0.0) {
                           this.H[ix][var41] = -r / wx;
                        } else {
                           this.H[ix][var41] = -r / (eps * norm);
                        }
                     } else {
                        double xx = this.H[ix][ix + 1];
                        double yx = this.H[ix + 1][ix];
                        q = (this.d[ix] - p) * (this.d[ix] - p) + this.e[ix] * this.e[ix];
                        double t = (xx * s - z * r) / q;
                        this.H[ix][var41] = t;
                        if (Math.abs(xx) > Math.abs(z)) {
                           this.H[ix + 1][var41] = (-r - wx * t) / xx;
                        } else {
                           this.H[ix + 1][var41] = (-s - yx * t) / z;
                        }
                     }

                     double tx = Math.abs(this.H[ix][var41]);
                     if (eps * tx * tx > 1.0) {
                        for (int j = ix; j <= var41; j++) {
                           this.H[j][var41] = this.H[j][var41] / tx;
                        }
                     }
                  }
               }
            } else if (q < 0.0) {
               int lx = var41 - 1;
               if (Math.abs(this.H[var41][var41 - 1]) > Math.abs(this.H[var41 - 1][var41])) {
                  this.H[var41 - 1][var41 - 1] = q / this.H[var41][var41 - 1];
                  this.H[var41 - 1][var41] = -(this.H[var41][var41] - p) / this.H[var41][var41 - 1];
               } else {
                  this.cdiv(0.0, -this.H[var41 - 1][var41], this.H[var41 - 1][var41 - 1] - p, q);
                  this.H[var41 - 1][var41 - 1] = this.cdivr;
                  this.H[var41 - 1][var41] = this.cdivi;
               }

               this.H[var41][var41 - 1] = 0.0;
               this.H[var41][var41] = 1.0;

               for (int ix = var41 - 2; ix >= 0; ix--) {
                  double ra = 0.0;
                  double sa = 0.0;

                  for (int j = lx; j <= var41; j++) {
                     ra += this.H[ix][j] * this.H[j][var41 - 1];
                     sa += this.H[ix][j] * this.H[j][var41];
                  }

                  double wx = this.H[ix][ix] - p;
                  if (this.e[ix] < 0.0) {
                     z = wx;
                     r = ra;
                     s = sa;
                  } else {
                     lx = ix;
                     if (this.e[ix] == 0.0) {
                        this.cdiv(-ra, -sa, wx, q);
                        this.H[ix][var41 - 1] = this.cdivr;
                        this.H[ix][var41] = this.cdivi;
                     } else {
                        double xx = this.H[ix][ix + 1];
                        double yx = this.H[ix + 1][ix];
                        double vr = (this.d[ix] - p) * (this.d[ix] - p) + this.e[ix] * this.e[ix] - q * q;
                        double vi = (this.d[ix] - p) * 2.0 * q;
                        if (vr == 0.0 & vi == 0.0) {
                           vr = eps * norm * (Math.abs(wx) + Math.abs(q) + Math.abs(xx) + Math.abs(yx) + Math.abs(z));
                        }

                        this.cdiv(xx * r - z * ra + q * sa, xx * s - z * sa - q * ra, vr, vi);
                        this.H[ix][var41 - 1] = this.cdivr;
                        this.H[ix][var41] = this.cdivi;
                        if (Math.abs(xx) > Math.abs(z) + Math.abs(q)) {
                           this.H[ix + 1][var41 - 1] = (-ra - wx * this.H[ix][var41 - 1] + q * this.H[ix][var41]) / xx;
                           this.H[ix + 1][var41] = (-sa - wx * this.H[ix][var41] - q * this.H[ix][var41 - 1]) / xx;
                        } else {
                           this.cdiv(-r - yx * this.H[ix][var41 - 1], -s - yx * this.H[ix][var41], z, q);
                           this.H[ix + 1][var41 - 1] = this.cdivr;
                           this.H[ix + 1][var41] = this.cdivi;
                        }
                     }

                     double t = Math.max(Math.abs(this.H[ix][var41 - 1]), Math.abs(this.H[ix][var41]));
                     if (eps * t * t > 1.0) {
                        for (int j = ix; j <= var41; j++) {
                           this.H[j][var41 - 1] = this.H[j][var41 - 1] / t;
                           this.H[j][var41] = this.H[j][var41] / t;
                        }
                     }
                  }
               }
            }
         }

         for (int ix = 0; ix < nn; ix++) {
            if (ix < low | ix > high) {
               for (int j = ix; j < nn; j++) {
                  this.V[ix][j] = this.H[ix][j];
               }
            }
         }

         for (int j = nn - 1; j >= low; j--) {
            for (int ixx = low; ixx <= high; ixx++) {
               z = 0.0;

               for (int k = low; k <= Math.min(j, high); k++) {
                  z += this.V[ixx][k] * this.H[k][j];
               }

               this.V[ixx][j] = z;
            }
         }
      }
   }

   public EigenvalueDecomposition(Matrix Arg) {
      double[][] A = Arg.getArray();
      this.n = Arg.getColumnDimension();
      this.V = new double[this.n][this.n];
      this.d = new double[this.n];
      this.e = new double[this.n];
      this.issymmetric = true;

      for (int j = 0; j < this.n & this.issymmetric; j++) {
         for (int i = 0; i < this.n & this.issymmetric; i++) {
            this.issymmetric = A[i][j] == A[j][i];
         }
      }

      if (this.issymmetric) {
         for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
               this.V[i][j] = A[i][j];
            }
         }

         this.tred2();
         this.tql2();
      } else {
         this.H = new double[this.n][this.n];
         this.ort = new double[this.n];

         for (int j = 0; j < this.n; j++) {
            for (int i = 0; i < this.n; i++) {
               this.H[i][j] = A[i][j];
            }
         }

         this.orthes();
         this.hqr2();
      }
   }

   public Matrix getV() {
      return new Matrix(this.V, this.n, this.n);
   }

   public double[] getRealEigenvalues() {
      return this.d;
   }

   public double[] getImagEigenvalues() {
      return this.e;
   }

   public Matrix getD() {
      Matrix X = new Matrix(this.n, this.n);
      double[][] D = X.getArray();

      for (int i = 0; i < this.n; i++) {
         for (int j = 0; j < this.n; j++) {
            D[i][j] = 0.0;
         }

         D[i][i] = this.d[i];
         if (this.e[i] > 0.0) {
            D[i][i + 1] = this.e[i];
         } else if (this.e[i] < 0.0) {
            D[i][i - 1] = this.e[i];
         }
      }

      return X;
   }
}
