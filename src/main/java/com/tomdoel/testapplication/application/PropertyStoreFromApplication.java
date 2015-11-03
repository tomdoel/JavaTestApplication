/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.application;

import com.tomdoel.testapplication.common.PasswordStore;
import com.tomdoel.testapplication.common.PropertyStore;
import com.tomdoel.testapplication.common.Reporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class PropertyStoreFromApplication implements PropertyStore {

    private Reporter reporter;
    private Optional<PasswordStore> passwordStore = null;
    private final Map<String, Optional<char[]>> passwords = new HashMap<String, Optional<char[]>>();
    private final Properties applicationProperties;

    public PropertyStoreFromApplication(final String applicationPropertyFileName, final Reporter reporter) {
        this.reporter = reporter;
        try {
            passwordStore = Optional.of(new PasswordStore(new File(System.getProperty("user.home"), ".testapplicationkeys"), "k>9TG*"));
        } catch (Throwable t) {
            reporter.silentLogException(t, "Unable to create or load local password keystore. Passwords will not be saved between sessions");
            passwordStore = Optional.empty();
        }

        applicationProperties = new Properties();
        try {
            if (applicationPropertyFileName != null) {
                String whereFrom = makePathToFileInUsersHomeDirectory(applicationPropertyFileName);
                FileInputStream in = new FileInputStream(whereFrom);
                applicationProperties.load(in);
                in.close();
            }
        } catch (IOException e) {
            reporter.silentLogException(e, "Unable to load properties");
        }
    }

    @Override
    public String getProperty(final String propertyName) {
        return applicationProperties.getProperty(propertyName);
    }


    @Override
    public Optional<char[]> getPassword(final String passwordKey) {
        if (passwordStore.isPresent()) {
            try {
                // Get the password from the keystore
                final char[] password = passwordStore.get().retrieve(passwordKey);

                // Store the password in memory cache
                passwords.put(passwordKey, password.length > 0 ? Optional.of(password) : Optional.<char[]>empty());

            } catch (InvalidKeySpecException e) {
                reporter.silentLogException(e, "Failure when accessing local keystore");
            } catch (UnrecoverableEntryException e) {
                reporter.silentLogException(e, "Failure when accessing local keystore");
            } catch (NoSuchAlgorithmException e) {
                reporter.silentLogException(e, "Failure when accessing local keystore");
            } catch (KeyStoreException e) {
                reporter.silentLogException(e, "Failure when accessing local keystore");
            }
        }

        // Get the password from the memory cache - the the event of a keystore failure, we will at least be able to store passwords for the current session
        if (passwords.containsKey(passwordKey)) {
            return passwords.get(passwordKey);
        } else {
            return Optional.empty();
        }
    }

    private static String makePathToFileInUsersHomeDirectory(final String fileName) {
        return System.getProperty("user.home")+System.getProperty("file.separator")+fileName;
    }
}