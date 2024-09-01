package de.topobyte.osm4j.extra.idbboxlist;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.extra.datatree.BoxUtil;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class IdBboxUtil {
   public static List<IdBboxEntry> read(InputStream input) throws IOException {
      IdBboxListInputStream bboxes = new IdBboxListInputStream(input);
      List<IdBboxEntry> entries = new ArrayList<>();

      while (true) {
         try {
            entries.add(bboxes.next());
         } catch (EOFException var4) {
            return entries;
         }
      }
   }

   public static List<IdBboxEntry> read(Path path) throws IOException {
      InputStream input = StreamUtil.bufferedInputStream(path);
      List<IdBboxEntry> entries = read(input);
      input.close();
      return entries;
   }

   public static List<IdBboxEntry> read(File file) throws IOException {
      InputStream input = StreamUtil.bufferedInputStream(file);
      List<IdBboxEntry> entries = read(input);
      input.close();
      return entries;
   }

   public static List<Geometry> readBoxes(InputStream input) throws IOException {
      GeometryFactory factory = new GeometryFactory();
      List<Geometry> boxList = new ArrayList<>();
      IdBboxListInputStream bboxes = new IdBboxListInputStream(input);

      while (true) {
         try {
            IdBboxEntry entry = bboxes.next();
            Envelope e = entry.getEnvelope().intersection(BoxUtil.WORLD_BOUNDS);
            boxList.add(factory.toGeometry(e));
         } catch (EOFException var6) {
            return boxList;
         }
      }
   }

   public static List<Geometry> readBoxes(Path path) throws IOException {
      InputStream input = StreamUtil.bufferedInputStream(path);
      List<Geometry> entries = readBoxes(input);
      input.close();
      return entries;
   }

   public static List<Geometry> readBoxes(File file) throws IOException {
      InputStream input = StreamUtil.bufferedInputStream(file);
      List<Geometry> entries = readBoxes(input);
      input.close();
      return entries;
   }
}
