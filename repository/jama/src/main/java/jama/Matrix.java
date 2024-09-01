package jama;

import jama.util.Maths;
import java.io.Serializable;

public class Matrix implements Cloneable, Serializable {
   private double[][] A;
   private int m;
   private int n;
   private static final long serialVersionUID = 1L;

   public Matrix(int m, int n) {
      this.m = m;
      this.n = n;
      this.A = new double[m][n];
   }

   public Matrix(int m, int n, double s) {
      this.m = m;
      this.n = n;
      this.A = new double[m][n];

      for (int i = 0; i < m; i++) {
         for (int j = 0; j < n; j++) {
            this.A[i][j] = s;
         }
      }
   }

   public Matrix(double[][] A) {
      this.m = A.length;
      this.n = A[0].length;

      for (int i = 0; i < this.m; i++) {
         if (A[i].length != this.n) {
            throw new IllegalArgumentException("All rows must have the same length.");
         }
      }

      this.A = A;
   }

   public Matrix(double[][] A, int m, int n) {
      this.A = A;
      this.m = m;
      this.n = n;
   }

   public Matrix(double[] vals, int m) {
      this.m = m;
      this.n = m != 0 ? vals.length / m : 0;
      if (m * this.n != vals.length) {
         throw new IllegalArgumentException("Array length must be a multiple of m.");
      } else {
         this.A = new double[m][this.n];

         for (int i = 0; i < m; i++) {
            for (int j = 0; j < this.n; j++) {
               this.A[i][j] = vals[i + j * m];
            }
         }
      }
   }

   public static Matrix constructWithCopy(double[][] A) {
      int m = A.length;
      int n = A[0].length;
      Matrix X = new Matrix(m, n);
      double[][] C = X.getArray();

      for (int i = 0; i < m; i++) {
         if (A[i].length != n) {
            throw new IllegalArgumentException("All rows must have the same length.");
         }

         for (int j = 0; j < n; j++) {
            C[i][j] = A[i][j];
         }
      }

      return X;
   }

   public Matrix copy() {
      Matrix X = new Matrix(this.m, this.n);
      double[][] C = X.getArray();

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            C[i][j] = this.A[i][j];
         }
      }

      return X;
   }

   @Override
   public Object clone() {
      return this.copy();
   }

   public double[][] getArray() {
      return this.A;
   }

   public double[][] getArrayCopy() {
      double[][] C = new double[this.m][this.n];

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            C[i][j] = this.A[i][j];
         }
      }

      return C;
   }

   public double[] getColumnPackedCopy() {
      double[] vals = new double[this.m * this.n];

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            vals[i + j * this.m] = this.A[i][j];
         }
      }

      return vals;
   }

   public double[] getRowPackedCopy() {
      double[] vals = new double[this.m * this.n];

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            vals[i * this.n + j] = this.A[i][j];
         }
      }

      return vals;
   }

   public int getRowDimension() {
      return this.m;
   }

   public int getColumnDimension() {
      return this.n;
   }

   public double get(int i, int j) {
      return this.A[i][j];
   }

   public Matrix getMatrix(int i0, int i1, int j0, int j1) {
      Matrix X = new Matrix(i1 - i0 + 1, j1 - j0 + 1);
      double[][] B = X.getArray();

      try {
         for (int i = i0; i <= i1; i++) {
            for (int j = j0; j <= j1; j++) {
               B[i - i0][j - j0] = this.A[i][j];
            }
         }

         return X;
      } catch (ArrayIndexOutOfBoundsException var9) {
         throw new ArrayIndexOutOfBoundsException("Submatrix indices");
      }
   }

   public Matrix getMatrix(int[] r, int[] c) {
      Matrix X = new Matrix(r.length, c.length);
      double[][] B = X.getArray();

      try {
         for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < c.length; j++) {
               B[i][j] = this.A[r[i]][c[j]];
            }
         }

         return X;
      } catch (ArrayIndexOutOfBoundsException var7) {
         throw new ArrayIndexOutOfBoundsException("Submatrix indices");
      }
   }

   public Matrix getMatrix(int i0, int i1, int[] c) {
      Matrix X = new Matrix(i1 - i0 + 1, c.length);
      double[][] B = X.getArray();

      try {
         for (int i = i0; i <= i1; i++) {
            for (int j = 0; j < c.length; j++) {
               B[i - i0][j] = this.A[i][c[j]];
            }
         }

         return X;
      } catch (ArrayIndexOutOfBoundsException var8) {
         throw new ArrayIndexOutOfBoundsException("Submatrix indices");
      }
   }

   public Matrix getMatrix(int[] r, int j0, int j1) {
      Matrix X = new Matrix(r.length, j1 - j0 + 1);
      double[][] B = X.getArray();

      try {
         for (int i = 0; i < r.length; i++) {
            for (int j = j0; j <= j1; j++) {
               B[i][j - j0] = this.A[r[i]][j];
            }
         }

         return X;
      } catch (ArrayIndexOutOfBoundsException var8) {
         throw new ArrayIndexOutOfBoundsException("Submatrix indices");
      }
   }

   public void set(int i, int j, double s) {
      this.A[i][j] = s;
   }

   public void setMatrix(int i0, int i1, int j0, int j1, Matrix X) {
      try {
         for (int i = i0; i <= i1; i++) {
            for (int j = j0; j <= j1; j++) {
               this.A[i][j] = X.get(i - i0, j - j0);
            }
         }
      } catch (ArrayIndexOutOfBoundsException var8) {
         throw new ArrayIndexOutOfBoundsException("Submatrix indices");
      }
   }

   public void setMatrix(int[] r, int[] c, Matrix X) {
      try {
         for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < c.length; j++) {
               this.A[r[i]][c[j]] = X.get(i, j);
            }
         }
      } catch (ArrayIndexOutOfBoundsException var6) {
         throw new ArrayIndexOutOfBoundsException("Submatrix indices");
      }
   }

   public void setMatrix(int[] r, int j0, int j1, Matrix X) {
      try {
         for (int i = 0; i < r.length; i++) {
            for (int j = j0; j <= j1; j++) {
               this.A[r[i]][j] = X.get(i, j - j0);
            }
         }
      } catch (ArrayIndexOutOfBoundsException var7) {
         throw new ArrayIndexOutOfBoundsException("Submatrix indices");
      }
   }

   public void setMatrix(int i0, int i1, int[] c, Matrix X) {
      try {
         for (int i = i0; i <= i1; i++) {
            for (int j = 0; j < c.length; j++) {
               this.A[i][c[j]] = X.get(i - i0, j);
            }
         }
      } catch (ArrayIndexOutOfBoundsException var7) {
         throw new ArrayIndexOutOfBoundsException("Submatrix indices");
      }
   }

   public Matrix transpose() {
      Matrix X = new Matrix(this.n, this.m);
      double[][] C = X.getArray();

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            C[j][i] = this.A[i][j];
         }
      }

      return X;
   }

   public double norm1() {
      double f = 0.0;

      for (int j = 0; j < this.n; j++) {
         double s = 0.0;

         for (int i = 0; i < this.m; i++) {
            s += Math.abs(this.A[i][j]);
         }

         f = Math.max(f, s);
      }

      return f;
   }

   public double norm2() {
      return new SingularValueDecomposition(this).norm2();
   }

   public double normInf() {
      double f = 0.0;

      for (int i = 0; i < this.m; i++) {
         double s = 0.0;

         for (int j = 0; j < this.n; j++) {
            s += Math.abs(this.A[i][j]);
         }

         f = Math.max(f, s);
      }

      return f;
   }

   public double normF() {
      double f = 0.0;

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            f = Maths.hypot(f, this.A[i][j]);
         }
      }

      return f;
   }

   public Matrix uminus() {
      Matrix X = new Matrix(this.m, this.n);
      double[][] C = X.getArray();

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            C[i][j] = -this.A[i][j];
         }
      }

      return X;
   }

   public Matrix plus(Matrix B) {
      this.checkMatrixDimensions(B);
      Matrix X = new Matrix(this.m, this.n);
      double[][] C = X.getArray();

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            C[i][j] = this.A[i][j] + B.A[i][j];
         }
      }

      return X;
   }

   public Matrix plusEquals(Matrix B) {
      this.checkMatrixDimensions(B);

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            this.A[i][j] = this.A[i][j] + B.A[i][j];
         }
      }

      return this;
   }

   public Matrix minus(Matrix B) {
      this.checkMatrixDimensions(B);
      Matrix X = new Matrix(this.m, this.n);
      double[][] C = X.getArray();

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            C[i][j] = this.A[i][j] - B.A[i][j];
         }
      }

      return X;
   }

   public Matrix minusEquals(Matrix B) {
      this.checkMatrixDimensions(B);

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            this.A[i][j] = this.A[i][j] - B.A[i][j];
         }
      }

      return this;
   }

   public Matrix arrayTimes(Matrix B) {
      this.checkMatrixDimensions(B);
      Matrix X = new Matrix(this.m, this.n);
      double[][] C = X.getArray();

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            C[i][j] = this.A[i][j] * B.A[i][j];
         }
      }

      return X;
   }

   public Matrix arrayTimesEquals(Matrix B) {
      this.checkMatrixDimensions(B);

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            this.A[i][j] = this.A[i][j] * B.A[i][j];
         }
      }

      return this;
   }

   public Matrix arrayRightDivide(Matrix B) {
      this.checkMatrixDimensions(B);
      Matrix X = new Matrix(this.m, this.n);
      double[][] C = X.getArray();

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            C[i][j] = this.A[i][j] / B.A[i][j];
         }
      }

      return X;
   }

   public Matrix arrayRightDivideEquals(Matrix B) {
      this.checkMatrixDimensions(B);

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            this.A[i][j] = this.A[i][j] / B.A[i][j];
         }
      }

      return this;
   }

   public Matrix arrayLeftDivide(Matrix B) {
      this.checkMatrixDimensions(B);
      Matrix X = new Matrix(this.m, this.n);
      double[][] C = X.getArray();

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            C[i][j] = B.A[i][j] / this.A[i][j];
         }
      }

      return X;
   }

   public Matrix arrayLeftDivideEquals(Matrix B) {
      this.checkMatrixDimensions(B);

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            this.A[i][j] = B.A[i][j] / this.A[i][j];
         }
      }

      return this;
   }

   public Matrix times(double s) {
      Matrix X = new Matrix(this.m, this.n);
      double[][] C = X.getArray();

      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            C[i][j] = s * this.A[i][j];
         }
      }

      return X;
   }

   public Matrix timesEquals(double s) {
      for (int i = 0; i < this.m; i++) {
         for (int j = 0; j < this.n; j++) {
            this.A[i][j] = s * this.A[i][j];
         }
      }

      return this;
   }

   public Matrix times(Matrix B) {
      if (B.m != this.n) {
         throw new IllegalArgumentException("Matrix inner dimensions must agree.");
      } else {
         Matrix X = new Matrix(this.m, B.n);
         double[][] C = X.getArray();
         double[] Bcolj = new double[this.n];

         for (int j = 0; j < B.n; j++) {
            for (int k = 0; k < this.n; k++) {
               Bcolj[k] = B.A[k][j];
            }

            for (int i = 0; i < this.m; i++) {
               double[] Arowi = this.A[i];
               double s = 0.0;

               for (int k = 0; k < this.n; k++) {
                  s += Arowi[k] * Bcolj[k];
               }

               C[i][j] = s;
            }
         }

         return X;
      }
   }

   public LUDecomposition lu() {
      return new LUDecomposition(this);
   }

   public QRDecomposition qr() {
      return new QRDecomposition(this);
   }

   public CholeskyDecomposition chol() {
      return new CholeskyDecomposition(this);
   }

   public SingularValueDecomposition svd() {
      return new SingularValueDecomposition(this);
   }

   public EigenvalueDecomposition eig() {
      return new EigenvalueDecomposition(this);
   }

   public Matrix solve(Matrix B) {
      return this.m == this.n ? new LUDecomposition(this).solve(B) : new QRDecomposition(this).solve(B);
   }

   public Matrix solveTranspose(Matrix B) {
      return this.transpose().solve(B.transpose());
   }

   public Matrix inverse() {
      return this.solve(identity(this.m, this.m));
   }

   public double det() {
      return new LUDecomposition(this).det();
   }

   public int rank() {
      return new SingularValueDecomposition(this).rank();
   }

   public double cond() {
      return new SingularValueDecomposition(this).cond();
   }

   public double trace() {
      double t = 0.0;

      for (int i = 0; i < Math.min(this.m, this.n); i++) {
         t += this.A[i][i];
      }

      return t;
   }

   public static Matrix random(int m, int n) {
      Matrix A = new Matrix(m, n);
      double[][] X = A.getArray();

      for (int i = 0; i < m; i++) {
         for (int j = 0; j < n; j++) {
            X[i][j] = Math.random();
         }
      }

      return A;
   }

   public static Matrix identity(int m, int n) {
      Matrix A = new Matrix(m, n);
      double[][] X = A.getArray();

      for (int i = 0; i < m; i++) {
         for (int j = 0; j < n; j++) {
            X[i][j] = i == j ? 1.0 : 0.0;
         }
      }

      return A;
   }

   private void checkMatrixDimensions(Matrix B) {
      if (B.m != this.m || B.n != this.n) {
         throw new IllegalArgumentException("Matrix dimensions must agree.");
      }
   }
}
