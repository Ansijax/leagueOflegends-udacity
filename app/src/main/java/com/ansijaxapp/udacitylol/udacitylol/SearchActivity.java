package com.ansijaxapp.udacitylol.udacitylol;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ansijaxapp.udacitylol.udacitylol.service.LolService;


public class SearchActivity extends ActionBarActivity {

    Button searchButton;
    EditText summonerNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }




    @Override
    protected void onResume() {
        super.onResume();


    }

    public boolean checkConnection(){
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(this.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public void startSearch(View view){
        summonerNameText = (EditText) findViewById(R.id.inputSummoneName_Text);
        String summonerToSearch = summonerNameText.getText().toString().trim();
        if (summonerToSearch.matches("")) {
            Toast.makeText(this, "You did not enter a summoner name!! FIX EUW!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!checkConnection()){
            Toast.makeText(this, "Too match lag to play!!Please check your internet connection!!", Toast.LENGTH_SHORT).show();
            return;
        }



        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("summonerName",summonerToSearch);
        startActivity(intent);

    }

    public void goToFavorite(View view){
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
    }

}
