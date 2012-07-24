package com.baco.ui.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSNumberedList extends ArrayList<Integer> {

   private static Pattern numberFinder;

   static {
      numberFinder = Pattern.compile("\\d+");
   }

   public BSNumberedList(String list) {
      Matcher matcher = numberFinder.matcher(list);

      while (matcher.find()) {
         add(Integer.valueOf(matcher.group(0)));
      }
   }

   @Override
   public String toString() {
      StringBuffer tokens = new StringBuffer("");

      for (Integer token : this) {
         tokens.append(token + ", ");
      }

      if (tokens.length() == 0) {
         return "";
      } else {
         return tokens.substring(0, tokens.length() - 2);
      }
   }
}
