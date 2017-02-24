package com.durga.sph.androidchallengetracker.ui.asynctaks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.durga.sph.androidchallengetracker.network.ProgressDatabaseInterface;
import com.durga.sph.androidchallengetracker.providers.MyProgressContract;
import com.durga.sph.androidchallengetracker.ui.listeners.IProgressListener;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import static com.durga.sph.androidchallengetracker.utils.Constants.NAMEDB_LIST;

/**
 * Created by root on 2/23/17.
 */

public class MyProgressAsyncTask extends AsyncTask<Void, Void, Cursor>{

    WeakReference<Context> m_weakcontext;
    Map<String, Long> m_localProgessMap;
    IProgressListener m_listener;
    ProgressDatabaseInterface m_databaseInterface;
    public MyProgressAsyncTask(WeakReference<Context> weakctx, IProgressListener listener, ProgressDatabaseInterface databaseInterface, Map<String, Long> localProgressMap){
        m_weakcontext = weakctx;
        m_localProgessMap = localProgressMap;
        m_listener = listener;
        m_databaseInterface = databaseInterface;
    }

    @Override
    protected Cursor doInBackground(Void... params) {
        Cursor l = null;
        if(m_weakcontext.get() != null) {
            l = m_weakcontext.get().getContentResolver().query(MyProgressContract.MyProgressEntry.CONTENT_URI, null, null, null, null);
        }
        return l;
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        if(cursor != null && cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                if(cursor.getString(4).equals("1")) {
                    if (cursor.getString(2).equals("1")) {
                        updateHashMap(NAMEDB_LIST[0]);
                    } else if (cursor.getString(2).equals("2")) {
                        updateHashMap(NAMEDB_LIST[1]);
                    } else if (cursor.getString(2).equals("3")) {
                        updateHashMap(NAMEDB_LIST[2]);
                    }
                }
                if(cursor.getString(6).equals("1")){
                    updateHashMap(NAMEDB_LIST[4]);
                }
                if(cursor.getString(5).equals("1")){
                    updateHashMap(NAMEDB_LIST[3]);
                }
                if(cursor.getString(7).equals("1")){
                    updateHashMap(NAMEDB_LIST[5]);
                }

            }while (cursor.moveToNext());
        }
        if(cursor != null) {
            cursor.close();
        }
        //call firebase
        getProgress(m_listener);
    }

    void updateHashMap(String key){
        long count = 1l;
        if(m_localProgessMap.containsKey(key)){
            count += m_localProgessMap.get(key);
        }
        m_localProgessMap.put(key, count);
    }

    void getProgress(IProgressListener listener){
        m_databaseInterface.getProgress(listener);
    }
}
