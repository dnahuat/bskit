package com.baco.ui.util;

import java.io.Serializable;

/**
 * Enumaracion para los mensajes de respuesta de los ejb
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public enum BSMessagesEnum implements Serializable {
   Success,
   AuthError,
   Error,
   Warning,
   SessionExpired
}
