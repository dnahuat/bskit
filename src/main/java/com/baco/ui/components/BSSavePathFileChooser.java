package com.baco.ui.components;

import com.baco.ui.services.BSSettings;
import java.io.File;
import javax.swing.JFileChooser;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Componente de dialogo para seleccion de ruta de guardado
 * @author dnahuat
 */
public class BSSavePathFileChooser extends JFileChooser {

   public BSSavePathFileChooser() {
      String file = (String) BSSettings.get("lastFileChooserPath");

      if (file != null) {
         this.setSelectedFile(new File(file));
      }
   }

   @Override
   public File getSelectedFile() {
      File file = super.getSelectedFile();
      String lastPath = "";

      if (file != null) {
         if (file.isDirectory()) {
            lastPath = file.getAbsolutePath();

         } else {
            lastPath = file.getParentFile().getAbsolutePath();
         }

         BSSettings.put("lastFileChooserPath", lastPath);
      }

      return file;
   }
}
