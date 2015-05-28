package com.ansijaxapp.udacitylol.udacitylol;


import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ansijaxapp.udacitylol.udacitylol.data.Game;
import com.ansijaxapp.udacitylol.udacitylol.database.Contract;


public class MatchDetailFragment extends Fragment implements  View.OnClickListener {


    Game match;
    public MatchDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        match = getArguments().getParcelable("match");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView= inflater.inflate(R.layout.fragment_match_deatil, container, false);
        ImageView championeImage =(ImageView) rootView.findViewById(R.id.champion_icon);
        TextView summonerName= (TextView) rootView.findViewById(R.id.summonerName);
        TextView championName= (TextView) rootView.findViewById(R.id.championName);
        TextView result = (TextView) rootView.findViewById(R.id.result);
        TextView gameMode = (TextView) rootView.findViewById(R.id.gameMode);
        TextView kda = (TextView) rootView.findViewById(R.id.kda);
        TextView cs = (TextView) rootView.findViewById(R.id.cs);
        TextView mapSite = (TextView) rootView.findViewById(R.id.mapSite);
        TextView gameModeSub = (TextView) rootView.findViewById(R.id.gameModeSub);
        TextView gold = (TextView) rootView.findViewById(R.id.gold);
        TextView ward = (TextView) rootView.findViewById(R.id.ward);
        TextView damage = (TextView) rootView.findViewById(R.id.damage);
        TextView lvl = (TextView) rootView.findViewById(R.id.lvl);
        Button saveButton = (Button) rootView.findViewById(R.id.save_to_favorite);


        if(saveButton!=null) {
            saveButton.setOnClickListener(this);
        }

        if(match!=null) {
            championeImage.setImageBitmap(match.getChampion());
            summonerName.setText(match.getSummonerName());
            championName.setText(match.getChampionName());
            lvl.setText("LVL: " + match.getLevel());
            if (match.getWin()) {
                result.setText("Victory");
            } else {
                result.setText("Defeat");
            }

            gameMode.setText("Game mode: " + match.getGameMode());
            gameModeSub.setText("Game type: " + match.getGameModeSub());
            kda.setText("K/D/A: " + match.getKill() + "/" + match.getDeath() + "/" + match.getAssists());
            cs.setText("Minion Killed: " + match.getCs());
            if (match.getMapSite() == 100) {
                mapSite.setText("Faction: Blue");
            } else
                mapSite.setText("Faction: Purple");

            gold.setText("Gold earned: " + match.getGold());
            damage.setText("Damage dealth: " + match.getDamage());
            ward.setText("Ward placed: " + match.getWard());
        }

        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_to_favorite:
                //TODO check if exists in DB
                Log.d("DETAIL", "button pressed");
                Cursor cursor=getActivity().getContentResolver().query(
                        Contract.FavoriteEntry.CONTENT_URI,
                        new String[]{Contract.FavoriteEntry.COLUMN_ID},
                        Contract.FavoriteEntry.COLUMN_ID + " = ? ",
                        new String[]{match.getMatchID().toString()},
                        null);

                if(cursor.moveToFirst()) {
                    //we Have a result
                    Toast.makeText(getActivity(), "Match already on Favorite ", Toast.LENGTH_LONG).show();

                }
                else {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Contract.FavoriteEntry.COLUMN_ID, match.getMatchID());
                    contentValues.put(Contract.FavoriteEntry.COLUMN_ASSIST, match.getAssists());
                    contentValues.put(Contract.FavoriteEntry.COLUMN_CHAMPION, match.getChampionName());
                    contentValues.put(Contract.FavoriteEntry.COLUMN_SUMMONER, match.getSummonerName());
                    contentValues.put(Contract.FavoriteEntry.COLUMN_GOLD, match.getGold());
                    contentValues.put(Contract.FavoriteEntry.COLUMN_KILL, match.getKill());
                    contentValues.put(Contract.FavoriteEntry.COLUMN_DEATH, match.getDeath());
                    contentValues.put(Contract.FavoriteEntry.COLUMN_LEVEL, match.getLevel());
                    contentValues.put(Contract.FavoriteEntry.COLUMN_DAMAGE, match.getDamage());
                    contentValues.put(Contract.FavoriteEntry.COLUMN_CS, match.getCs());
                    contentValues.put(Contract.FavoriteEntry.COLUMN_GAME_MODE, match.getGameMode());
                    contentValues.put(Contract.FavoriteEntry.COLUMN_GAME_MODE_SUB, match.getGameModeSub());
                    contentValues.put(Contract.FavoriteEntry.COLUMN_RESULT, match.getWin());
                    getActivity().getContentResolver().insert(Contract.FavoriteEntry.CONTENT_URI, contentValues);
                    Toast.makeText(getActivity(), "added to favorites!! OP GG!", Toast.LENGTH_LONG).show();
                }
                cursor.close();





        }
    }
}
