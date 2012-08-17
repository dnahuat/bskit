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

import com.baco.ui.util.*;
import java.io.Serializable;

/**
 * <p>Define los datos que se deben proporcionar en la respuesta de un servicio</p>
 * 
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 * 2011-03-16 : Se agrega el atributo messageSet para saber si el mensaje ha sido
 *    establecido o continua por el mensaje por defecto
 *
 */
public class BSResponse<T extends Serializable> implements Serializable {

   private static final long serialVersionUID = -3608192223000170123L;
   private BSMessageWrapper header;
   private T payload;
   private boolean messageSet = false;
   private Throwable t;

   public BSResponse() {
      this.header = new BSMessageWrapper("", "", "", BSMessagesEnum.Error);
      this.payload = null;
      this.t = null;
   }

   /**
    * <p>Indica si la peticion en el <code>EJB</code> fue exitosa</p>
    * @return <code>True</code> si la peticion fue exitosa,
    *		   <code>False</code> de otro modo.
    */
   public boolean isSuccessful() {
      return header.getSuccessful();
   }

   /**
    * <p>Obtiene el mensaje incrustado en esta respuesta</p>
    * @return La cadena con el mensaje de respuesta
    */
   public BSMessageWrapper getMessage() {
      return header;

   }

   /**
    * <p>Establece el mensaje de respuesta</p>
    * @param message El nuevo mensaje de respuesta
    */
   public void setMessage(BSMessageWrapper message) {
      this.header = message;
      messageSet = true;
   }

   public boolean isMessageSet() {
      return messageSet;
   }

   /**
    * <p>Obtiene los datos de respuesta</p>
    * @return Los datos de respuesta como tipo <code>Serializable</code>
    */
   public T getData() {
      return payload;
   }

   /**
    * <p>Establece los datos de respuesta</p>
    * @param data Los nuevos datos de respuesta
    */
   public void setData(T data) {
      this.payload = data;
   }

   /**
    * Establece la excepcion que se pudo haber generado
    * al generar esta respuesta
    * @param t
    */
   public void setThrowable(Throwable t) {
      this.t = t;
   }

   public Throwable getThrowable() {
      return t;
   }
}
