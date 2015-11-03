/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.gui;

import com.tomdoel.testapplication.application.ReporterFromApplication;
import com.tomdoel.testapplication.application.TestApplicationMainFrame;
import com.tomdoel.testapplication.common.StatusObservable;

import java.util.Optional;
import java.util.ResourceBundle;

public class SystemTrayController {

    private final Optional<SystemTray> systemTray;

    SystemTrayController(final TestApplicationController controller, final ResourceBundle resourceBundle, final ReporterFromApplication reporter) {
        // Try to create a system tray icon. If this fails, then we warn the user and make the main dialog visible
        systemTray = SystemTray.safeCreateSystemTray(controller, resourceBundle, reporter);
    }

    public boolean isPresent() {
        return systemTray.isPresent();
    }

    public void windowVisibilityStatusChanged(TestApplicationMainFrame.MainWindowVisibility visibility) {
        if (systemTray.isPresent()) {
            systemTray.get().updateMenuForWindowVisibility(visibility);
        }
    }

    public void remove() {
        if (systemTray.isPresent()) {
            systemTray.get().remove();
        }
    }

    public class MainWindowVisibilityListener implements StatusObservable.StatusListener<MainFrame.MainWindowVisibility> {
        public void statusChanged(final MainFrame.MainWindowVisibility visibility) {
            windowVisibilityStatusChanged(visibility);
        }
    }

}
