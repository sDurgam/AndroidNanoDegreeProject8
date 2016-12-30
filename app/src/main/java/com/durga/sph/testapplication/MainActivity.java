package com.durga.sph.testapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView my_recyclerView;
    MyRecyclerViewAdapter my_adapter;
    List<Recipe> my_recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        my_recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        my_recipes = new ArrayList<>();
        String path = "https://lh3.googleusercontent.com/F_6IVSoAr53icqCyX2pfW-vqscgHpuf_y7TL13_SKZl86mO76ih4yw7vG5j0cRET2H4=w300";
        my_recipes.add(new Recipe("a", path));
        my_recipes.add(new Recipe("b", path));
        my_recipes.add(new Recipe("c", path));
        my_recipes.add(new Recipe("d", path));
        my_recipes.add(new Recipe("e", path));
        my_adapter = new MyRecyclerViewAdapter(this, my_recipes);
    }

    public void onResume(){
        super.onResume();
        LinearLayoutManager lmanager = new LinearLayoutManager(this);
        my_recyclerView.setLayoutManager(lmanager);
        my_recyclerView.setAdapter(my_adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
