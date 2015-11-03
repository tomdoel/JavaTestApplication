/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.gui;

import com.tomdoel.testapplication.application.PropertyStoreFromApplication;
import com.tomdoel.testapplication.application.ReporterFromApplication;
import com.tomdoel.testapplication.application.TestApplicationMainFrame;

import javax.swing.*;

/**
 * TestApplicationTestContainer is a Java gui that mimics TestAppMain
 */
public class TestApplicationTestContainer {

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
            TestApplicationController uploaderMain = new TestApplicationController(mainFrame, new PropertyStoreFromApplication(TestApplicationMainFrame.propertiesFileName, reporter), dialogs, reporter);
            uploaderMain.start();
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
}
