package com.baco.ui.core;

import java.util.concurrent.atomic.AtomicLong;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Generador del identificador unico
 * Esta clase soporta concurrencia.
 * @author dnahuat
 */
public final class BSOID {

   private static final AtomicLong uniqueOID = new AtomicLong(0);
   private final long currentOID;

   public BSOID() {
      currentOID = uniqueOID.getAndIncrement();
   }

   public long longValue() {
      return currentOID;
   }
}
