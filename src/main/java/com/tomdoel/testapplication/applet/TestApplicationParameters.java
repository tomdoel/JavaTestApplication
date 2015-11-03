
/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.applet;

import org.apache.commons.lang.StringUtils;

import java.util.Optional;

public class TestApplicationParameters {

    private AppletParameters appletParameters;

    public TestApplicationParameters(final AppletParameters appletParameters) {
        this.appletParameters = appletParameters;
    }

    public String getParameter(final String key) {
        return appletParameters.getParameter(key);
    }


    public String getParameter(final String key, final String defaultValue) {
        final String v = appletParameters.getParameter(key);
        return null == v ? defaultValue : v;
    }

    public Optional<String> getOptionalParameter(String key) {
        final String value = appletParameters.getParameter(key);
        if (StringUtils.isBlank(value)) {
            return Optional.empty();
        } else {
            return Optional.of(value);
        }
    }

    public static final String LOG4J_PROPS_URL = "log4j-properties-url";
    public static final String CUSTOM_PROPS_URL = "custom-properties-url";

}
