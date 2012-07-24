package com.baco.ui.demo;

import com.baco.ui.core.BSSessionWrapper;

/**
 * Implementacion demo del wrapper de autenticacion
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSSessionWrapperImpl implements BSSessionWrapper {

   private String user1 = "usuario";
   private String pass1 = "12345";
   private String user;
   private String pass;

   public BSSessionWrapperImpl(String user, String pass) {
      this.user = user;
      this.pass = pass;
   }

   @Override
   public boolean isActive() {
      if (user.equals(user1) && pass.equals(pass1)) {
         return true;
      } else {
         return false;
      }
   }

   @Override
   public Object getSession() {
      return user1;
   }

   @Override
   public String getSessionId() {
      if (user1 != null) {
         return user1;
      } else {
         return "NA";
      }
   }
}
