package com.durga.sph.androidchallengetracker.ui.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.network.LevelQuestionsInterface;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.providers.MyProgressContract;
import com.durga.sph.androidchallengetracker.ui.RecylclerViewEndlessScrollListener;
import com.durga.sph.androidchallengetracker.ui.adapters.LevelRecyclerViewAdapter;
import com.durga.sph.androidchallengetracker.ui.listeners.GetQuestionsInterface;
import com.durga.sph.androidchallengetracker.ui.listeners.OnLevelItemClickListerner;
import com.durga.sph.androidchallengetracker.utils.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 1/2/17.
 */

public class LevelFragment extends BaseFragment {
    int mCurrentLevel = 1;
    @Nullable
    @BindView(R.id.levelNameTxt)
    TextView levelNameTxt;
    @BindView(R.id.questionsView)
    RecyclerView recyclerView;
    List<String> mysolvedQuestions;
    ChildEventListener mLevelListener;

    public LevelFragment() {
        mTag = getClass().getName();
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
        mLevelListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TrackerQuestion question = dataSnapshot.getValue(TrackerQuestion.class);
                mAdapter.addItem(question);
                //update approved column if the question is added by the user
                updateDatabase(question.id, MyProgressContract.MyProgressEntry.COLUMN_ISAPPROVED);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

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
        View view = inflater.inflate(R.layout.listquestions_fragment, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new LevelRecyclerViewAdapter(this.getActivity(), new ArrayList<TrackerQuestion>(), mUsername, new OnLevelItemClickListerner() {
            int pos;

            @Override
            public void onisSolvedClick(String questionId) {
                TrackerQuestion question = mAdapter.getQuestionById(questionId);
                addToDatabase(question, MyProgressContract.MyProgressEntry.COLUMN_ISSOLVED);
                mAdapter.removeItem(mAdapter.findPos(question));
                mAdapter.setisUpdating(false);
            }
        });
        Object levelargs = getArguments().get(getResources().getString(R.string.level));
        if (levelargs != null)
            mCurrentLevel = (int) levelargs;
        mFirebaseDatabaseInterface = new LevelQuestionsInterface(String.format(Constants.LEVELFORMATTER, mCurrentLevel));
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
    }

    @Override
    public void onResume() {
        super.onResume();
        //mFirebaseDatabaseInterface.registerEventListener(mLevelListener);
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... params) {
                //get questions of matching level which are not solved
                List<String> myquestions = new ArrayList<>();
                String[] projectionFields = new String[]{MyProgressContract.MyProgressEntry._ID};
                String selection = MyProgressContract.MyProgressEntry.COLUMN_ISSOLVED + "=?";
                Cursor c = getActivity().getContentResolver().query(MyProgressContract.MyProgressEntry.CONTENT_URI, projectionFields, selection, new String[]{"1"}, null);
                if (c.getCount() > 0) {
                    c.moveToFirst();
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
                mysolvedQuestions = myquestions;
                setUpAdapter();
            }
        }.execute();
    }

    private void setUpAdapter() {

        LinearLayoutManager lmanager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(lmanager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecylclerViewEndlessScrollListener(this, lmanager) {
            @Override
            public void onLoadMore(GetQuestionsInterface callback) {
                if (mLastQuestionId == null) return;
                mFirebaseDatabaseInterface.getMoreQuestions(null, callback, mLastQuestionId, Constants.MAX_QUESTIONS_API_COUNT + 1, mysolvedQuestions);
            }
        });
        if (mFirebaseAuth.getCurrentUser() != null) {
            mUsername = mFirebaseAuth.getCurrentUser().getUid();
            loadingBar.setVisibility(View.VISIBLE);
            mFirebaseDatabaseInterface.getQuestions(null, this, Constants.MAX_QUESTIONS_API_COUNT + 1, mysolvedQuestions);
        }
    }

    @Override
    public void onPause() {
        // mFirebaseDatabaseInterface.unregisterEventListener(mLevelListener);
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //set the current level as screen name
    public void setScreenName() {
        if (levelNameTxt != null) {
            if (mCurrentLevel == 1 || mCurrentLevel == 2 || mCurrentLevel == 3) {
                levelNameTxt.setText(String.format(getResources().getString(R.string.level_current_name), String.valueOf(mCurrentLevel)));
            } else if (mCurrentLevel == 5) {
                levelNameTxt.setText(getResources().getString(R.string.review_questions_name));
            } else if (mCurrentLevel == 6) {
                levelNameTxt.setText(getResources().getString(R.string.added_questions_name));
            } else if (mCurrentLevel == 7) {
                levelNameTxt.setText(getResources().getString(R.string.reviewed_questions_name));
            }
        }
    }

    @Override
    public void onQuestionsReady(final List<TrackerQuestion> questionsList) {
        super.onQuestionsReady(questionsList);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (questionsList != null) {
                    TrackerQuestion question = null;
                    for (int i = 0; i < questionsList.size(); i++) {
                        question = questionsList.get(i);
                        if (question.userId.equals(mUsername)) {
                            updateDatabase(question.id, MyProgressContract.MyProgressEntry.COLUMN_ISAPPROVED);
                        }
                    }
                }
                return null;
            }
        }.execute();
    }
}
