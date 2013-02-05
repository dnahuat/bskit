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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.effect.BufferedImageOpEffect;
import org.jdesktop.jxlayer.plaf.ext.LockableUI;
import org.jdesktop.swingx.JXFrame;

import com.baco.ui.components.BSTabComponent;
import com.baco.ui.core.BSBusMgr;
import com.baco.ui.core.BSClosableListener;
import com.baco.ui.core.BSCore;
import com.baco.ui.core.BSCoreComponent;
import com.baco.ui.core.BSCoreFactory;
import com.baco.ui.core.BSElement;
import com.baco.ui.core.BSOID;
import com.baco.ui.core.BSSessionManager;
import com.baco.ui.menu.BSTreeNodeMenuItem;
import com.baco.ui.renderers.BSMenuRenderer;
import com.baco.ui.services.BSLogger;
import com.baco.ui.util.BSDefaultLogger;
import com.baco.ui.util.BSMessageWrapper;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentAdapter;
import java.awt.event.WindowAdapter;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.plaf.TabbedPaneUI;
import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.image.ColorTintFilter;

/**
 * CHANGELOG
 * ----------
 * 2011-04-15 (closeApplication) Al cerrar los paneles se verifica si la operacion
 * de cerrado es exitosa por cada panel cerrado
 * 2011-04-14 (closeAllTabs) : Se repara un bug que evitaba que se cerraran
 * (afterLoad) : Se verifica que todos los paneles se hayan cerrado antes de proceder
 * (close) : Se reimplementa segun 'BSCoreComponent'
 * (closeApplication) : Se reimplementan segun 'BSCore'
 * 2011-03-23 : Formato y estilo
 * 2011-03-22 (setupMenu) : El menu solo se muestra si la implementacion de sesion
 * permite menu.
 * - (setupEvents) Se elimina el evento para ocultar el menu
 * - Los elementos graficos se vuelven protected
 *
 *
 */
/**
 * Implementacion de pantalla principal y nucleo de BSKit
 *
 * @author dnahuat
 */
public class BSMainContainer extends JXFrame implements BSCore {

	private String resourcesUrl;
	private BSOID oid;
	private BSBusMgr bus;
	private String title;
	private BSDialog loading;
	private LockableUI lockUI;
	private String secondaryTitle;
	private JXLayer<JComponent> rootLayer;
	private BSTreeNodeMenuItem pressedNode;
	private BSSessionManager sessionService = null;
	private BSLogger logger = new BSDefaultLogger();
	private final Stack<BSCoreComponent> loadedDialogs =
			new Stack<BSCoreComponent>();
	private final AtomicBoolean isModalLoading = new AtomicBoolean(false);
	private final AtomicBoolean isFullscreen = new AtomicBoolean(false);
	private List<BSClosableListener> closableListeners =
			new ArrayList<BSClosableListener>();

	public BSMainContainer() {
		oid = new BSOID();
		bus = BSBusMgr.get();
	}

	@Override
	public boolean beforeLoad() throws Exception {
		title = "";
		initComponents();
		splContainer.setInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT,
				new InputMap());
		splContainer.setInputMap(JComponent.WHEN_FOCUSED, new InputMap());
		treeMenu.setRootVisible(false);
		logInfo("Preparando framework BSKit...");
		if (isVisible()) {
			logWarning("BSKit no iniciara", null);
		}
		loading = new BSDlgLoading("Cargando...");
		lockUI = new LockableUI();
		lockUI.setLockedEffects(new BufferedImageOpEffect(new ColorTintFilter(
				Color.BLACK, 0.7f)));
		lockUI.setLockedCursor(Cursor.getDefaultCursor());
		rootLayer = new JXLayer<JComponent>(pnlRoot, lockUI);
		pnlTopLayer.setDoubleBuffered(true);
		rootLayer.setDoubleBuffered(true);
		glassLayer.setDoubleBuffered(true);
		remove(pnlRoot);
		add(rootLayer, BorderLayout.CENTER);
		pnlTopLayer.setInheritAlpha(false);
		pnlTopLayer.setOpaque(false);
		pnlTopLayer.setAlpha(0.0f);
		rootLayer.setGlassPane(pnlTopLayer);
		setLocationRelativeTo(null);
		lockUI.updateUI(rootLayer);
		setupEvents();
		logInfo("BSKit listo para iniciar...");
		return true;
	}

	@Override
	public boolean onLoad() throws Exception {
		return true;
	}

	@Override
	public void afterLoad() {
		ComponentAdapter ca = new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent ce) {
				if (sessionService != null) {
					logInfo("Iniciando administrador de sesion");
					btnCloseSession.setVisible(true);
					btnCloseSession.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							getSessionService().closeSession();
							if (closeAllTabs()) {
								sessionService.run();
							}
						}
					});
					sessionService.run();
					logInfo("Administrador de sesion listo");
				} else {
					btnCloseSession.setVisible(false);
				}
				BSMainContainer.this.removeComponentListener(this);
			}
		};
		BSMainContainer.this.addComponentListener(ca);
		if (!isFullscreen.get()) {
			super.setVisible(true);
		} else {
			GraphicsDevice device =
					GraphicsEnvironment.getLocalGraphicsEnvironment().
					getDefaultScreenDevice();
			if (device.isFullScreenSupported()) {
				super.dispose();
				super.setUndecorated(true);
				super.setResizable(false);
				device.setFullScreenWindow(this);
				super.setVisible(true);
			} else {
				super.setVisible(true);
			}
		}
		logInfo("Nucleo BSKit iniciado exitosamente");
	}

	@Override
	public final void setVisible(boolean visible) {
		logWarning("No es posible cambiar la visibilidad del core.", null);
	}

	@Override
	public final String getResourcesURL() {
		if (resourcesUrl == null) {
			resourcesUrl = "/";
		}
		return resourcesUrl;
	}

	@Override
	public final void setResourcesURL(String baseUrl) {
		this.resourcesUrl = baseUrl;
	}

	private void setupEvents() {
		treeMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					executeTreeCommand();
				}
			}
		});
		treeMenu.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
					executeTreeCommand();
				}
			}
		});
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				closeApplication();
			}
		});
	}

	private void executeTreeCommand() {
		TreePath selPath = treeMenu.getSelectionPath();

		if (selPath != null) {
			pressedNode = (BSTreeNodeMenuItem) selPath.getLastPathComponent();
			if (pressedNode.isLeaf()) {
				try {
					BSElement option = (BSElement) Class.forName(
							pressedNode.getClassName()).newInstance();
					option.setTitle(pressedNode.getLabel());
					option.setIcon(pressedNode.getIconName());
					option.run();

				} catch (ClassNotFoundException ex) {
					logError("Modulo no encontrado - " + pressedNode.getClassName(),
							ex);
					BSCoreFactory.getCore().showErrorMessage(treeMenu,
							"Modulo no encontrado",
							"El modulo que desea ejecutar no existe",
							ex);
				} catch (ClassCastException ex) {
					logError("Modulo invalido - " + pressedNode.getClassName(), ex);
					BSCoreFactory.getCore().showErrorMessage(treeMenu,
							"Modulo invalido",
							"El modulo que desea ejecutar no es valido",
							ex);
				} catch (InstantiationException ex) {
					logError("Modulo corrupto - " + pressedNode.getClassName(), ex);
					BSCoreFactory.getCore().showErrorMessage(treeMenu,
							"Modulo corrupto",
							"El modulo que desea ejecutar no puede iniciarse",
							ex);
				} catch (IllegalAccessException ex) {
					logError("Modulo inaccesible - " + pressedNode.getClassName(), ex);
					BSCoreFactory.getCore().showErrorMessage(treeMenu,
							"Modulo invalido",
							"El modulo que desea ejecutar no es valido",
							ex);
				}
			}
		}
	}

	@Override
	public void setupMenu() {
		if (sessionService != null && sessionService.getSessionWrapper().isActive() && sessionService.
				hasMenu()) {
			treeMenu.setModel(new DefaultTreeModel(sessionService.fetchMenu()));
			treeMenu.setCellRenderer(new BSMenuRenderer());
			for (int i = 0; i < treeMenu.getRowCount(); i++) {
				treeMenu.expandRow(i);
			}
		}
	}

	@Override
	public void loadAsTab(final BSCoreComponent component) {
		if (component.getAsComponent() == null) {
			logWarning(
					"El componente(" + component.getUniqueName() + ") no es grafico.",
					null);
			return;
		}
		TabLoader loader = new TabLoader(component);
		loader.execute();
		logDebug(
				"Preparando para mostrar componente(" + component.getUniqueName() + ")...");
		logDebug("Componente abierto exitosamente...");
	}

	@Override
	public void updateTab(final BSCoreComponent component) {
		if (component.getAsComponent() == null) {
			logWarning("El componente(" + component.getTitle() + ") no es grafico.",
					null);
			return;
		}
		if (bus.checkOIDIsRegisteredInBus("panels", component.getOID().longValue())) {
			logDebug(
					"Preparando para actualizar componente(" + component.getTitle() + ")...");
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					int index = tabPnlContainer.indexOfComponent(component.
							getAsComponent());
					if (index != -1) {
						((BSTabComponent) tabPnlContainer.getTabComponentAt(index)).
								setTitle(component.getTitle());
						((BSTabComponent) tabPnlContainer.getTabComponentAt(index)).
								setIcon(component.getIcon());
						tabPnlContainer.repaint();
					}
				}
			});
		}
	}

	@Override
	public void setBusyTab(final BSCoreComponent component, final boolean busy) {
		if (component.getAsComponent() == null) {
			logWarning("El componente(" + component.getTitle() + ") no es grafico.",
					null);
			return;
		}
		if (bus.checkOIDIsRegisteredInBus("panels", component.getOID().longValue())) {
			logDebug(
					"Preparando para actualizar componente(" + component.getTitle() + ")...");
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					int index = tabPnlContainer.indexOfComponent(component.
							getAsComponent());
					if (index != -1) {
						((BSTabComponent) tabPnlContainer.getTabComponentAt(index)).
								setBusy(busy);
					}
				}
			});
		}
	}

	@Override
	public int isOpenedAsTab(String name) {
		int counter = tabPnlContainer.getTabCount();
		BSCoreComponent component;
		for (int i = 0; i < counter; ++i) {
			try {
				component = (BSCoreComponent) tabPnlContainer.getComponentAt(i);
				if (component.getUniqueName().equals(name)) {
					return i;
				}
			} catch (ClassCastException ex) {
				BSCoreFactory.getCore().logError(
						"El componente no es parte del framework", ex);
				return -1;
			}
		}
		return -1;
	}

	@Override
	public BSCoreComponent getOpenedComponent(String name) {
		int counter = tabPnlContainer.getTabCount();
		BSCoreComponent component = null;
		for (int i = 0; i < counter; ++i) {
			try {
				component = (BSCoreComponent) tabPnlContainer.getComponentAt(i);
				if (component.getUniqueName().equals(name)) {
					return component;
				}
			} catch (ClassCastException ex) {
				BSCoreFactory.getCore().logError(
						"El componente no se encuentra abierto", ex);
				return null;
			}
		}
		return null;
	}

	@Override
	public boolean isOpenedAsTab(BSCoreComponent component) {
		if (component.getAsComponent() == null) {
			return false;
		}
		return (bus.checkOIDIsRegisteredInBus("panels", component.getOID().
				longValue()));
	}

	@Override
	public void loadAsDialog(final BSCoreComponent component) {
		if (component.getAsComponent() == null) {
			logWarning(
					"El componente (" + component.getTitle() + ") no es grafico.",
					null);
			return;
		}
		DialogLoader loader = new DialogLoader(component);
		loader.execute();
		logDebug(
				"Preparando para mostrar componente(" + component.getUniqueName() + ")...");
		logDebug("Componente abierto exitosamente...");
	}

	@Override
	public boolean close() {
		return closeApplication();
	}

	@Override
	public Component getAsComponent() {
		return (JXFrame) this;
	}

	@Override
	public BSOID getOID() {
		if (oid == null) {
			oid = new BSOID();
		}
		return oid;
	}

	@Override
	public String getIcon() {
		return "";
	}

	@Override
	public void setIcon(String icon) {
	}

	@Override
	public String getUniqueName() {
		return "BSMainContainer";
	}

	@Override
	public void run() {
	}

	@Override
	public void setLoadDelay(long delay) {
	}

	@Override
	public long getLoadDelay() {
		return 0;
	}

	@Override
	public void setFullscreen(Boolean fullscreen) {
		isFullscreen.set(fullscreen);
	}

	private class DialogLoader extends SwingWorker<BSCoreComponent, Void> {

		private long loadOID;
		private final BSCoreComponent dialogForLoad;
		private boolean isRegistered;
		private boolean proceed = true;

		public DialogLoader(BSCoreComponent dialogForLoad) {
			this.dialogForLoad = dialogForLoad;
			if (isRegistered = bus.registerComponentInBus("dialogs", dialogForLoad)) {
				try {
					proceed = dialogForLoad.beforeLoad();
				} catch (Exception ex) {
					proceed = false;
					BSCoreFactory.getCore().logError("Error al cargar : " + dialogForLoad.
							getUniqueName(), ex);
				}
			} else {
				proceed = false;
			}
			loadOID = showLoading();
		}

		@Override
		protected BSCoreComponent doInBackground() throws Exception {
			if (proceed && isRegistered) {
				Thread.sleep(dialogForLoad.getLoadDelay());
				proceed = dialogForLoad.onLoad();
				return dialogForLoad;
			}
			return null;
		}

		@Override
		protected void done() {
			BSCoreComponent dialog;
			Exception cex = null;
			try {
				if (isRegistered && proceed) {
					dialog = get();
					hideLoading(loadOID);
					/*
					 * Agregar evento para actualizacion de layer parent
					 */
					if (dialog instanceof BSDialog) {
						((BSDialog) dialog).addDialogListener(new BSDialogAdapter() {
							@Override
							public void updateParentLayer(BSDialogEvent ev) {
								updateLayer();
							}
						});
					}
					if (!isModalLoading.get()) {
						Component component = dialog.getAsComponent();
						pnlTopLayer.removeAll();
						component.setSize(component.getPreferredSize());
						pnlTopLayer.add(component);
						pnlTopLayer.validate();
						lockHydra();
						lockUI.updateUI(rootLayer);
					}
					dialog.afterLoad();
					loadedDialogs.push(dialogForLoad);
				}
			} catch (InterruptedException ex) {
				cex = ex;
			} catch (ExecutionException ex) {
				cex = ex;
			} catch (Exception ex) {
				cex = ex;
			} finally {
				hideLoading(loadOID);
				if (cex != null) {
					BSCoreFactory.getCore().showErrorMessage(treeMenu,
							"Carga fallida",
							"No es posible cargar el dialogo",
							cex);
					if (isRegistered) {
						bus.deregisterComponentFromBus("dialogs", dialogForLoad);
						isRegistered = false;
					}
				}
				if (!proceed && isRegistered) {
					bus.deregisterComponentFromBus("dialogs", dialogForLoad);
					isRegistered = false;
				}
			}
		}
	}

	@Override
	public void closeAllDialogs() {
		if (!loadedDialogs.empty()) {
			for (BSCoreComponent component : loadedDialogs) {
				bus.deregisterComponentFromBus("dialogs", component);
			}
			loadedDialogs.removeAllElements();
			Runnable hideCode = new Runnable() {
				@Override
				public void run() {
					pnlTopLayer.removeAll();
					unlockHydra();
					lockUI.updateUI(rootLayer);
				}
			};
			if (SwingUtilities.isEventDispatchThread()) {
				hideCode.run();
			} else {
				SwingUtilities.invokeLater(hideCode);
			}
		}
	}

	@Override
	public void closeDialog(BSCoreComponent component) {
		/*
		 * Verificar componente
		 */
		if (component.getAsComponent() == null) {
			logWarning(
					"El componente (" + component.getTitle() + ") no es grafico.",
					null);
			return;
		}

		if (!loadedDialogs.empty()) {
			if (!isModalLoading.get() && (loadedDialogs.peek().equals(component))) {
				loadedDialogs.pop();
				bus.deregisterComponentFromBus("dialogs", component);
				Runnable hideCode = new Runnable() {
					@Override
					public void run() {
						if (loadedDialogs.isEmpty()) {
							pnlTopLayer.removeAll();
							unlockHydra();
							lockUI.updateUI(rootLayer);
						} else {
							if (!isModalLoading.get()) {
								BSCoreComponent component = loadedDialogs.peek();
								Component comp = component.getAsComponent();
								pnlTopLayer.removeAll();
								comp.setSize(comp.getPreferredSize());
								pnlTopLayer.add(comp);
								pnlTopLayer.validate();
								lockHydra();
								lockUI.updateUI(rootLayer);
							}
						}
					}
				};
				if (SwingUtilities.isEventDispatchThread()) {
					hideCode.run();
				} else {
					SwingUtilities.invokeLater(hideCode);
				}
			} else {
				bus.deregisterComponentFromBus("dialogs", component);
				loadedDialogs.remove(component);
			}
		}
	}

	@Override
	public void closeTab(final BSCoreComponent component) {
		logDebug("Cerrando pestaña(" + component.getTitle() + ")");
		Runnable dereg = new Runnable() {
			@Override
			public void run() {
				bus.deregisterComponentFromBus("panels", component);
				tabPnlContainer.remove(component.getAsComponent());
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			dereg.run();
		} else {
			SwingUtilities.invokeLater(dereg);
		}
	}

	@Override
	public boolean closeAllTabsExceptOne(final BSCoreComponent component) {
		boolean retVal = true;
		for (int i = tabPnlContainer.getTabCount() - 1; (i >= 0 && retVal); i--) {
			if (!((BSPanel) tabPnlContainer.getComponentAt(i)).equals(component)) {
				retVal =
						((BSCoreComponent) tabPnlContainer.getComponentAt(i)).close();
				if (!retVal && (tabPnlContainer.getTabCount() > i)) {
					tabPnlContainer.setSelectedIndex(i);
				}
			}
		}
		return retVal;
	}

	@Override
	public boolean closeAllTabs() {
		boolean retVal = true;
		for (int i = tabPnlContainer.getTabCount() - 1; (i >= 0) && retVal; i--) {
			retVal = ((BSCoreComponent) tabPnlContainer.getComponentAt(i)).close();
			if (!retVal && (tabPnlContainer.getTabCount() > i)) {
				tabPnlContainer.setSelectedIndex(i);
			}
		}
		return retVal;
	}

	@Override
	public void setSessionService(BSSessionManager sessionService) {
		this.sessionService = sessionService;
	}

	@Override
	public void showErrorMessage(final Component parent, final String title,
			final String message, final String stacktrace) {
		Runnable runDialog = new Runnable() {
			@Override
			public void run() {
				BSDlgError errorDialog = new BSDlgError(parent, title, message, stacktrace);
				errorDialog.run();
				errorDialog.requestFocusInWindow();
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			runDialog.run();
		} else {
			SwingUtilities.invokeLater(runDialog);
		}	
	}
	
	@Override
	public void showErrorMessage(final Component parent, final String title,
			final String message,
			final Throwable t) {
		Runnable runDialog = new Runnable() {
			@Override
			public void run() {
				BSDlgError errorDialog = new BSDlgError(parent, title, message, t);
				errorDialog.run();
				errorDialog.requestFocusInWindow();
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			runDialog.run();

		} else {
			SwingUtilities.invokeLater(runDialog);
		}
	}

	@Override
	public void showErrorMessage(final Component parent, final String title,
			final String message) {
		Runnable runDialog = new Runnable() {
			@Override
			public void run() {
				BSDlgError errorDialog = new BSDlgError(parent, title, message);
				errorDialog.run();
				errorDialog.requestFocusInWindow();
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			runDialog.run();
		} else {
			SwingUtilities.invokeLater(runDialog);
		}
	}

	@Override
	public void showErrorMessage(final Component parent,
			final BSMessageWrapper message) {
		Runnable runDialog = new Runnable() {
			@Override
			public void run() {
				BSDlgError errorDialog = new BSDlgError(parent, message.getTitle(),
						message.getBody(), message.
						getErrorMessage());
				errorDialog.run();
				errorDialog.requestFocusInWindow();
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			runDialog.run();
		} else {
			SwingUtilities.invokeLater(runDialog);
		}
	}

	@Override
	public void showWarningMessage(final Component parent, final String title,
			final String message,
			final Throwable t) {
		Runnable runDialog = new Runnable() {
			@Override
			public void run() {
				BSDlgWarn warnDialog = new BSDlgWarn(parent, title, message, t);
				warnDialog.run();
				warnDialog.requestFocusInWindow();
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			runDialog.run();
		} else {
			SwingUtilities.invokeLater(runDialog);
		}
	}

	@Override
	public void showWarningMessage(final Component parent, final String title,
			final String message) {
		Runnable runDialog = new Runnable() {
			@Override
			public void run() {
				BSDlgWarn warnDialog = new BSDlgWarn(parent, title, message);
				warnDialog.run();
				warnDialog.requestFocusInWindow();
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			runDialog.run();
		} else {
			SwingUtilities.invokeLater(runDialog);
		}
	}

	@Override
	public void showWarningMessage(final Component parent,
			final BSMessageWrapper message) {
		Runnable runDialog = new Runnable() {
			@Override
			public void run() {
				BSDlgWarn warnDialog = new BSDlgWarn(parent, message.getTitle(),
						message.getBody(), message.
						getErrorMessage());
				warnDialog.run();
				warnDialog.requestFocusInWindow();
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			runDialog.run();
		} else {
			SwingUtilities.invokeLater(runDialog);
		}
	}

	@Override
	public void showInfoMessage(final Component parent, final String title,
			final String message) {
		Runnable runDialog = new Runnable() {
			@Override
			public void run() {
				new BSDlgInfo(parent, title, message).run();
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			runDialog.run();
		} else {
			SwingUtilities.invokeLater(runDialog);
		}
	}

	@Override
	public void showInfoMessage(final Component parent,
			final BSMessageWrapper message) {
		Runnable runDialog = new Runnable() {
			@Override
			public void run() {
				new BSDlgInfo(parent, message.getTitle(), message.getBody()).run();
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			runDialog.run();
		} else {
			SwingUtilities.invokeLater(runDialog);
		}
	}

	@Override
	public boolean showConfirmDialog(String title, String message,
			String secondaryMessage) {
		int selection = JOptionPane.showConfirmDialog(this, message, title,
				JOptionPane.YES_NO_OPTION);
		return (selection == JOptionPane.YES_OPTION);
	}

	@Override
	public void showModalLoading(String message) {
		if (isModalLoading.get()) {
			return;
		}
		isModalLoading.set(true);
		loading.setTitle(message);
		Runnable showLoadingCode = new Runnable() {
			@Override
			public void run() {
				pnlTopLayer.removeAll();
				pnlTopLayer.add(loading.getAsComponent());
				pnlTopLayer.validate();
				lockHydra();
				lockUI.updateUI(rootLayer);
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			showLoadingCode.run();
		} else {
			SwingUtilities.invokeLater(showLoadingCode);
		}
	}

	@Override
	public void showModalLoading() {
		showModalLoading("Cargando...");
	}

	@Override
	public void hideModalLoading(final Component focusAfter) {
		if (!isModalLoading.get()) {
			return;
		}
		isModalLoading.set(false);
		if (!lockUI.isLocked()) {
			return;
		}
		Runnable hideCode = new Runnable() {
			@Override
			public void run() {
				pnlTopLayer.removeAll();
				if (!loadedDialogs.empty()) {
					Component comp = loadedDialogs.peek().getAsComponent();
					comp.setSize(comp.getPreferredSize());
					pnlTopLayer.add(comp);
					pnlTopLayer.validate();
				} else {
					unlockHydra();
				}
				lockUI.updateUI(rootLayer);
				if (focusAfter != null) {
					focusAfter.requestFocusInWindow();
				}
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			hideCode.run();
		} else {
			SwingUtilities.invokeLater(hideCode);
		}
	}

	@Override
	public void hideModalLoading() {
		if (!isModalLoading.get()) {
			return;
		}
		isModalLoading.set(false);
		if (!lockUI.isLocked()) {
			return;
		}
		Runnable hideCode = new Runnable() {
			@Override
			public void run() {
				pnlTopLayer.removeAll();
				if (!loadedDialogs.empty()) {
					Component comp = loadedDialogs.peek().getAsComponent();
					comp.setSize(comp.getPreferredSize());
					pnlTopLayer.add(comp);
					pnlTopLayer.validate();
				} else {
					unlockHydra();
				}
				lockUI.updateUI(rootLayer);
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			hideCode.run();
		} else {
			SwingUtilities.invokeLater(hideCode);
		}
	}

	@Override
	public long showLoading() {
		return showLoading("Cargando...");
	}

	@Override
	public long showLoading(String message) {
		BSDialog loadDialog = new BSDlgLoading(message);
		final Component comp = loadDialog.getAsComponent();
		Runnable showCode = new Runnable() {
			@Override
			public void run() {
				pnlTopLayer.removeAll();
				comp.setSize(comp.getPreferredSize());
				pnlTopLayer.add(comp);
				pnlTopLayer.validate();
				lockHydra();
				lockUI.updateUI(rootLayer);
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			showCode.run();
		} else {
			SwingUtilities.invokeLater(showCode);
		}
		loadedDialogs.push(loadDialog);
		bus.registerComponentInBus("dialogs", loadDialog);
		return loadDialog.getOID().longValue();
	}

	@Override
	public void hideLoading(long oid, final Component focusAfter) {
		BSCoreComponent component = bus.getComponentInBusByOID("dialogs", oid);
		if (component != null) {
			Component comp = component.getAsComponent();
			if (comp != null) {
				if (!loadedDialogs.empty()) {
					if (!isModalLoading.get() && (loadedDialogs.peek().equals(
							component))) {
						loadedDialogs.pop();
						bus.deregisterComponentFromBus("dialogs", component);
						Runnable hideCode = new Runnable() {
							@Override
							public void run() {
								if (loadedDialogs.isEmpty()) {
									pnlTopLayer.removeAll();
									unlockHydra();
									lockUI.updateUI(rootLayer);
									if (focusAfter != null) {
										focusAfter.requestFocusInWindow();
									}
								} else {
									if (!isModalLoading.get()) {
										BSCoreComponent component = loadedDialogs.peek();
										Component comp = component.getAsComponent();
										pnlTopLayer.removeAll();
										comp.setSize(comp.getPreferredSize());
										pnlTopLayer.add(comp);
										pnlTopLayer.validate();
										lockHydra();
										lockUI.updateUI(rootLayer);
									}
								}
							}
						};
						if (SwingUtilities.isEventDispatchThread()) {
							hideCode.run();
						} else {
							SwingUtilities.invokeLater(hideCode);
						}
					} else {
						bus.deregisterComponentFromBus("dialogs", component);
						loadedDialogs.remove(component);
					}
				}
			}
		}
	}

	@Override
	public void hideLoading(long oid) {
		BSCoreComponent component = bus.getComponentInBusByOID("dialogs", oid);
		if (component != null) {
			Component comp = component.getAsComponent();
			if (comp != null) {
				if (!loadedDialogs.empty()) {
					if (!isModalLoading.get() && (loadedDialogs.peek().equals(
							component))) {
						loadedDialogs.pop();
						bus.deregisterComponentFromBus("dialogs", component);
						Runnable hideCode = new Runnable() {
							@Override
							public void run() {
								if (loadedDialogs.isEmpty()) {
									pnlTopLayer.removeAll();
									unlockHydra();
									lockUI.updateUI(rootLayer);
								} else {
									if (!isModalLoading.get()) {
										BSCoreComponent component = loadedDialogs.peek();
										Component comp = component.getAsComponent();
										pnlTopLayer.removeAll();
										comp.setSize(comp.getPreferredSize());
										pnlTopLayer.add(comp);
										pnlTopLayer.validate();
										lockHydra();
										lockUI.updateUI(rootLayer);
									}
								}
							}
						};
						if (SwingUtilities.isEventDispatchThread()) {
							hideCode.run();
						} else {
							SwingUtilities.invokeLater(hideCode);
						}
					} else {
						bus.deregisterComponentFromBus("dialogs", component);
						loadedDialogs.remove(component);
					}
				}
			}
		}
	}

	private void lockHydra() {
		if (lockUI.isLocked()) {
			return;
		}
		if (SwingUtilities.isEventDispatchThread()) {
			lockUI.setLocked(true);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					lockUI.setLocked(true);
				}
			});
		}
	}

	private void unlockHydra() {
		if (!lockUI.isLocked()) {
			return;
		}
		if (SwingUtilities.isEventDispatchThread()) {
			pnlTopLayer.removeAll();
			lockUI.setLocked(false);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					pnlTopLayer.removeAll();
					lockUI.setLocked(false);
				}
			});
		}
	}

	@Override
	public BSSessionManager getSessionService() {
		return sessionService;
	}

	@Override
	public void addClosableListener(BSClosableListener closableListener) {
		if (!closableListeners.contains(closableListener)) {
			closableListeners.add(closableListener);
		}
	}

	@Override
	public void removeClosableListener(BSClosableListener closableListener) {
		closableListeners.remove(closableListener);
	}

	@Override
	public boolean closeApplication() {
		boolean success = true;
		for (int i = 0; i < closableListeners.size(); ++i) {
			success = closableListeners.get(i).tryClose();
			if (!success) {
				break;
			}
		}
		if (success) {
			for (int i = tabPnlContainer.getTabCount() - 1; (i >= 0 && success);
					i--) {
				success = ((BSCoreComponent) tabPnlContainer.getComponentAt(i)).
						close();
			}
			if (success) {
				BSCoreFactory.finalizeCore();
				BSCoreFactory.getCore().logInfo("Terminando nucleo BSKit. Adios");
				this.dispose();
				System.exit(0);
			}
		}
		return success;
	}

	private class TabLoader extends SwingWorker<BSCoreComponent, Void> {

		private final BSCoreComponent itemForLoad;
		private long loadingOID;
		private boolean isRegistered;
		private boolean proceed = false;

		public TabLoader(BSCoreComponent itemForLoad) {
			this.itemForLoad = itemForLoad;
			if (isRegistered = bus.registerComponentInBus("panels", itemForLoad)) {
				try {
					proceed = itemForLoad.beforeLoad();
				} catch (Exception ex) {
					proceed = false;
					BSCoreFactory.getCore().logError("Error al cargar : " + itemForLoad.
							getUniqueName(), ex);
				}
			} else {
				proceed = false;
			}
			loadingOID = showLoading();
		}

		@Override
		protected BSCoreComponent doInBackground() throws Exception {
			if (proceed && isRegistered) {
				Thread.sleep(itemForLoad.getLoadDelay());
				proceed = itemForLoad.onLoad();
				return itemForLoad;
			}
			return null;
		}

		@Override
		protected void done() {
			BSCoreComponent item;
			Exception cex = null;
			try {
				if (isRegistered && proceed) {
					item = get();
					/*
					 * Cargar el TabComponent
					 */
					BSTabComponent tabComponent = new BSTabComponent(
							tabPnlContainer, item.getIcon(), item.getTitle());
					if (item instanceof ActionListener) {
						tabComponent.addActionListener((ActionListener) item);
					}
					tabPnlContainer.addTab(item.getTitle(), item.getAsComponent());

					tabPnlContainer.setTabComponentAt(
							tabPnlContainer.getTabCount() - 1,
							tabComponent);
					tabPnlContainer.setSelectedIndex(
							tabPnlContainer.getTabCount() - 1);
					hideLoading(loadingOID);
					item.afterLoad();

					/*
					 * Si el componente es un panel agregar acciones ENTER
					 */
					if (item.getAsComponent() instanceof JPanel) {
						addEnterActionToJPanel((JPanel) item.getAsComponent());
					}

				} else {
					if (!isRegistered) {
						List<Long> uniqueComponents = BSBusMgr.get().
								getComponentsByUniqueName(itemForLoad.getUniqueName());
						if (!uniqueComponents.isEmpty()) {
							tabPnlContainer.setSelectedComponent(BSBusMgr.get().
									getComponentInBusByOID("panels", uniqueComponents.
									get(
									0)).getAsComponent());
						}
					}
				}
			} catch (InterruptedException ex) {
				cex = ex;
			} catch (ExecutionException ex) {
				cex = ex;
			} catch (Exception ex) {
				cex = ex;
			} finally {
				hideLoading(loadingOID);
				if (cex != null) {
					BSCoreFactory.getCore().showErrorMessage(treeMenu,
							"Carga fallida",
							"No es posible cargar el panel",
							cex);
					if (isRegistered) {
						bus.deregisterComponentFromBus("panels", itemForLoad);
						isRegistered = false;
					}
				}
				if (!proceed && isRegistered) {
					bus.deregisterComponentFromBus("panels", itemForLoad);
					isRegistered = false;
				}
			}
		}
	}

	private void addEnterActionToButton(final JButton button) {
		TabbedPaneUI tabPaneUi = (TabbedPaneUI) UIManager.getUI(tabPnlContainer);

		button.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(
				"ENTER"), "doEnterClick");
		button.getActionMap().put("doEnterClick", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button.doClick();
			}
		});
	}

	private void addEnterActionToLink(final JXHyperlink link) {
		link.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(
				"ENTER"), "doEnterClick");
		link.getActionMap().put("doEnterClick", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				link.doClick();
			}
		});
	}

	private void addEnterActionToJPanel(final JPanel panel) {
		Component[] components = panel.getComponents();
		for (Component component : components) {
			if (component instanceof JPanel) {
				addEnterActionToJPanel((JPanel) component);
			} else if (component instanceof JXHyperlink) {
				addEnterActionToLink((JXHyperlink) component);
			} else if (component instanceof JButton) {
				addEnterActionToButton((JButton) component);
			}
		}
	}

	@Override
	public void updateLayer() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				lockUI.updateUI(rootLayer);
			}
		});
	}

	/*
	 * LOGGER METHODS
	 */
	@Override
	public void setLogger(BSLogger logger) {
		this.logger = logger;
	}

	@Override
	public BSLogger getLogger() {
		return logger;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
		super.setTitle(title + ((secondaryTitle != null)
				? (" : " + secondaryTitle) : ""));
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setSecondaryTitle(String secondaryTitle) {
		this.secondaryTitle = secondaryTitle;
		setTitle(this.title);
	}

	@Override
	public String getSecondaryTitle() {
		return secondaryTitle;
	}

	@Override
	public void logError(String message, Throwable t) {
		if (sessionService != null && sessionService.getSessionWrapper() != null) {
			logger.error(sessionService.getSessionWrapper().getSessionId(), message,
					t);
		} else {
			logger.error("No iniciada", message, t);
		}
	}

	@Override
	public void logWarning(String message, Throwable t) {
		if (sessionService != null && sessionService.getSessionWrapper() != null) {
			logger.warning(sessionService.getSessionWrapper().getSessionId(),
					message, t);
		} else {
			logger.warning("No iniciada", message, t);
		}
	}

	@Override
	public void logDebug(String message) {
		if (sessionService != null && sessionService.getSessionWrapper() != null) {
			logger.debug(sessionService.getSessionWrapper().getSessionId(), message);
		} else {
			logger.debug("No iniciada", message);
		}
	}

	@Override
	public void logInfo(String message) {
		if (sessionService != null && sessionService.getSessionWrapper() != null) {
			logger.info(sessionService.getSessionWrapper().getSessionId(), message);
		} else {
			logger.info("No iniciada", message);
		}
	}

	@Override
	public boolean isSingleInstance() {
		return true;
	}

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        glassLayer = new org.jdesktop.swingx.JXGlassBox();
        pnlTopLayer = new org.jdesktop.swingx.JXPanel();
        pnlRoot = new javax.swing.JPanel();
        splContainer = new javax.swing.JSplitPane();
        tabPnlContainer = new javax.swing.JTabbedPane();
        pnlMenu = new org.jdesktop.swingx.JXPanel();
        btnCloseSession = new org.jdesktop.swingx.JXHyperlink();
        jScrollPane1 = new javax.swing.JScrollPane();
        treeMenu = new javax.swing.JTree();

        glassLayer.setLayout(new java.awt.BorderLayout());

        pnlTopLayer.setOpaque(false);
        pnlTopLayer.setLayout(new java.awt.GridBagLayout());
        glassLayer.add(pnlTopLayer, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setStartPosition(org.jdesktop.swingx.JXFrame.StartPosition.CenterInScreen);

        pnlRoot.setLayout(new java.awt.BorderLayout());

        splContainer.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        splContainer.setDividerSize(3);

        tabPnlContainer.setMinimumSize(new java.awt.Dimension(20, 500));
        tabPnlContainer.setPreferredSize(new java.awt.Dimension(600, 600));
        splContainer.setRightComponent(tabPnlContainer);

        pnlMenu.setBackground(new java.awt.Color(51, 51, 51));
        pnlMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnlMenu.setMinimumSize(new java.awt.Dimension(150, 0));
        pnlMenu.setPreferredSize(new java.awt.Dimension(200, 399));

        btnCloseSession.setClickedColor(new java.awt.Color(204, 204, 204));
        btnCloseSession.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/baco/ui/icons/icon_logout.png"))); // NOI18N
        btnCloseSession.setText("<html><p>Cerrar sesi&oacute;n</p></html>");
        btnCloseSession.setUnclickedColor(new java.awt.Color(204, 204, 204));
        btnCloseSession.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnCloseSession.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode(".");
        treeMenu.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(treeMenu);

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(btnCloseSession, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCloseSession, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        splContainer.setLeftComponent(pnlMenu);

        pnlRoot.add(splContainer, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlRoot, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected org.jdesktop.swingx.JXHyperlink btnCloseSession;
    private org.jdesktop.swingx.JXGlassBox glassLayer;
    private javax.swing.JScrollPane jScrollPane1;
    protected org.jdesktop.swingx.JXPanel pnlMenu;
    private javax.swing.JPanel pnlRoot;
    private org.jdesktop.swingx.JXPanel pnlTopLayer;
    protected javax.swing.JSplitPane splContainer;
    protected javax.swing.JTabbedPane tabPnlContainer;
    protected javax.swing.JTree treeMenu;
    // End of variables declaration//GEN-END:variables
}
