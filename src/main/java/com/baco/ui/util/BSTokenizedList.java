package com.baco.ui.util;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * 
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSTokenizedList extends ArrayList<String> implements Serializable {

   public BSTokenizedList(String line, String delimiter) {
      tokenize(line, delimiter);
   }

   private void tokenize(String line, String delimiter) {
      int delimiterIndex = line.indexOf(delimiter);

      if (delimiterIndex == -1) {
         this.add(line.trim());

      } else {
         this.add(line.substring(0, delimiterIndex).trim());
         tokenize(line.substring(delimiterIndex + delimiter.length()),
                  delimiter);
      }
   }
}
