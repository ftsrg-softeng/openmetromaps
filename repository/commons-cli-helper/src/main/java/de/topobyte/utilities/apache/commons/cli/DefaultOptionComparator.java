package de.topobyte.utilities.apache.commons.cli;

import java.util.Comparator;
import org.apache.commons.cli.Option;

public class DefaultOptionComparator implements Comparator<Option> {
   public int compare(Option opt1, Option opt2) {
      String o1 = OptionUtil.getKey(opt1);
      String o2 = OptionUtil.getKey(opt2);
      return o1.compareTo(o2);
   }
}
