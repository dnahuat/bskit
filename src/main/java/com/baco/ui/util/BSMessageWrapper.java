package com.baco.ui.util;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Clase para envolver el mensaje con informacion adicional
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSMessageWrapper implements Serializable {

   private static final long serialVersionUID = -4280066974863221260L;
   private String className;
   private String methodName;
   private BSMessagesEnum status;
   private String message;
   private String title;
   private Boolean successful;
   private String errorMessage;
   
   public BSMessageWrapper() {
       
   }

   public BSMessageWrapper(String className, String methodName,
                           String message, BSMessagesEnum status) {
      this.className = className;
      this.methodName = methodName;
      this.message = message;
      this.status = status;
      this.title = null;
      this.errorMessage = null;
      if (status.equals(BSMessagesEnum.Error) || status.equals(
              BSMessagesEnum.AuthError) || status.equals(
              BSMessagesEnum.SessionExpired)) {
         this.successful = false;
      } else {
         this.successful = true;
      }
   }

   public BSMessageWrapper(String className, String methodName,
                           String title, String message, BSMessagesEnum type) {
      this.className = className;
      this.methodName = methodName;
      this.message = message;
      this.status = type;
      this.title = title;
      this.errorMessage = null;
      if (type.equals(BSMessagesEnum.Error) || type.equals(
              BSMessagesEnum.AuthError) || type.equals(
              BSMessagesEnum.SessionExpired)) {
         this.successful = false;
      } else {
         this.successful = true;
      }
   }

   public BSMessageWrapper(String className, String methodName,
                           String title, String message, Throwable throwable,
                           BSMessagesEnum type) {
      this.className = className;
      this.methodName = methodName;
      this.message = message;
      this.status = type;
      this.title = title;
      this.errorMessage = getStackTrace(throwable);
      if (type.equals(BSMessagesEnum.Error) || type.equals(
              BSMessagesEnum.AuthError) || type.equals(
              BSMessagesEnum.SessionExpired)) {
         this.successful = false;
      } else {
         this.successful = true;
      }
   }

   public String getClassName() {
      return className;
   }

   public String getMethodName() {
      return methodName;
   }

   public BSMessagesEnum getType() {
      return status;
   }

   public String getErrorMessage() {
      return errorMessage;
   }

   public String getTitle() {
      if (title != null) {
         return title;
      }
      if (status == BSMessagesEnum.Error) {
         return "Error";
      }
      if (status == BSMessagesEnum.AuthError) {
         return "Error de autorización";
      }
      if (status == BSMessagesEnum.SessionExpired) {
         return "Sesion invalida";
      }
      if (status == BSMessagesEnum.Success) {
         return "Operación exitosa";
      }
      if (status == BSMessagesEnum.Warning) {
         return "Advertencia";
      }
      return "";
   }

   public Boolean getSuccessful() {
      return this.successful;
   }

   public String getBody() {
      return this.message;
   }

   private static String getStackTrace(Throwable throwable) {
      if (throwable != null) {
         Writer writer = new StringWriter();
         PrintWriter printWriter = new PrintWriter(writer);
         throwable.printStackTrace(printWriter);
         return writer.toString();
      } else {
         return "Oops - No Available Stacktrace";
      }
   }
}
