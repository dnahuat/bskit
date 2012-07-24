package com.baco.ui.print;

import java.awt.print.Printable;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Impresor simple para documentos carta y en portrato
 * @author dnahuat
 */
public class BSPrinter {

   private OrientationRequested orientation;
   private MediaSize mediaSize;

   public BSPrinter() {
      orientation = OrientationRequested.PORTRAIT;
   }

   public PrintService[] lookupPrintServices() {
      return PrintServiceLookup.lookupPrintServices(
              DocFlavor.SERVICE_FORMATTED.PRINTABLE,
              getAttributes());
   }

   public void print(PrintService service, Printable printable)
           throws PrintException {

      Doc doc = new BSPrintableDoc(printable);
      DocPrintJob job = service.createPrintJob();
      job.print(doc, getAttributes());
   }

   public void print(Printable printable) throws PrintException {
      print(lookupPrintServices()[0], printable);
   }

   private PrintRequestAttributeSet getAttributes() {
      PrintRequestAttributeSet attributes =
              new HashPrintRequestAttributeSet();

      if (mediaSize != null) {
         attributes.add(mediaSize);
      } else {
         attributes.add(MediaSizeName.NA_LETTER);
      }


      attributes.add(orientation);

      return attributes;
   }

   /**
    * @return the orientation
    */
   public OrientationRequested getOrientation() {
      return orientation;
   }

   /**
    * @param orientation the orientation to set
    */
   public void setOrientation(OrientationRequested orientation) {
      this.orientation = orientation;
   }

   /**
    * @return the mediaSize
    */
   public MediaSize getMediaSize() {
      return mediaSize;
   }

   /**
    * @param mediaSize the mediaSize to set
    */
   public void setMediaSize(MediaSize mediaSize) {
      this.mediaSize = mediaSize;
   }
}
