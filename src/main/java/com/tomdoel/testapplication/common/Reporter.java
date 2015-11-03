
/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.common;

public interface Reporter extends Progress {

    /**
     * Used to display a message to the end user, unless running in background mode
     * @param errorText error text to display
     * @param throwable exception to report.
     */
    void reportErrorToUser(final String errorText, final Throwable throwable);

    /**
     * Used to display a message to the end user, unless running in background mode
     * @param messageText text to display
     */
    void showMessageToUser(final String messageText);

    /**
     * Indicates a warning that should not be reported to the user, but should be recorded in the log
     * @param warning the text of the warning
     */
    void silentWarning(final String warning);

    /**
     * Indicates that we wish to log an exception because it may be swallowed
     * @param errorMessage the text of the error
     */
    void silentLogException(final Throwable throwable, final String errorMessage);
}
