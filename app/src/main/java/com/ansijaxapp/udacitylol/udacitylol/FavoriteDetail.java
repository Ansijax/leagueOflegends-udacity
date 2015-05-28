package com.ansijaxapp.udacitylol.udacitylol;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ansijaxapp.udacitylol.udacitylol.R;

public class FavoriteDetail extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_detail);
        FavoriteDetailFragment fragment = new FavoriteDetailFragment();
        Bundle bundle= new Bundle();
        bundle.putLong("matchID", getIntent().getExtras().getLong("matchID"));
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_favorite_detail,fragment).commit();
    }


}
