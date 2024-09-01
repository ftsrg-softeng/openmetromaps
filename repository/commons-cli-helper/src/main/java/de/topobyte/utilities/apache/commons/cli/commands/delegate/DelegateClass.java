package de.topobyte.utilities.apache.commons.cli.commands.delegate;

public class DelegateClass implements Delegate {
   private Class<?> clazz;
   private boolean passName;

   public DelegateClass(Class<?> clazz, boolean passName) {
      this.clazz = clazz;
      this.passName = passName;
   }

   public Class<?> getClazz() {
      return this.clazz;
   }

   public boolean isPassName() {
      return this.passName;
   }
}
