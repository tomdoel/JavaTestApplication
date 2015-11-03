
/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.applet;

import com.tomdoel.testapplication.gui.TestApplicationDialogs;
import com.tomdoel.testapplication.gui.MainFrame;
import com.tomdoel.testapplication.gui.TestApplicationController;
import com.tomdoel.testapplication.common.PropertyStore;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class TestApplet extends JApplet {
    private Optional<ReporterFromApplet> reporter = Optional.empty();
    private Optional<TestApplicationController> testApplicationController = Optional.empty();

    /**
     * Default constructor.
     */
    public TestApplet() {
        setLayout(new BorderLayout());
    }

    /**
     * Initializes the applet.
     *
     * @see java.applet.Applet#init()
     */
    @Override
    public void init() {
        try {
            final MainFrame mainFrame = new MainFrame(this, new JFrame());
            final TestApplicationDialogs dialogs = new TestApplicationDialogs(mainFrame);
            reporter = Optional.of(new ReporterFromApplet(this, dialogs));
            final AppletParameters appletParameters = new AppletParameters(this, reporter.get());
            final PropertyStore propertyStore = new PropertyStoreFromApplet(new TestApplicationParameters(appletParameters));
            testApplicationController = Optional.of(new TestApplicationController(mainFrame, propertyStore, dialogs, reporter.get()));


        } catch (Throwable t) {
            if (reporter.isPresent()) {
                reporter.get().reportErrorToUser("Applet initialisation failed", t);
            }
            throw new RuntimeException(t);
        }
    }

    /**
     * Implementation of the {@link java.applet.Applet#start()} method.
     *
     * @see java.applet.Applet#start()
     */
    @Override
    public void start() {
        try {
            testApplicationController.get().start();

        } catch (Throwable t) {
            reporter.get().reportErrorToUser("Applet startup failed", t);
            throw new RuntimeException(t);
        }
    }

    /**
     * Implementation of the {@link java.applet.Applet#stop()} method.
     *
     * @see java.applet.Applet#stop()
     */
    @Override
    public void stop()
    {
    }

    /**
     * Implementation of the {@link java.applet.Applet#getAppletInfo()} method.
     *
     * @see java.applet.Applet#getAppletInfo()
     */
    @Override
    public String getAppletInfo() {
        return TestApplicationAppletParameterInfo.getAppletInfo();
    }

    /**
     * Implementation of the {@link java.applet.Applet#getParameterInfo()} method.
     *
     * @see java.applet.Applet#getParameterInfo()
     */
    @Override
    public String[][] getParameterInfo() {
        return TestApplicationAppletParameterInfo.getParameterInfo();
    }
}
