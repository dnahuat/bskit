package com.baco.ui.util;

import com.baco.ui.services.BSLogger;

/**
 * Implementacion default de HydraLogger
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 * 
 */
public class BSDefaultLogger implements BSLogger {

   private boolean isDebugMode = false;
   private String sessionId = "NA";
   private String clientId = "NA";

   @Override
   public void error(String sessionId, String message, Throwable t) {
      System.out.println("ERROR : " + message + "\n");
      if (t != null) {
         t.printStackTrace();
      }
   }

   @Override
   public void warning(String sessionId, String message, Throwable t) {
      System.out.println("WARN  : " + message + "\n");
      if (t != null) {
         t.printStackTrace();
      }
   }

   @Override
   public void debug(String sessionId, String message) {
      if (isDebugMode) {
         System.out.println("DEBUG : " + message + "\n");
      }
   }

   @Override
   public void info(String sessionId, String message) {
      System.out.println("INFO  : " + message + "\n");
   }

   @Override
   public boolean isDebugModeEnabled() {
      return isDebugMode;
   }

   @Override
   public void setDebugMode(boolean debugMode) {
      this.isDebugMode = debugMode;
   }
}
