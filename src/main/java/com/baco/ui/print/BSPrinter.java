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
