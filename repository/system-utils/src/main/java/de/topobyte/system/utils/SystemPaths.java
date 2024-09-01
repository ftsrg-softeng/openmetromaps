package de.topobyte.system.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SystemPaths {
   public static final String STRING_HOME = System.getProperty("user.home");
   public static final String STRING_CWD = System.getProperty("user.dir");
   public static final Path HOME = Paths.get(STRING_HOME);
   public static final Path CWD = Paths.get(STRING_CWD);
}
