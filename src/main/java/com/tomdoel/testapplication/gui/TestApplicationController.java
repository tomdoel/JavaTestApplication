/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.gui;

import com.apple.eawt.Application;
import com.tomdoel.testapplication.application.ReporterFromApplication;
import com.tomdoel.testapplication.application.TestAppMain;
import com.tomdoel.testapplication.common.PropertyStore;
import com.tomdoel.testapplication.common.TestApplicationProperties;

import javax.imageio.ImageIO;
import javax.jnlp.ServiceManager;
import javax.jnlp.SingleInstanceListener;
import javax.jnlp.SingleInstanceService;
import javax.jnlp.UnavailableServiceException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The main controller class
 */
public class TestApplicationController {

    private final ResourceBundle resourceBundle;
    private final TestApplicationProperties properties;
    private final MainFrame mainFrame;
    private final TestApplicationDialogs testApplicationDialogs;
    private final TestApplicationPanel testApplicationPanel;
    private final ReporterFromApplication reporter;
    private final SystemTrayController systemTrayController;
    private Optional<SingleInstanceService> singleInstanceService;

    public TestApplicationController(final MainFrame mainFrame, final PropertyStore propertyStore, final TestApplicationDialogs dialogs, final ReporterFromApplication reporter) throws IOException {
        resourceBundle = mainFrame.getResourceBundle();
        this.mainFrame = mainFrame;
        this.testApplicationDialogs = dialogs;
        this.reporter = reporter;

        try {
            singleInstanceService = Optional.of((SingleInstanceService) ServiceManager.lookup("javax.jnlp.SingleInstanceService"));
            TestApplicationSingleInstanceListener singleInstanceListener = new TestApplicationSingleInstanceListener();
            singleInstanceService.get().addSingleInstanceListener(singleInstanceListener);
        } catch (UnavailableServiceException e) {
            singleInstanceService = Optional.empty();
        }

        // Set the dock icon - we need to do this before the main class is created
        final URL iconURL = TestAppMain.class.getResource("/com/tomdoel/TestApplicationMiniIcon.png");

        if (iconURL == null) {
            System.out.println("Warning: could not find the icon resource");
        } else {
            if (isOSX()) {
                try {
                    Image iconImage = ImageIO.read(iconURL);
                    if (iconImage == null) {
                        System.out.println("Could not find icon");
                    } else {
                        Application.getApplication().setDockIconImage(new ImageIcon(iconImage).getImage());
                    }
                } catch (Exception e) {
                    System.out.println("Warning: could not configure the dock menu");
                    e.printStackTrace(System.err);
                }
            }
        }

        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.UIElement", "true");

        final String applicationTitle = resourceBundle.getString("applicationTitle");

        // This is used to set the gui title on OSX, but may not work when run from the debugger
        System.setProperty("com.apple.mrj.gui.apple.menu.about.name", applicationTitle);

        setSystemLookAndFeel();

        // Initialise gui properties
        properties = new TestApplicationProperties(propertyStore);


        testApplicationPanel = new TestApplicationPanel(mainFrame.getParent(), this, properties, resourceBundle, reporter);

        mainFrame.addMainPanel(testApplicationPanel);

        systemTrayController = new SystemTrayController(this, resourceBundle, reporter);
        mainFrame.addListener(systemTrayController.new MainWindowVisibilityListener());

        final Optional<Boolean> hideWindowOnStartupProperty = properties.getHideWindowOnStartup();

        // We hide the main window only if specified in the preferences, AND if the system tray is supported
        final boolean hideMainWindow = hideWindowOnStartupProperty.isPresent() && hideWindowOnStartupProperty.get() && systemTrayController.isPresent();
        if (hideMainWindow) {
            hide();
        } else {
            show();
        }

    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
            }
        }).start();
    }


    private void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Panel.background", Color.white);
            UIManager.put("CheckBox.background", Color.lightGray);
            UIManager.put("SplitPane.background", Color.white);
            UIManager.put("OptionPane.background", Color.white);
            UIManager.put("Panel.background", Color.white);

            Font font = new Font("Arial Unicode MS",Font.PLAIN,12);
            if (font != null) {
                UIManager.put("Tree.font", font);
                UIManager.put("Table.font", font);
            }
        } catch (Throwable t) {
            reporter.silentLogException(t, "Error when setting the system look and feel");
        }
    }

    public void showAboutDialog() {
        mainFrame.show();
        testApplicationDialogs.showMessage(resourceBundle.getString("testApplicationProductName"));
    }

    public void hide() {
        mainFrame.hide();
    }

    public void show() {
        mainFrame.show();
    }

    public static boolean isOSX() {
        return (System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0);
    }

    private class TestApplicationSingleInstanceListener implements SingleInstanceListener {

        public TestApplicationSingleInstanceListener() {

            // Add a shutdown hook to unregister the single instance
            // ShutdownHook will run regardless of whether Command-Q (on Mac) or window closed ...
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    if (singleInstanceService.isPresent()) {
                        singleInstanceService.get().removeSingleInstanceListener(TestApplicationSingleInstanceListener.this);
                    }
                }
            });

        }
        @Override
        public void newActivation(String[] strings) {
            show();
        }
    }
}
