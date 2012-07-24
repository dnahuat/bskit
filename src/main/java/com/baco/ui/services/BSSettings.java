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
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSSettings {

   /**
    * Aqui se cargan las preferencias
    */
   private static Map<Serializable, Serializable> settings =
                                                  Collections.synchronizedMap(
           new HashMap<Serializable, Serializable>());
   private static AtomicBoolean isReaded = new AtomicBoolean(false);

   public static synchronized Serializable get(Serializable key) {
      try {
         if (!isReaded.get()) {
            readFile();
            isReaded.set(true);
         }
         return settings.get(key);
      } catch (Exception ex) {
         ex.printStackTrace();
         return null;
      }

   }

   public static synchronized Serializable put(Serializable key,
                                               Serializable value) {
      try {
         if (!isReaded.get()) {
            readFile();
            isReaded.set(true);
         }
         if (settings.get(key) != null && !settings.get(key).equals(value)) {
            Serializable ret = settings.put(key, value);
            writeFile();
            return ret;
         } else {
            if (settings.get(key) == null) {
               Serializable ret = settings.put(key, value);
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

   public static synchronized Serializable remove(Serializable key) {
      try {
         if (!isReaded.get()) {
            readFile();
            isReaded.set(true);
         }
         if (settings.containsKey(key)) {
            Serializable ret = settings.remove(key);
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
      file = new File(filename);
      if (!file.exists()) {
         file.createNewFile();
      } else {
         FileInputStream fileIStream = new FileInputStream(file);
         ObjectInputStream objectIStream = new ObjectInputStream(
                 fileIStream);
         settings = Collections.synchronizedMap((Map<Serializable, Serializable>) objectIStream.
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
      file = new File(filename);
      if (!file.exists()) {
         file.createNewFile();
      }
      fileOStream = new FileOutputStream(file);
      objectOStream = new ObjectOutputStream(fileOStream);

      objectOStream.writeObject(
              new HashMap<Serializable, Serializable>(settings));
      objectOStream.flush();
      objectOStream.close();
   }

   /**
    * Inicializa las preferencias para que se vuelva a cargar el archivo
    */
   public static synchronized void reset() {
      settings = null;
      isReaded.set(false);
   }
}
