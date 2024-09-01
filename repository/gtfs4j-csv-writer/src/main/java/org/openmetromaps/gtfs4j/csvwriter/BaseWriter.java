package org.openmetromaps.gtfs4j.csvwriter;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import org.openmetromaps.gtfs4j.csv.Field;

public abstract class BaseWriter<S, T extends Enum<T> & Field> implements Closeable {
   protected Class<T> clazz;
   protected List<T> fields;
   protected CSVWriter csvWriter;
   protected String[] values;

   public BaseWriter(Writer writer, Class<T> clazz, List<T> fields) {
      this.clazz = clazz;
      this.fields = fields;
      this.csvWriter = new CSVWriter(writer);
      this.values = new String[fields.size()];

      for (int i = 0; i < fields.size(); i++) {
         T field = fields.get(i);
         this.values[i] = field.getCsvName();
      }

      this.csvWriter.writeNext(this.values);
   }

   public abstract String get(S var1, T var2);

   public void write(S object) {
      this.writeDefault(object);
   }

   public void writeDefault(S object) {
      for (int i = 0; i < this.fields.size(); i++) {
         this.values[i] = this.get(object, this.fields.get(i));
      }

      this.csvWriter.writeNext(this.values);
   }

   @Override
   public void close() throws IOException {
      this.csvWriter.close();
   }

   public void flush() throws IOException {
      this.csvWriter.flush();
   }
}
