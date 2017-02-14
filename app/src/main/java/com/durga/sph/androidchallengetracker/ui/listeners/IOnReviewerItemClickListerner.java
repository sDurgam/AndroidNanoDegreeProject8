package com.durga.sph.androidchallengetracker.ui.listeners;

import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;

/**
 * Created by root on 2/11/17.
 */

public interface IOnReviewerItemClickListerner extends IOnItemClickListener {
    void onisApprovedClick(TrackerQuestion question, String user, int position);
    void onisNotApprovedClick(TrackerQuestion question, String user, int position);
    void onisSpamClick(TrackerQuestion question, int position);
    void success(boolean success);
}
