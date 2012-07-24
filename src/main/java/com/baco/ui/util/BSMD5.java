package com.baco.ui.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Esta clase se usa para encriptar las contraseñas en md5
 * 
 * 
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public final class BSMD5 {

   private static MessageDigest md = null;
   private static BSMD5 md5 = null;
   private static final char[] HexChars = {
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
   };

   /** Creates a new instance of MD5 */
   private BSMD5() throws NoSuchAlgorithmException {
      md = MessageDigest.getInstance("MD5");
   }

   private static BSMD5 getMD5() throws NoSuchAlgorithmException {
      if (md5 == null) {
         md5 = new BSMD5();
      }

      return md5;
   }

   /**
    * Encripta una cadena usando el algorimo md5
    *
    * @param toEncrypt Cadena a encriptar
    * @return cadena encriptada
    */
   public static String encrypt(String toEncrypt) {
      try {
         md5 = BSMD5.getMD5();
         return md5.hashData(toEncrypt);
      } catch (Exception ex) {
         return null;
      }
   }

   private String hashData(String dataToHash) {
      return hexStringFromBytes((calculateHash(dataToHash.getBytes())));
   }

   private byte[] calculateHash(byte[] dataToHash) {
      md.update(dataToHash, 0, dataToHash.length);
      return (md.digest());
   }

   private String hexStringFromBytes(byte[] b) {
      String hex = "";

      int msb;

      int lsb = 0;
      int i;

      // MSB maps to idx 0

      for (i = 0; i < b.length; i++) {
         msb = ((int) b[i] & 0x000000FF) / 16;

         lsb = ((int) b[i] & 0x000000FF) % 16;
         hex = hex + HexChars[msb] + HexChars[lsb];
      }

      return (hex);
   }
}
