package com.baco.ui.services;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * Interface de sesion
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSSession {

   /** variables de entorno */
   private static Map enviromentVariables;

   /** inicializa el hash de variables de entorno */
   static {
      enviromentVariables = Collections.synchronizedMap(new HashMap());
   }

   /**
    * De vuelve el valor de la variable segun la llave o null si la variable
    * no existe
    *
    * @param key nombre de la variable
    * @return valor de la variable
    */
   public static Object get(Object key) {
      return enviromentVariables.get(key);
   }

   /**
    * Establece un valor a una variable de entorno. Si la variable ya existe
    * la sobreescribe.
    *
    * @param key nombre de la variable
    * @param value valor de la variable
    * @return valor de la variable
    */
   public static Object put(Object key, Object value) {
      return enviromentVariables.put(key, value);
   }

   public static void reset() {
      enviromentVariables.clear();
   }
}
