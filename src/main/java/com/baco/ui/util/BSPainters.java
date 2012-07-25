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

import com.baco.ui.core.BSCoreFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import org.jdesktop.swingx.painter.Painter;
import org.jdesktop.swingx.painter.TextPainter;
import org.jdesktop.swingx.painter.AlphaPainter;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.RectanglePainter;
import org.jdesktop.swingx.painter.effects.GlowPathEffect;
import org.jdesktop.swingx.painter.AbstractAreaPainter.Style;
import org.jdesktop.swingx.painter.AbstractLayoutPainter.HorizontalAlignment;
import org.jdesktop.swingx.painter.AbstractLayoutPainter.VerticalAlignment;

/**
 * Clase para obtener instancias de Painters comunes
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSPainters {

   private static RectanglePainter roundedBoxPainter;
   private static CompoundPainter dialogBoxPainter;
   private static AlphaPainter alphaPainter50;
   private static TextPainter watermarkPainter;
   private static GlowPathEffect watermarkEffect;

   static {
      roundedBoxPainter = new RectanglePainter(Color.black, Color.black);
      alphaPainter50 = new AlphaPainter();
      roundedBoxPainter.setRoundHeight(10);
      roundedBoxPainter.setRoundWidth(10);
      roundedBoxPainter.setRounded(true);
      alphaPainter50.setAlpha(0.5f);
      dialogBoxPainter = new CompoundPainter(roundedBoxPainter, alphaPainter50);
      watermarkPainter = new TextPainter("BSKit");
      watermarkPainter.setFillPaint(Color.WHITE);
      watermarkPainter.setVerticalAlignment(VerticalAlignment.BOTTOM);
      watermarkPainter.setHorizontalAlignment(HorizontalAlignment.LEFT);
      watermarkPainter.setFont(new Font("sansserif", Font.BOLD, 22));
      watermarkPainter.setInsets(new Insets(5, 10, 5, 10));
      watermarkPainter.setAntialiasing(true);
      watermarkPainter.setStyle(Style.OUTLINE);
      watermarkEffect = new GlowPathEffect();
      watermarkEffect.setBrushColor(Color.LIGHT_GRAY);
      watermarkPainter.setAreaEffects(watermarkEffect);
   }

   public static Painter getRoundedBoxPainter() {
      return roundedBoxPainter;
   }

   public static Painter getAlphaPainter50() {
      return alphaPainter50;
   }

   public static Painter getDialogBoxPainter() {
      return roundedBoxPainter;
   }

   public static Painter getWatermarkPainter() {
      if (BSCoreFactory.getCore().getTitle() != null) {
         watermarkPainter.setText(BSCoreFactory.getCore().getTitle());
      }
      return watermarkPainter;
   }
}
