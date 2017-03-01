package com.durga.sph.androidchallengetracker.ui.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.network.ProgressDatabaseInterface;
import com.durga.sph.androidchallengetracker.providers.MyProgressContract;
import com.durga.sph.androidchallengetracker.ui.asynctaks.MyProgressAsyncTask;
import com.durga.sph.androidchallengetracker.ui.listeners.ProgressListener;
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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.durga.sph.androidchallengetracker.ui.activites.BaseActivity.mTwoPane;
import static com.durga.sph.androidchallengetracker.utils.Constants.NAMEDB_LIST;

/**
 * Created by root on 1/30/17.
 */

public class MyPointsFragment extends BaseFragment implements ProgressListener {


    private static String[] SNAMESLIST = new String[]{"LEVEL1", "LEVEL2", "LEVEl3", "REVIEWED", "ADDED", "APPROVED"};
    Map<String, Long> mLocalProgessMap;
    Map<String, Long> mProgessMap;
    ProgressDatabaseInterface mDatabaseInterface;
    @BindView(R.id.mypointsTitle)
    TextView titleView;
    @BindView(R.id.progressChart)
    PieChart mChart;

    public static MyPointsFragment newInstance() {
        MyPointsFragment fragment = new MyPointsFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDatabaseInterface = new ProgressDatabaseInterface();
        mLocalProgessMap = new HashMap<>();
        mProgessMap = new HashMap<>();
        for (int i = 0; i < NAMEDB_LIST.length; i++) {  //initialize hashmap
            mLocalProgessMap.put(NAMEDB_LIST[i], 0l);
        }
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mProgessMap.put(dataSnapshot.getKey(), dataSnapshot.getValue() != null ? (Long) dataSnapshot.getValue() : 0l);
                generatePieData();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                mProgessMap.put(dataSnapshot.getKey(), dataSnapshot.getValue() != null ? (Long) dataSnapshot.getValue() : 0l);
                generatePieData();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                mProgessMap.put(dataSnapshot.getKey(), dataSnapshot.getValue() != null ? (Long) dataSnapshot.getValue() : 0l);
                generatePieData();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.mypoints_fragment, container, false);
        ButterKnife.bind(this, view);
        //hide title if tablet
        if (mTwoPane) {
            titleView.setVisibility(View.GONE);
        }
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
        mDatabaseInterface.registerListener(mChildEventListener);
        if (mFirebaseAuth.getCurrentUser() != null) {
            new MyProgressAsyncTask(new WeakReference<Context>(this.getActivity()), this, mDatabaseInterface, mLocalProgessMap).execute();
        }
    }


    protected PieData generatePieData() {

        ArrayList<PieEntry> entries1 = new ArrayList<PieEntry>();

        //add level 1, level2, level 3
        for (int i = 0; i < 4; i++) {
            addPieEntry(entries1, i);
        }

        //isadded percentage
        float c = getTotalAdded();
        /*if(c == 0){
            entries1.add(new PieEntry(0.0f, SNAMESLIST[3]));
        }*/
        if (c > 0) {
            c = (float) mLocalProgessMap.get(MyProgressContract.MyProgressEntry.COLUMN_ISADDED) / c;
            if (c > 0) {
                entries1.add(new PieEntry(c, SNAMESLIST[4]));
            }
        }

        //isapproved percentage
        if (mProgessMap.containsKey(Constants.REVIEWEQUES)) {
            c = c - mProgessMap.get(Constants.REVIEWEQUES);
        /*if(c <= 0){
            entries1.add(new PieEntry(0.0f, SNAMESLIST[5]));
        }*/
            if (c > 0) {
                c = (float) mLocalProgessMap.get(MyProgressContract.MyProgressEntry.COLUMN_ISAPPROVED) / c;
                if (c > 0) {
                    entries1.add(new PieEntry(c, SNAMESLIST[5]));
                }
            }
        }
        PieDataSet ds1 = new PieDataSet(entries1, Constants.MYPOINTS);
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(24f);

        PieData d = new PieData(ds1);
        return d;
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString(Constants.MYPROGRESSTRACKER);
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
        mDatabaseInterface.unregisterListerner(mChildEventListener);
        super.onPause();
    }

    @Override
    public void onProgressReceived(Map<String, Long> progressMap) {
        loadingBar.setVisibility(View.GONE);
        this.mProgessMap = progressMap;
        mChart.setData(generatePieData());
        mChart.invalidate();
    }

    void addPieEntry(List<PieEntry> entryList, int i) {
        Float progress = 0.0f;
        Long p = null;
        progress = (float) mLocalProgessMap.get(NAMEDB_LIST[i]);
        if (mProgessMap.containsKey(NAMEDB_LIST[i])) {
            p = mProgessMap.get(NAMEDB_LIST[i]);
            if (progress != 0 && p != null && p != 0) {
                progress = progress / p;
                entryList.add(new PieEntry(progress, SNAMESLIST[i]));
            }
        }
    }

    long getTotalAdded() {
        long count = 0l;
        for (Long data : mProgessMap.values()) {
            count += data;
        }
        return count;
    }
}
