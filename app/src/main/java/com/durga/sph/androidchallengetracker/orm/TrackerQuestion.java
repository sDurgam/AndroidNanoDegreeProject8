package com.durga.sph.androidchallengetracker.orm;

import com.durga.sph.androidchallengetracker.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 1/30/17.
 */

public class TrackerQuestion {
    public String questionId;
    public String title;
    public String userId;
    public int level;
    public String lastModified;
    public Set<String> reviewers;

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
        reviewers = new HashSet<>();
        reviewers.add(userId);
        isSpam = true;
    }

    public TrackerQuestion(HashMap<String, Object> map) {
        if (map.containsKey(Constants.QUESTIONID))
            this.questionId = map.get(Constants.QUESTIONID).toString();
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
            this.questionId = map.get(Constants.QUESTIONID).toString();
        if (map.containsKey(Constants.TITLE))
            this.title = map.get(Constants.TITLE).toString();
        if (map.containsKey(Constants.USERID))
            this.userId = map.get(Constants.USERID).toString();
        if (map.containsKey(Constants.LEVEL))
            this.level = Integer.valueOf(map.get(Constants.LEVEL).toString());
        if (map.containsKey(Constants.LASTMODIFIED))
            lastModified = map.get(Constants.LASTMODIFIED).toString();
        reviewers = new HashSet<>();
        if(isReviewer && map.get(Constants.REVIEWER) != null){
            reviewers.addAll((ArrayList<String>)(map.get(Constants.REVIEWER)));
        }
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

    public Set<String> getReviewers() {
        return reviewers;
    }

    public void setReviewers(Set<String> reviewers) {
        this.reviewers = reviewers;
    }
}
