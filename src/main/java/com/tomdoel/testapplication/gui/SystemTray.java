/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.gui;

import com.tomdoel.testapplication.application.ReporterFromApplication;
import com.tomdoel.testapplication.application.TestApplicationMainFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *  Creates an icon and menu in the system tray, allowing the user to execute actions and monitor progress while the main window is hidden
 *
 * @author  Tom Doel
 */
public class SystemTray {

    private final java.awt.SystemTray tray;
    private final TrayIcon trayIcon;
    private final MenuItem hideItem;
    private final MenuItem showItem;

    /**
     * Private constructor for creating a new menu and icon for the system tray
     *
     * @param controller        the controller used to perform menu actions
     * @param resourceBundle    the gui resources used to choose menu text
     *
     * @param reporter
     * @throws AWTException     if the desktop system tray is missing
     * @throws IOException      if an error occured while attempting to read the icon file
     */
    private SystemTray(final TestApplicationController controller, final ResourceBundle resourceBundle, final ReporterFromApplication reporter) throws AWTException, IOException {

        Image iconImage = ImageIO.read(this.getClass().getClassLoader().getResource("com/tomdoel/testapplication/TestApplicationMiniIcon.png"));
        trayIcon = new TrayIcon(iconImage, resourceBundle.getString("systemTrayIconText"));
        trayIcon.setImageAutoSize(true);
        tray = java.awt.SystemTray.getSystemTray();
        trayIcon.setToolTip(resourceBundle.getString("systemTrayIconToolTip"));

        tray.add(trayIcon);

        final PopupMenu popup = new PopupMenu();

        MenuItem aboutItem = new MenuItem(resourceBundle.getString("systemTrayAbout"));
        popup.add(aboutItem);
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.showAboutDialog();
            }
        });



        popup.addSeparator();

        hideItem = new MenuItem(resourceBundle.getString("systemTrayHide"));
        popup.add(hideItem);
        hideItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.hide();
            }
        });

        showItem = new MenuItem(resourceBundle.getString("systemTrayShow"));
        popup.add(showItem);
        showItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.show();
            }
        });


        popup.addSeparator();
        MenuItem exitItem = new MenuItem(resourceBundle.getString("systemTrayExit"));
        popup.add(exitItem);
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //ToDo: remove system tray icon here
                System.exit(0);
            }
        });

        trayIcon.setPopupMenu(popup);

        updateMenuForWindowVisibility(TestApplicationMainFrame.MainWindowVisibility.HIDDEN);
    }

    /**
     * Static factory method for creating the menu and icon for the system tray.
     *
     * <p>Does not throw any exceptions. If the menu cannot be created or an error occurs, an empty Optional is returned.
     *
     * @param controller        the controller used to perform menu actions
     * @param resourceBundle    the gui resources used to choose menu text
     * @param reporter          the reporter object used to record errors
     * @return                  an (@link Optional) containing the (@link SystemTray) object or an empty (@link Optional) if the SystemTray is not supported, or an error occurred, e.g. in attempting to load the icon
     */
    static Optional<SystemTray> safeCreateSystemTray(final TestApplicationController controller, final ResourceBundle resourceBundle, final ReporterFromApplication reporter) {
        if (!java.awt.SystemTray.isSupported()) {
            reporter.silentError("SystemTray is not supported on this system.", null);
            return Optional.empty();
        } else {
            try {
                return Optional.of(new SystemTray(controller, resourceBundle, reporter));
            } catch (Throwable t) {
                reporter.silentError("The system tray icon could not be created due to the following error: " + t.getLocalizedMessage(), t);
                return Optional.empty();
            }
        }
    }

    /**
     * Updates the system tray menu according to enable/disable the show/hide menu items according to the visibility of the main window
     *
     * @param mainWindowVisibility  whether the main window is currently hidden or visible
     */
    void updateMenuForWindowVisibility(final TestApplicationMainFrame.MainWindowVisibility mainWindowVisibility) {
        if (tray == null) {
            return;
        }

        hideItem.setEnabled(mainWindowVisibility.isVisible());
        showItem.setEnabled(!mainWindowVisibility.isVisible());
    }

    /**
     * Removes the menu from the system tray
     */
    void remove() {
        tray.remove(trayIcon);
    }
}
