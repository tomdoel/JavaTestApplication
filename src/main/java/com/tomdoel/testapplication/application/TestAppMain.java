/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.application;

import com.tomdoel.testapplication.gui.*;

import javax.swing.*;

/**
 * TestAppMain is the main entry point for the Java application
 */
public class TestAppMain {

    /**
     * <p>The method to invoke the gui.</p>
     *
     * @param	arg	none
     */
    public static void main(String arg[]) {
        try {
            final TestApplicationMainFrame mainFrame = new TestApplicationMainFrame(new JFrame());
            final TestApplicationDialogs dialogs = new TestApplicationDialogs(mainFrame);
            final ReporterFromApplication reporter = new ReporterFromApplication(dialogs);
            final TestApplicationController uploaderMain = new TestApplicationController(mainFrame, new PropertyStoreFromApplication(TestApplicationMainFrame.propertiesFileName, reporter), dialogs, reporter);
            uploaderMain.start();
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
