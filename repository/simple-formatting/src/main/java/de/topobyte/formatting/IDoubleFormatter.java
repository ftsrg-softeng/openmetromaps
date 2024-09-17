package de.topobyte.formatting;

public interface IDoubleFormatter extends IFormatter {
   String format(double var1);

   void format(StringBuilder var1, double var2);
}
