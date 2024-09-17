package de.topobyte.formatting;

import java.util.ArrayList;
import java.util.List;

public class Formatter {
   private List<IFormatter> formatters = new ArrayList<>();

   public Formatter(List<IFormatter> formatters) {
      this.formatters = formatters;
   }

   public String format(Object... args) {
      StringBuilder buffer = new StringBuilder();
      this.formatBuilder(buffer, args);
      return buffer.toString();
   }

   public void formatBuilder(StringBuilder buffer, Object... args) {
      int idx = 0;

      for (IFormatter formatter : this.formatters) {
         Type type = formatter.getType();
         switch (type) {
            case NONE: {
               IVoidFormatter f = (IVoidFormatter)formatter;
               f.format(buffer);
               break;
            }
            case STRING: {
               IStringFormatter f = (IStringFormatter)formatter;
               f.format(buffer, (String)args[idx++]);
               break;
            }
            case BOOLEAN: {
               IBooleanFormatter f = (IBooleanFormatter)formatter;
               f.format(buffer, (Boolean)args[idx++]);
               break;
            }
            case INT: {
               IIntFormatter f = (IIntFormatter)formatter;
               Object argxx = args[idx++];
               if (argxx instanceof Integer) {
                  f.format(buffer, (Integer)argxx);
               } else if (argxx instanceof Short) {
                  f.format(buffer, (Short)argxx);
               } else {
                  if (!(argxx instanceof Byte)) {
                     throw new IllegalArgumentException("integral argument expected");
                  }

                  f.format(buffer, (Byte)argxx);
               }
               break;
            }
            case LONG: {
               ILongFormatter f = (ILongFormatter)formatter;
               Object argx = args[idx++];
               if (argx instanceof Long) {
                  f.format(buffer, (Long)argx);
               } else if (argx instanceof Integer) {
                  f.format(buffer, (long)((Integer)argx).intValue());
               } else if (argx instanceof Short) {
                  f.format(buffer, (long)((Short)argx).shortValue());
               } else {
                  if (!(argx instanceof Byte)) {
                     throw new IllegalArgumentException("integral argument expected");
                  }

                  f.format(buffer, (long)((Byte)argx).byteValue());
               }
               break;
            }
            case DOUBLE: {
               IDoubleFormatter f = (IDoubleFormatter)formatter;
               Object arg = args[idx++];
               if (arg instanceof Double) {
                  f.format(buffer, (Double)arg);
               } else if (arg instanceof Float) {
                  f.format(buffer, (double)((Float)arg).floatValue());
               } else if (arg instanceof Long) {
                  f.format(buffer, (double)((Long)arg).longValue());
               } else if (arg instanceof Integer) {
                  f.format(buffer, (double)((Integer)arg).intValue());
               } else if (arg instanceof Short) {
                  f.format(buffer, (double)((Short)arg).shortValue());
               } else {
                  if (!(arg instanceof Byte)) {
                     throw new IllegalArgumentException("numeric argument expected");
                  }

                  f.format(buffer, (double)((Byte)arg).byteValue());
               }
            }
         }
      }
   }
}
