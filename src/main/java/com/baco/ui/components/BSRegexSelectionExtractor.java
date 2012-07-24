package com.baco.ui.components;

import java.util.Map;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Extraccion de la expresion regular que valide el texto proporcionado
 * @author dnahuat
 */
public class BSRegexSelectionExtractor implements BSTextExtractor {

   private StringBuffer buffer;
   private Map<Integer, Boolean> selectPositions;
   private int optionalLeftPadding = 0;
   private StringBuffer extractedText;
   private StringBuffer barcodeLengthText;
   private StringBuffer selectedGroups;

   @Override
   public String getProcessedText(StringBuffer buffer,
                                  Map<Integer, Boolean> selectPositions,
                                  int optionalLeftPadding) {
      this.buffer = buffer;
      this.selectPositions = selectPositions;
      this.optionalLeftPadding = optionalLeftPadding;
      extractedText = new StringBuffer();
      selectedGroups = new StringBuffer();
      if (buffer.length() <= 0) {
         return null;
      }
      int groupCount = 0;
      int maxIndexSelection = buffer.length() - 1;
      int symbolCount = 0;
      int numberCount = 0;
      int letterCount = 0;
      boolean selectedText = false;
      boolean selectionChanged = false;
      boolean isSymbol = false;
      boolean isNumber = false;
      boolean isLetter = false;

      extractedText.append('^');
      if (optionalLeftPadding > 0) {
         extractedText.append("[0]{0,").append(optionalLeftPadding).append("}");
      }

      // Obtener el indice seleccionado mas grande
      for (int i = (buffer.length() - 1); i >= 0; i--) {
         if (selectPositions.get(i) != null && selectPositions.get(i)) {
            maxIndexSelection = i + 1;
            break;
         }
      }

      for (int i = 0; i < buffer.length(); i++) {
         if (selectPositions.get(i) != null && selectPositions.get(i)) {
            selectionChanged = !selectedText;
            selectedText = true;
         } else {
            selectionChanged = selectedText;
            selectedText = false;
         }

         if (buffer.charAt(i) == '-' || buffer.charAt(i) == '/' || buffer.charAt(
                 i) == '\'' || buffer.charAt(i) == '.') {
            symbolCount += 1;
            if (!isSymbol || (i == buffer.length() - 1) || selectionChanged) {
               groupCount += 1;
               if (isNumber) {
                  extractedText.append("(\\d{").append(numberCount).append("})");
                  numberCount = 0;
               }
               if (isLetter) {
                  extractedText.append("([a-zA-Z]{").append(letterCount).append(
                          "})");
                  letterCount = 0;
               }
               if (isSymbol) {
                  extractedText.append("(").append("/{").append(symbolCount
                          - (selectionChanged ? 1 : 0)).append("}").append("||").
                          append("-{").append(symbolCount
                          - (selectionChanged ? 1 : 0)).append("}").append("||").
                          append("\\'{").append(symbolCount
                          - (selectionChanged ? 1 : 0)).append("}").append("||").
                          append("\\.{").append(symbolCount
                          - (selectionChanged ? 1 : 0)).append("}").append(")");
                  symbolCount = (selectionChanged ? 1 : 0);
               }
               if (selectPositions.get(i) != null && selectPositions.get(i) && i < buffer.
                       length() - 1) {
                  selectedGroups.append("$").append(groupCount);
               }
            }
            isSymbol = true;
            isLetter = false;
            isNumber = false;
            selectionChanged = false;
         } else {
            if (Character.isDigit(buffer.charAt(i))) {
               numberCount += 1;
               if (!isNumber || (i == buffer.length() - 1) || selectionChanged) {
                  groupCount += 1;
                  if (isSymbol) {
                     extractedText.append("(").append("/{").append(symbolCount).
                             append("}").append("||").append("-{").append(
                             symbolCount).append("}").append("||").append("\\'{").
                             append(symbolCount).append("}").append("||").append(
                             "\\.{").append(symbolCount).append("}").append(")");
                     symbolCount = 0;
                  }
                  if (isLetter) {
                     extractedText.append("([a-zA-Z]{").append(letterCount).
                             append("})");
                     letterCount = 0;
                  }
                  if (isNumber) {
                     extractedText.append("(\\d{").append(numberCount
                             - (selectionChanged ? 1 : 0)).append("})");
                     numberCount = (selectionChanged ? 1 : 0);
                  }
                  if (selectPositions.get(i) != null && selectPositions.get(i) && i < buffer.
                          length() - 1) {
                     selectedGroups.append("$").append(groupCount);
                  }
               }
               isNumber = true;
               isSymbol = false;
               isLetter = false;
               selectionChanged = false;
            } else {
               if (Character.isLetter(buffer.charAt(i))) {
                  letterCount += 1;
                  if (!isLetter || (i == buffer.length() - 1)
                          || selectionChanged) {
                     groupCount += 1;
                     if (isNumber) {
                        extractedText.append("(\\d{").append(numberCount).append(
                                "})");
                        numberCount = 0;
                     }
                     if (isSymbol) {
                        extractedText.append("(").append("/{").append(
                                symbolCount).append("}").append("||").append(
                                "-{").append(symbolCount).append("}").append(
                                "||").append("\\'{").append(symbolCount).append(
                                "}").append("||").append("\\.{").append(
                                symbolCount).append("}").append(")");
                        symbolCount = 0;
                     }
                     if (isLetter) {
                        extractedText.append("([a-zA-Z]{").append(letterCount
                                - (selectionChanged ? 1 : 0)).append("})");
                        letterCount = (selectionChanged ? 1 : 0);
                     }
                     if (selectPositions.get(i) != null
                             && selectPositions.get(i) && i < buffer.length()
                             - 1) {
                        selectedGroups.append("$").append(groupCount);
                     }
                  }
                  isLetter = true;
                  isNumber = false;
                  isSymbol = false;
                  selectionChanged = false;
               }
            }
         }
         if (i == maxIndexSelection) {
            extractedText.append(".*");
            break;
         }
      }
      extractedText.append('$');
      return extractedText.toString();
   }

   @Override
   public String getSelectedGroups() {
      return selectedGroups.toString();
   }

   @Override
   public String getProcessedLength() {
      if (buffer == null || selectPositions == null) {
         return "";
      }
      barcodeLengthText = new StringBuffer();

      if (buffer.length() <= 0) {
         return null;
      }
      int symbolCount = 0;
      int numberCount = 0;
      int letterCount = 0;
      boolean isSymbol = false;
      boolean isNumber = false;
      boolean isLetter = false;

      barcodeLengthText.append('^');
      if (optionalLeftPadding > 0) {
         barcodeLengthText.append("[0]{0,").append(optionalLeftPadding).append(
                 "}");
      }


      for (int i = 0; i < buffer.length(); i++) {
         if (buffer.charAt(i) == '-' || buffer.charAt(i) == '/' || buffer.charAt(
                 i) == '\'' || buffer.charAt(i) == '.') {
            symbolCount += 1;
            if (!isSymbol || (i == buffer.length() - 1)) {
               if (isNumber) {
                  barcodeLengthText.append("[0-9]{").append(numberCount).append(
                          "}");
                  numberCount = 0;
               }
               if (isLetter) {
                  barcodeLengthText.append("[a-zA-Z]{").append(letterCount).
                          append("}");
                  letterCount = 0;
               }
               if (isSymbol) {
                  barcodeLengthText.append("(").append("/{").append(symbolCount).
                          append("}").append("||").append("-{").append(
                          symbolCount).append("}").append("||").append("\\'{").
                          append(symbolCount).append("}").append("||").append(
                          "\\.{").append(symbolCount).append("}").append(")");
                  symbolCount = 0;
               }
            }
            isSymbol = true;
            isLetter = false;
            isNumber = false;
         } else {
            if (Character.isDigit(buffer.charAt(i))) {
               numberCount += 1;
               if (!isNumber || (i == buffer.length() - 1)) {
                  if (isSymbol) {
                     barcodeLengthText.append("(").append("/{").append(
                             symbolCount).append("}").append("||").append("-{").
                             append(symbolCount).append("}").append("||").append(
                             "\\'{").append(symbolCount).append("}").append("||").
                             append("\\.{").append(symbolCount).append("}").
                             append(")");
                     symbolCount = 0;
                  }
                  if (isLetter) {
                     barcodeLengthText.append("[a-zA-Z]{").append(letterCount).
                             append("}");
                     letterCount = 0;
                  }
                  if (isNumber) {
                     barcodeLengthText.append("[0-9]{").append(numberCount).
                             append("}");
                     numberCount = 0;
                  }
               }
               isNumber = true;
               isSymbol = false;
               isLetter = false;
            } else {
               if (Character.isLetter(buffer.charAt(i))) {
                  letterCount += 1;
                  if (!isLetter || (i == buffer.length() - 1)) {
                     if (isNumber) {
                        barcodeLengthText.append("[0-9]{").append(numberCount).
                                append("}");
                        numberCount = 0;
                     }
                     if (isSymbol) {
                        barcodeLengthText.append("(").append("/{").append(
                                symbolCount).append("}").append("||").append(
                                "-{").append(symbolCount).append("}").append(
                                "||").append("\\'{").append(symbolCount).append(
                                "}").append("||").append("\\.{").append(
                                symbolCount).append("}").append(")");
                        symbolCount = 0;
                     }
                     if (isLetter) {
                        barcodeLengthText.append("[a-zA-Z]{").append(letterCount).
                                append("}");
                        letterCount = 0;
                     }
                  }
                  isLetter = true;
                  isNumber = false;
                  isSymbol = false;
               }
            }
         }
      }
      barcodeLengthText.append('$');
      return barcodeLengthText.toString();

   }
}
