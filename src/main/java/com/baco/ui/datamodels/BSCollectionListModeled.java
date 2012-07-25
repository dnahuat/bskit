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

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 */
/**
 * Modelo de lista para beans
 * @author dnahuat
 * @param <E> La clase que almacenara la lista
 */
public class BSCollectionListModeled<E> extends ArrayList<E> implements
        ListModel {

   private List<ListDataListener> listeners =
           new ArrayList<ListDataListener>();

   public int getSize() {
      return size();
   }

   public Object getElementAt(int index) {
      return get(index);
   }

   public void addListDataListener(ListDataListener l) {
      listeners.add(l);
   }

   public void removeListDataListener(ListDataListener l) {
      listeners.remove(l);
   }

   public void changeList(List<E> newList) {
      if (newList != null) {
         if (newList.size() > size()) {
            int indexChanged = size() - 1;
            super.clear();
            addAll(newList);
            fireIntervalAdded(indexChanged + 1, size() - 1);
            fireContentsChange(0, indexChanged);

         } else if (newList.size() == size()) {
            super.clear();
            addAll(newList);
            fireContentsChange(0, size() - 1);

         } else {
            int indexChange = newList.size() - 1;
            int indexRemoved = size() - 1;
            super.clear();
            addAll(newList);
            fireIntervalRemoved(indexChange + 1, indexRemoved);
            fireContentsChange(0, indexChange);
         }
      }
   }

   @Override
   public boolean add(E e) {
      boolean result = super.add(e);

      if (result) {
         fireIntervalAdded(size() - 1, size() - 1);
      }

      return result;
   }

   @Override
   public boolean remove(Object o) {
      int index = indexOf(o);
      boolean result = super.remove(o);

      if (result) {
         fireIntervalRemoved(index, index);
      }

      return result;
   }

   @Override
   public void clear() {
      int index = size() - 1;
      super.clear();
      fireIntervalRemoved(0, index);
   }

   public void update(int index) {
      fireContentsChange(index, index);
   }

   public void update(E e) {
      int index = indexOf(e);

      if (index != -1) {
         fireContentsChange(index, index);
      }
   }

   private void fireIntervalRemoved(int index0, int index1) {
      fireListeners(new ListDataEvent(
              this,
              ListDataEvent.INTERVAL_REMOVED,
              index0,
              index1));
   }

   private void fireIntervalAdded(int index0, int index1) {
      fireListeners(new ListDataEvent(
              this,
              ListDataEvent.INTERVAL_ADDED,
              index0,
              index1));
   }

   private void fireContentsChange(int index0, int index1) {
      fireListeners(new ListDataEvent(
              this,
              ListDataEvent.CONTENTS_CHANGED,
              index0,
              index1));
   }

   private void fireListeners(ListDataEvent evt) {
      for (ListDataListener listener : listeners) {
         switch (evt.getType()) {
            case ListDataEvent.CONTENTS_CHANGED:
               listener.contentsChanged(evt);
            case ListDataEvent.INTERVAL_ADDED:
               listener.intervalAdded(evt);
            case ListDataEvent.INTERVAL_REMOVED:
               listener.intervalRemoved(evt);
         }
      }
   }
}
