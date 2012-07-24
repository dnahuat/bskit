package com.baco.ui.containers;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.interpolation.LinearInterpolator;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Este panel permite transicionar entre dos colores de fondo
 * @author dnahuat
 */
public class BSColorAnimPanel extends javax.swing.JPanel implements
        TimingTarget {

    private Color firstColor;
    private Color lastColor;
    private boolean sameColor;
    private boolean isBackwards = false;
    private float ro; // Origen rojo
    private float go; // Origen verde
    private float bo; // Origen azul
    private float rf; // Final rojo
    private float gf; // Final verde
    private float bf; // Final azul
    private float cm; // Minima absoluta mayor a cero de diferencia de colores
    private float ri; // Factor de rojo
    private float gi; // Factor de verde
    private float bi; // Factor de azul
    // Animation
    Animator coreAnimation;

    /** Creates new form BackgroundAnimPanel */
    public BSColorAnimPanel(boolean embeddedEvent) {
        super();
        coreAnimation = new Animator(100, this);
        coreAnimation.setInterpolator(LinearInterpolator.getInstance());
        isBackwards = false;
        lastColor = Color.DARK_GRAY;
        firstColor = super.getBackground();
        initComponents();
        initColors();
        calculateConstants();
        if (embeddedEvent) {
            setupEvents();
        }
    }

    public BSColorAnimPanel(boolean embeddedEvent, Color lastColor) {
        super();
        coreAnimation = new Animator(100, this);
        coreAnimation.setInterpolator(LinearInterpolator.getInstance());
        isBackwards = false;
        this.lastColor = lastColor;
        firstColor = super.getBackground();
        initComponents();
        initColors();
        calculateConstants();
        if (embeddedEvent) {
            setupEvents();
        }
    }

    private void setupEvents() {
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent arg0) {
                isBackwards = false;
                coreAnimation.stop();
                coreAnimation.start();
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                isBackwards = true;
                coreAnimation.stop();
                coreAnimation.start();
            }
        });
    }

    public void setDuration(int milis) {
        coreAnimation.setDuration(milis);
    }

    public void setRepeatCount(int times) {
        coreAnimation.setRepeatCount(times);
    }

    private void initColors() {
        if (firstColor == null) {
            return;
        }
        if (lastColor == null) {
            return;
        }
        sameColor = (lastColor.equals(firstColor));
        ro = firstColor.getRed();
        go = firstColor.getGreen();
        bo = firstColor.getBlue();
        rf = lastColor.getRed();
        gf = lastColor.getGreen();
        bf = lastColor.getBlue();
    }

    public void setLastColor(Color lastColor) {
        this.lastColor = lastColor;
        initColors();
        calculateConstants();
    }

    @Override
    public void setBackground(Color arg0) {
        super.setBackground(arg0);
        firstColor = arg0;
        initColors();
        calculateConstants();
    }

    private void calculateConstants() {
        // Si son el mismo color no hacer nada
        if (sameColor || (firstColor == null) || (lastColor == null)) {
            return;
        }
        // Obtener el minimo absoluto, mayor a 0 de las diferencias
        float rda = Math.abs(rf - ro);
        float gda = Math.abs(gf - go);
        float bda = Math.abs(bf - bo);
        cm = rda;
        float rgbArray[] = {rda, gda, bda};
        for (int i = 1; i < rgbArray.length; i++) {
            if ((rgbArray[i] < cm) && (rgbArray[i] != 0)) {
                cm = rgbArray[i];
            }
        }
        // Obtener los factores
        ri = (rf - ro) / cm;
        gi = (gf - go) / cm;
        bi = (bf - bo) / cm;
    }

    @Override
    public void timingEvent(float arg0) {
        // Si son el mismo color no hacer nada
        if (sameColor || arg0 == 0.0) {
            return;
        }
        float step = arg0;
        if (isBackwards) {
            step = 1.0f - arg0;
        }
        float rc = step * cm * ri + ro;
        float gc = step * cm * gi + go;
        float bc = step * cm * bi + bo;
        super.setBackground(new Color((int) rc, (int) gc, (int) bc));
    }

    @Override
    public void begin() {
        if (!isBackwards) {
            calculateConstants();
        }
    }

    public void setBackwards(boolean backwards) {
        this.isBackwards = backwards;
    }

    @Override
    public void end() {
    }    

    @Override
    public void repeat() {
        isBackwards = !isBackwards;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {		
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup().addGap(0, 385,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup().addGap(0, 290,
				Short.MAX_VALUE));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
