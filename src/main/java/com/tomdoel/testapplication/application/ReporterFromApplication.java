/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.application;


import com.tomdoel.testapplication.common.Progress;
import com.tomdoel.testapplication.common.ProgressModel;
import com.tomdoel.testapplication.gui.TestApplicationDialogs;
import com.tomdoel.testapplication.common.Reporter;
import org.apache.log4j.PropertyConfigurator;

import java.util.Optional;

public class ReporterFromApplication implements Reporter, Progress {
    private final TestApplicationDialogs testApplicationDialogs;


    private final ProgressModel progressModel = new ProgressModel();

    public ReporterFromApplication(final TestApplicationDialogs testApplicationDialogs) {
        this.testApplicationDialogs = testApplicationDialogs;
        configureLogging();
    }

    public void showMessageToUser(final String messageText) {
        testApplicationDialogs.showMessage(messageText);
        updateStatusText(messageText);
    }

    public void reportErrorToUser(String errorText, Throwable throwable) {
        final Optional<String> additionalText = Optional.of(throwable.getLocalizedMessage());
        testApplicationDialogs.showError(errorText, additionalText);

        String errorMessageForStatusBar = errorText + " " + throwable.getLocalizedMessage();
        updateStatusText(errorMessageForStatusBar);
        throwable.printStackTrace(System.err);
    }

    public void silentWarning(final String warning) {

    }

    public void silentLogException(final Throwable throwable, final String errorMessage) {
    }

    /**
     * Loads logging resources
     */
    private void configureLogging() {
        PropertyConfigurator.configure(this.getClass().getClassLoader().getResource("com/tomdoel/testapplication/log4j.properties"));
    }



    public void startProgressBar(int maximum) {
        progressModel.startProgress(maximum);
    }

    public void startProgressBar() {
        progressModel.startProgress();
    }

    public void updateProgressBar(int value) {
        progressModel.updateProgressBar(value);
    }

    public void updateProgressBar(int value, int maximum) {
        progressModel.updateProgressBar(value, maximum);
    }

    public void endProgressBar() {
        progressModel.endProgressBar();
    }

    public void updateStatusText(String progressText) {
        progressModel.updateProgressText(progressText);
    }

    public boolean isCancelled() {
        return progressModel.isCancelled();
    }


    // These are the preferred methods for reporting to the user

    public void silentError(final String errorMessage, final Throwable throwable) {
    }


}
