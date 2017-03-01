package com.durga.sph.androidchallengetracker.providers;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by root on 2/13/17.
 */

public class MyProgressContract {
    public static final String CONTENT_AUTHORITY = "com.durga.sph.androidchallengetracker.providers";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MYPROGRESS = "myprogress";

    public static final class MyProgressEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MYPROGRESS).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MYPROGRESS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MYPROGRESS;
        //table name
        public static final String TABLE_NAME = "myprogress";
        public static final String _ID = "_id";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_LEVEL = "level";
        public static final String COLUMN_UPVOTE = "upvote";
        public static final String COLUMN_ISSOLVED = "solved";
        public static final String COLUMN_ISREVIEWED = "reviewed";
        public static final String COLUMN_ISADDED = "added";
        public static final String COLUMN_ISAPPROVED = "approved";
        public static final String COLUMN_CREATEDATE = "createdate";
        public static final String COLUMN_LASTMODIFIED = "lastmodified";
        public static final String PREPARED_QUERY = "=?";

        public static Uri buildUriByID(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static Uri buildUriApproved() {
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_ISAPPROVED, "1").build();
        }

        public static Uri buildUriSolved() {
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_ISSOLVED, "1").build();
        }

        public static Uri buildUriReviewed() {
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_ISREVIEWED, "1").build();
        }

        public static Uri buildUriAdded() {
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_ISADDED, "1").build();
        }

        public static Uri buildUriLevel(int level) {
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_LEVEL, Integer.toString(level)).build();
        }

        public static Uri buildUriQuestionById(String id) {
            return CONTENT_URI.buildUpon().appendQueryParameter(_ID, id).build();
        }
    }
}
