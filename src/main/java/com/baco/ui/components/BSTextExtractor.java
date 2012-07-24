package com.baco.ui.components;

import java.util.Map;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Interface utilizada por BSTextSelectEditor para extrar el texto seleccionado
 * @author dnahuat
 */
public interface BSTextExtractor {

   /**
    * Devuelve el texto procesado, segun la seleccion indicada por el mapa
    * de seleccion
    * @param buffer El texto
    * @param selectPositions El mapa de seleccion
    * @param optionalLeftPadding Indica si se desea agregar 0's a la izquierda
    * @return  El texto procesado
    */
   String getProcessedText(StringBuffer buffer,
                           Map<Integer, Boolean> selectPositions,
                           int optionalLeftPadding);

   /**
    * Devuelve los grupos de seleccion.
    * @return los grupos de seleccion
    */
   String getSelectedGroups();

   /**
    * Devuelve la longitud de la cadena procesada
    * @return La longitud
    */
   String getProcessedLength();
}
