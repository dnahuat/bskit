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
package com.baco.ui.datamodels;

import com.baco.ui.exceptions.BSInvalidModelAttributesException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Clase envoltura para insercion, extraccion y transformacion de atributos del BSBeanTableModel
 * @author dnahuat
 */
public abstract class BSTableModelAttributeList {

   public static final int COLUMN_NAME = 0;
   public static final int CLASS = 1;
   public static final int ATTRIBUTE_NAME = 2;
   public static final int FORMAT = 3;
   public static final int REPLACE = 4;
   private final Object[][] attributes;
   private final Pattern[] patterns;

   public BSTableModelAttributeList(Object[][] attributes) {
      this.attributes = attributes;
      patterns = new Pattern[attributes.length];

      for (int i = 0, size = attributes.length; i < size; i++) {
         if (attributes[i][REPLACE] != null) {
            patterns[i] = Pattern.compile((String) attributes[i][FORMAT]);
         }
      }
   }

   public final String getColumnName(int column) {
      return (String) attributes[column][COLUMN_NAME];
   }

   public final Class<?> getColumnClass(int column) {
      return (Class<?>) attributes[column][CLASS];
   }

   public final int size() {
      return attributes.length;
   }

   public final Object getValue(Object bean, int column) throws
           BSInvalidModelAttributesException {

      try {
         Object value = null;

         if (attributes[column][ATTRIBUTE_NAME] == null) {
            return "";
         }

         if (attributes[column][ATTRIBUTE_NAME].equals("this")) {
            return bean;

         } else {
            try {
               value = PropertyUtils.getProperty(bean,
                                                 (String) attributes[column][ATTRIBUTE_NAME]);
            } catch (NullPointerException ex) {
               return "";

            } catch (NestedNullException ex) {
               return "";
            }
         }
         if (value == null) {
            return null;

         } else if (patterns[column] != null) {
            Matcher matcher = patterns[column].matcher(String.valueOf(value));
            if (matcher.find()) {
               return matcher.replaceAll(
                       (String) attributes[column][REPLACE]);
            } else {
               return null;
            }

         } else if (value instanceof Date) {
            if (attributes[column][FORMAT] != null) {
               DateFormat format = new SimpleDateFormat(
                       (String) attributes[column][FORMAT]);
               return format.format((Date) value);

            } else {
               return value;
            }

         } else if (attributes[column][FORMAT] != null) {
            return String.format(
                    (String) attributes[column][FORMAT], value);
         } else {
            return value;
         }
      } catch (IllegalAccessException ex) {
         String message = "The property named '"
                 + (String) attributes[column][ATTRIBUTE_NAME]
                 + "' has a private getter method";
         throw new BSInvalidModelAttributesException(message, ex);
      } catch (InvocationTargetException ex) {
         String message = "An exception has ocurred while retrieving value for bean property '"
                 + (String) attributes[column][ATTRIBUTE_NAME] + "'";
         throw new BSInvalidModelAttributesException(message, ex);
      } catch (NoSuchMethodException ex) {
         String message = "The bean doesn't have a property named '"
                 + (String) attributes[column][ATTRIBUTE_NAME] + "'";
         throw new BSInvalidModelAttributesException(message, ex);
      }
   }
}
