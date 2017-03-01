package com.durga.sph.androidchallengetracker.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.widget.RemoteViews;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.network.ProgressDatabaseInterface;
import com.durga.sph.androidchallengetracker.providers.MyProgressContract;
import com.durga.sph.androidchallengetracker.ui.activites.MainActivity;
import com.durga.sph.androidchallengetracker.ui.asynctaks.MyProgressAsyncTask;
import com.durga.sph.androidchallengetracker.ui.listeners.IProgressListener;
import com.durga.sph.androidchallengetracker.utils.Constants;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.durga.sph.androidchallengetracker.utils.Constants.NAMEDB_LIST;

/**
 * Created by root on 2/13/17.
 */

public class MyProgressAppWidgetProvider extends AppWidgetProvider implements IProgressListener {

    Map<String, Long> m_localProgessMap;
    AppWidgetManager appWidgetManager;
    int[] appWidgetIds;
    Context context;
    public MyProgressAppWidgetProvider(){
        m_localProgessMap = new HashMap<>();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        this.context = context;
        this.appWidgetManager = appWidgetManager;
        this.appWidgetIds = appWidgetIds;
        if(FirebaseAuth.getInstance() != null && FirebaseAuth.getInstance().getCurrentUser() != null) {
            new MyProgressAsyncTask(new WeakReference<Context>(context), this, new ProgressDatabaseInterface(), m_localProgessMap).execute();
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
        final int N = appWidgetIds.length;
        int appwidgetId = -1;
        //add level 1, level2, level 3
        for (int i = 0; i < N; i++) {
            appwidgetId = appWidgetIds[i];
            //make an async task to track MyProgress
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            //make an api call to know the progress for each level, solved, added, approved and reviewed questions
            views.setTextViewText(R.id.widget_level1, getValue(m_localProgessMap, NAMEDB_LIST[0]).toString() + "/" + getValue(progressMap, NAMEDB_LIST[0].toString()));
            views.setTextViewText(R.id.widget_level2, getValue(m_localProgessMap, NAMEDB_LIST[1]) + "/" + getValue(progressMap, NAMEDB_LIST[1]));
            views.setTextViewText(R.id.widget_level3, getValue(m_localProgessMap, NAMEDB_LIST[2]) + "/" + getValue(progressMap, NAMEDB_LIST[2]));
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Constants.FROMWIDGET, Constants.TRUE);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widgetMainLayout, pendingIntent);
            appWidgetManager.updateAppWidget(appwidgetId, views);
        }
    }

    private Long getValue(Map<String, Long> map, String key){
        return  (map.get(key) == null)? 0 : map.get(key);
    }
}
