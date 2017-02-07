package com.durga.sph.androidchallengetracker.orm;

/**
 * Created by root on 1/30/17.
 */

public class TrackerQuestion {
    public String questionId;
    public String title;
    public String userId;
    public int level;

    public TrackerQuestion(String questionId, String title, String userId, int level) {
        this.questionId = questionId;
        this.title = title;
        this.userId = userId;
        this.level = level;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
