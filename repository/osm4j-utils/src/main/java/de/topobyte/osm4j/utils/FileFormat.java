package de.topobyte.osm4j.utils;

import java.util.HashMap;
import java.util.Map;

public enum FileFormat {
   XML,
   TBO,
   PBF;

   private static Map<String, FileFormat> formatSwitch = new HashMap<>();
   private static String supportedFormats = "xml, tbo, pbf";

   public static FileFormat parseFileFormat(String format) {
      return formatSwitch.get(format);
   }

   public static String getHumanReadableListOfSupportedFormats() {
      return supportedFormats;
   }

   static {
      formatSwitch.put("xml", XML);
      formatSwitch.put("tbo", TBO);
      formatSwitch.put("pbf", PBF);
   }
}
