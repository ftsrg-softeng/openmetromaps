package jama;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;

public class MatrixIO {
   public static void print(int w, int d, Matrix m) {
      print(new PrintWriter(System.out, true), w, d, m);
   }

   public static void print(PrintWriter output, int w, int d, Matrix m) {
      DecimalFormat format = new DecimalFormat();
      format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
      format.setMinimumIntegerDigits(1);
      format.setMaximumFractionDigits(d);
      format.setMinimumFractionDigits(d);
      format.setGroupingUsed(false);
      print(output, format, w + 2, m);
   }

   public static void print(NumberFormat format, int width, Matrix m) {
      print(new PrintWriter(System.out, true), format, width, m);
   }

   public static void print(PrintWriter output, NumberFormat format, int width, Matrix m) {
      output.println();

      for (int i = 0; i < m.getRowDimension(); i++) {
         for (int j = 0; j < m.getColumnDimension(); j++) {
            String s = format.format(m.get(i, j));
            int padding = Math.max(1, width - s.length());

            for (int k = 0; k < padding; k++) {
               output.print(' ');
            }

            output.print(s);
         }

         output.println();
      }

      output.println();
   }

   public static Matrix read(BufferedReader input) throws IOException {
      StreamTokenizer tokenizer = new StreamTokenizer(input);
      tokenizer.resetSyntax();
      tokenizer.wordChars(0, 255);
      tokenizer.whitespaceChars(0, 32);
      tokenizer.eolIsSignificant(true);
      Vector<Double> vD = new Vector<>();

      while (tokenizer.nextToken() == 10) {
      }

      if (tokenizer.ttype == -1) {
         throw new IOException("Unexpected EOF on matrix read.");
      } else {
         do {
            vD.addElement(Double.valueOf(tokenizer.sval));
         } while (tokenizer.nextToken() == -3);

         int n = vD.size();
         double[] row = new double[n];

         for (int j = 0; j < n; j++) {
            row[j] = vD.elementAt(j);
         }

         Vector<double[]> v = new Vector<>();
         v.addElement(row);

         label34:
         while (tokenizer.nextToken() == -3) {
            v.addElement(row = new double[n]);
            int j = 0;

            while (j < n) {
               row[j++] = Double.valueOf(tokenizer.sval);
               if (tokenizer.nextToken() != -3) {
                  if (j < n) {
                     throw new IOException("Row " + v.size() + " is too short.");
                  }
                  continue label34;
               }
            }

            throw new IOException("Row " + v.size() + " is too long.");
         }

         int m = v.size();
         double[][] A = new double[m][];
         v.copyInto(A);
         return new Matrix(A);
      }
   }
}
