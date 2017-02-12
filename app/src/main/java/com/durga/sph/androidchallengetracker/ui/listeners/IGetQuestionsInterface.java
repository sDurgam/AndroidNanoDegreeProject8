package com.durga.sph.androidchallengetracker.ui.listeners;

import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;

import java.util.List;

/**
 * Created by root on 2/8/17.
 */

public interface IGetQuestionsInterface {
    void onQuestionsReady(List<TrackerQuestion> questionsList);
}
