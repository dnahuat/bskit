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
