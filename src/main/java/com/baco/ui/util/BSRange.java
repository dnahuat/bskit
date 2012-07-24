package com.baco.ui.util;

/**
 *
 * 
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSRange<T extends Comparable<? super T>> {

   private T minLimit;
   private T maxLimit;
   private boolean minInclude;
   private boolean maxInclude;

   /**
    * Crea un rango con los limites establecidos incluyendolos dentro del
    * rango
    *
    * @param limit1 primer limite
    * @param limit2 segudno limite
    */
   public BSRange(T limit1, T limit2) {
      if (((Comparable) limit1).compareTo(limit2) < 0) {
         this.minLimit = limit1;
         this.maxLimit = limit2;
      } else {
         this.minLimit = limit2;
         this.maxLimit = limit1;
      }

      minInclude = true;
      maxInclude = true;
   }

   /**
    * Crea un rango con los limites establecidos indicando si se incluyen o
    * no los limites
    *
    * @param limit1 primer numero del limite
    * @param limit2 segundo numero del limite
    * @param minInclude true si el primer limite indicado sera incluido
    * @param maxInclude true si el segundo limite indicado sera incluido
    */
   public BSRange(T limit1, T limit2, boolean minInclude, boolean maxInclude) {
      if (limit1.compareTo(limit2) < 0) {
         this.minLimit = limit1;
         this.maxLimit = limit2;
         this.minInclude = minInclude;
         this.maxInclude = maxInclude;
      } else {
         this.minLimit = limit2;
         this.maxLimit = limit1;
         this.minInclude = maxInclude;
         this.maxInclude = minInclude;
      }
   }

   /**
    * Determina si otro rango intersecta a este rango
    *
    * @param range el rango a probar
    * @return true si el rango es intersectado
    */
   public boolean intersect(BSRange<T> range) {
      if (this.maxLimit.compareTo(range.minLimit) < 0) {
         return false;
      } else if (this.maxLimit.compareTo(range.minLimit) == 0) {
         return this.maxInclude && range.minInclude;
      }

      if (this.minLimit.compareTo(range.maxLimit) > 0) {
         return false;
      } else if (this.minLimit.compareTo(range.maxLimit) == 0) {
         return this.minInclude && range.maxInclude;
      }

      return true;
   }

   /**
    * Determina si un item se encuentra dentro del rango
    *
    * @param item item a probar
    * @return true si se encuentra dentro del rango
    */
   public boolean contains(T item) {
      if (item.compareTo(minLimit) < 0) {
         return false;
      } else if (item.compareTo(minLimit) == 0) {
         return minInclude;
      }

      if (item.compareTo(maxLimit) > 0) {
         return false;
      } else if (item.compareTo(maxLimit) == 0) {
         return maxInclude;
      }

      return true;
   }

   /**
    * Obtiene el limite maximo
    *
    * @return el limite mas grande
    */
   public T getMaxLimit() {
      return maxLimit;
   }

   /**
    * Obtiene el limite minimo
    *
    * @return el limite mas chico
    */
   public T getMinLimit() {
      return minLimit;
   }

   /**
    * determina si el limite maximo es incluido dentro del rango
    *
    * @return true si es incluido
    */
   public boolean isMaxInclude() {
      return maxInclude;
   }

   /**
    * determina si el limite minimo es incluid dentro del rango
    *
    * @return true si es incluido
    */
   public boolean isMinInclude() {
      return minInclude;
   }
}
