package com.durga.sph.androidchallengetracker.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.ui.activites.MainActivity;
import com.durga.sph.androidchallengetracker.utils.Constants;

/**
 * Created by root on 2/13/17.
 */

public class MyProgressAppWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        final int N = appWidgetIds.length;
        int appwidgetId = -1;
        for(int i =0; i < N; i++){
            appwidgetId = appWidgetIds[i];
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Constants.FROMWIDGET, true);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.widgetMainLayout, pendingIntent);
            //make an api call to know the progress for each level, solved, added, approved and reviewed questions
            appWidgetManager.updateAppWidget(appwidgetId, views);
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
}
