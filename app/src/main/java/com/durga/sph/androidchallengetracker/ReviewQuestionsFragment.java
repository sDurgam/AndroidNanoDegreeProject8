package com.durga.sph.androidchallengetracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.ui.adapters.BaseRecyclerViewAdapter;
import com.durga.sph.androidchallengetracker.ui.adapters.ReviewQuestionsAdapter;
import com.durga.sph.androidchallengetracker.ui.fragments.BaseFragment;
import com.durga.sph.androidchallengetracker.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 1/30/17.
 */

public class ReviewQuestionsFragment extends BaseFragment implements IGetQuestionsInterface{

    @Nullable @BindView(R.id.levelNameTxt)
    TextView screenTitle;
    @BindView(R.id.questionsView)
    RecyclerView reviewQuestionsView;
    ReviewQuestionsAdapter m_reviewquestionsAdapter;


    public static ReviewQuestionsFragment newInstance() {
        Bundle args = new Bundle();
        ReviewQuestionsFragment fragment = new ReviewQuestionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View  view = inflater.inflate(R.layout.listquestions_fragment, container, false);
        ButterKnife.bind(this, view);
        if(screenTitle != null){
            screenTitle.setText(getResources().getString(R.string.review_questions_name));
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        LinearLayoutManager lmanager = new LinearLayoutManager(this.getActivity());
        m_reviewquestionsAdapter = new ReviewQuestionsAdapter(this.getActivity(), new ArrayList<TrackerQuestion>(), super.userName());
        reviewQuestionsView.setLayoutManager(lmanager);
        reviewQuestionsView.setAdapter(m_reviewquestionsAdapter);
        mFirebaseDatabaseInterface.getQuestionsForReview(Constants.QUESTIONS,this);
    }

    //register for new questions
    @Override
    public void onPause() {
        mFirebaseDatabaseInterface.unregisterEventListener(Constants.QUESTIONS);
        super.onPause();
    }

    //register for new questions
    @Override
    public void onStart() {
        super.onStart();
        mFirebaseDatabaseInterface.registerEventListener(Constants.QUESTIONS);
    }

    @Override
    public void onQuestionsReady(List<TrackerQuestion> questionsList) {
        m_reviewquestionsAdapter.updateAdapter(questionsList);
    }
}
