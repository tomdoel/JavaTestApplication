
/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.applet;

import com.tomdoel.testapplication.application.ReporterFromApplication;
import com.tomdoel.testapplication.gui.TestApplicationDialogs;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.applet.Applet;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

public class ReporterFromApplet extends ReporterFromApplication {

    private final Applet applet;
    private static final Logger logger = LoggerFactory.getLogger(ReporterFromApplet.class);

    public ReporterFromApplet(final Applet applet, final TestApplicationDialogs dialogs) {
        super(dialogs);
        this.applet = applet;
        configureLogging();
    }

    @Override
    public void reportErrorToUser(String errorText, Throwable throwable) {
        String finalErrorText = errorText + " " + throwable.getLocalizedMessage();
        errorBox(finalErrorText, throwable);
        updateStatusText("Error: " + throwable);
        logger.error("Error: " + throwable.getLocalizedMessage(), throwable);
        throwable.printStackTrace(System.err);
    }

    @Override
    public void showMessageToUser(String messageText) {
        messageBox(messageText);
        updateStatusText("Message: " + messageText);
        logger.error("Message: " + messageText);
    }

    @Override
    public void silentWarning(String warning) {
        logger.info(warning);
    }

    @Override
    public void silentLogException(final Throwable throwable, final String errorMessage) {
        logger.info(errorMessage + ":" + throwable.getLocalizedMessage());
    }

    /**
     * Loads logging resources, including loading logging properties from custom URLs specified by the
     * LOG4J_PROPS_URL applet parameter.
     */
    private void configureLogging() {
        final String log4jProps = applet.getParameter(TestApplicationParameters.LOG4J_PROPS_URL);
        if (StringUtils.isNotBlank(log4jProps)) {
            try {
                PropertyConfigurator.configure(new URL(log4jProps));
            } catch (MalformedURLException e) {
                logger.error("Unable to read remote log4j configuration file " + log4jProps, e);
            }
        }
    }

    private void errorBox(final String errorMessage, final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter writer = new PrintWriter(sw);
        writer.println(errorMessage);
        writer.println("Error details:");
        throwable.printStackTrace(writer);
        final JTextArea text = new JTextArea(sw.toString());
        text.setEditable(false);
        applet.add(text);
        applet.validate();
    }

    private void messageBox(final String message) {
        final StringWriter sw = new StringWriter();
        final PrintWriter writer = new PrintWriter(sw);
        writer.println(message);
        final JTextArea text = new JTextArea(sw.toString());
        text.setEditable(false);
        applet.add(text);
        applet.validate();
    }
}
