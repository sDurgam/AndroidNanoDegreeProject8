package com.durga.sph.androidchallengetracker.ui.listeners;

import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;

/**
 * Created by root on 2/9/17.
 */

public interface IOnItemClickListener {
    void onisSpamClick(TrackerQuestion question, int position);
    void isSuccess(boolean success);
}
