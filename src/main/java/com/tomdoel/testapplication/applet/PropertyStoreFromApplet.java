/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.applet;

import com.tomdoel.testapplication.common.PropertyStore;

import java.util.Optional;

public class PropertyStoreFromApplet implements PropertyStore {

    private final TestApplicationParameters testApplicationParameters;

    public PropertyStoreFromApplet(final TestApplicationParameters testApplicationParameters) {
        this.testApplicationParameters = testApplicationParameters;
    }

    @Override
    public String getProperty(final String propertyName) {
        return testApplicationParameters.getParameter(propertyName);
    }


    @Override
    public Optional<char[]> getPassword(final String passwordKey) {
        return Optional.empty();
    }

}
