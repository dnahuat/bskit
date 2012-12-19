/*
 *  Copyright 2012, Deiby Nahuat Uc
 */
package com.baco.ui.services;

import java.awt.MenuComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Work unit used in the job execution subsystem
 * @author dnahuat
 */
public abstract class BSWorkItem implements Callable<BSWorkItem>, ActionListener {
	
	private String workname = "generic work";
	private String message = "ok";
	private String displayLabel = "generic work";
	private String timeLabel = "0s";
	private AtomicBoolean isExecuted = new AtomicBoolean(false);
	private AtomicBoolean isSuccessful = new AtomicBoolean(false);
	private Date startDate = null;
	private Date endDate = null;
	private Exception cex = null;
	private MenuComponent menuComponent = null;

	public BSWorkItem() {
	}

	/**
	 * Obtains the canonical name of this work unit
	 * @return The canonical name
	 */
	public final String getWorkName() {
		return workname;
	}

	/**
	 * Sets the canonical name
	 * @param workName The canonical name
	 */
	protected final void setWorkName(String workName) {
		this.workname = workName;
	}

	/**
	 * Obtains the string used in labels
	 * @return The display label
	 */
	public final String getDisplayLabel() {
		return displayLabel;
	}

	/**
	 * Sets the string used in labels
	 * @param displayLabel The new display label
	 */
	protected final void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}

	/**
	 * Gets the operation execution message
	 * @return The operation message 
	 */
	public final String getOperationMessage() {
		return message;
	}

	/**
	 * Sets the operation execution message
	 * @param message The operation message
	 */
	protected final void setOperationMessage(String message) {
		this.message = message;
	}

	/**
	 * Obtain work start date 
	 * @return Whe work item execution start timestamp
	 */
	public final Date getStartDate() {
		return startDate;
	}

	/**
	 * Obtain work end date
	 * @return The work item execution end timestamp
	 */
	public final Date getEndDate() {
		return endDate;
	}

	/**
	 * Obtain execution time as a string
	 */
	public final String getTimeLabel() {
		return timeLabel;
	}
	
	/**
	 * The exception in case of an error
	 * @return The thrown exception
	 */
	public final Exception getException() {
		return cex;
	}

	/**
	 * Indicates if the job execution was succesful
	 * @return The job operation status 
	 */
	public final Boolean isSuccessful() {
		return isSuccessful.get();
	}

	/**
	 * Indicates if the job has been executed
	 * @return The job execution status
	 */
	public final Boolean isExecuted() {
		return isExecuted.get();
	}

	/**
	 * Sets the menuitem to show in taskbar
	 * @param menu The menuitem to show
	 */
	public final void setWorkMenu(MenuComponent menu) {
		this.menuComponent = menu;
	}

	/**
	 * Gets the menuitem to show in taskbar
	 * @return  The menuitem to show
	 */
	public final MenuComponent getWorkMenu() {
		return this.menuComponent;
	}

	/**
	 * This method should return a signature that uniquely identifies this work item
	 * @return The signature
	 */
	public abstract String getWorkItemSignature();

	/**
	 * Implement this method to process events launched through the work item systray
	 */
	@Override
	public abstract void actionPerformed(ActionEvent e);

	/**
	 * This method should contain job initialization
	 * When updating graphical elements, SwingUtilities.invokeLater should be
	 * used to avoid inconsistence or dead locks.
	 */
	public abstract void beforeExecution() throws Exception;
	
	/**
	 * This method should contain job logic.
	 * Never update graphical elements in this method
	 */
	public abstract void execute() throws Exception;

	/**
	 * This method is called after a succesful execution
	 * When updating graphical elements, SwingUtilities.invokeLater should be
	 * used to avoid inconsistence or dead locks.
	 */
	public abstract void afterExecution() throws Exception;
	
	@Override
	public final BSWorkItem call() throws Exception {
		long start = System.currentTimeMillis();
		if(!isExecuted.get()) {
			startDate = new Date(start);
			isExecuted.set(true);
			try {
				/**
				 * Ejecutar la tarea
				 */
				execute();
				isSuccessful.set(true);
			} catch (Exception ex) {
				isSuccessful.set(false);
				cex = ex;
			}
		}
		long end = System.currentTimeMillis();
		endDate = new Date(end);
		/**
		 * Set time label
		 */
		long executionTime = endDate.getTime() - startDate.getTime();
		double executionSeconds = executionTime / 1000.00;
		timeLabel = String.valueOf(executionSeconds) + "s";
		return this;
	}
	
}
