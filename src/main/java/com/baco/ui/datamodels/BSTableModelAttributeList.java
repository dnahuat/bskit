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
