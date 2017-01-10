package com.durga.sph.androidchallengetracker;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 1/2/17.
 */

public class LevelFragment extends ListFragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, Callback<List<Object>>
{
   // @BindView(R.id.questionsView) RecyclerView my_recyclerView;
   // MyRecyclerViewAdapter my_adapter;
    SimpleCursorAdapter mAdapter;
    List<Recipe> my_recipes;
    public String TAG;

    public LevelFragment(){
        TAG = getClass().getName();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.level_fragment, container, false);
        ButterKnife.bind(this, view);
        my_recipes = new ArrayList<>();
        String path = "https://lh3.googleusercontent.com/F_6IVSoAr53icqCyX2pfW-vqscgHpuf_y7TL13_SKZl86mO76ih4yw7vG5j0cRET2H4=w300";
        my_recipes.add(new Recipe("a", path));
        my_recipes.add(new Recipe("b", path));
        my_recipes.add(new Recipe("c", path));
        my_recipes.add(new Recipe("d", path));
        my_recipes.add(new Recipe("e", path));
       // my_adapter = new MyRecyclerViewAdapter(this.getActivity(), my_recipes);
        mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, null, new String[]{"test1", "test2"},
                new int[]{android.R.id.text1, android.R.id.text2}, 0);
        setListAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
       // AndroidRecipeService adapter = AndroidRecipeRetorfitAdapter.getRestService(this.getActivity());
        //Call<List<Object>> call = adapter.listRepos("sDurgam");
        //call.enqueue(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        LinearLayoutManager lmanager = new LinearLayoutManager(this.getActivity());
        //my_recyclerView.setLayoutManager(lmanager);
        //my_recyclerView.setAdapter(my_adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
        Log.d(TAG, response.toString());
    }

    @Override
    public void onFailure(Call<List<Object>> call, Throwable t) {
        Log.e(TAG, t.toString());
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
