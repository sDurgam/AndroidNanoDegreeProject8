package com.durga.sph.androidchallengetracker.challengeprovider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by root on 1/9/17.
 */

public class AndroidChallengeTrackerContract {

    public static final String CONTENT_SCHEME = "content://";
    public static final String CONTENT_AUTHORITY = "com.durga.sph.androidchallengetracker.challengeprovider";
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);

    public static final class Level1Entry implements BaseColumns{

    }

    public static final class Level2Entry implements BaseColumns{

    }

    public static final class Level3Entry implements BaseColumns{

    }

    public static final class QuestionsEntry implements BaseColumns{

    }

    public static final class MyPointsEntry implements BaseColumns{

    }

    public static final class MyAddedQuestionsEntry implements BaseColumns{

    }

    public static final class MyReviewedQuestionsEntry implements BaseColumns{

    }
}
