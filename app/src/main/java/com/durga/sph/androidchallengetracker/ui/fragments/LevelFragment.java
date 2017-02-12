package com.durga.sph.androidchallengetracker.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import com.durga.sph.androidchallengetracker.ui.listeners.IGetQuestionsInterface;
import com.durga.sph.androidchallengetracker.network.LevelQuestionsInterface;
import com.durga.sph.androidchallengetracker.ui.RecylclerViewEndlessScrollListener;
import com.durga.sph.androidchallengetracker.ui.adapters.LevelRecyclerViewAdapter;
import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.ui.listeners.IOnItemClickListener;
import com.durga.sph.androidchallengetracker.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 1/2/17.
 */

public class LevelFragment extends BaseFragment
{
    SimpleCursorAdapter mAdapter;
    public String TAG;
    int mcurrentLevel = 1;
    @Nullable @BindView(R.id.levelNameTxt)
    TextView mlevelNameTxt;
    @BindView(R.id.questionsView)
    RecyclerView m_recyclerView;

    public LevelFragment(){
        TAG = getClass().getName();
    }

    public static LevelFragment newInstance(String levelargs, int level) {
        Bundle args = new Bundle();
        args.putInt(levelargs, level);
        LevelFragment fragment = new LevelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabaseInterface = new LevelQuestionsInterface();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.listquestions_fragment, container, false);
        ButterKnife.bind(this, view);
        m_adapter = new LevelRecyclerViewAdapter(this.getActivity(), new ArrayList<TrackerQuestion>(), new IOnItemClickListener() {
            @Override
            public void onisSpamClick(TrackerQuestion question, int position) {
                mFirebaseDatabaseInterface.markQuestionAsSpam(question.id, this);
            }
            @Override
            public void isSuccess(boolean success) {

            }
        });
        Object levelargs = getArguments().get(getResources().getString(R.string.level));
        if(levelargs != null)
            mcurrentLevel = (int)levelargs;
        //getLoaderManager().initLoader(0, null, this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        setScreenName();
       // mFirebaseDatabaseInterface.getQuestions(Constants.QUESTIONS, Constants.LEVEL, String.valueOf(mcurrentLevel), 0, this);
        //mFirebaseDatabaseInterface.registerQuestionsByLevelEventListener(Constants.QUESTIONS);
    }

    @Override
    public void onResume() {
        super.onResume();
        LinearLayoutManager lmanager = new LinearLayoutManager(this.getActivity());
        m_recyclerView.setLayoutManager(lmanager);
        m_recyclerView.setAdapter(m_adapter);
        m_recyclerView.addOnScrollListener(new RecylclerViewEndlessScrollListener(this, lmanager) {
            @Override
            public void onLoadMore(IGetQuestionsInterface callback) {
                if(m_lastQuestionId == null) return;
                mFirebaseDatabaseInterface.getMoreQuestions(Constants.QUESTIONS, m_username, callback, m_lasttimeStamp, m_lastQuestionId, Constants.MAX_QUESTIONS_API_COUNT+1);
            }
        });
        m_username = mFirebaseAuth.getCurrentUser().getUid();
        mFirebaseDatabaseInterface.getQuestions(Constants.QUESTIONS, m_username, this,Constants.MAX_QUESTIONS_API_COUNT+1);
    }

    @Override
    public void onPause() {
        mFirebaseDatabaseInterface.unregisterEventListener(Constants.QUESTIONS);
        super.onPause();
    }

    @Override
    public void onStop() {

        super.onStop();
    }

    //set the current level as screen name
    public void setScreenName(){
        if(mlevelNameTxt != null) {
            if (mcurrentLevel == 1 || mcurrentLevel == 2 || mcurrentLevel == 3) {
                mlevelNameTxt.setText(String.format(getResources().getString(R.string.level_current_name), String.valueOf(mcurrentLevel)));
            } else if (mcurrentLevel == 5) {
                mlevelNameTxt.setText(getResources().getString(R.string.review_questions_name));
            } else if (mcurrentLevel == 6) {
                mlevelNameTxt.setText(getResources().getString(R.string.added_questions_name));
            } else if (mcurrentLevel == 7) {
                mlevelNameTxt.setText(getResources().getString(R.string.reviewed_questions_name));
            }
        }
    }
}
