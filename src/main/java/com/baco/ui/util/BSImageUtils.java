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
package com.baco.ui.util;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Transparency;
import javax.swing.ImageIcon;
import java.awt.GraphicsDevice;
import java.awt.image.ColorModel;
import java.awt.HeadlessException;
import java.awt.image.PixelGrabber;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.GraphicsConfiguration;

/**
 * Utilidades de imagen
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSImageUtils {
   // This method returns a buffered image with the contents of an image

   public static BufferedImage toBufferedImage(Image image) {
      if (image instanceof BufferedImage) {
         return (BufferedImage) image;
      }

      // This code ensures that all the pixels in the image are loaded
      image = new ImageIcon(image).getImage();

      // Determine if the image has transparent pixels; for this method's
      // implementation, see Determining If an Image Has Transparent Pixels
      boolean hasAlpha = hasAlpha(image);

      // Create a buffered image with a format that's compatible with the screen
      BufferedImage bimage = null;
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      try {
         // Determine the type of transparency of the new buffered image
         int transparency = Transparency.OPAQUE;
         if (hasAlpha) {
            transparency = Transparency.BITMASK;
         }

         // Create the buffered image
         GraphicsDevice gs = ge.getDefaultScreenDevice();
         GraphicsConfiguration gc = gs.getDefaultConfiguration();
         bimage = gc.createCompatibleImage(
                 image.getWidth(null), image.getHeight(null), transparency);
      } catch (HeadlessException e) {
         // The system does not have a screen
      }

      if (bimage == null) {
         // Create a buffered image using the default color model
         int type = BufferedImage.TYPE_INT_RGB;
         if (hasAlpha) {
            type = BufferedImage.TYPE_INT_ARGB;
         }
         bimage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                                    type);
      }
      // Copy image to buffered image
      Graphics g = bimage.createGraphics();
      // Paint the image onto the buffered image
      g.drawImage(image, 0, 0, null);
      g.dispose();
      return bimage;
   }

   // This method returns true if the specified image has transparent pixels
   public static boolean hasAlpha(Image image) {
      // If buffered image, the color model is readily available
      if (image instanceof BufferedImage) {
         BufferedImage bimage = (BufferedImage) image;
         return bimage.getColorModel().hasAlpha();
      }
      // Use a pixel grabber to retrieve the image's color model;
      // grabbing a single pixel is usually sufficient
      PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
      try {
         pg.grabPixels();
      } catch (InterruptedException e) {
      }
      // Get the image's color model
      ColorModel cm = pg.getColorModel();
      return cm.hasAlpha();
   }
}
