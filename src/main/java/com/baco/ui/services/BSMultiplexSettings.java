package com.baco.ui.services;

import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Lector de configuracion en disco, con lectura Lazy
 * y con multiplexacion en base a indice entero
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSMultiplexSettings {

   /**
    * Aqui se cargan las preferencias
    */
   private static Map<Integer, HashMap<Serializable, Serializable>> multiplexSettings = Collections.
           synchronizedMap(
           new HashMap<Integer, HashMap<Serializable, Serializable>>());
   private static AtomicBoolean isReaded = new AtomicBoolean(false);

   public static synchronized Serializable get(Integer multiKey,
                                               Serializable key) {
      try {
         if (!isReaded.get()) {
            readFile();
            isReaded.set(true);
         }
         return multiplexSettings.get(multiKey).get(key);
      } catch (Exception ex) {
         ex.printStackTrace();
         return null;
      }

   }

   public static synchronized Serializable put(Integer multiKey,
                                               Serializable key,
                                               Serializable value) {
      try {
         if (!isReaded.get()) {
            readFile();
            isReaded.set(true);
         }
         if (multiplexSettings.get(multiKey) != null &&
             multiplexSettings.get(multiKey).get(key) != null &&
             !multiplexSettings.get(multiKey).get(key).equals(value)) {
            Serializable ret = multiplexSettings.get(multiKey).put(key, value);
            writeFile();
            return ret;
         } else {
            if (multiplexSettings.get(multiKey) == null) {
               multiplexSettings.put(multiKey, new HashMap());
            }
            if(multiplexSettings.get(multiKey).get(key) == null) {
               Serializable ret = multiplexSettings.get(multiKey).put(key, value);
               writeFile();
               return ret;
            } else {
               return value;
            }
         } 
      } catch (Exception ex) {
         ex.printStackTrace();
         return null;
      }
   }

   public static synchronized Serializable remove(Integer multiKey,
                                                  Serializable key) {
      try {
         if (!isReaded.get()) {
            readFile();
            isReaded.set(true);
         }
         Serializable ret = null;
         if (multiplexSettings.get(multiKey) != null) {
            ret = multiplexSettings.get(multiKey).remove(key);
            writeFile();
            return ret;
         } else {
            return null;
         }                  
      } catch (Exception ex) {
         ex.printStackTrace();
         return null;
      }
   }

   private static synchronized void readFile()
           throws IOException, ClassNotFoundException {
      String filename = (String) BSSession.get("SettingsFile");
      File file;
      if (filename == null) {
         filename = "settings.dat";
      }
      filename = filename + ".multiplex";
      file = new File(filename);
      if (!file.exists()) {
         file.createNewFile();
      } else {
         FileInputStream fileIStream = new FileInputStream(file);
         ObjectInputStream objectIStream = new ObjectInputStream(
                 fileIStream);
         multiplexSettings = Collections.synchronizedMap((Map<Integer, HashMap<Serializable, Serializable>>) objectIStream.
                 readObject());
         objectIStream.close();
      }
   }

   private static synchronized void writeFile() throws IOException {
      String filename = (String) BSSession.get("SettingsFile");
      File file;
      FileOutputStream fileOStream;
      ObjectOutputStream objectOStream;
      if (filename == null) {
         filename = "settings.dat";
      }
      filename = filename + ".multiplex";
      file = new File(filename);
      if (!file.exists()) {
         file.createNewFile();
      }
      fileOStream = new FileOutputStream(file);
      objectOStream = new ObjectOutputStream(fileOStream);

      objectOStream.writeObject(
              new HashMap<Integer, HashMap<Serializable, Serializable>>(
              multiplexSettings));
      objectOStream.flush();
      objectOStream.close();
   }

   /**
    * Inicializa las preferencias para que se vuelva a cargar el archivo
    */
   public static synchronized void reset() {
      multiplexSettings = null;
      isReaded.set(true);
   }
}
