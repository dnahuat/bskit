package com.baco.ui.components;

import java.awt.Font;
import java.util.Map;
import java.awt.Color;
import java.util.HashMap;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.FontMetrics;
import javax.swing.JComponent;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeListener;


/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Componente grafico que permite seleccionar los caracteres individuales de una
 * cadena y devuelve la cadena seleccionada segun el procesamiento dado por un
 * extractor de texto.
 * @author dnahuat
 */
public class BSTextSelectEditor extends JComponent {
    
    /* Elementos graficos */
    private int oldHeight;
    private RoundRectangle2D rRect;
    private boolean isResized = true;
    private boolean drawMeasureTape = true;
    private BufferedImage charInactiveBubble;
    private BufferedImage charActiveBubbleLeft;
    private BufferedImage charActiveBubbleAlone;
    private BufferedImage charActiveBubbleRight;
    private BufferedImage charActiveBubbleCenter;

    /* Variables de dimension */
    private int charHeight;
    private int bubbleHeight;
    private int bubbleWidth = 12;
    private int measureFontWidth;
    private int bubbleWidthPadding=7;
    private int currentPosition = -1;
    private int measureTapeArea = 20;
    private Rectangle2D activeArea = new Rectangle2D.Float();
    private Font measureFont =  new Font( "Monospaced", Font.BOLD, 8);
    private FontMetrics tapeMetrics = new FontMetrics(measureFont) {};
    /* Texto */
    private StringBuffer textBuffer;
    private Map<Integer, Boolean> selectedPositions = new HashMap<Integer, Boolean>();
    private BSTextExtractor extractor;

    public BSTextSelectEditor(){
        super();
        extractor = new BSSimpleSelectionExtractor();
        setDefaults();
        oldHeight = getHeight();
        textBuffer = new StringBuffer();
        setupEvents();
    }

    private void setDefaults() {
        setMinimumSize(new Dimension(100, 70));
        setPreferredSize(new Dimension(150, 70));

    }

    private void setupEvents() {
        addMouseMotionListener(new MouseMotionAdapter() {
            private int currentTraversedPosition = -1;
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                currentPosition = (x - 5) / (bubbleWidth - 1);
                if((currentPosition >= 0) && (currentPosition < textBuffer.length()) &&
                        (currentPosition != currentTraversedPosition)) {
                    if((y > 5) && (y < (getHeight()-measureTapeArea-5))) {

                        selectedPositions.put(currentPosition, (selectedPositions.get(currentPosition) == null || !selectedPositions.get(currentPosition)));
                    }
                    repaint();
                }
                currentTraversedPosition = currentPosition;
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    int x = e.getX();
                    int y = e.getY();
                    currentPosition = (x - 5) / (bubbleWidth - 1);
                    if((currentPosition < textBuffer.length())) {
                        if((y > 5) && (y < (getHeight()-measureTapeArea-5))) {
                            selectedPositions.put(currentPosition, (selectedPositions.get(currentPosition) == null || !selectedPositions.get(currentPosition)));
                        }
                        repaint();
                    }                    
                }
            }            
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if(oldHeight != getHeight()) {
                    setResized();
                }
                super.componentResized(e);
            }
        });
        addPropertyChangeListener("font", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                setResized();
                repaint();
            }
        });
    }

    public String getSelectedText() {
        StringBuffer selectedText = new StringBuffer();
        for(int i = 0; i < textBuffer.length(); i++) {
            if(selectedPositions.get(i) != null && selectedPositions.get(i)) {
                selectedText.append(textBuffer.charAt(i));
            }
        }
        return selectedText.toString();
    }

    public void setSelectedText(String selectedText) {
    }

    private void updateDimensions() {
        if(drawMeasureTape) {
            measureTapeArea = 25;
        } else {
            measureTapeArea = 0;
        }
        bubbleHeight = getHeight()-measureTapeArea-8;
        FontMetrics metrics = new FontMetrics(getFont()) {
        };
        Rectangle2D bounds = metrics.getStringBounds("W", null);
        int widthInPixels = (int) bounds.getWidth();
        int heightInPixels = (int) bounds.getHeight();
        bubbleWidthPadding = 2;
        bubbleWidth = widthInPixels + 4;
        charHeight  = heightInPixels;
        activeArea.setRect(5, 5, (textBuffer.length() * bubbleWidth), getHeight()-measureTapeArea-5);
    }
    
    public BSTextSelectEditor(String text) {
        super();
        oldHeight = getHeight();
        textBuffer = new StringBuffer(text);
        setupEvents();
    }

    private void updateGraphicalBuffer() {        
        charInactiveBubble    = new BufferedImage(bubbleWidth, 
                                                  bubbleHeight,
                                                  BufferedImage.TYPE_INT_ARGB);
        charActiveBubbleAlone = new BufferedImage(bubbleWidth, 
                                                  bubbleHeight,
                                                  BufferedImage.TYPE_INT_ARGB);
        charActiveBubbleCenter= new BufferedImage(bubbleWidth, 
                                                  bubbleHeight,
                                                  BufferedImage.TYPE_INT_ARGB);
        charActiveBubbleLeft  = new BufferedImage(bubbleWidth, 
                                                  bubbleHeight,
                                                  BufferedImage.TYPE_INT_ARGB);
        charActiveBubbleRight = new BufferedImage(bubbleWidth, 
                                                  bubbleHeight,
                                                  BufferedImage.TYPE_INT_ARGB);

        /* INACTIVE BUBBLE */
        Graphics2D c1 = (Graphics2D)charInactiveBubble.getGraphics();
        c1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                 RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp1 = new GradientPaint(0, 0, Color.LIGHT_GRAY, 0,
                                              bubbleHeight, Color.DARK_GRAY, false);
        c1.setPaint(gp1);
        c1.fillRoundRect(0, 0, (bubbleWidth-1), bubbleHeight-2, 11, 11);
        c1.setColor(Color.DARK_GRAY);
        c1.setStroke(new BasicStroke(0.5f));
        c1.drawRoundRect(0, 0, (bubbleWidth-1), bubbleHeight-2, 11, 11);
        c1.dispose();

        /* ISOLATED ACTIVE BUBBLE */
        c1 = (Graphics2D)charActiveBubbleAlone.getGraphics();
        c1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                 RenderingHints.VALUE_ANTIALIAS_ON);
        Color activeColor = new Color(20,200,50);
        gp1 = new GradientPaint(0, 0, activeColor.darker(), 0,
                                bubbleHeight, activeColor, false);
        c1.setPaint(gp1);
        c1.fillRoundRect(0, 0, (bubbleWidth-1), bubbleHeight-2, 11, 11);
        c1.setColor(Color.BLACK);
        c1.setStroke(new BasicStroke(0.5f));
        c1.drawRoundRect(0, 0, (bubbleWidth-1), bubbleHeight-2, 11, 11);
        c1.dispose();

        /* ACTIVE BUBBLE CENTER */
        c1 = (Graphics2D)charActiveBubbleAlone.getGraphics();
        c1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                 RenderingHints.VALUE_ANTIALIAS_ON);
        gp1 = new GradientPaint(0, 0, activeColor.darker(), 0,
                                bubbleHeight, activeColor, false);
        c1.setPaint(gp1);
        c1.fillRoundRect(0, 0, (bubbleWidth-1), bubbleHeight-2, 11, 11);
        c1.setColor(Color.BLACK);
        c1.setStroke(new BasicStroke(0.5f));
        c1.drawRoundRect(0, 0, (bubbleWidth-1), bubbleHeight-2, 11, 11);
        c1.dispose();

        /* ACTIVE BUBBLE CENTER */
        c1 = (Graphics2D)charActiveBubbleCenter.getGraphics();
        c1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                 RenderingHints.VALUE_ANTIALIAS_ON);
        gp1 = new GradientPaint(0, 0, activeColor.darker(), 0,
                                bubbleHeight, activeColor, false);
        c1.setPaint(gp1);
        c1.fillRect(0, 0, (bubbleWidth-1), bubbleHeight-2);
        c1.setColor(Color.BLACK);
        c1.setStroke(new BasicStroke(0.5f));
        c1.drawLine(0, 0, (bubbleWidth-1), 0);
        c1.drawLine(0, bubbleHeight-2, (bubbleWidth-1), bubbleHeight-2);
        c1.dispose();

        /* ACTIVE BUBBLE RIGHT */
        c1 = (Graphics2D)charActiveBubbleLeft.getGraphics();
        c1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                 RenderingHints.VALUE_ANTIALIAS_ON);
        gp1 = new GradientPaint(0, 0, activeColor.darker(), 0,
                                bubbleHeight, activeColor, false);
        c1.setPaint(gp1);
        c1.fillRoundRect(0, 0, (bubbleWidth-1), bubbleHeight-2, 11, 11);
        c1.setColor(Color.BLACK);
        c1.setStroke(new BasicStroke(0.5f));
        c1.drawRoundRect(0, 0, (bubbleWidth-1), bubbleHeight-2, 11, 11);
        c1.setPaint(gp1);
        c1.fillRect(0, 0, 5, bubbleHeight-2);
        c1.setColor(Color.BLACK);
        c1.setStroke(new BasicStroke(0.5f));
        c1.drawLine(0, 0, 5, 0);
        c1.drawLine(0, bubbleHeight-2, 5, bubbleHeight-2);
        c1.dispose();

        /* ACTIVE BUBBLE LEFT */
        c1 = (Graphics2D)charActiveBubbleRight.getGraphics();
        c1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                 RenderingHints.VALUE_ANTIALIAS_ON);
        gp1 = new GradientPaint(0, 0, activeColor.darker(), 0,
                                bubbleHeight, activeColor, false);
        c1.setPaint(gp1);
        c1.fillRoundRect(0, 0, (bubbleWidth-1), bubbleHeight-2, 11, 11);
        c1.setColor(Color.BLACK);
        c1.setStroke(new BasicStroke(0.5f));
        c1.drawRoundRect(0, 0, (bubbleWidth-1), bubbleHeight-2, 11, 11);
        c1.setPaint(gp1);
        c1.fillRect(5, 0, (bubbleWidth-1), bubbleHeight-2);
        c1.setColor(Color.BLACK);
        c1.setStroke(new BasicStroke(0.5f));
        c1.drawLine((bubbleWidth-1), bubbleHeight-2, 5, bubbleHeight-2);
        c1.drawLine((bubbleWidth-1), 0, 5, 0);
        c1.dispose();

        /* CLEAN RESIZED FLAG */
        isResized = false;
    }

    private void updateTextPaint(Graphics2D g2d) {
        if(textBuffer != null) {                        
            for (int i = 0; i < textBuffer.length(); i++) {
                if((selectedPositions.get(i) != null) && selectedPositions.get(i)) {
                    if((selectedPositions.get(i-1) != null && selectedPositions.get(i-1)) &&
                            (selectedPositions.get(i+1) != null && selectedPositions.get(i+1))) {
                        /* CENTER */
                        g2d.drawImage(charActiveBubbleCenter, (i*(bubbleWidth-1))+5, 5, null);
                        g2d.setColor(Color.BLUE);
                    } else {
                        if((selectedPositions.get(i-1) != null && selectedPositions.get(i-1))) {
                            /* LEFT */
                            g2d.drawImage(charActiveBubbleLeft, (i*(bubbleWidth-1))+5, 5, null);
                            g2d.setColor(Color.RED);
                        } else {
                            if((selectedPositions.get(i+1) != null && selectedPositions.get(i+1))) {
                                /* RIGHT */
                                g2d.drawImage(charActiveBubbleRight, (i*(bubbleWidth-1))+5, 5, null);
                                g2d.setColor(Color.RED);
                            } else {
                                /* ISOLATED */
                                g2d.drawImage(charActiveBubbleAlone, (i*(bubbleWidth-1))+5, 5, null);
                                g2d.setColor(Color.RED);
                            }
                        }
                    }
                } else {
                    /* INACTIVE */
                    g2d.drawImage(charInactiveBubble, (i*(bubbleWidth-1))+5, 5, null);
                    g2d.setColor(Color.DARK_GRAY);
                }
                if(drawMeasureTape) {
                    /* DRAW MEASURE TAPE TEXT */
                    g2d.setFont(measureFont);
                    Rectangle2D bounds = tapeMetrics.getStringBounds(String.valueOf(i+1), null);
                    measureFontWidth = (int) bounds.getWidth();
                    g2d.drawString(String.valueOf(i+1), ((i*(bubbleWidth-1)) + (bubbleWidth/2) - (measureFontWidth/2))+5, getHeight()-5);

                    /* DRAW MEASURE TAPE */
                    g2d.setColor(Color.DARK_GRAY);
                    if((i+1) % 5 == 0) {
                        g2d.setStroke(new BasicStroke(2));
                        g2d.drawLine(((i+1)*(bubbleWidth-1))-((bubbleWidth-1)/2)+5,
                                     getHeight() - measureTapeArea,
                                     ((i+1)*(bubbleWidth-1))-((bubbleWidth-1)/2)+5,
                                     getHeight()-20);
                    } else {
                        g2d.setStroke(new BasicStroke(1));
                        g2d.drawLine(((i+1)*(bubbleWidth-1))-((bubbleWidth-1)/2)+5,
                                     getHeight() - measureTapeArea,
                                     ((i+1)*(bubbleWidth-1))-((bubbleWidth-1)/2)+5,
                                     getHeight()-22);
                    }
                    if(((i+1) == textBuffer.length()) || ((i+1) == 1)) {
                        g2d.setStroke(new BasicStroke(2));
                        g2d.drawLine(((i+1)*(bubbleWidth-1))-((bubbleWidth-1)/2)+5,
                                     getHeight() - measureTapeArea,
                                     ((i+1)*(bubbleWidth-1))-((bubbleWidth-1)/2)+5,
                                     getHeight()-17);
                    }
                }
                g2d.setColor(Color.BLACK);
                /* DRAW BUBBLES TEXT */
                g2d.setFont(getFont());
                g2d.setColor(getForeground());
                g2d.drawString(String.valueOf(textBuffer.charAt(i)), 
                                              (i*(bubbleWidth-1))+5+bubbleWidthPadding,
                                              ((getHeight()-measureTapeArea)/2)+((charHeight*2)/6));
            }
        }
    }

    public void resetSelection() {
        selectedPositions.clear();
        repaint();
    }

    public void reset() {
        textBuffer = new StringBuffer();
        selectedPositions.clear();
        repaint();
    }

    public void setText(String text) {
        if(textBuffer.length()  == (text.length() - 1)) {
            if(text.contains(textBuffer)) {
                selectedPositions.put(text.length(), false);
            }
        }
        if((textBuffer.length()-1) == text.length()) {
            if(textBuffer.toString().contains(text)) {
                selectedPositions.put(textBuffer.length()-1, false);
            }
        }
        textBuffer = new StringBuffer(text);
        repaint();
    }

    public String getText() {
        return textBuffer.toString();
    }

    public String getExtractorText(int padding) {
        return extractor.getProcessedText(textBuffer, selectedPositions, padding);
    }

    public void setTextExtractor(BSTextExtractor extractor) {
        this.extractor = extractor;
    }

    public BSTextExtractor getTextExtractor() {
        return extractor;
    }
        
    @Override
    protected void paintComponent(Graphics g) {
        updateDimensions();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                 RenderingHints.VALUE_ANTIALIAS_ON);        
        if(!isOpaque()) {
            rRect = new RoundRectangle2D.Double(0,0, getWidth(),
                                               (getHeight()-measureTapeArea), 25,25);
            GradientPaint gp1 = new GradientPaint(0, 0,
                                                 this.getBackground().darker(),
                                                 0, (getHeight()-measureTapeArea),
                                                 this.getBackground(), false);
            g2d.setPaint(gp1);
            g2d.fill(rRect);
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawRoundRect(1, 1, getWidth()-3, getHeight()-measureTapeArea-3, 25, 25);
        } else {
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight()-measureTapeArea);
        }
        if(isResized()) {
            updateGraphicalBuffer();
        }
        updateTextPaint(g2d);
    }
    
    private synchronized boolean isResized() {
        return isResized;
    }
    
    private synchronized void setResized() {
        isResized = true;
    }
    
    public boolean getTapeDrawed() {
        return drawMeasureTape;
    }
    
    public void setTapeDrawed(boolean drawMeasureTape) {
        if(this.drawMeasureTape ^ drawMeasureTape) {
            isResized = true;
            this.drawMeasureTape = drawMeasureTape;
            this.repaint();
        }

    }
    
}
