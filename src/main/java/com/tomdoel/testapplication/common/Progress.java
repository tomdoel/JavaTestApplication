/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.common;

public interface Progress {
    void startProgressBar(int maximum);

    void startProgressBar();

    void updateProgressBar(int value);

    void updateProgressBar(int value, int maximum);

    void endProgressBar();

    void updateStatusText(final String progressText);

    boolean isCancelled();
}
