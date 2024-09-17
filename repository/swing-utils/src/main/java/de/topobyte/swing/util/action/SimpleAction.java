package de.topobyte.swing.util.action;

import de.topobyte.swing.util.ImageLoader;
import javax.swing.AbstractAction;
import javax.swing.Icon;

public abstract class SimpleAction extends AbstractAction {
   private static final long serialVersionUID = 1727617884915345905L;
   protected String name = null;
   protected String description = null;
   protected Icon icon = null;

   public SimpleAction() {
   }

   public SimpleAction(String name, String description) {
      this.name = name;
      this.description = description;
   }

   public SimpleAction(String name, String description, Icon icon) {
      this.name = name;
      this.description = description;
      this.icon = icon;
   }

   public SimpleAction(String name, String description, String resourceIcon) {
      this.name = name;
      this.description = description;
      this.icon = ImageLoader.load(resourceIcon);
   }

   @Override
   public Object getValue(String key) {
      if (key.equals("SmallIcon")) {
         return this.icon;
      } else if (key.equals("Name")) {
         return this.name;
      } else {
         return key.equals("ShortDescription") ? this.description : null;
      }
   }

   protected void setName(String name) {
      this.name = name;
   }

   protected void setDescription(String description) {
      this.description = description;
   }

   protected void setIcon(Icon icon) {
      this.icon = icon;
   }

   protected void setIcon(String resourceIcon) {
      this.icon = ImageLoader.load(resourceIcon);
   }
}
