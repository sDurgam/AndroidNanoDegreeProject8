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

import com.durga.sph.androidchallengetracker.ui.listeners.IGetQuestionsInterface;
import com.durga.sph.androidchallengetracker.ui.listeners.IListener;
import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.network.ReviewQuestionsInterface;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.ui.RecylclerViewEndlessScrollListener;
import com.durga.sph.androidchallengetracker.ui.adapters.ReviewQuestionsAdapter;
import com.durga.sph.androidchallengetracker.ui.listeners.IOnReviewerItemClickListerner;
import com.durga.sph.androidchallengetracker.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 1/30/17.
 */

public class ReviewQuestionsFragment extends BaseFragment implements IListener {

    @Nullable @BindView(R.id.levelNameTxt)
    TextView screenTitle;
    @BindView(R.id.questionsView)
    RecyclerView reviewQuestionsView;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabaseInterface = new ReviewQuestionsInterface();
    }

    @Override
    public void onResume() {
        super.onResume();
        LinearLayoutManager lmanager = new LinearLayoutManager(this.getActivity());
        m_adapter = new ReviewQuestionsAdapter(this.getActivity(), new ArrayList<TrackerQuestion>(), super.userName(), new IOnReviewerItemClickListerner() {
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
                    m_adapter.removeItem(pos);
                }
                else{
                       //display message
                    Toast.makeText(getActivity(), "Action could not be performed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        reviewQuestionsView.setLayoutManager(lmanager);
        reviewQuestionsView.setAdapter(m_adapter);
        reviewQuestionsView.addOnScrollListener(new RecylclerViewEndlessScrollListener(this, lmanager){
            @Override
            public void onLoadMore(IGetQuestionsInterface callback) {
                if(m_lastQuestionId == null) return;
                mFirebaseDatabaseInterface.getMoreQuestions(Constants.QUESTIONS, m_username, callback, m_lasttimeStamp, m_lastQuestionId, Constants.MAX_QUESTIONS_API_COUNT+1);
            }
        });
        m_username = mFirebaseAuth.getCurrentUser().getUid();
        mFirebaseDatabaseInterface.getQuestions(Constants.QUESTIONS, super.userName(), this,Constants.MAX_QUESTIONS_API_COUNT+1);
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
    public void add(TrackerQuestion question) {
        m_adapter.addItem(question);
    }
}
