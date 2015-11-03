/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.applet;


import com.tomdoel.testapplication.common.Reporter;
import org.apache.commons.lang.StringUtils;

import java.applet.Applet;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppletParameters {

    private final Applet applet;
    private Reporter reporter;

    public AppletParameters(final Applet applet, final Reporter reporter) {
        this.applet = applet;
        this.reporter = reporter;

        Properties properties = new Properties(System.getProperties());
        loadAppletProperties(properties);
        loadCustomProperties(properties);
        System.setProperties(properties);
    }

    public String getParameter(final String key) {
        return applet.getParameter(key);
    }

    /**
     * Loads applet properties from the <b>applet.properties</b>properties file. This
     * can be overridden by placing a custom version of the properties file ahead of
     * the applet jar on the classpath.
     *
     * @param properties The system properties for the applet.
     */
    private void loadAppletProperties(Properties properties) {
        InputStream appletProperties = null;

        try {
            appletProperties = applet.getClass().getResourceAsStream("/com/tomdoel/testapplication/applet/applet.properties");
            if (appletProperties != null) {
                properties.load(appletProperties);
            }
        } catch (IOException exception) {
            reporter.silentLogException(exception, "Unable to find the applet.properties resource for initialization");
        } finally {
            if (appletProperties != null) {
                try {
                    appletProperties.close();
                } catch (IOException e) {
                    // Whatever.
                }
            }
        }
    }

    /**
     * Loads properties from custom properties specified in the CUSTOM_PROPS_URL
     * applet parameter. If this parameter is not specified, no properties are loaded.
     *
     * @param properties The system properties for the applet.
     */
    private void loadCustomProperties(Properties properties) {
        final String customProps = getParameter(TestApplicationParameters.CUSTOM_PROPS_URL);
        if (StringUtils.isNotBlank(customProps)) {
            InputStream customProperties = null;
            try {
                customProperties = applet.getClass().getResourceAsStream(customProps);
                properties.load(customProperties);
            } catch (IOException exception) {
                reporter.silentLogException(exception, "Unable to find the custom properties resource " + customProps + " for initialization");
            } finally {
                if (customProperties != null) {
                    try {
                        customProperties.close();
                    } catch (IOException ignored) {
                        // Once again, whatever.
                    }
                }
            }
        }
    }
}
