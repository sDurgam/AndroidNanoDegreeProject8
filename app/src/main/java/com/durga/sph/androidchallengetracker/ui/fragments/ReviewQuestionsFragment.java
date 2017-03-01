package com.durga.sph.androidchallengetracker.ui.fragments;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.durga.sph.androidchallengetracker.providers.MyProgressContract;
import com.durga.sph.androidchallengetracker.ui.listeners.IGetQuestionsInterface;
import com.durga.sph.androidchallengetracker.ui.listeners.IListener;
import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.network.ReviewQuestionsInterface;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.ui.RecylclerViewEndlessScrollListener;
import com.durga.sph.androidchallengetracker.ui.adapters.ReviewQuestionsAdapter;
import com.durga.sph.androidchallengetracker.ui.listeners.IOnReviewerItemClickListerner;
import com.durga.sph.androidchallengetracker.utils.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 1/30/17.
 */

public class ReviewQuestionsFragment extends BaseFragment {

    @Nullable
    @BindView(R.id.levelNameTxt)
    TextView screenTitle;
    @BindView(R.id.questionsView)
    RecyclerView reviewQuestionsView;
    List<String> myreviewedQuestions;
    ChildEventListener m_reviewQuestionsListener;

    public static ReviewQuestionsFragment newInstance() {
        Bundle args = new Bundle();
        ReviewQuestionsFragment fragment = new ReviewQuestionsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.listquestions_fragment, container, false);
        ButterKnife.bind(this, view);
        if (screenTitle != null) {
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
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... params) {
                //get questions which are reviewed
                List<String> myquestions = new ArrayList<>();
                String[] projectionFields = new String[]{MyProgressContract.MyProgressEntry._ID};
                String selection = MyProgressContract.MyProgressEntry.COLUMN_ISREVIEWED + Constants.PREP_STATEMENT;
                Cursor c = getActivity().getContentResolver().query(MyProgressContract.MyProgressEntry.CONTENT_URI, projectionFields, selection, new String[]{Constants.ONE}, null);
                c.moveToFirst();
                if(c.getCount() > 0) {
                    do {
                        myquestions.add(c.getString(0));
                    } while (c.moveToNext());
                }
                c.close();
                return myquestions;
            }

            @Override
            protected void onPostExecute(List<String> myquestions) {
                super.onPostExecute(myquestions);
                myreviewedQuestions = myquestions;
                setUpAdapter();
            }
        }.execute();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void setUpAdapter(){
        m_adapter = new ReviewQuestionsAdapter(this.getActivity(), new ArrayList<TrackerQuestion>(), super.userName(), new IOnReviewerItemClickListerner() {
            TrackerQuestion ques;
            @Override
            public void onisSpamClick(TrackerQuestion question, RadioGroup group) {
                if(!m_adapter.getisUpdating()) {
                    this.ques = question;
                    mFirebaseDatabaseInterface.markQuestionAsSpam(question.id, this);
                    m_adapter.setisUpdating(true);
                }else{
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.review_inprocess), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onisApprovedClick(TrackerQuestion question, RadioGroup group) {
                if(!m_adapter.getisUpdating()) {
                    this.ques = question;
                    mFirebaseDatabaseInterface.markQuestionAsApproved(question.id, String.format(Constants.LEVELFORMATTER, question.level), true, this);
                    m_adapter.setisUpdating(true);
                }else{
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.review_inprocess), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onisNotApprovedClick(TrackerQuestion question, RadioGroup group) {
                if(!m_adapter.getisUpdating()) {
                    this.ques = question;
                    mFirebaseDatabaseInterface.markQuestionAsApproved(question.id, String.format(Constants.LEVELFORMATTER, question.level), false, this);
                    m_adapter.setisUpdating(true);
                }else{
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.review_inprocess), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void isSuccess(boolean success) {
                if(success){
                    //mark this question as reviewed in db
                    addToDatabase(this.ques, MyProgressContract.MyProgressEntry.COLUMN_ISREVIEWED);
                    int pos = m_adapter.findPos(ques);
                    myreviewedQuestions.add(ques.id);
                    displayToastMessage(getResources().getString(R.string.review_completed));
                }
                else{
                    //show message that action could not be performed
                    displayToastMessage(getResources().getString(R.string.action_failed));
                }
                m_adapter.setisUpdating(false);
            }
        });
        final String key = String.format(Constants.LEVELFORMATTER, 1);
        LinearLayoutManager lmanager = new LinearLayoutManager(this.getActivity());
        reviewQuestionsView.setLayoutManager(lmanager);
        reviewQuestionsView.setAdapter(m_adapter);
        reviewQuestionsView.addOnScrollListener(new RecylclerViewEndlessScrollListener(this, lmanager){
            @Override
            public void onLoadMore(IGetQuestionsInterface callback) {
                if(m_lastQuestionId == null) return;
                mFirebaseDatabaseInterface.getMoreQuestions(m_username, callback, m_lastQuestionId, Constants.MAX_QUESTIONS_API_COUNT+1, myreviewedQuestions);
            }
        });
        m_username = mFirebaseAuth.getCurrentUser().getUid();
        mloadingBar.setVisibility(View.VISIBLE);
        mFirebaseDatabaseInterface.getQuestions(m_username, this,Constants.MAX_QUESTIONS_API_COUNT+1, myreviewedQuestions);
    }
}
