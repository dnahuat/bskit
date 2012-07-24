package com.baco.ui.core;

import com.baco.ui.util.BSMessageWrapper;
import com.baco.ui.services.BSLogger;
import java.awt.Component;

/**
 * CHANGELOG
 * ----------
 * 2011-04-14 (closeAllTabs,closeAllTabsExceptOne) : Ahora se devuelve boolean
 * (closeApplication) : Ahora devuelve boolean para indicar finalizacion
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Interface para control de BSKit
 * @author dnahuat
 */
public interface BSCore extends BSCoreComponent {

    String getResourcesURL();

    void setResourcesURL(String baseUrl);

    void setupMenu();

    void setSecondaryTitle(String secondaryTitle);

    String getSecondaryTitle();
    
    void setFullscreen(Boolean fullscreen);

    void loadAsDialog(BSCoreComponent component);

    void loadAsTab(BSCoreComponent component);

    void updateTab(BSCoreComponent component);

    void setBusyTab(BSCoreComponent component, boolean busy);

    void closeTab(BSCoreComponent component);

    void closeDialog(BSCoreComponent component);
    
    void closeAllDialogs();

    boolean closeAllTabsExceptOne(BSCoreComponent component);

    boolean closeAllTabs();

    void setSessionService(BSSessionManager sessionService);

    BSSessionManager getSessionService();

    void showErrorMessage(Component parent, String title, String message,
                          Throwable t);

    void showErrorMessage(Component parent, String title, String message);

    void showErrorMessage(Component parent, BSMessageWrapper message);

    void showWarningMessage(Component parent, String title, String message,
                            Throwable t);

    void showWarningMessage(Component parent, String title, String message);

    void showWarningMessage(Component parent, BSMessageWrapper message);

    void showInfoMessage(Component parent, String title, String message);

    void showInfoMessage(Component parent, BSMessageWrapper message);

    boolean showConfirmDialog(String title, String message,
                              String secondaryMessage);

    int isOpenedAsTab(String name);

    public BSCoreComponent getOpenedComponent(String name);

    boolean isOpenedAsTab(BSCoreComponent component);

    void updateLayer();

    void setLogger(BSLogger logger);

    BSLogger getLogger();

    void logError(String message, Throwable t);

    void logWarning(String message, Throwable t);

    void logDebug(String message);

    void logInfo(String message);

    void showModalLoading(String message);

    void showModalLoading();

    void hideModalLoading(final Component focusAfter);

    void hideModalLoading();

    long showLoading(String message);

    long showLoading();

    void hideLoading(long oid, final Component focusAfter);

    void hideLoading(long oid);

    boolean closeApplication();

    void addClosableListener(BSClosableListener closeListener);

    void removeClosableListener(BSClosableListener closeListener);
}
