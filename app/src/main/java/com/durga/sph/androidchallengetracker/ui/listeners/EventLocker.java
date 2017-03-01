package com.durga.sph.androidchallengetracker.ui.listeners;

/**
 * Created by root on 2/28/17.
 */

public final class EventLocker {

    private static Object lock;

    public static synchronized boolean lock(final Object object) {
        if (null == object) { throw new IllegalArgumentException("object must not be null"); }
        if (null == lock) {
            lock = object;
            return true;
        }
        return false;
    }

    public static synchronized boolean release(final Object object) {
        if (null != lock && lock.equals(object)) {
            lock = null;
            return true;
        }
        return false;
    }

}