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
