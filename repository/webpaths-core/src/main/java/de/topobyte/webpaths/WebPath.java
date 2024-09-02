package de.topobyte.webpaths;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import de.topobyte.collections.util.ListUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WebPath implements Iterable<String> {
   static Splitter SPLITTER = Splitter.on('/').omitEmptyStrings();
   static Joiner JOINER = Joiner.on('/');
   private int ups;
   private boolean isDir;
   private List<String> components = new ArrayList<>();

   public WebPath(int ups, Iterable<String> components, boolean isDir) {
      this.ups = ups;
      this.isDir = isDir;
      Iterables.addAll(this.components, components);
   }

   public int getUps() {
      return this.ups;
   }

   public boolean isDir() {
      return this.isDir;
   }

   void setDir(boolean isDir) {
      this.isDir = isDir;
   }

   public int getNameCount() {
      return this.components.size();
   }

   public String getName(int i) {
      return this.components.get(i);
   }

   public String getFileName() {
      return (String)ListUtil.last(this.components);
   }

   @Override
   public Iterator<String> iterator() {
      return this.components.iterator();
   }

   public WebPath resolve(String other) {
      WebPath tOther = WebPaths.get(other);
      return this.resolve(tOther);
   }

   public WebPath resolve(WebPath other) {
      int otherUps = other.getUps();
      List<String> comps = new ArrayList<>();
      int remainingUps;
      if (otherUps == this.components.size()) {
         remainingUps = 0;
      } else if (otherUps > this.components.size()) {
         remainingUps = otherUps - this.components.size();
      } else {
         remainingUps = 0;
         int n = this.components.size() - otherUps;
         comps.addAll(this.components.subList(0, n));
      }

      if (!this.isDir && !comps.isEmpty()) {
         ListUtil.removeLast(comps);
      }

      int ups = this.ups + remainingUps;
      Iterables.addAll(comps, other);
      return new WebPath(ups, comps, other.isDir());
   }

   public WebPath relativize(WebPath other) {
      if (this.ups != other.ups) {
         int remaining = other.ups - Math.min(this.ups, other.ups);
         return new WebPath(remaining, other.components, other.isDir);
      } else {
         int n1 = this.components.size();
         int n2 = other.components.size();
         int min = Math.min(n1, n2);
         int equal = 0;

         for (int i = 0; i < min; i++) {
            boolean compIsDir1 = i < n1 - 1 | this.isDir;
            boolean compIsDir2 = i < n2 - 1 | other.isDir;
            String comp1 = this.components.get(i);
            String comp2 = other.components.get(i);
            if (!comp1.equals(comp2) || compIsDir1 != compIsDir2) {
               break;
            }

            equal++;
         }

         int nup;
         if (this.isDir) {
            nup = this.components.size() - equal;
         } else {
            nup = this.components.size() - equal - 1;
         }

         List<String> comps = other.components.subList(equal, other.components.size());
         return new WebPath(nup, comps, other.isDir);
      }
   }

   @Override
   public String toString() {
      StringBuilder buffer = new StringBuilder();
      if (this.ups > 0) {
         buffer.append("..");
      }

      for (int i = 1; i < this.ups; i++) {
         buffer.append("/..");
      }

      if (this.ups > 0 && !this.components.isEmpty()) {
         buffer.append("/");
      }

      JOINER.appendTo(buffer, this.components);
      if (this.isDir) {
         buffer.append("/");
      }

      return buffer.toString();
   }

   @Override
   public int hashCode() {
      int prime = 31;
      int result = 1;
      result = 31 * result + this.components.hashCode();
      result = 31 * result + (this.isDir ? 1231 : 1237);
      return 31 * result + this.ups;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         WebPath other = (WebPath)obj;
         if (this.isDir != other.isDir) {
            return false;
         } else {
            return this.ups != other.ups ? false : this.components.equals(other.components);
         }
      }
   }

   public WebPath subPathFrom(int from) {
      if (from < 0) {
         throw new IllegalArgumentException();
      } else if (from > this.ups + this.components.size()) {
         throw new IllegalArgumentException();
      } else if (from == 0) {
         return this;
      } else {
         List<String> parts = new ArrayList<>();
         parts.addAll(this.components);
         if (from <= this.ups) {
            int rem = this.ups - from;
            return new WebPath(rem, parts, this.isDir);
         } else {
            int rem = from - this.ups;
            return rem == parts.size() ? new WebPath(0, new ArrayList<String>(), false) : new WebPath(0, parts.subList(rem, parts.size()), this.isDir);
         }
      }
   }

   public WebPath subPathTo(int to) {
      if (to <= 0) {
         throw new IllegalArgumentException();
      } else if (to > this.ups + this.components.size()) {
         throw new IllegalArgumentException();
      } else if (to <= this.ups) {
         return new WebPath(to, new ArrayList<String>(), true);
      } else {
         List<String> parts = new ArrayList<>();
         parts.addAll(this.components);
         int rem = to - this.ups;
         return rem < parts.size() ? new WebPath(this.ups, parts.subList(0, rem), true) : new WebPath(this.ups, parts, this.isDir);
      }
   }
}
