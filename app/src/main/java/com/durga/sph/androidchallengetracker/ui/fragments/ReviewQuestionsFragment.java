package com.durga.sph.androidchallengetracker.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.durga.sph.androidchallengetracker.IGetQuestionsInterface;
import com.durga.sph.androidchallengetracker.IListener;
import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.ui.adapters.ReviewQuestionsAdapter;
import com.durga.sph.androidchallengetracker.ui.listeners.IOnItemClickListener;
import com.durga.sph.androidchallengetracker.ui.listeners.IOnReviewerItemClickListerner;
import com.durga.sph.androidchallengetracker.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 1/30/17.
 */

public class ReviewQuestionsFragment extends BaseFragment implements IGetQuestionsInterface, IListener {

    @Nullable @BindView(R.id.levelNameTxt)
    TextView screenTitle;
    @BindView(R.id.questionsView)
    RecyclerView reviewQuestionsView;
    ReviewQuestionsAdapter m_reviewquestionsAdapter;


    public static ReviewQuestionsFragment newInstance() {
        Bundle args = new Bundle();
        ReviewQuestionsFragment fragment = new ReviewQuestionsFragment();
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
        m_reviewquestionsAdapter = new ReviewQuestionsAdapter(this.getActivity(), new ArrayList<TrackerQuestion>(), super.userName(), new IOnReviewerItemClickListerner() {
            int pos;
            @Override
            public void onisSpamClick(TrackerQuestion question, int position) {
                pos = position;
                mFirebaseDatabaseInterface.markQuestionAsSpam(question.id, this);
            }

            @Override
            public void onisApprovedClick(TrackerQuestion question, String user, int position) {
                pos = position;
                mFirebaseDatabaseInterface.updateReviewersForQuestion(user, question.id, true, this);
            }

            @Override
            public void onisNotApprovedClick(TrackerQuestion question, String user, int position) {
                pos = position;
                mFirebaseDatabaseInterface.updateReviewersForQuestion(user, question.id, false, this);
            }

            @Override
            public void isSuccess(boolean success) {
                if(success){
                    m_reviewquestionsAdapter.removeItem(pos);
                }
                else{
                       //display message
                    Toast.makeText(getActivity(), "Action could not be performed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        reviewQuestionsView.setLayoutManager(lmanager);
        reviewQuestionsView.setAdapter(m_reviewquestionsAdapter);
        mFirebaseDatabaseInterface.getQuestionsForReview(Constants.QUESTIONS, super.userName(), this);
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
        mFirebaseDatabaseInterface.registerQuestionsByLevelEventListener(Constants.QUESTIONS, this, 1);
    }

    @Override
    public void onQuestionsReady(List<TrackerQuestion> questionsList) {
        m_reviewquestionsAdapter.updateAdapter(questionsList);
    }

    @Override
    public void add(TrackerQuestion question) {
        m_reviewquestionsAdapter.addItem(question);
    }
}
