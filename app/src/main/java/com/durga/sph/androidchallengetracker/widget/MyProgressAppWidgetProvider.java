package com.durga.sph.androidchallengetracker.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.network.ProgressDatabaseInterface;
import com.durga.sph.androidchallengetracker.ui.activites.MainActivity;
import com.durga.sph.androidchallengetracker.ui.asynctaks.MyProgressAsyncTask;
import com.durga.sph.androidchallengetracker.ui.listeners.ProgressListener;
import com.durga.sph.androidchallengetracker.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import static com.durga.sph.androidchallengetracker.utils.Constants.NAMEDB_LIST;

/**
 * Created by root on 2/13/17.
 */

public class MyProgressAppWidgetProvider extends AppWidgetProvider implements ProgressListener {

    Map<String, Long> mLocalProgessMap;
    AppWidgetManager mAppWidgetManager;
    int[] mAppWidgetIds;
    Context mContext;

    public MyProgressAppWidgetProvider() {
        mLocalProgessMap = new HashMap<>();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        this.mContext = context;
        this.mAppWidgetManager = appWidgetManager;
        this.mAppWidgetIds = appWidgetIds;
        if (FirebaseAuth.getInstance() != null && FirebaseAuth.getInstance().getCurrentUser() != null) {
            new MyProgressAsyncTask(new WeakReference<Context>(context), this, new ProgressDatabaseInterface(), mLocalProgessMap).execute();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onProgressReceived(Map<String, Long> progressMap) {
        final int N = mAppWidgetIds.length;
        int appwidgetId = -1;
        //add level 1, level2, level 3
        for (int i = 0; i < N; i++) {
            appwidgetId = mAppWidgetIds[i];
            //make an async task to track MyProgress
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_layout);
            //make an api call to know the progress for each level, solved, added, approved and reviewed questions
            views.setTextViewText(R.id.widget_level1, getValue(mLocalProgessMap, NAMEDB_LIST[0]).toString() + "/" + getValue(progressMap, NAMEDB_LIST[0].toString()));
            views.setTextViewText(R.id.widget_level2, getValue(mLocalProgessMap, NAMEDB_LIST[1]) + "/" + getValue(progressMap, NAMEDB_LIST[1]));
            views.setTextViewText(R.id.widget_level3, getValue(mLocalProgessMap, NAMEDB_LIST[2]) + "/" + getValue(progressMap, NAMEDB_LIST[2]));
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Constants.FROMWIDGET, Constants.TRUE);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widgetMainLayout, pendingIntent);
            mAppWidgetManager.updateAppWidget(appwidgetId, views);
        }
    }

    private Long getValue(Map<String, Long> map, String key) {
        return (map.get(key) == null) ? 0 : map.get(key);
    }
}
