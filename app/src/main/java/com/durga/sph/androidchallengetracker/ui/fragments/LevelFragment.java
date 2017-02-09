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

import com.durga.sph.androidchallengetracker.IGetQuestionsInterface;
import com.durga.sph.androidchallengetracker.ui.adapters.BaseRecyclerViewAdapter;
import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 1/2/17.
 */

public class LevelFragment extends BaseFragment implements IGetQuestionsInterface
        //implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, Callback<List<Object>>
{
    SimpleCursorAdapter mAdapter;
    public String TAG;
    int mcurrentLevel = 1;
    @Nullable @BindView(R.id.levelNameTxt)
    TextView mlevelNameTxt;
    @BindView(R.id.questionsView)
    RecyclerView m_recyclerView;
    BaseRecyclerViewAdapter m_adapter;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.listquestions_fragment, container, false);
        ButterKnife.bind(this, view);
        m_adapter = new BaseRecyclerViewAdapter(this.getActivity(), new ArrayList<TrackerQuestion>());
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
        mFirebaseDatabaseInterface.getNewQuestionsByLevel(Constants.QUESTIONS, Constants.LEVEL, String.valueOf(mcurrentLevel), this);
        mFirebaseDatabaseInterface.registerEventListener(Constants.QUESTIONS);
        //AndroidRecipeService adapter = AndroidRecipeRetorfitAdapter.getRestService(this.getActivity());
        //Call<List<Object>> call = adapter.listRepos("sDurgam");
        //call.enqueue(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        LinearLayoutManager lmanager = new LinearLayoutManager(this.getActivity());
        m_recyclerView.setLayoutManager(lmanager);
        m_recyclerView.setAdapter(m_adapter);
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

    @Override
    public void onQuestionsReady(List<TrackerQuestion> questionsList) {
        m_adapter.updateAdapter(questionsList);
    }

//    @Override
//    public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
//        Log.d(TAG, response.toString());
//    }
//
//    @Override
//    public void onFailure(Call<List<Object>> call, Throwable t) {
//        Log.e(TAG, t.toString());
//    }
//
//    @Override
//    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        return null;
//    }
//
//    @Override
//    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
//        mAdapter.swapCursor(data);
//    }
//
//    @Override
//    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
//        mAdapter.swapCursor(null);
//    }
}
