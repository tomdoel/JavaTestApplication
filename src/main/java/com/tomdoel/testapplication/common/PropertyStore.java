/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.common;

import java.util.Optional;

/**
 * An interface for storing and retrieving properties and passwords
 */
public interface PropertyStore {

    /**
     * Retrieves the property with the given name
     */
    String getProperty(final String propertyName);

    /**
     * Gets the password with the given name
     */
    Optional<char[]> getPassword(final String passwordKey);
}

