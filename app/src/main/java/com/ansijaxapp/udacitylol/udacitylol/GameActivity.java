package com.ansijaxapp.udacitylol.udacitylol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ansijaxapp.udacitylol.udacitylol.service.LolService;


public class GameActivity extends ActionBarActivity {

    private String RIGHT_TAG = "DETAIL";
    private boolean mTwoPane;
    private static final String TAG_DEBUG = GameActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.datail_activity_game) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                MatchDetailFragment rightFragment = new MatchDetailFragment();
                Bundle rightBundle = new Bundle();
                rightBundle.putInt("start", 0);
                rightFragment.setArguments(rightBundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.datail_activity_game, rightFragment, RIGHT_TAG)
                        .commit();
            } else
                mTwoPane = false;
        }

        Bundle bundle=new Bundle();
        bundle.putBoolean("mTwoPane", mTwoPane);
        if (savedInstanceState == null) {
            GameActivityFragment fragment = new GameActivityFragment();

            bundle.putString("summonerName", getIntent().getStringExtra("summonerName"));
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_game, fragment).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==android.R.id.home){
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}




