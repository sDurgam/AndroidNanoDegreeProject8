package com.durga.sph.androidchallengetracker.ui.listeners;

import com.durga.sph.androidchallengetracker.orm.MyProgressQuestion;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;

/**
 * Created by root on 2/11/17.
 */

public interface IOnLevelItemClickListerner {
    void onisSolvedClick(String questionId, String user, int position);
}
