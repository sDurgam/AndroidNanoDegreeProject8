package com.durga.sph.androidchallengetracker.ui.listeners;

import java.util.Map;

/**
 * Created by root on 2/23/17.
 */

public interface ProgressListener {
    void onProgressReceived(Map<String, Long> progressMap);
}
