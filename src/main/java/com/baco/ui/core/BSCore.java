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

	void showErrorMessage(Component parent, String title, String message, String stacktrace);

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
