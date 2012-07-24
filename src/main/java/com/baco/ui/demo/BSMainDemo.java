package com.baco.ui.demo;

import com.baco.ui.core.BSCoreFactory;
import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import java.util.Properties;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSMainDemo {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      try {
         Properties props = new Properties();
         props.put("logoString", "Hydra");
         props.put("windowDecoration", "off");
         props.put("menuOpaque", "off");
         AcrylLookAndFeel.setTheme("Small-Font");
         AcrylLookAndFeel.setCurrentTheme(props);
         UIManager.setLookAndFeel(new com.jtattoo.plaf.acryl.AcrylLookAndFeel());
      } catch (UnsupportedLookAndFeelException ex) {
         System.exit(-1);
      }
      BSCoreFactory.setSettingsFile((String) System.getProperty("user.home")
              + "/bskit_demo_settings.dat");
      BSCoreFactory.setSessionImplementation("com.baco.ui.demo.BSSessionDemo");
      BSCoreFactory.setResourcesUrl("/icons/");
      BSCoreFactory.startCore("BSKit DEMO");
   }
}
