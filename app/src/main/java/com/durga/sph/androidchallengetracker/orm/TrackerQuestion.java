package com.durga.sph.androidchallengetracker.orm;

import com.durga.sph.androidchallengetracker.utils.Constants;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 1/30/17.
 */

public class TrackerQuestion {
    public String id;
    public String title;
    public String userId;
    public int level;
    public String lastModified;
    public List<String> reviewers;
    public long upvote;

    public boolean isSpam() {
        return spam;
    }

    public void setSpam(boolean spam) {
        this.spam = spam;
    }

    public boolean spam;

    public TrackerQuestion(){}

    public TrackerQuestion(String questionId, String title, String userId, int level) {
        this.id = questionId;
        this.title = title;
        this.userId = userId;
        this.level = level;
        lastModified = String.valueOf(System.currentTimeMillis());
        reviewers = new ArrayList<>();
        upvote = 0l;
    }

    public TrackerQuestion(HashMap<String, Object> map) {
        if (map.containsKey(Constants.QUESTIONID))
            this.id = map.get(Constants.QUESTIONID).toString();
        if (map.containsKey(Constants.TITLE))
            this.title = map.get(Constants.TITLE).toString();
        if (map.containsKey(Constants.USERID))
            this.userId = map.get(Constants.USERID).toString();
        if (map.containsKey(Constants.LEVEL))
            this.level = Integer.valueOf(map.get(Constants.LEVEL).toString());
        if (map.containsKey(Constants.LASTMODIFIED))
            lastModified = map.get(Constants.LASTMODIFIED).toString();
    }


    public TrackerQuestion(HashMap<String, Object> map, boolean isReviewer) {
        if (map.containsKey(Constants.QUESTIONID))
            this.id = map.get(Constants.QUESTIONID).toString();
        if (map.containsKey(Constants.TITLE))
            this.title = map.get(Constants.TITLE).toString();
        if (map.containsKey(Constants.USERID))
            this.userId = map.get(Constants.USERID).toString();
        if (map.containsKey(Constants.LEVEL))
            this.level = Integer.valueOf(map.get(Constants.LEVEL).toString());
        if (map.containsKey(Constants.LASTMODIFIED))
            lastModified = map.get(Constants.LASTMODIFIED).toString();
        reviewers = new ArrayList<>();
        if(isReviewer && map.get(Constants.REVIEWER) != null){
            reviewers.addAll((ArrayList<String>)(map.get(Constants.REVIEWER)));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
