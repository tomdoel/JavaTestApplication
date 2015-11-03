/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.application;

import com.tomdoel.testapplication.gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestApplicationMainFrame extends MainFrame {
    private JFrame frame;

    public static String propertiesFileName  = "TestApplication.properties";


    public TestApplicationMainFrame(final JFrame frame) {
        super(frame, frame);
        this.frame = frame;
        final Image image = new ImageIcon(this.getClass().getClassLoader().getResource("com/tomdoel/testapplication/TestApplicationMiniIcon.png")).getImage();
        frame.setIconImage(image);
        frame.setTitle(getResourceBundle().getString("applicationTitle"));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Invoke the hide method on the controller, to ensure the system tray menu gets updated correctly
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });
    }

    @Override
    public void show() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frame.setVisible(true);
                frame.setAlwaysOnTop(true);
                frame.toFront();
                frame.requestFocus();
                frame.setAlwaysOnTop(false);
                notifyStatusChanged(MainWindowVisibility.VISIBLE);
            }
        });
    }

    @Override
    public void addMainPanel(final Container panel) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frame.add(panel);
                frame.pack();
            }
        });
    }
}