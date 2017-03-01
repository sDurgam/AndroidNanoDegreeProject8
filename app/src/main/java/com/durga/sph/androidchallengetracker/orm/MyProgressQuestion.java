package com.durga.sph.androidchallengetracker.orm;

/**
 * Created by root on 2/12/17.
 */

public class MyProgressQuestion {
    public String id;
    public boolean isSolved;
    public boolean isReviewed;
    public boolean isAdded;

    public MyProgressQuestion(String id) {
        id = this.id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }
}
