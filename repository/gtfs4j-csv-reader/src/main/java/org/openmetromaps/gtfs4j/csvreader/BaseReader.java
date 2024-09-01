package org.openmetromaps.gtfs4j.csvreader;

import au.com.bytecode.opencsv.CSVReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.openmetromaps.gtfs4j.csv.Field;

public abstract class BaseReader<S, T extends Enum<T> & Field> implements Closeable {
   private Class<T> clazz;
   protected Map<T, Integer> idx;
   protected List<T> fields;
   protected CSVReader csvReader;

   public BaseReader(Reader reader, Class<T> clazz) throws IOException {
      this.clazz = clazz;
      this.idx = new EnumMap<>(clazz);
      this.fields = new ArrayList<>();
      this.csvReader = Util.defaultCsvReader(reader);
      String[] head = this.csvReader.readNext();
      this.initIndexes(head);
   }

   protected boolean hasField(T field) {
      return this.idx.get(field) >= 0;
   }

   public void initIndexes(String[] head) {
      for (int i = 0; i < head.length; i++) {
         this.fields.add(null);
      }

      for (T field : this.clazz.getEnumConstants()) {
         int index = Util.getIndex(head, field.getCsvName());
         this.idx.put(field, index);
         if (index >= 0) {
            this.fields.set(index, field);
         }
      }
   }

   public List<T> getFields() {
      return this.fields;
   }

   public abstract List<S> readAll() throws IOException;

   @Override
   public void close() throws IOException {
      this.csvReader.close();
   }
}
