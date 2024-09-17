package de.topobyte.osm4j.tbo.access;

import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.tbo.data.FileHeader;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WriterUtil {
   public static FileHeader createHeader(boolean hasMetadata, OsmBounds bounds) {
      Map<String, String> tags = new HashMap<>();
      FileHeader header = new FileHeader(2, tags, hasMetadata, bounds);
      Date date = new Date();
      DateFormat dateFormat = DateFormat.getDateTimeInstance(2, 2, Locale.US);
      String formattedDate = dateFormat.format(date);
      tags.put("creation-time", formattedDate);
      return header;
   }
}
