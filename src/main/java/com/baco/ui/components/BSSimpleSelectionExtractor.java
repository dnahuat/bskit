package com.baco.ui.components;

import java.util.Map;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Extractor de texto simple, devuelve lo mismo que se le entrego
 * @author dnahuat
 */
public class BSSimpleSelectionExtractor implements BSTextExtractor {

   private StringBuffer buffer;

   @Override
   public String getProcessedText(StringBuffer buffer,
                                  Map<Integer, Boolean> selectPositions,
                                  int optionalLeftPadding) {
      this.buffer = buffer;
      return buffer.toString();
   }

   @Override
   public String getSelectedGroups() {
      return "";
   }

   @Override
   public String getProcessedLength() {
      return this.buffer.toString();
   }
}
