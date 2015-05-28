package com.ansijaxapp.udacitylol.udacitylol;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;


public class FavoriteActivity extends ActionBarActivity implements FavoriteListFragment.Callback {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        if(findViewById(R.id.activity_favorite_detail)!=null){

            mTwoPane=true;
            if(savedInstanceState==null){
                FavoriteDetailFragment rightFragment = new FavoriteDetailFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_favorite_detail,rightFragment,"RIGHT_TAG").commit();
            }
        }else
        mTwoPane=false;
        FavoriteListFragment fragment = new FavoriteListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_favorite,fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
       // (FavoriteDetailFragment)getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
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

    @Override
    public void onItemSelected(Long gameID) {
        if(mTwoPane){
            Bundle bundle = new Bundle();
            bundle.putLong("matchID",gameID);
            FavoriteDetailFragment rightFragment = new FavoriteDetailFragment();
            rightFragment.setArguments(bundle);
            FrameLayout frameLT= (FrameLayout) findViewById(R.id.activity_favorite_detail);
            frameLT.setVisibility(View.VISIBLE);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_favorite_detail, rightFragment, "RIGHTTAG")
                    .commit();

        }else {
            Intent intent = new Intent(this, FavoriteDetail.class);
            intent.putExtra("matchID", gameID);
            startActivity(intent);
        }
    }
}
