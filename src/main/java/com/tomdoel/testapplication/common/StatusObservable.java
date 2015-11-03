/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.common;

import java.util.ArrayList;

public class StatusObservable<T> {
    private final java.util.List<StatusListener<T>> listeners = new ArrayList<StatusListener<T>>();

    public void addListener(final StatusListener<T> listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public interface StatusListener<T> {
        void statusChanged(final T visibility);
    }

    protected void notifyStatusChanged(final T status) {
        for (final StatusListener<T> listener : listeners) {
            listener.statusChanged(status);
        }
    }
}
