package com.durga.sph.androidchallengetracker.ui.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.graphics.Color;
import android.net.LocalSocketAddress;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.network.FirebaseDatabaseInterface;
import com.durga.sph.androidchallengetracker.network.LevelQuestionsInterface;
import com.durga.sph.androidchallengetracker.network.ProgressDatabaseInterface;
import com.durga.sph.androidchallengetracker.providers.MyProgressContract;
import com.durga.sph.androidchallengetracker.ui.RecylclerViewEndlessScrollListener;
import com.durga.sph.androidchallengetracker.ui.listeners.IGetQuestionsInterface;
import com.durga.sph.androidchallengetracker.ui.listeners.IProgressListener;
import com.durga.sph.androidchallengetracker.utils.Constants;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 1/30/17.
 */

public class MyPointsFragment extends BaseFragment implements IProgressListener {


    private static String[] NAME_LIST = new String[] { "LEVEL1", "LEVEL2", "LEVEl3", "ADDED", "REVIEWED", "APPROVED"};
    private  String[] NAMEDB_LIST = new String[] { Constants.LEVEL1, Constants.LEVEL2, Constants.LEVEL3, MyProgressContract.MyProgressEntry.COLUMN_ISADDED, Constants.REVIEWEQUES, MyProgressContract.MyProgressEntry.COLUMN_ISAPPROVED};
    Map<String, Long> m_localProgessMap;
    Map<String, Long> m_ProgessMap;
    ProgressDatabaseInterface databaseInterface;
    ChildEventListener mChildEventListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        databaseInterface = new ProgressDatabaseInterface();
        m_ProgessMap = new HashMap<>();
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                m_ProgessMap.put(dataSnapshot.getKey(), (Long) dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                m_ProgessMap.put(dataSnapshot.getKey(), (Long) dataSnapshot.getValue());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                m_ProgessMap.put(dataSnapshot.getKey(), 0l);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    @BindView(R.id.progressChart)
    PieChart mChart;

    public static MyPointsFragment newInstance() {
        MyPointsFragment fragment = new MyPointsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.mypoints_fragment, container, false);
        ButterKnife.bind(this, view);
        m_localProgessMap = new HashMap<>();
        mChart.getDescription().setEnabled(false);
        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(45f);
        mChart.setTransparentCircleRadius(50f);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        databaseInterface.registerListener(mChildEventListener);
        new AsyncTask<Void, Void, Cursor>(){

            @Override
            protected Cursor doInBackground(Void... params) {
                Cursor l = getActivity().getContentResolver().query(MyProgressContract.MyProgressEntry.CONTENT_URI, null, null, null, null);
                return l;
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                super.onPostExecute(cursor);
                if(cursor.getCount() != 0){
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
                            updateHashMap(NAMEDB_LIST[3]);
                        }
                        if(cursor.getString(5).equals("1")){
                            updateHashMap(NAMEDB_LIST[4]);
                        }
                        if(cursor.getString(7).equals("1")){
                            updateHashMap(NAMEDB_LIST[5]);
                        }

                    }while (cursor.moveToNext());
                }
                cursor.close();
                //call firebase
                getProgress();
            }
        }.execute();
    }

    private void getProgress(){

        if(mFirebaseAuth.getCurrentUser() != null) {
            m_username = mFirebaseAuth.getCurrentUser().getUid();
            databaseInterface.getProgress(this);
        }
    }

    protected PieData generatePieData() {

        ArrayList<PieEntry> entries1 = new ArrayList<PieEntry>();

        //add level 1, level2, level 3
        for(int i = 0; i < 3; i++) {
            addPieEntry(entries1, i);
        }

        //isadded percentage
        float c =getTotalAdded();
        /*if(c == 0){
            entries1.add(new PieEntry(0.0f, NAME_LIST[3]));
        }*/
        if(c > 0) {
            c = m_localProgessMap.get(MyProgressContract.MyProgressEntry.COLUMN_ISADDED) / c;
            entries1.add(new PieEntry(c, NAME_LIST[3]));
        }

        //isreviewed percentage
        addPieEntry(entries1, 4);

        //isapproved percentage
        c = c - m_ProgessMap.get(Constants.REVIEWEQUES);
        /*if(c <= 0){
            entries1.add(new PieEntry(0.0f, NAME_LIST[5]));
        }*/
        if(c > 0){
            c = m_localProgessMap.get(MyProgressContract.MyProgressEntry.COLUMN_ISAPPROVED)/c;
            entries1.add(new PieEntry(c, NAME_LIST[5]));
        }

        PieDataSet ds1 = new PieDataSet(entries1, "My Progress");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(24f);

        PieData d = new PieData(ds1);
        return d;
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("My Progress\nTracker");
        s.setSpan(new RelativeSizeSpan(2f), 0, 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        databaseInterface.unregisterListerner(mChildEventListener);
        super.onPause();
    }

    @Override
    public void onProgressReceived(Map<String, Long> progressMap) {
        this.m_ProgessMap = progressMap;
        mChart.setData(generatePieData());
        mChart.invalidate();
    }

    void updateHashMap(String key){
        long count = 1l;
        if(m_localProgessMap.containsKey(key)){
            count += m_localProgessMap.get(key);
        }
        m_localProgessMap.put(key, count);
    }

    void addPieEntry(List<PieEntry> entryList, int i){
        Float progress = 0.0f;
        Long p = null;
        if(!m_localProgessMap.containsKey(NAMEDB_LIST[i]) || m_localProgessMap.get(NAMEDB_LIST[i]) == null) {
            //entryList.add(new PieEntry(progress, NAME_LIST[i]));
            return;
        }
        else {
            progress = (float) m_localProgessMap.get(NAMEDB_LIST[i]);
            p = m_ProgessMap.get(NAMEDB_LIST[i]);
            if (p != null && p != 0) {
                progress = progress / p;
            } else {
                progress = 0.0f;
            }
            entryList.add(new PieEntry(progress, NAME_LIST[i]));
        }
    }

    long getTotalAdded(){
        long count = 0l;
        for(Long data : m_ProgessMap.values()){
            count += data;
        }
        return count;
    }
}
