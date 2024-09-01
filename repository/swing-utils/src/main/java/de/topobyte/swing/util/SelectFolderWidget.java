package de.topobyte.swing.util;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class SelectFolderWidget extends JPanel {
   private static final long serialVersionUID = 4065990233323219436L;
   private JTextField inputLocation;

   public SelectFolderWidget(File location) {
      this.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      this.inputLocation = new JTextField();
      this.inputLocation.setText(location.getAbsolutePath());
      JButton buttonPickLocation = new JButton("select");
      c.fill = 1;
      c.weightx = 1.0;
      this.add(this.inputLocation, c);
      c.weightx = 0.0;
      this.add(buttonPickLocation, c);
      buttonPickLocation.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            SelectFolderWidget.this.pickFolder();
         }
      });
   }

   @Override
   public void setEnabled(boolean enabled) {
      super.setEnabled(enabled);

      for (Component c : this.getComponents()) {
         c.setEnabled(enabled);
      }
   }

   public File getSelectedFolder() {
      return new File(this.inputLocation.getText());
   }

   protected void pickFolder() {
      JFileChooser chooser = new JFileChooser();
      chooser.setAcceptAllFileFilterUsed(false);
      chooser.setFileSelectionMode(1);
      chooser.setFileFilter(new FileFilter() {
         @Override
         public String getDescription() {
            return "directories";
         }

         @Override
         public boolean accept(File f) {
            return f.isDirectory();
         }
      });
      int ret = chooser.showOpenDialog(this);
      if (ret == 0) {
         File file = chooser.getSelectedFile();
         this.inputLocation.setText(file.getAbsolutePath());
      }
   }
}
