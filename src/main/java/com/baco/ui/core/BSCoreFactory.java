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

import com.baco.ui.services.BSLogger;
import com.baco.ui.services.BSSession;
import com.baco.ui.services.BSStartupAction;
import com.baco.ui.services.BSStartupLevel;
import com.baco.ui.util.BSDefaultLogger;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 * CHANGELOG 
 * ---------- 
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Bootloader del nucleo
 * @author dnahuat
 */
public final class BSCoreFactory {

    private static BSCore instance = null;
    private static String appTitle = null;
    private static BSSplash splash;
    private static AtomicBoolean hasBeenStarted = new AtomicBoolean(false);
    private static AtomicBoolean isFullScreen = new AtomicBoolean(false);
    private static List<BSStartupAction> preLoadActions = new ArrayList();
    private static List<BSStartupAction> loadActions = new ArrayList();
    private static List<BSStartupAction> postLoadActions = new ArrayList();

    private static boolean initParameters() {
        if (BSSession.get("SettingsFile") == null) {
            return false;
        }
        if (BSSession.get("CoreImplementation") == null) {
            BSSession.put("CoreImplementation",
                    "com.baco.ui.containers.BSMainContainer");
        }
        if (BSSession.get("SplashImplementation") == null) {
            BSSession.put("SplashImplementation",
                    "com.baco.ui.util.BSDefaultSplash");
        }
        return true;
    }

    public static void setResourcesUrl(String resourcesUrl) {
        BSSession.put("ResourcesUrl", resourcesUrl);
        if (instance != null) {
            instance.setResourcesURL(resourcesUrl);
        }
    }

    public static String getResourcesUrl() {
        if (BSSession.get("ResourcesUrl") != null) {
            return (String) BSSession.get("ResourcesUrl");
        } else {
            return null;
        }
    }

    public static void setSettingsFile(String settingsFile) {
        BSSession.put("SettingsFile", settingsFile);
    }

    public static String getSettingsFile() {
        if (BSSession.get("SettingsFile") != null) {
            return (String) BSSession.get("SettingsFile");
        } else {
            return null;
        }
    }

    public static void setCoreImplementation(String coreImplementation) {
        BSSession.put("CoreImplementation", coreImplementation);
    }

    public static String getCoreImplementation() {
        if (BSSession.get("CoreImplementation") != null) {
            return (String) BSSession.get("CoreImplementation");
        } else {
            return null;
        }
    }

    public static void setSessionImplementation(String sessionImplementation) {
        BSSession.put("SessionImplementation", sessionImplementation);
    }

    public static String getSessionImplementation() {
        if (BSSession.get("SessionImplementation") != null) {
            return (String) BSSession.get("sessionImplementation");
        } else {
            return null;
        }
    }

    public static void setLoggerImplementation(String loggerImplementation) {
        BSSession.put("LoggerImplementation", loggerImplementation);
    }

    public static String getLoggerImplementation() {
        if (BSSession.get("LoggerImplementation") != null) {
            return (String) BSSession.get("LoggerImplementation");
        } else {
            return null;
        }
    }

    public static void setSplashImplementation(String splashImplementation) {
        BSSession.put("SplashImplementation", splashImplementation);
    }

    public static String getSplashImplementation() {
        if (BSSession.get("SplashImplementation") != null) {
            return (String) BSSession.get("SplashImplementation");
        } else {
            return null;
        }
    }

    public static void setFullScreen(Boolean fullscreen) {
        isFullScreen.set(fullscreen);
    }

    private static boolean initInstances() {
        /*
         * CORE
         */
        try {
            if (BSSession.get("CoreImplementation") != null) {
                instance = (BSCore) Class.forName(
                        (String) BSSession.get("CoreImplementation")).newInstance();
                instance.setResourcesURL(getResourcesUrl());
                BSBusMgr.get().registerComponentInBus("Core", instance);
            } else {
                System.out.println("Core class doesn't exists. You need to"
                        + " set the key 'CoreImplementation' in the class"
                        + " 'BSSession'");
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Core class doesn't exists. You need to"
                    + " set the key 'CoreImplementation' in the class"
                    + " 'BSSession'");
            return false;
        }
        /*
         * LOGGER
         */
        try {
            if (BSSession.get("LoggerImplementation") != null) {
                BSLogger logger = (BSLogger) Class.forName(
                        (String) BSSession.get("LoggerImplementation")).newInstance();
                instance.setLogger(logger);
            } else {
                instance.setLogger(new BSDefaultLogger());
            }
        } catch (Exception ex) {
            System.out.println("Logger class doesn't exists. You need to"
                    + " set the key 'LoggerImplementation' in the class"
                    + " 'BSSession'");
            return false;
        }
        /*
         * SPLASH
         */
        try {
            if (BSSession.get("SplashImplementation") != null) {
                splash = (BSSplash) Class.forName(
                        (String) BSSession.get("SplashImplementation")).newInstance();
            } else {
                System.out.println("Logger class doesn't exists. You need to"
                        + " set the key 'LoggerImplementation' in the class"
                        + " 'BSSession'");
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Logger class doesn't exists. You need to"
                    + " set the key 'LoggerImplementation' in the class"
                    + " 'BSSession'");
            return false;
        }
        /*
         * SESSION
         */
        try {
            if (BSSession.get("SessionImplementation") != null) {
                BSSessionManager sessionImp = (BSSessionManager) Class.forName(
                        (String) BSSession.get("SessionImplementation")).newInstance();
                instance.setSessionService(sessionImp);
                //BSBusMgr.get().registerComponentInBus("core", sessionImp);
            } else {
                instance.setSessionService(null);
            }
        } catch (Exception ex) {
            System.out.println("Session class doesn't exists. You need to"
                    + " set the key 'SessionImplementation' in the class"
                    + " 'BSSession'");
            return false;
        }
        return true;
    }

    public static void addStartupAction(BSStartupAction action,
            BSStartupLevel startupLevel) {
        switch (startupLevel) {
            case PRELOAD:
                preLoadActions.add(action);
                break;
            case LOAD:
                loadActions.add(action);
                break;
            case POSTLOAD:
                postLoadActions.add(action);
                break;
        }
    }

    public static void startCore(String startTitle) {
        appTitle = startTitle;
        if (instance == null) {
            new BSKitStarter().execute();
        } else {
            System.out.println("BSCore ya ha sido iniciado");
        }
    }

    public static void finalizeCore() {
        // Finalizar acciones postcarga       
        for (int i = 0; i < postLoadActions.size(); i++) {
            postLoadActions.get(i).finalizeAction();            
        }
    }

    /**
     * Devuelve la instancia iniciada del nucleo
     *
     * @return La instancia del nucleo iniciada
     */
    public static BSCore getCore() {
        if (instance == null) {
            if (!hasBeenStarted.get()) {
                new BSKitStarter().execute();
            }
            throw new RuntimeException("El nucleo esta siendo iniciado");
        }
        return instance;
    }

    private static class BSKitStarter extends SwingWorker<Void, String> {

        boolean instanceError = false;
        boolean parameterError = false;

        public BSKitStarter() {
            hasBeenStarted.set(true);
            if (!initParameters()) {
                parameterError = true;
                cancel(true);
                return;
            }
            if (!initInstances()) {
                instanceError = true;
                cancel(true);
                return;
            }
            if (!isCancelled()) {
                if (splash instanceof Window) {
                    centerSplash((Window) splash);
                }
                splash.setNumOperations(preLoadActions.size() + loadActions.size() + postLoadActions.size() + 2);
                splash.beginSplash();
                splash.publishStep("Iniciando ejecucion de aplicacion");
                for (int i = 0; i < preLoadActions.size(); i++) {
                    splash.publishStep(preLoadActions.get(i).getStartupMessage());
                    if (!preLoadActions.get(i).executeAction()) {
                        splash.publishStep(preLoadActions.get(i).getErrorMessages());
                        if (preLoadActions.get(i).isCritical()) {
                            cancel(true);
                            return;
                        }
                    }
                }
                try {
                    instance.beforeLoad();
                    instance.setTitle(appTitle);
                    instance.setFullscreen(isFullScreen.get());
                } catch (Exception ex) {
                }
            }
        }

        @Override
        protected Void doInBackground() throws Exception {
            for (int i = 0; i < loadActions.size(); i++) {
                if (!isCancelled()) {
                    publish(loadActions.get(i).getStartupMessage());
                    if (!loadActions.get(i).executeAction()) {
                        publish(loadActions.get(i).getErrorMessages());
                        if (loadActions.get(i).isCritical()) {
                            publish("Carga abortada");
                            cancel(true);
                            return null;
                        }
                    }
                }
            }
            instance.onLoad();
            publish("Carga finalizada");
            return null;
        }

        @Override
        protected void process(List<String> chunks) {
            splash.publishStep(chunks.get(chunks.size() - 1));
        }

        @Override
        protected void done() {
            try {
                if (isCancelled()) {
                    StringBuilder errorMessages = new StringBuilder();
                    errorMessages.append("<html>");
                    if (parameterError) {
                        errorMessages.append(
                                "<p><span style='font-weight:bold;color:#ff2222;'>CRITICO: </span>El archivo de configuracion no fue configurado.</p>");
                    }
                    if (instanceError) {
                        errorMessages.append(
                                "<p><span style='font-weight:bold;color:#ff2222;'>CRITICO: </span>Los componentes de nucleo no pueden ser inicializados.</p>");
                    }
                    for (int i = 0; i < preLoadActions.size(); ++i) {
                        if (!preLoadActions.get(i).isSuccesful() && preLoadActions.get(
                                i).
                                isCritical()) {
                            errorMessages.append(
                                    "<p><span style='font-weight:bold;color:#ff2222;'>CRITICO: </span> ");
                            errorMessages.append("<span style='font-weight:bold;'>");
                            errorMessages.append(preLoadActions.get(i).getName());
                            errorMessages.append("</span> ");
                            errorMessages.append(
                                    preLoadActions.get(i).getErrorMessages());
                            errorMessages.append("</p>");
                        }
                    }
                    for (int i = 0; i < loadActions.size(); ++i) {
                        if (!loadActions.get(i).isSuccesful() && loadActions.get(i).
                                isCritical()) {
                            errorMessages.append(
                                    "<p><span style='font-weight:bold;color:#ff2222;'>CRITICO: </span> ");
                            errorMessages.append("<span style='font-weight:bold;'>");
                            errorMessages.append(loadActions.get(i).getName());
                            errorMessages.append("</span> ");
                            errorMessages.append(loadActions.get(i).getErrorMessages());
                            errorMessages.append("</p>");
                        }
                    }
                    for (int i = 0; i < preLoadActions.size(); ++i) {
                        if (!preLoadActions.get(i).isSuccesful() && !preLoadActions.get(i).
                                isCritical()) {
                            errorMessages.append(
                                    "<p><span style='font-weight:bold;color:#aaaa22;'>ADVERTENCIA: </span> ");
                            errorMessages.append("<span style='font-weight:bold;'>");
                            errorMessages.append(preLoadActions.get(i).getName());
                            errorMessages.append("</span> ");
                            errorMessages.append(
                                    preLoadActions.get(i).getErrorMessages());
                            errorMessages.append("</p>");
                        }
                    }
                    for (int i = 0; i < loadActions.size(); ++i) {
                        if (!loadActions.get(i).isSuccesful() && !loadActions.get(i).
                                isCritical()) {
                            errorMessages.append(
                                    "<p><span style='font-weight:bold;color:#aaaa22;'>ADVERTENCIA: </span> ");
                            errorMessages.append("<span style='font-weight:bold;'>");
                            errorMessages.append(loadActions.get(i).getName());
                            errorMessages.append("</span> ");
                            errorMessages.append(loadActions.get(i).getErrorMessages());
                            errorMessages.append("</p>");
                        }
                    }
                    errorMessages.append("</html>");
                    if (splash != null) {
                        JOptionPane.showMessageDialog((Component) splash,
                                new JLabel(errorMessages.toString()),
                                "Error de inicio",
                                JOptionPane.ERROR_MESSAGE);
                        splash.stopSplash();
                        System.exit(0);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                new JLabel(errorMessages.toString()),
                                "Error de inicio",
                                JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }
                }
                get();
                if (splash != null) {
                    splash.stopSplash();
                }
                instance.afterLoad();
                boolean criticalError = false;
                /*
                 * Ejecutar tareas postcarga
                 */
                for (int i = 0; i < postLoadActions.size(); i++) {
                    BSCoreFactory.getCore().logInfo(postLoadActions.get(i).
                            getStartupMessage());
                    if (!postLoadActions.get(i).executeAction()) {
                        BSCoreFactory.getCore().logError(postLoadActions.get(i).
                                getErrorMessages(), null);
                        if (postLoadActions.get(i).isCritical()) {
                            criticalError = true;
                        }
                    }
                }
                /*
                 * Si hay un error critico mostrar errores y terminar
                 */
                if (criticalError) {
                    StringBuilder errorMessages = new StringBuilder();
                    errorMessages.append("<html>");
                    for (int i = 0; i < postLoadActions.size(); ++i) {
                        if (!postLoadActions.get(i).isSuccesful()) {
                            if (postLoadActions.get(i).isCritical()) {
                                errorMessages.append(
                                        "<p><span style='font-weight:bold;color:#ff2222;'>CRITICO: </span> ");
                            } else {
                                errorMessages.append(
                                        "<p><span style='font-weight:bold;color:#aaaa22;'>ADVERTENCIA: </span> ");
                            }
                            errorMessages.append("<span style='font-weight:bold;'>");
                            errorMessages.append(postLoadActions.get(i).getName());
                            errorMessages.append("</span> ");
                            errorMessages.append(postLoadActions.get(i).
                                    getErrorMessages());
                            errorMessages.append("</p>");
                        }
                    }
                    errorMessages.append("</html>");
                    JOptionPane.showMessageDialog(null,
                            new JLabel(errorMessages.toString()),
                            "Error de inicio",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                //instance.afterLoad();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        new JLabel(ex.getMessage()),
                        "Error critico",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void centerSplash(Window window) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = window.getWidth();
        int height = window.getHeight();
        int x = (int) ((screen.getWidth() - width) / 2);
        int y = (int) ((screen.getHeight() - height) / 2);
        window.setBounds(x, y, width, height);
    }
}
