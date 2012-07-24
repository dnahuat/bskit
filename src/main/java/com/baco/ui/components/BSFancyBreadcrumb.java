package com.baco.ui.components;

import java.util.List;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Paint;
import sl.shapes.RoundPolygon;
import javax.swing.JComponent;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import java.awt.font.LineMetrics;

/**
 * CHANGELOG
 * ----------
 * 2011-05-31 (getNavigableElements) : Ya no se permiten duplicados
 * 2011-03-24 : Finalizacion de la implementacion
 * 2011-03-23 : Creación de la primera version
 *
 */
/**
 * Componente para desplegar un elemento de migajas
 * @author dnahuat
 */
public class BSFancyBreadcrumb extends JComponent {

   /* Elementos de color */
   private Paint currentBGPaint = Color.GREEN;
   private Paint futureBGPaint = Color.BLUE;
   private Paint pastBGPaint = Color.GRAY;

   /* Elementos de color de letra */
   private Paint currentFGPaint = Color.BLACK;
   private Paint futureFGPaint = Color.WHITE;
   private Paint pastFGPaint = Color.DARK_GRAY;
   /* Elementos del breadcrumb */
   List<BSBreadcrumbable> bcElements = new ArrayList();
   List<BSBreadcrumbable> bcParentElements = new ArrayList();
   BSBreadcrumbable currentParent = null;
   BSBreadcrumbable currentElement = null;

   /* Elementos graficos */
   private boolean isDirty = true;
   private BufferedImage parentComponentBG;
   private BufferedImage childComponentBG;
   private int childComponentXPos = 0;
   FontMetrics fmetrics;

   public BSFancyBreadcrumb() {
      super();
      super.setFont(Font.decode("tahoma-plain-16"));
      fmetrics = getFontMetrics(getFont());
      setOpaque(false);
      setupEvents();
   }

   private void setupEvents() {
      addComponentListener(new ComponentAdapter() {

         @Override
         public void componentResized(ComponentEvent e) {
            super.componentResized(e);
            isDirty = true;
         }
      });
   }

   /**
    * Establece los elementos a mostrar en el despliegue de navegacion
    * @param breadcrumbElements La lista de elementos
    */
   public void setElements(List<BSBreadcrumbable> breadcrumbElements) {
      //if (bcElements.isEmpty() && !breadcrumbElements.isEmpty()) {
      bcElements = new ArrayList();
      bcParentElements = new ArrayList();
      currentParent = null;
      currentElement = null;
      if (!breadcrumbElements.isEmpty()) {
         /* Obtener padres y ordenar elementos */
         for (int i = 0; i < breadcrumbElements.size(); i++) {
            if (breadcrumbElements.get(i).getParent() == null) {
               bcElements.add(breadcrumbElements.get(i));
               bcParentElements.add(breadcrumbElements.get(i));
               for (int j = 0; j < breadcrumbElements.size(); j++) {
                  if (breadcrumbElements.get(j).getParent() != null && breadcrumbElements.
                          get(j).getParent().equals(breadcrumbElements.get(i))) {
                     bcElements.add(breadcrumbElements.get(j));
                  }
               }
            }
         }
         /* Establecer estado inicial */
         currentElement = bcElements.get(0);
         currentParent = bcElements.get(0);
         if (bcElements.size() > 1) {
            if (bcElements.get(1).getParent() != null && currentElement.equals(bcElements.
                    get(1).getParent())) {
               currentElement = bcElements.get(1);
               currentParent = bcElements.get(0);
            }
         }
         isDirty = true;
         repaint();
      }
   }

   /**
    * Avanza un elemento en la navegacion
    */
   public boolean goForward() {
      int index = bcElements.indexOf(currentElement);
      index += 1;
      if (index < bcElements.size()) {
         currentElement = bcElements.get(index);
         if (currentElement.getParent() == null) {
            currentParent = currentElement;
         }
         if (index < bcElements.size() - 1) {
            if (currentElement.equals(bcElements.get(index + 1).getParent())) {
               currentParent = currentElement;
               currentElement = bcElements.get(index + 1);
            }
         }
         isDirty = true;
         repaint();
         return true;
      } else {
         return false;
      }
   }

   public boolean setElement(Integer valueId) {
      for (int i = 0; i < bcElements.size(); i++) {
         if (bcElements.get(i).getValueId() == valueId) {
            currentElement = bcElements.get(i);            
            if (currentElement.getParent() == null) {
               currentParent = currentElement;
            } else {
                if(i>0) {
                    if(currentElement.getParent().equals(bcElements.get(i-1))) {
                        currentParent = bcElements.get(i-1);
                    }
                }
            }
            isDirty = true;
            repaint();
            return true;
         } 
      }
      return false;
   }

   public List<BSBreadcrumbable> getNavigableElements() {
      List<BSBreadcrumbable> navigableElements = new ArrayList();
      BSBreadcrumbable element = null;
      for (int i = 0; i < bcElements.size(); i++) {
         element = bcElements.get(i);
         if (i < bcElements.size() - 1) {
            if (element.equals(bcElements.get(i + 1).getParent())) {
               element = bcElements.get(i + 1);
            }
         }
         if (!navigableElements.contains(element)) {
            navigableElements.add(element);
         }
      }
      return navigableElements;
   }

   public boolean isLast() {
      int index = bcElements.indexOf(currentElement);
      return (index == bcElements.size() - 1);
   }

   /**
    * Reinicia al primer elemento de navegacion
    */
   public void reset() {
      if (bcElements.size() > 0) {
         currentElement = this.bcElements.get(0);
         currentParent = this.bcElements.get(0);
         if (bcElements.size() > 1) {
            if (bcElements.get(1).getParent() != null && currentElement.equals(bcElements.
                    get(1).getParent())) {
               currentElement = this.bcElements.get(1);
               currentParent = this.bcElements.get(0);
            }
         }
         isDirty = true;
         repaint();
      }
   }

   /**
    * Establece el fondo para el elemento actual
    * @param currentBGPaint El fondo
    */
   public void setCurrentBGPaint(Paint currentBGPaint) {
      this.currentBGPaint = currentBGPaint;
      isDirty = true;
      repaint();
   }

   /**
    * Establece el fondo para elementos pasados
    * @param pastBGPaint El fondo
    */
   public void setPastBGPaint(Paint pastBGPaint) {
      this.pastBGPaint = pastBGPaint;
      isDirty = true;
      repaint();
   }

   /**
    * Establece el fondo para elementos futuros
    * @param futureBGPaint El fondo
    */
   public void setFutureBGPaint(Paint futureBGPaint) {
      this.futureBGPaint = futureBGPaint;
      isDirty = true;
      repaint();
   }

   /**
    * Establece el color de pintado para el texto del elemento actual
    * @param currentFGPaint El color
    */
   public void setCurrentFGPaint(Paint currentFGPaint) {
      this.currentFGPaint = currentFGPaint;
      isDirty = true;
      repaint();
   }

   /**
    * Establece el color de pintado para el texto de elementos pasados
    * @param pastFGPaint El color
    */
   public void setPastFGPaint(Paint pastFGPaint) {
      this.pastFGPaint = pastFGPaint;
      isDirty = true;
      repaint();
   }

   /**
    * Establece el color de pintado para el texto de elementos futuros
    * @param futureFGPaint El color
    */
   public void setFutureFGPaint(Paint futureFGPaint) {
      this.futureFGPaint = futureFGPaint;
      isDirty = true;
      repaint();
   }

   /**
    * Obtiene el elemento actual en la navegacion
    * @return El elemento actual
    */
   public BSBreadcrumbable getCurrentElement() {
      return currentElement;
   }

   public BSBreadcrumbable getFirstElement() {
      if (bcElements.size() > 1) {
         List<BSBreadcrumbable> navigableElements = getNavigableElements();
         return navigableElements.get(0);        
      }
      return bcElements.get(0);
   }

   /**
    * Devuelve el padre actual en la navegacion
    * @return El padre actual
    */
   public BSBreadcrumbable getCurrentParent() {
      return currentParent;
   }

   @Override
   public void setFont(Font font) {
      super.setFont(font);
      fmetrics = getFontMetrics(getFont());
      isDirty = true;
      repaint();
   }

   @Override
   protected void paintComponent(Graphics g) {
      if (bcParentElements.size() > 0) {
         if (isDirty) {
            parentComponentBG = new BufferedImage(getWidth(), getHeight() / 2,
                                                  BufferedImage.TYPE_INT_ARGB);
            int sectionWidth = getWidth() / bcParentElements.size();
            int sectionHeight = getHeight() / 2;
            boolean hasParent = (currentElement.getParent() != null);
            Graphics2D c1 = (Graphics2D) parentComponentBG.getGraphics();
            c1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            c1.setFont(getFont());
            boolean hasPastCurrent = false;
            childComponentXPos = 0;
            Paint currentBG = currentBGPaint;
            Paint currentFG = currentFGPaint;
            for (int i = bcParentElements.size() - 1; i >= 0; i--) {
               /* Poligono para esta iteracion */
               int sectionStartPosX = sectionWidth * i;
               int sectionEndPosX = sectionWidth * (i + 1);
               Polygon poly = new Polygon();
               poly.addPoint(0, 0);
               poly.addPoint(sectionEndPosX - 15, 0);
               poly.addPoint(sectionEndPosX, (sectionHeight / 2) - 1);
               poly.addPoint(sectionEndPosX - 15, sectionHeight - 1);
               poly.addPoint(0, sectionHeight - 1);
               RoundPolygon rpoly = new RoundPolygon(poly, 4);
               if (bcParentElements.get(i).equals(currentParent)) {
                  /* Si hay mas de un elemento padre, la posicion x de los hijos puede ser diferente de 0 */
                  if (bcParentElements.size() > 1) {
                     /* Si el padre es el ultimo en la lista, la posicion x de los hijos es anterior a
                     la posicion x del padre */
                     childComponentXPos = (i == bcParentElements.size() - 1) ?
                                          sectionStartPosX - (sectionWidth / 2) :
                                          sectionStartPosX;
                  }
                  currentBG = currentBGPaint;
                  currentFG = currentFGPaint;
                  hasPastCurrent = true;
               } else {
                  currentBG = (!hasPastCurrent) ? futureBGPaint : pastBGPaint;
                  currentFG = (!hasPastCurrent) ? futureFGPaint : pastFGPaint;
               }
               c1.setPaint(currentBG);
               c1.fill(rpoly);
               c1.setColor(Color.BLACK);
               c1.setStroke(new BasicStroke(2f));
               c1.draw(rpoly);
               c1.setPaint(currentFG);
               c1.setFont(getFont());
               String value = bcParentElements.get(i).getValue().toString();
               LineMetrics lineMetrics = fmetrics.getLineMetrics(value, g);
               c1.drawString(value,
                             (sectionEndPosX - (sectionWidth / 2)) - (fmetrics.
                       stringWidth(value) / 2),
                             (sectionHeight / 2) + ((lineMetrics.getAscent() - lineMetrics.
                       getDescent()) / 2));
            }
            c1.dispose();
            /* Dibujado de hijos */
            if (hasParent) {
               int childSectionHeight = getHeight() / 2;
               /* Si solo existe un elemento padre, el ancho es el mismo que el del padre */
               int childWidth = (bcParentElements.size() > 1) ?
                                sectionWidth + (sectionWidth / 2) : sectionWidth;

               childComponentBG = new BufferedImage(childWidth,
                                                    childSectionHeight,
                                                    BufferedImage.TYPE_INT_ARGB);
               Graphics2D c2 = (Graphics2D) childComponentBG.getGraphics();
               c2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                   RenderingHints.VALUE_ANTIALIAS_ON);
               List<BSBreadcrumbable> siblings = new ArrayList();
               /* Obtener la lista de elementos hijo */
               for (BSBreadcrumbable element : bcElements) {
                  if (element.getParent() != null && element.getParent().
                          equals(currentElement.getParent())) {
                     siblings.add(element);
                  }
               }
               hasPastCurrent = false;
               int childSectionWidth = childWidth / siblings.size();
               for (int i = siblings.size() - 1; i >= 0; i--) {
                  int childSectionEndPosX = childSectionWidth * (i + 1);
                  /* Poligono para esta iteracion */
                  Polygon poly = new Polygon();
                  poly.addPoint(0, 0);
                  poly.addPoint(childSectionEndPosX - 15, 0);
                  poly.addPoint(childSectionEndPosX,
                                (childSectionHeight / 2) - 1);
                  poly.addPoint(childSectionEndPosX - 15,
                                childSectionHeight - 1);
                  poly.addPoint(0, childSectionHeight - 1);
                  RoundPolygon rpoly = new RoundPolygon(poly, 4);
                  if (siblings.get(i).equals(currentElement)) {
                     currentBG = currentBGPaint;
                     currentFG = currentFGPaint;
                     hasPastCurrent = true;
                  } else {
                     currentBG = (!hasPastCurrent) ? futureBGPaint : pastBGPaint;
                     currentFG = (!hasPastCurrent) ? futureFGPaint : pastFGPaint;
                  }
                  c2.setPaint(currentBG);
                  c2.fill(rpoly);
                  c2.setColor(Color.BLACK);
                  c2.setStroke(new BasicStroke(2f));
                  c2.draw(rpoly);
                  c2.setPaint(currentFG);
                  c2.setFont(getFont());
                  String value = siblings.get(i).getValue().toString();
                  LineMetrics lineMetrics = fmetrics.getLineMetrics(value, g);
                  c2.drawString(value,
                                (childSectionEndPosX - (childSectionWidth / 2)) - (fmetrics.
                          stringWidth(value) / 2),
                                (childSectionHeight / 2) + ((lineMetrics.
                          getAscent() - lineMetrics.getDescent()) / 2));

               }
               c2.dispose();
            }
            isDirty = false;
         }
         Graphics2D g2d = (Graphics2D) g;
         g2d.drawImage(parentComponentBG, 0, 0, null);
         if (currentElement.getParent() != null) {
            g2d.drawImage(childComponentBG, childComponentXPos, getHeight() / 2,
                          null);
         }
      }
   }
}
