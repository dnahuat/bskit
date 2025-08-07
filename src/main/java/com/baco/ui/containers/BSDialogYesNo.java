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
package com.baco.ui.containers;

import com.baco.ui.core.BSCoreFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Implementacion de dialogo para confirmacion de respuestas SI/NO
 *
 * @author dnahuat
 */
public class BSDialogYesNo extends BSDefaultDialog {

	private String completeMessage;
	private final BSConfirmListener listener;

	private BSDialogYesNo(String primaryTitle, String secondaryTitle, BSConfirmListener listener) {
		this.completeMessage = "<html><body><p style='color:rgb(255,102,0);font-weight:bolder;font:sans-serif;font-size:1.1em;'>"
				+ primaryTitle
				+ "</p>"
				+ "<p style= 'font:sans-serif;color:#ffffff;'>"
				+ secondaryTitle
				+ "</p>"
				+ "</body></html>";
		this.listener = listener;
		initComponents();
		edtMessage.setText(completeMessage);
		setupEvents();
	}

	public static void showDialog(final BSConfirmListener listener, final String question, final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BSDialog dialog = new BSDialogYesNo(question, message, listener);
				BSCoreFactory.getCore().loadAsDialog(dialog);
			}
		});
	}

	private void setupEvents() {
		btnYes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BSCoreFactory.getCore().closeDialog(BSDialogYesNo.this);
				if (listener != null) {
					listener.yesOption();
				}
			}
		});
		btnYes.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F9"), "doClick");
		btnYes.getActionMap().put("doClick", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnYes.doClick();
			}
		});
		btnNo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BSCoreFactory.getCore().closeDialog(BSDialogYesNo.this);
				if (listener != null) {
					listener.noOption();
				}
			}
		});
		btnNo.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F4"), "doClick");
		btnNo.getActionMap().put("doClick", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnNo.doClick();
			}
		});
	}

	@Override
	public String getUniqueName() {
		return "BSDialogYesNo";
	}

	@Override
	public void afterLoad() {
		btnNo.requestFocusInWindow();
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXPanel1 = new org.jdesktop.swingx.JXPanel();
        jLabel2 = new javax.swing.JLabel();
        edtMessage = new javax.swing.JTextPane();
        btnNo = new javax.swing.JButton();
        btnYes = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jXPanel1.setInheritAlpha(false);
        jXPanel1.setOpaque(false);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/baco/ui/icons/icon_details.png"))); // NOI18N

        edtMessage.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        edtMessage.setContentType("text/html");
        edtMessage.setEditable(false);
        edtMessage.setFont(new java.awt.Font("Dialog", 1, 12));
        edtMessage.setOpaque(false);
        edtMessage.setDoubleBuffered(true);
        edtMessage.setForeground(new java.awt.Color(255, 255, 255));
        edtMessage.setMargin(new java.awt.Insets(0, 0, 0, 0));
        edtMessage.setVerifyInputWhenFocusTarget(false);

        btnNo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel_button.png"))); // NOI18N
        btnNo.setMnemonic('N');
        btnNo.setText("<html><p><span style='vertical-align:super;font-size:0.9em;'>F4</span> <span style='font-weight:bold;'>NO</span></html>");

        btnYes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ok_button.png"))); // NOI18N
        btnYes.setMnemonic('S');
        btnYes.setText("<html><p><span style='vertical-align:super;font-size:0.9em;'>F9</span> <span style='font-weight:bold;'>SI</span></html>");

        jXPanel1.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gridBagConstraints;

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jXPanel1.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jXPanel1.add(edtMessage, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        jXPanel1.add(btnNo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        jXPanel1.add(btnYes, gridBagConstraints);

        add(jXPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNo;
    private javax.swing.JButton btnYes;
    private javax.swing.JTextPane edtMessage;
    private javax.swing.JLabel jLabel2;
    private org.jdesktop.swingx.JXPanel jXPanel1;
    // End of variables declaration//GEN-END:variables
}
