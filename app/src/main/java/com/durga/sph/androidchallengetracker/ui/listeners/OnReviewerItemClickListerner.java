package com.durga.sph.androidchallengetracker.ui.listeners;

import android.widget.RadioGroup;

import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;

/**
 * Created by root on 2/11/17.
 */

public interface OnReviewerItemClickListerner extends OnItemClickListener {
    void onisApprovedClick(TrackerQuestion question, RadioGroup group);

    void onisNotApprovedClick(TrackerQuestion question, RadioGroup group);

    void onisSpamClick(TrackerQuestion question, RadioGroup group);
}