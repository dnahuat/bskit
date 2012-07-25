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
package com.baco.ui.core;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Bus de componentes activos en BSKit
 * @author dnahuat
 */
public final class BSBusMgr {

   private static BSBusMgr busMgr;
   private final Multimap bus;
   private final Map<Long, BSCoreComponent> instances;
   private final Multimap oidsByUniqueName;

   private BSBusMgr() {
      bus = Multimaps.synchronizedMultimap(ArrayListMultimap.create());
      instances = Collections.synchronizedMap(
              new HashMap<Long, BSCoreComponent>());
      oidsByUniqueName = Multimaps.synchronizedMultimap(
              ArrayListMultimap.create());
   }

   public static BSBusMgr get() {
      if (busMgr == null) {
         busMgr = new BSBusMgr();
      }
      return busMgr;
   }

   public synchronized boolean registerComponent(BSCoreComponent component) {
      return registerComponentInBus("DEFAULT", component);
   }

   public synchronized boolean registerComponentInBus(String busName,
                                                      BSCoreComponent component) {
      if (component.getOID() != null) {
         if (component.isSingleInstance() && oidsByUniqueName.containsKey(component.
                 getUniqueName())) {
            System.out.println(
                    "BUSMGR - Trying to register single instance type CoreComponent that has been already registered");
            return false;
         }
         if (!bus.containsEntry(busName, component.getOID().longValue())) {
            bus.put(busName, component.getOID().longValue());
            instances.put(component.getOID().longValue(), component);
            oidsByUniqueName.put(component.getUniqueName(), component.getOID().
                    longValue());
            return true;
         }
         System.out.println("BUSMGR - CoreComponent: "
                 + component.getUniqueName() + " with OID: " + component.getOID().
                 longValue() + " Already loaded in BUS:" + busName);
         return false;
      } else {
         System.out.println(
                 "BUSMGR - Trying to register CoreComponent With no OID");
         return false;
      }
   }

   public synchronized boolean deregisterComponentFromBus(String busName,
                                                          BSCoreComponent component) {
      if (component.getOID() != null) {
         bus.remove(busName, component.getOID().longValue());
         if (!bus.containsValue(component.getOID().longValue())) {
            instances.remove(component.getOID().longValue());
            oidsByUniqueName.remove(component.getUniqueName(), component.getOID().
                    longValue());
         }
         return true;
      } else {
         System.out.println(
                 "BUSMGR - Trying to deregister CoreComponent With no OID");
         return false;
      }
   }

   public synchronized boolean deregisterComponentFromAll(
           BSCoreComponent component) {
      if (component.getOID() != null) {
         if (bus.containsValue(component.getOID().longValue())) {
            for (Object obj : bus.keySet()) {
               bus.remove(obj, component.getOID().longValue());
            }
         }
         oidsByUniqueName.remove(component.getUniqueName(), component.getOID().
                 longValue());
         instances.remove(component.getOID().longValue());
         return true;
      } else {
         System.out.println(
                 "BUSMGR - Trying to deregister CoreComponent With no OID");
         return false;
      }
   }

   public synchronized boolean checkOIDIsRegistered(Long componentOID) {
      return instances.containsKey(componentOID);
   }

   public synchronized boolean checkUniqueNameIsRegistered(String uniqueName) {
      return (oidsByUniqueName.containsKey(uniqueName));
   }

   public synchronized boolean checkOIDIsRegisteredInBus(String busName,
                                                         Long componentOID) {
      return bus.containsEntry(busName, componentOID);
   }

   public synchronized boolean checkInstanceIsRegistered(
           BSCoreComponent component) {
      if (component.getOID() == null) {
         System.out.println("BUSMGR - Trying to query CoreComponent With no OID");
         return false;
      }
      return instances.containsKey(component.getOID().longValue());
   }

   public synchronized BSCoreComponent getComponentByOID(Long componentOID) {
      return getComponentInBusByOID("DEFAULT", componentOID);
   }

   public synchronized BSCoreComponent getComponentInBusByOID(String busName,
                                                              Long componentOID) {
      if (bus.containsEntry(busName, componentOID)) {
         return instances.get(componentOID);
      }
      return null;
   }

   public synchronized List<Long> getComponentsByUniqueName(String uniqueName) {
      return new ArrayList(oidsByUniqueName.get(uniqueName));
   }

   @Override
   protected Object clone() throws CloneNotSupportedException {
      throw new CloneNotSupportedException();
   }
}
