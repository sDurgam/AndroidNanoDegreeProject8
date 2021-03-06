package com.durga.sph.androidchallengetracker.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.durga.sph.androidchallengetracker.BuildConfig;
import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.network.FirebaseDatabaseInterface;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.providers.MyProgressContract;
import com.durga.sph.androidchallengetracker.ui.listeners.OnQuestionAddedListener;
import com.durga.sph.androidchallengetracker.utils.Constants;
import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by root on 1/30/17.
 */

public class NewQuestionFragment extends BaseFragment implements OnQuestionAddedListener {

    @BindView(R.id.newQuestionEditTxt)
    EditText newQuestionEditTxt;
    @BindView(R.id.newquestionlevelRadioGroup)
    RadioGroup newquestionlevelRadioGroup;
    @BindView(R.id.addQuesSummaryLabel)
    TextView addQuesSummaryLabel;
    @BindString(R.string.submitted_for_review)
    String submittedForReview;
    @BindString(R.string.question_empty_error)
    String quesEmptyError;
    @BindString(R.string.level_empty_error)
    String levelEmptyError;
    String mUserName;
    TrackerQuestion mNewQuestion;
    String mLevel;

    public NewQuestionFragment() {

    }

    public static NewQuestionFragment newInstance() {
        Bundle args = new Bundle();
        NewQuestionFragment fragment = new NewQuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabaseInterface = new FirebaseDatabaseInterface() {
        };
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.newquestion_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.addquesSubmitReviewBtn)
    public void addquesSubmitReviewClick(View view) {
        resetErrorMessage();
        //update database and show edit question fragment
        boolean isValid = validateQuestion();
        if (isValid) {
            //save to firebase database
            saveQuestioninDB();
            displayErrorMessage(submittedForReview);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    resetFields();
                }
            }, 500);
        }
    }

    private void saveQuestioninDB() {
        newQuestionEditTxt.setContentDescription(newQuestionEditTxt.getText());
        String title = newQuestionEditTxt.getText().toString();
        String userId = mFirebaseAuth.getCurrentUser().getUid();
        mLevel = String.format(Constants.LEVELFORMATTER, getLevel());
        // Generate a reference to a new location and add some data using push()
        //id;
        mNewQuestion = new TrackerQuestion(title, userId, getLevel());
        if (BuildConfig.DEBUG) {
            mFirebaseDatabaseInterface.addNewQuestionToLevel(mNewQuestion, this);
        } else {
            mFirebaseDatabaseInterface.addNewQuestion(mNewQuestion, this);
        }
    }

    private int getLevel() {
        int id = newquestionlevelRadioGroup.getCheckedRadioButtonId();
        if (id == R.id.radiobutton_level1) return 1;
        if (id == R.id.radiobutton_level2) return 2;
        return 3;
    }

    private boolean validateQuestion() {
        boolean isValidQuestion = true;
        if (newQuestionEditTxt.getText().toString().isEmpty()) {
            isValidQuestion = false;
            displayErrorMessage(quesEmptyError);
        } else if (newquestionlevelRadioGroup.getCheckedRadioButtonId() == -1) {
            isValidQuestion = false;
            displayErrorMessage(levelEmptyError);
        }
        return isValidQuestion;
    }

    private void resetFields() {
        newQuestionEditTxt.setText("");
        newquestionlevelRadioGroup.clearCheck();
        resetErrorMessage();
    }

    private void displayErrorMessage(String message) {
        addQuesSummaryLabel.setText(message);
    }

    private void resetErrorMessage() {
        addQuesSummaryLabel.setText(Constants.BLANKSTR);
    }

    //Add under 'MyAdded' questions in the local database
    @Override
    public void issuccess(boolean success, String questionId) {
        if (success && questionId != null) {
            //add this new question in 'MyAdded Questions' in database
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, questionId);
            logFirebaseAnalyticsEvent(getResources().getString(R.string.isadded), bundle);
            mNewQuestion.id = questionId;
            addToDatabase(mNewQuestion, MyProgressContract.MyProgressEntry.COLUMN_ISADDED);
        } else {
            Log.e(mTag, getResources().getString(R.string.action_failed) + getResources().getString(R.string.isadded));
        }
    }
}
