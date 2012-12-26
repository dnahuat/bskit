/*
 *  Copyright 2012, Deiby Nahuat Uc
 */
package com.baco.ui.services;

import javax.swing.ImageIcon;

/**
 * Background process executor definition
 * @author dnahuat
 */
public interface BSExecutor {

	/**
	 * Submits a worker item for execution
	 * @param workItem The workItem
	 */
	void submitWorkItem(BSWorkItem workItem);
	
	/**
	 * Sets the systray icon while idle
	 * @param imageIcon The tray icon 
	 */
	void setIdleImageIcon(ImageIcon imageIcon);

	/**
	 * Sets the systray icon while running
	 * @param imageIcon  the Tray icon
	 */
	void setActiveImageIcon(ImageIcon imageIcon);
	
}	
