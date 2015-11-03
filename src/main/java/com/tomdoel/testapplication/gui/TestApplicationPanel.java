/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.gui;

import com.tomdoel.testapplication.application.ReporterFromApplication;
import com.tomdoel.testapplication.common.TestApplicationProperties;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ResourceBundle;

/**
 *
 */
public class TestApplicationPanel extends JPanel {

    // User interface components
    private final JPanel srcDatabasePanel;

    public TestApplicationPanel(final JFrame dialog, final TestApplicationController controller, final TestApplicationProperties properties, final ResourceBundle resourceBundle, final ReporterFromApplication reporter) {
        super();

        srcDatabasePanel = new JPanel();
        srcDatabasePanel.setLayout(new GridLayout(1, 1));

        Border panelBorder = BorderFactory.createEtchedBorder();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(panelBorder);

        {
            GridBagLayout mainPanelLayout = new GridBagLayout();
            setLayout(mainPanelLayout);
            {
                GridBagConstraints localBrowserPanesConstraints = new GridBagConstraints();
                localBrowserPanesConstraints.gridx = 0;
                localBrowserPanesConstraints.gridy = 0;
                localBrowserPanesConstraints.weightx = 1;
                localBrowserPanesConstraints.weighty = 1;
                localBrowserPanesConstraints.fill = GridBagConstraints.BOTH;
                mainPanelLayout.setConstraints(srcDatabasePanel,localBrowserPanesConstraints);
                add(srcDatabasePanel);
            }
            {
                GridBagConstraints buttonPanelConstraints = new GridBagConstraints();
                buttonPanelConstraints.gridx = 0;
                buttonPanelConstraints.gridy = 1;
                buttonPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
                mainPanelLayout.setConstraints(buttonPanel,buttonPanelConstraints);
                add(buttonPanel);
            }
        }
    }

}
