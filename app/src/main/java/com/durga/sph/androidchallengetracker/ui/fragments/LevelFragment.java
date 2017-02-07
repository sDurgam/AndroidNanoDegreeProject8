package com.durga.sph.androidchallengetracker.ui.fragments;

import android.app.Fragment;
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

import com.durga.sph.androidchallengetracker.MyRecyclerViewAdapter;
import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.Recipe;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 1/2/17.
 */

public class LevelFragment extends BaseFragment
        //implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, Callback<List<Object>>
{
    SimpleCursorAdapter mAdapter;
    List<Recipe> my_recipes;
    public String TAG;
    int mcurrentLevel = 1;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mMessgaesDatabaseReference;
    //https://android-challenge-tracker.firebaseio.com/

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

    


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        my_recipes = new ArrayList<>();
        String path = "https://lh3.googleusercontent.com/F_6IVSoAr53icqCyX2pfW-vqscgHpuf_y7TL13_SKZl86mO76ih4yw7vG5j0cRET2H4=w300";
        my_recipes.add(new Recipe("a", path));
        my_recipes.add(new Recipe("b", path));
        my_recipes.add(new Recipe("c", path));
        my_recipes.add(new Recipe("d", path));
        my_recipes.add(new Recipe("e", path));
        m_adapter = new MyRecyclerViewAdapter(this.getActivity(), my_recipes);
        Object levelargs = getArguments().get(getResources().getString(R.string.level));
        if(levelargs != null)
            mcurrentLevel = (int)levelargs;
        //mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, null, new String[]{"test1", "test2"},
        //        new int[]{android.R.id.text1, android.R.id.text2}, 0);
        //setListAdapter(mAdapter);
        //getLoaderManager().initLoader(0, null, this);
        //mFirebaseDatabase = FirebaseDatabase.getInstance();
        //mMessgaesDatabaseReference = mFirebaseDatabase.getReference();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        setScreenName();
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
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //set the current level as screen name
    public void setScreenName(){
       if(mcurrentLevel == 1 || mcurrentLevel == 2 || mcurrentLevel == 3) {
           mlevelNameTxt.setText(String.format(getResources().getString(R.string.level_current_name), String.valueOf(mcurrentLevel)));
       }
       else if(mcurrentLevel == 5){
           mlevelNameTxt.setText(getResources().getString(R.string.review_questions_name));
       }
        else if(mcurrentLevel == 6){
           mlevelNameTxt.setText(getResources().getString(R.string.added_questions_name));
       }
        else if(mcurrentLevel == 7){
           mlevelNameTxt.setText(getResources().getString(R.string.reviewed_questions_name));
       }
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
