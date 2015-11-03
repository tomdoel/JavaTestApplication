/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.common;

import org.apache.commons.lang.StringUtils;

import java.util.Optional;


public class TestApplicationProperties {

    private String propertyName_Username = "TestApplication_LastUsername";
    private String propertyName_HideWindowOnStartup = "TestApplication_HideWindowOnStartup";

    protected static String KEYSTORE_UPLOAD_PASSWORD_KEY = "TestApplication.UploadPassword";
    private final PropertyStore properties;

    public TestApplicationProperties(final PropertyStore properties) {
        this.properties = properties;
    }

    public Optional<String> getUserName() {
        return getOptionalProperty(propertyName_Username);
    }

    public Optional<Boolean> getHideWindowOnStartup() {
        return getOptionalBoolean(propertyName_HideWindowOnStartup);
    }

    public Optional<char[]> getPassword() {
        return properties.getPassword(KEYSTORE_UPLOAD_PASSWORD_KEY);
    }

    private final Optional<String> getOptionalProperty(final String propertyName) {
        final String propertyValue = getPropertyValue(propertyName);
        if (StringUtils.isNotBlank(propertyValue)) {
            return Optional.of(propertyValue);
        } else {
            return Optional.empty();
        }
    }

    private final Optional<Boolean> getOptionalBoolean(final String propertyName) {
        final String propertyValue = getPropertyValue(propertyName);
        if (StringUtils.isNotBlank(propertyValue)) {
            try {
                return Optional.of(Boolean.parseBoolean(propertyValue));
            } catch (Throwable t) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    private String getStringWithDefault(final String propertyName, final String defaultValue) {
        final String propertyValue = getPropertyValue(propertyName);
        if (StringUtils.isNotBlank(propertyValue)) {
            return propertyValue;
        } else {
            return defaultValue;
        }
    }

    private String getPropertyValue(final String propertyName) {
        return properties.getProperty(propertyName);
    }


}
