package com.durga.sph.androidchallengetracker.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by root on 1/30/17.
 */

public class NewQuestionFragment extends Fragment {

    @BindView(R.id.newQuestionEditTxt)
    EditText newQuestionEditTxt;
    @BindView(R.id.newquestionlevelRadioGroup)
    RadioGroup newquestionlevelRadioGroup;
    @BindView(R.id.addQuesSummaryLabel)
    TextView addQuesSummaryLabel;
    @BindString(R.string.submitted_for_review) String submittedForReview;
    @BindString(R.string.question_empty_error) String quesEmptyError;
    @BindString(R.string.level_empty_error) String levelEmptyError;
    FirebaseDatabase mFireBaseDatabase;
    DatabaseReference mDatabaseReference;
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    ChildEventListener mChildEventListener;
    String mUsername;

    public NewQuestionFragment(){

    }
    public static NewQuestionFragment newInstance() {
        Bundle args = new Bundle();
        NewQuestionFragment fragment = new NewQuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFireBaseDatabase.getReference().child("Questions");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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
        mDatabaseReference.addChildEventListener(mChildEventListener);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){

                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onPause() {
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
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
    public void addquesSubmitReviewClick(View view){
        resetErrorMessage();
        //update database and show edit question fragment
        // EditQuestionFragment editQuestionFragment = EditQuestionFragment.newInstance("abc");
        // getFragmentManager().beginTransaction().replace(R.id.main_frameLayout, editQuestionFragment).commit();
        boolean isValid = validateQuestion();
        if(isValid) {
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

    private void saveQuestioninDB(){
        String title = newQuestionEditTxt.getText().toString();
        String userId  = mFirebaseAuth.getCurrentUser().getUid();
        int level = getLevel();
        // Generate a reference to a new location and add some data using push()
        DatabaseReference pushedQuestionRef = mDatabaseReference.push();
        // Get the unique ID generated by a push()
        String questionId = pushedQuestionRef.getKey();
        TrackerQuestion question = new TrackerQuestion(questionId, title, userId, level);
        mDatabaseReference.setValue(question);
    }

    private int getLevel(){
        int id = newquestionlevelRadioGroup.getCheckedRadioButtonId();
        if(id == R.id.radiobutton_level1) return 1;
        if(id == R.id.radiobutton_level2) return 2;
        return 3;
    }

    private boolean validateQuestion(){
        boolean isValidQuestion = true;
        if(newQuestionEditTxt.getText().toString().isEmpty()){
            isValidQuestion = false;
            displayErrorMessage(quesEmptyError);
        }
        else if(newquestionlevelRadioGroup.getCheckedRadioButtonId() == -1){
            isValidQuestion = false;
            displayErrorMessage(levelEmptyError);
        }
        return isValidQuestion;
    }

    private void resetFields(){
        newQuestionEditTxt.setText("");
        newquestionlevelRadioGroup.clearCheck();
        resetErrorMessage();
    }

    private void displayErrorMessage(String message){
        addQuesSummaryLabel.setText(message);
    }

    private void resetErrorMessage(){
        addQuesSummaryLabel.setText(Constants.BLANKSTR);
    }
}