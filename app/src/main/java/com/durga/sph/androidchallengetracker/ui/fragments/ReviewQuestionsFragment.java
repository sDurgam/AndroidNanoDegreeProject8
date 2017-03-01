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
import com.durga.sph.androidchallengetracker.ui.listeners.GetQuestionsInterface;
import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.network.ReviewQuestionsInterface;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.ui.RecylclerViewEndlessScrollListener;
import com.durga.sph.androidchallengetracker.ui.adapters.ReviewQuestionsAdapter;
import com.durga.sph.androidchallengetracker.ui.listeners.OnReviewerItemClickListerner;
import com.durga.sph.androidchallengetracker.utils.Constants;
import com.google.firebase.database.ChildEventListener;

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
    ChildEventListener reviewQuestionsListener;

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
        firebaseDatabaseInterface = new ReviewQuestionsInterface();
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
        adapter = new ReviewQuestionsAdapter(this.getActivity(), new ArrayList<TrackerQuestion>(), super.userName(), new OnReviewerItemClickListerner() {
            TrackerQuestion ques;
            @Override
            public void onisSpamClick(TrackerQuestion question, RadioGroup group) {
                if(!adapter.getisUpdating()) {
                    this.ques = question;
                    firebaseDatabaseInterface.markQuestionAsSpam(question.id, this);
                    adapter.setisUpdating(true);
                }else{
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.review_inprocess), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onisApprovedClick(TrackerQuestion question, RadioGroup group) {
                if(!adapter.getisUpdating()) {
                    this.ques = question;
                    firebaseDatabaseInterface.markQuestionAsApproved(question.id, String.format(Constants.LEVELFORMATTER, question.level), true, this);
                    adapter.setisUpdating(true);
                }else{
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.review_inprocess), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onisNotApprovedClick(TrackerQuestion question, RadioGroup group) {
                if(!adapter.getisUpdating()) {
                    this.ques = question;
                    firebaseDatabaseInterface.markQuestionAsApproved(question.id, String.format(Constants.LEVELFORMATTER, question.level), false, this);
                    adapter.setisUpdating(true);
                }else{
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.review_inprocess), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void isSuccess(boolean success) {
                if(success){
                    //mark this question as reviewed in db
                    addToDatabase(this.ques, MyProgressContract.MyProgressEntry.COLUMN_ISREVIEWED);
                    int pos = adapter.findPos(ques);
                    myreviewedQuestions.add(ques.id);
                    adapter.removeItem(pos);
                    displayToastMessage(getResources().getString(R.string.review_completed));
                }
                else{
                    //show message that action could not be performed
                    displayToastMessage(getResources().getString(R.string.action_failed));
                }
                adapter.setisUpdating(false);
            }
        });
        final String key = String.format(Constants.LEVELFORMATTER, 1);
        LinearLayoutManager lmanager = new LinearLayoutManager(this.getActivity());
        reviewQuestionsView.setLayoutManager(lmanager);
        reviewQuestionsView.setAdapter(adapter);
        reviewQuestionsView.addOnScrollListener(new RecylclerViewEndlessScrollListener(this, lmanager){
            @Override
            public void onLoadMore(GetQuestionsInterface callback) {
                if(lastQuestionId == null) return;
                firebaseDatabaseInterface.getMoreQuestions(username, callback, lastQuestionId, Constants.MAX_QUESTIONS_API_COUNT+1, myreviewedQuestions);
            }
        });
        username = firebaseAuth.getCurrentUser().getUid();
        loadingBar.setVisibility(View.VISIBLE);
        firebaseDatabaseInterface.getQuestions(username, this,Constants.MAX_QUESTIONS_API_COUNT+1, myreviewedQuestions);
    }
}
