/*
 *  Copyright 2012, Deiby Nahuat Uc
 */
package com.baco.ui.services;

import com.baco.ui.core.BSCoreFactory;
import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

/**
 * Background process execution service
 *
 * @author dnahuat
 */
public class BSExecutorService implements BSStartupAction, BSExecutor {

	/**
	 * Decorator for backend executor
	 */
	private class BackendExecutor extends ThreadPoolExecutor {

		public BackendExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		}

		@Override
		protected void beforeExecute(Thread t, Runnable r) {
			if (serviceStarted.get()) {
				runningTasks.incrementAndGet();
				/**
				 * Change execution sys icon
				 */
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if (SystemTray.isSupported()) {
							trayIcon.setImage(activeImageIcon.getImage());
						}
					}
				});
			}
			super.beforeExecute(t, r);
		}

		@Override
		protected void afterExecute(Runnable r, Throwable t) {
			if (serviceStarted.get()) {
				if (runningTasks.decrementAndGet() <= 0) {
					/**
					 * Change execution sys icon
					 */
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							if (SystemTray.isSupported()) {
								trayIcon.setImage(idleImageIcon.getImage());
							}
						}
					});
				}
			}
			super.afterExecute(r, t);
		}
	}
	/**
	 * This instance
	 */
	private static BSExecutor instance;
	/**
	 * Startup service status
	 */
	private final AtomicBoolean serviceStarted;
	/**
	 * Number executing tasks
	 */
	private final AtomicInteger runningTasks;
	/**
	 * The backend executor
	 */
	private BackendExecutor backendExecutor;
	/**
	 * The system tray
	 */
	private SystemTray tray;
	/**
	 * The tray icon
	 */
	private TrayIcon trayIcon;
	/**
	 * Idle Image Icon
	 */
	private ImageIcon idleImageIcon;
	/**
	 * Active image icon
	 */
	private ImageIcon activeImageIcon;
	/**
	 * The work items menu
	 */
	private PopupMenu trayMenu;
	/**
	 * The service executor
	 */
	private ExecutorCompletionService<BSWorkItem> serviceExecutor;
	/**
	 * The operation exception
	 */
	private Exception cex = null;
	private List<String> signatures;

	private BSExecutorService() {
		runningTasks = new AtomicInteger(0);
		serviceStarted = new AtomicBoolean(false);
		backendExecutor = new BackendExecutor(15, 15,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		//this.backendExecutor = Executors.newFixedThreadPool(15);
		this.serviceExecutor = new ExecutorCompletionService<>(backendExecutor);
		this.signatures = Collections.synchronizedList(new ArrayList());
	}

	public static BSExecutor getInstance() {
		if (instance == null) {
			instance = new BSExecutorService();
		}
		return instance;
	}

	public static BSExecutorService getServiceInstance() {
		if (instance == null) {
			instance = new BSExecutorService();
		}
		return (BSExecutorService) instance;
	}

	@Override
	public final Exception getActionException() {
		return cex;
	}

	@Override
	public final boolean executeAction() {
		/**
		 * Bootup service consumer
		 */
		backendExecutor.submit(new WorkItemConsumer());
		if (idleImageIcon == null) {
			idleImageIcon = new ImageIcon(getClass().getResource("/com/baco/ui/icons/menu_not_found.png"));
		}
		if (activeImageIcon == null) {
			activeImageIcon = idleImageIcon;
		}
		/**
		 * Bootup system tray
		 */
		if (SystemTray.isSupported()) {
			tray = SystemTray.getSystemTray();
			trayMenu = new PopupMenu("Trabajos en fondo");
			trayIcon = new TrayIcon(idleImageIcon.getImage(), "BSKit - WorkMan", trayMenu);
			trayIcon.setImageAutoSize(true);
			try {
				tray.add(trayIcon);
			} catch (AWTException ex) {
			}
		}
		serviceStarted.set(true);
		return true;
	}

	@Override
	public final void setIdleImageIcon(ImageIcon imageIcon) {
		this.idleImageIcon = imageIcon;
	}

	@Override
	public void setActiveImageIcon(ImageIcon imageIcon) {
		this.activeImageIcon = imageIcon;
	}

	@Override
	public final void submitWorkItem(BSWorkItem workItem) {
		if (!workItem.isExecuted()) {
			/**
			 * Check signature
			 */
			if (signatures.contains(workItem.getWorkItemSignature())) {
				if (SystemTray.isSupported()) {
					trayIcon.displayMessage("Trabajo repetido", "Ya existe un trabajo '" + workItem.getWorkName() + "' con la misma firma operacional. Revise log", TrayIcon.MessageType.WARNING);
				}
				BSCoreFactory.getCore().logError("Work Item duplicated signature.", null);
				return;
			}
			/**
			 * Initialize the work item
			 */
			try {
				workItem.beforeExecution();
				if (SystemTray.isSupported()) {
					MenuItem menuItem = new MenuItem(workItem.getDisplayLabel());
					menuItem.addActionListener(workItem);
					workItem.setWorkMenu(menuItem);
					trayMenu.add(menuItem);
					trayIcon.setPopupMenu(trayMenu);
					trayIcon.displayMessage("Nuevo " + workItem.getWorkName() + " iniciado", workItem.getDisplayLabel(), TrayIcon.MessageType.INFO);
				}
				/**
				 * Submit the work item to execution queue
				 */
				signatures.add(workItem.getWorkItemSignature());
				serviceExecutor.submit(workItem);
			} catch (Exception ex) {
				/**
				 * Notify initialization errors
				 */
				if (SystemTray.isSupported()) {
					trayIcon.displayMessage("El " + workItem.getWorkName() + " no pudo inicializarse", "Consulte el log para mas información", TrayIcon.MessageType.ERROR);
				}
				BSCoreFactory.getCore().logError("Cannot initialize work item " + workItem.getWorkName(), ex);
			}
		} else {
			/**
			 * Notify the user and log
			 */
			if (SystemTray.isSupported()) {
				trayIcon.displayMessage("El " + workItem.getWorkName() + " ya ha sido iniciado", workItem.getDisplayLabel(), TrayIcon.MessageType.WARNING);
			}
			BSCoreFactory.getCore().logInfo("Work item " + workItem.getWorkName() + " has already been started");
		}
	}

	@Override
	public void finalizeAction() {
		if (SystemTray.isSupported()) {
			tray.remove(trayIcon);
		}
	}

	@Override
	public boolean isSuccesful() {
		return true;
	}

	@Override
	public String getName() {
		return "Background worker service";
	}

	@Override
	public String getStartupMessage() {
		return "Iniciando servicio de ejecución en fondo";
	}

	@Override
	public String getErrorMessages() {
		return "No es posible iniciar el servicio de ejecución en fondo";
	}

	@Override
	public boolean isCritical() {
		return true;
	}

	/**
	 * Consumidor de respuesta de trabajos
	 */
	private class WorkItemConsumer implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					Future<BSWorkItem> workItemFuture = serviceExecutor.take();
					final BSWorkItem workItem = workItemFuture.get();
					if (workItem != null) {
						try {
							if (workItem.isSuccessful()) {
								/**
								 * Call after execution to close work item lifecycle
								 */
								try {
									workItem.afterExecution();
									if (SystemTray.isSupported()) {
										trayIcon.displayMessage(workItem.getWorkName() + " finalizado. (" + workItem.getTimeLabel() + ")", workItem.getOperationMessage(), TrayIcon.MessageType.INFO);
									}
									BSCoreFactory.getCore().logInfo("Work item " + workItem.getWorkName() + " has finalized in " + workItem.getTimeLabel());
								} catch (Exception ex) {
									if (SystemTray.isSupported()) {
										trayIcon.displayMessage("Trabajo " + workItem.getWorkName() + " no completo ciclo de vida.",
												"El proceso aborto en su etapa de cierre de trabajo, verificar log",
												TrayIcon.MessageType.ERROR);
									}
									BSCoreFactory.getCore().logError("Work item aborted while processing after execution phase.", ex);
								}
							} else {
								if (SystemTray.isSupported()) {
									trayIcon.displayMessage("Trabajo " + workItem.getWorkName() + " finalizado con error",
											"El trabajo finalizo con mensaje '" + workItem.getOperationMessage() + "', verificar log",
											TrayIcon.MessageType.ERROR);
								}
								BSCoreFactory.getCore().logError("Work item " + workItem.getWorkName() + " finalized with error '" + workItem.getOperationMessage() + "'",
										workItem.getException());
							}
						} finally {
							signatures.remove(workItem.getWorkItemSignature());
							if (SystemTray.isSupported()) {
								trayMenu.remove(workItem.getWorkMenu());
								trayIcon.setPopupMenu(trayMenu);
							}
						}
					}
				} catch (InterruptedException | ExecutionException ex) {
					if (SystemTray.isSupported()) {
						trayIcon.displayMessage("Error de ejecucion", "El servicio de ejecución de trabajos ha finalizado inesperadamente. Verifique el log de la aplicación", TrayIcon.MessageType.ERROR);
					}
					BSCoreFactory.getCore().logError("The consumer thread in the BSExecutorService has died :-(, will reboot", ex);
				}
			}
		}
	}
}
