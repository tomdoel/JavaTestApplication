
/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.applet;

import com.google.common.base.Joiner;

import java.util.Calendar;

public class TestApplicationAppletParameterInfo {
    private static final String[][] PARAMETER_INFO = {};

    private static final String AUTHORS = "Author: Tom Doel";
    private static final String COPYRIGHT = String.format("Copyright (c) 2014-%d Tom Doel", Calendar.getInstance().get(Calendar.YEAR));
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String APPLET_INFO = Joiner.on(LINE_SEPARATOR).join(AUTHORS, COPYRIGHT);


    public static final String[][] getParameterInfo() {
        return PARAMETER_INFO;
    }

    public static final String getAppletInfo() {
        return APPLET_INFO;
    }
}
