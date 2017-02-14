package com.durga.sph.androidchallengetracker.providers;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URL;

/**
 * Created by root on 2/13/17.
 */

public class MyProgressContract {
    public static final String CONTENT_AUTHORITY="com.durga.sph.androidchallengetracker.providers";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MYPROGRESS = "myprogress";

    public static final class MyProgressEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MYPROGRESS).build();
        //table name
        public static final String TABLE_NAME = "myprogress";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_LEVEL = "level";
        public static final String COLUMN_UPVOTE = "upvote";
        public static final String COLUMN_ISSOLVED = "solved";
        public static final String COLUMN_ISREVIEWED = "reviewed";
        public static final String COLUMN_ISADDED = "added";
        public static final String COLUMN_LASTMODIFIED = "lastmodified";
    }

}
