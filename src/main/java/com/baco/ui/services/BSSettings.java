/*
 *   Copyright (c) 2012, Deiby Dathat Nahuat Uc
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met
 *  1. Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  3. All advertising materials mentioning features or use of this software
 *  must display the following acknowledgement:
 *  This product includes software developed by Deiby Dathat Nahuat.
 *  4. Neither the name of Deiby Dathat Nahuat Uc nor the
 *  names of its contributors may be used to endorse or promote products
 *  derived from this software without specific prior written permission.

 *  THIS SOFTWARE IS PROVIDED BY DEIBY DATHAT NAHUAT UC ''AS IS'' AND ANY
 *  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL DEIBY DATHAT NAHUAT UC BE LIABLE FOR ANY
 *  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
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
