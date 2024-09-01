package de.topobyte.xml4jah.dom;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.text.translate.AggregateTranslator;
import org.apache.commons.text.translate.CharSequenceTranslator;
import org.apache.commons.text.translate.LookupTranslator;
import org.apache.commons.text.translate.NumericEntityEscaper;
import org.apache.commons.text.translate.UnicodeUnpairedSurrogateRemover;

public class Escaping {
   public static final Map<CharSequence, CharSequence> BASIC_ESCAPE;
   public static final Map<CharSequence, CharSequence> APOS_ESCAPE;
   public static final Map<CharSequence, CharSequence> REALLY_BASIC_ESCAPE;
   public static final CharSequenceTranslator ESCAPE_XML10;

   public static String escapeXml10(String input) {
      return ESCAPE_XML10.translate(input);
   }

   static {
      Map<CharSequence, CharSequence> initialMap = new HashMap<>();
      initialMap.put("\"", "&quot;");
      initialMap.put("&", "&amp;");
      initialMap.put("<", "&lt;");
      initialMap.put(">", "&gt;");
      BASIC_ESCAPE = Collections.unmodifiableMap(initialMap);
      initialMap = new HashMap<>();
      initialMap.put("'", "&apos;");
      APOS_ESCAPE = Collections.unmodifiableMap(initialMap);
      initialMap = new HashMap<>();
      initialMap.put("&", "&amp;");
      initialMap.put("<", "&lt;");
      REALLY_BASIC_ESCAPE = Collections.unmodifiableMap(initialMap);
      initialMap = new HashMap<>();
      initialMap.put("\u0000", "");
      initialMap.put("\u0001", "");
      initialMap.put("\u0002", "");
      initialMap.put("\u0003", "");
      initialMap.put("\u0004", "");
      initialMap.put("\u0005", "");
      initialMap.put("\u0006", "");
      initialMap.put("\u0007", "");
      initialMap.put("\b", "");
      initialMap.put("\u000b", "");
      initialMap.put("\f", "");
      initialMap.put("\u000e", "");
      initialMap.put("\u000f", "");
      initialMap.put("\u0010", "");
      initialMap.put("\u0011", "");
      initialMap.put("\u0012", "");
      initialMap.put("\u0013", "");
      initialMap.put("\u0014", "");
      initialMap.put("\u0015", "");
      initialMap.put("\u0016", "");
      initialMap.put("\u0017", "");
      initialMap.put("\u0018", "");
      initialMap.put("\u0019", "");
      initialMap.put("\u001a", "");
      initialMap.put("\u001b", "");
      initialMap.put("\u001c", "");
      initialMap.put("\u001d", "");
      initialMap.put("\u001e", "");
      initialMap.put("\u001f", "");
      initialMap.put("\ufffe", "");
      initialMap.put("\uffff", "");
      ESCAPE_XML10 = new AggregateTranslator(
         new CharSequenceTranslator[]{
            new LookupTranslator(REALLY_BASIC_ESCAPE),
            new LookupTranslator(Collections.unmodifiableMap(initialMap)),
            NumericEntityEscaper.between(127, 132),
            NumericEntityEscaper.between(134, 159),
            new UnicodeUnpairedSurrogateRemover()
         }
      );
   }
}
