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

/**
 * Definicion de metodos que debe implementar un logger de Hydra
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public interface BSLogger {

   /**
    * Registra un mensaje de error en el Log
    * @param sessionId El id de la sesion si existe alguna
    * @param message El mensaje
    * @param t El error (Debe ser capaz de aceptar nulos)
    */
   void error(String sessionId, String message, Throwable t);

   /**
    * Registra un mensaje de advertencia en el Log
    * @param sessionId El id de la sesion si existe
    * @param message El mensaje
    * @param t La advertencia (Debe ser capaz de aceptar nulos)
    */
   void warning(String sessionId, String message, Throwable t);

   /**
    * Registra un mensaje de debug en el Log
    * Solo deberia funcionar si se establece en modo debug
    * @param sessionId El id de la sesion si existe
    * @param message El mensaje de debug
    */
   void debug(String sessionId, String message);

   /**
    * Registra un mensaje de informacion en el Log
    * @param sessionId El id de la sesion si existe
    * @param message El mensaje de debug
    */
   void info(String sessionId, String message);

   /**
    * Devuelve el estado de activacion del modo debug
    * @return True si el modo debug esta activado, false de otro modo
    */
   boolean isDebugModeEnabled();

   /**
    * Establece el estado del modo debug
    * @param debugMode true para activar el modo debug, false para desactivarlo
    */
   void setDebugMode(boolean debugMode);
}
