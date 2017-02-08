package com.durga.sph.androidchallengetracker.orm;

import java.util.HashMap;

/**
 * Created by root on 1/30/17.
 */

public class TrackerQuestion {
    public String questionId;
    public String title;
    public String userId;
    public int level;
    public String lastModified;

    public TrackerQuestion(String title, String userId, int level) {
        this.questionId = questionId;
        this.title = title;
        this.userId = userId;
        this.level = level;
        lastModified = String.valueOf(System.currentTimeMillis());
    }

    public TrackerQuestion(HashMap<String, Object> map) {
        if(map.containsKey("questionId"))
        this.questionId = map.get("questionId").toString();
        if(map.containsKey("title"))
        this.title = map.get("title").toString();
        if(map.containsKey("userId"))
        this.userId = map.get("userId").toString();
        if(map.containsKey("level"))
        this.level = Integer.valueOf(map.get("level").toString());
        if(map.containsKey("lastModified"))
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
}
