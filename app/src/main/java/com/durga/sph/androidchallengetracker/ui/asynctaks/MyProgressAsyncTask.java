package com.durga.sph.androidchallengetracker.ui.asynctaks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.durga.sph.androidchallengetracker.network.ProgressDatabaseInterface;
import com.durga.sph.androidchallengetracker.providers.MyProgressContract;
import com.durga.sph.androidchallengetracker.ui.listeners.ProgressListener;
import com.durga.sph.androidchallengetracker.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.Map;

import static com.durga.sph.androidchallengetracker.utils.Constants.NAMEDB_LIST;

/**
 * Created by root on 2/23/17.
 */

public class MyProgressAsyncTask extends AsyncTask<Void, Void, Cursor>{

    WeakReference<Context> weakContext;
    Map<String, Long> localProgessMap;
    ProgressListener listener;
    ProgressDatabaseInterface databaseInterface;

    public MyProgressAsyncTask(WeakReference<Context> weakctx, ProgressListener listener, ProgressDatabaseInterface databaseInterface, Map<String, Long> localProgressMap){
        weakContext = weakctx;
        localProgessMap = localProgressMap;
        this.listener = listener;
        this.databaseInterface = databaseInterface;
    }

    @Override
    protected Cursor doInBackground(Void... params) {
        Cursor l = null;
        if(weakContext.get() != null) {
            l = weakContext.get().getContentResolver().query(MyProgressContract.MyProgressEntry.CONTENT_URI, null, null, null, null);
        }
        return l;
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        if(cursor != null && cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                if(cursor.getString(4).equals(Constants.ONE)) {
                    if (cursor.getString(2).equals(Constants.ONE)) {
                        updateHashMap(NAMEDB_LIST[0]);
                    } else if (cursor.getString(2).equals(Constants.TWO)) {
                        updateHashMap(NAMEDB_LIST[1]);
                    } else if (cursor.getString(2).equals(Constants.THREE)) {
                        updateHashMap(NAMEDB_LIST[2]);
                    }
                }
                if(cursor.getString(6).equals(Constants.ONE)){
                    updateHashMap(NAMEDB_LIST[4]);
                }
                if(cursor.getString(5).equals(Constants.ONE)){
                    updateHashMap(NAMEDB_LIST[3]);
                }
                if(cursor.getString(7).equals(Constants.ONE)){
                    updateHashMap(NAMEDB_LIST[5]);
                }

            }while (cursor.moveToNext());
        }
        if(cursor != null) {
            cursor.close();
        }
        //call firebase
        getProgress(listener);
    }

    void updateHashMap(String key){
        long count = 1l;
        if(localProgessMap.containsKey(key)){
            count += localProgessMap.get(key);
        }
        localProgessMap.put(key, count);
    }

    void getProgress(ProgressListener listener){
        databaseInterface.getProgress(listener);
    }
}
