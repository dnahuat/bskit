package com.baco.ui.print;

import java.awt.print.Printable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.attribute.DocAttributeSet;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Wrapper para imprimible
 * @author dnahuat
 */
public class BSPrintableDoc implements Doc {

   private Printable printable;

   public BSPrintableDoc(Printable printable) {
      this.printable = printable;
   }

   @Override
   public DocFlavor getDocFlavor() {
      return DocFlavor.SERVICE_FORMATTED.PRINTABLE;
   }

   @Override
   public Object getPrintData() throws IOException {
      return printable;
   }

   @Override
   public DocAttributeSet getAttributes() {
      return null;
   }

   @Override
   public Reader getReaderForText() throws IOException {
      return null;
   }

   @Override
   public InputStream getStreamForBytes() throws IOException {
      return null;
   }
}
