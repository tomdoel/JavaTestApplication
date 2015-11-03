/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class TestApplicationDialogs {

    private final MainFrame mainFrame;
    private final ImageIcon icon;

    public TestApplicationDialogs(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        // Set the default background colour to white
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", Color.white);
        UI.put("Panel.background", Color.white);

        // Get the gui icon - this will return null if not found
        icon = new ImageIcon(this.getClass().getClassLoader().getResource("com/tomdoel/testapplication/TestApplication.png"));
    }

    public void showMessage(final String message) throws HeadlessException {
        final JPanel messagePanel = new JPanel(new GridBagLayout());
        messagePanel.add(new JLabel(message, SwingConstants.CENTER));
        JOptionPane.showMessageDialog(mainFrame.getContainer(), messagePanel, "Test Application", JOptionPane.INFORMATION_MESSAGE, icon);
    }

    public void showError(final String message, final Optional<String> additionalText) throws HeadlessException {
        final JPanel messagePanel = new JPanel(new GridBagLayout());
        final StringBuilder stringMessage = new StringBuilder();
        stringMessage.append("<html>");
        stringMessage.append(message);
        if (additionalText.isPresent()) {
            stringMessage.append("<br>");
            stringMessage.append(additionalText.get());
        }
        stringMessage.append("</html>");
        messagePanel.add(new JLabel(stringMessage.toString(), SwingConstants.CENTER));

        JOptionPane.showMessageDialog(mainFrame.getContainer(), messagePanel, "Test Application", JOptionPane.ERROR_MESSAGE, icon);
    }


}
