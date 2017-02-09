package com.durga.sph.androidchallengetracker.orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 1/30/17.
 */

public class TrackerQuestion {
    public String questionId;
    public String title;
    public String userId;
    public int level;
    public String lastModified;
    public List<String> reviewers;

    public boolean isSpam() {
        return isSpam;
    }

    public void setSpam(boolean spam) {
        isSpam = spam;
    }

    public boolean isSpam;

    public TrackerQuestion(String title, String userId, int level) {
        this.questionId = questionId;
        this.title = title;
        this.userId = userId;
        this.level = level;
        lastModified = String.valueOf(System.currentTimeMillis());
        reviewers = new ArrayList<>();
        reviewers.add(userId);
        isSpam = true;
    }

    public TrackerQuestion(HashMap<String, Object> map) {
        if (map.containsKey("questionId"))
            this.questionId = map.get("questionId").toString();
        if (map.containsKey("title"))
            this.title = map.get("title").toString();
        if (map.containsKey("userId"))
            this.userId = map.get("userId").toString();
        if (map.containsKey("level"))
            this.level = Integer.valueOf(map.get("level").toString());
        if (map.containsKey("lastModified"))
            lastModified = map.get("lastModified").toString();
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

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public List<String> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<String> reviewers) {
        this.reviewers = reviewers;
    }
}
