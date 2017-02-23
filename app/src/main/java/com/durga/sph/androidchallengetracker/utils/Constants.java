package com.durga.sph.androidchallengetracker.utils;

import java.util.HashMap;

/**
 * Created by root on 1/30/17.
 */

public class Constants {
    public static int TAB_ITEM_COUNT = 5;
    public static String BLANKSTR = "\u0020";
    public static String MYPOINTS = "mypoints";
    public static String MYADDEDQUES = "myaddedques";
    public static String MYREVIEWEDQUES = "myreviewedques";
    public static String REVIEWEQUES = "revieweques";
    public static String REVIEWQUESPATH = "revieweques/";
    public static String TRACKER = "Tracker";
    public static String QUESTIONS = "Questions";
    public static String LEVELFORMATTER = "level%s";
    public static String LEVEL = "level";
    public static String LEVEL1 = "level1";
    public static String LEVEL2 = "level2";
    public static String LEVEL3 = "level3";
    public static String UPVOTE = "upvote";
    public static String ID = "id";
    public static String TIME = "time";
    public static String UNKNOWN_URL = "unknown uri:";

    //move records path
    public static String LEVELIDFORMATTER = "level%s/%s";

    //tracker question
    public static String QUESTIONID = "id";
    public static String ISSPAM = "spam";
    public static String TITLE = "description";
    public static String USERID = "userId";
    public static String DATECREATEDAT = "dateCreated";
    public static String DATELASTCHANGED = "dateLastChanged";
    public static String REVIEWER = "reviewers";
    //three reviewers
    public static int APPROVE_QUESTION_COUNT = 5;
    public static int APPROVE_MAX_QUESTION_COUNT = 10;
    public static int APPROVE_MIN_QUESTION_COUNT = -3;
    public static int MAX_QUESTIONS_API_COUNT = 20;

    //my progress table columns


    public static HashMap<String, Object> updateMapForAllWithValue
            (final String questionId,
             final String mlevel, HashMap<String, Object> mapToUpdate,
             String propertyToUpdate, Object valueToUpdate) {
        mapToUpdate.put("/" + mlevel + "/" + questionId + "/"
                + propertyToUpdate, valueToUpdate);
        return mapToUpdate;
    }

}
