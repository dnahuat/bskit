package com.baco.ui.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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
public class BSTextListReader extends ArrayList<String> {

   public BSTextListReader(URL fileUrl) throws IOException {
      InputStreamReader isReader = new InputStreamReader(
              fileUrl.openStream());
      BufferedReader reader = new BufferedReader(isReader);
      String line;

      while ((line = reader.readLine()) != null) {
         add(line);
      }

      reader.close();
   }
}
