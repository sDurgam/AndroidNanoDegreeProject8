package com.durga.sph.androidchallengetracker.orm;

import com.durga.sph.androidchallengetracker.utils.Constants;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

/**
 * Created by root on 1/30/17.
 */

public class TrackerQuestion {
    public String id;
    public String description;
    public String userId;
    public HashMap<String, Object> dateCreated;
    public HashMap<String, Object> dateLastChanged;
    public long upvote;
    public boolean spam;
    public int level;

    public TrackerQuestion() {
    }

    public TrackerQuestion(String description, String userId, int level) {
        this.description = description;
        this.userId = userId;
        dateCreated = new HashMap<>();
        Object timestamp = ServerValue.TIMESTAMP;
        dateCreated.put(Constants.TIME, timestamp);
        dateLastChanged = new HashMap<>();
        dateLastChanged.put(Constants.TIME, timestamp);
        upvote = 0;
        this.level = level;
    }

    public TrackerQuestion(HashMap<String, Object> map) {
        if (map.containsKey(Constants.QUESTIONID))
            this.id = map.get(Constants.QUESTIONID).toString();
        if (map.containsKey(Constants.TITLE))
            this.description = map.get(Constants.TITLE).toString();
        if (map.containsKey(Constants.USERID))
            this.userId = map.get(Constants.USERID).toString();
        if (map.containsKey(Constants.DATECREATEDAT))
            dateCreated = (HashMap<String, Object>) map.get(Constants.DATECREATEDAT);
        if (map.containsKey(Constants.DATELASTCHANGED))
            dateLastChanged = (HashMap<String, Object>) map.get(Constants.DATELASTCHANGED);
        level = Integer.valueOf(map.get(Constants.LEVEL).toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getUpvote() {
        return upvote;
    }

    public void setUpvote(long upvote) {
        this.upvote = upvote;
    }

    public boolean isSpam() {
        return spam;
    }

    public void setSpam(boolean spam) {
        this.spam = spam;
    }

    public HashMap<String, Object> getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(HashMap<String, Object> dateCreated) {
        this.dateCreated = dateCreated;
    }

    public HashMap<String, Object> getDateLastChanged() {
        return dateLastChanged;
    }

    public void setDateLastChanged(HashMap<String, Object> dateLastChanged) {
        this.dateLastChanged = dateLastChanged;
    }
}
