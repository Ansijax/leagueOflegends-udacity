package com.ansijaxapp.udacitylol.udacitylol;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ansijaxapp.udacitylol.udacitylol.database.Contract;
import com.ansijaxapp.udacitylol.udacitylol.database.FavoriteDbHelper;
import com.ansijaxapp.udacitylol.udacitylol.database.FavoriteProvider;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,View.OnClickListener  {



    public static final int _ID=0;
    public static final int COLUMN_ID=1;
    public static final int COLUMN_KILL=2;
    public static final int COLUMN_DEATH=3;
    public static final int COLUMN_ASSIST=4;
    public static final int COLUMN_SUMMONER=5;
    public static final int COLUMN_CHAMPION=6;
    public static final int COLUMN_RESULT=7;
    public static final int COLUMN_GAME_MODE_SUB=8;
    public static final int COLUMN_GAME_MODE=9;
    public static final int COLUMN_LEVEL=10;
    public static final int COLUMN_GOLD=11;
    public static final int COLUMN_DAMAGE=12;
    public static final int COLUMN_CS=13;
    private int mPosition = ListView.INVALID_POSITION;
    private Long GAMEID;

    private final String[] FAVORITE_COLUMNS= {
            Contract.FavoriteEntry._ID,
            Contract.FavoriteEntry.COLUMN_ID,
            Contract.FavoriteEntry.COLUMN_KILL,
            Contract.FavoriteEntry.COLUMN_DEATH,
            Contract.FavoriteEntry.COLUMN_ASSIST,
            Contract.FavoriteEntry.COLUMN_SUMMONER,
            Contract.FavoriteEntry.COLUMN_CHAMPION,
            Contract.FavoriteEntry.COLUMN_RESULT,
            Contract.FavoriteEntry.COLUMN_GAME_MODE_SUB,
            Contract.FavoriteEntry.COLUMN_GAME_MODE,
            Contract.FavoriteEntry.COLUMN_LEVEL,
            Contract.FavoriteEntry.COLUMN_GOLD,
            Contract.FavoriteEntry.COLUMN_DAMAGE,
            Contract.FavoriteEntry.COLUMN_CS,


    };


    TextView summonerName;
    TextView championName;
    TextView result;
    TextView gameMode;
    TextView kda ;
    TextView cs;
    TextView gameModeSub;
    TextView gold;
    TextView damage;
    TextView lvl;
    Button deleteButton;

    public FavoriteDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(getArguments()!=null)
        GAMEID=getArguments().getLong("matchID");
        View rootView= inflater.inflate(R.layout.fragment_favorite_detail, container, false);


        summonerName= (TextView) rootView.findViewById(R.id.summoner_name_text);
        championName= (TextView) rootView.findViewById(R.id.championName);
        result = (TextView) rootView.findViewById(R.id.game_result_text);
        gameMode = (TextView) rootView.findViewById(R.id.gameMode);
        kda = (TextView) rootView.findViewById(R.id.kda);
        cs = (TextView) rootView.findViewById(R.id.cs);
        gameModeSub = (TextView) rootView.findViewById(R.id.gameModeSub);
        gold = (TextView) rootView.findViewById(R.id.gold);
        damage = (TextView) rootView.findViewById(R.id.damage);
        lvl = (TextView) rootView.findViewById(R.id.lvl);
        deleteButton = (Button) rootView.findViewById(R.id.remove_to_favorite);
        deleteButton.setOnClickListener(this);

        return rootView;

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if(GAMEID!=null) {
            Uri gameUri = Contract.FavoriteEntry.buildMatchWithId(GAMEID.toString());

            return new CursorLoader(getActivity(),
                    gameUri,
                    FAVORITE_COLUMNS,
                    null,
                    null,
                    null);
        }
        return null;


    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data != null && data.moveToFirst()) {

            summonerName.setText(data.getString(COLUMN_SUMMONER));
            championName.setText(data.getString(COLUMN_CHAMPION));
            lvl.setText("LVL: "+data.getString(COLUMN_LEVEL));
            damage.setText("Damage dealth: "+data.getString(COLUMN_DAMAGE));
            kda.setText("K/D/A: "+data.getString(COLUMN_KILL)+"/"+data.getString(COLUMN_DEATH)+"/"+data.getString(COLUMN_ASSIST));
            cs.setText("Minion killed: "+data.getString(COLUMN_CS));
            gold.setText("Gold earned"+data.getString(COLUMN_GOLD));
            gameMode.setText("Game Mode: "+data.getString(COLUMN_GAME_MODE));
            gameModeSub.setText("Game Type: "+data.getString(COLUMN_GAME_MODE_SUB));
            if(data.getInt(COLUMN_RESULT)==0){
                result.setText("Victory");
            }else
                result.setText("Defeat");




        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(0, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.remove_to_favorite:
                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete from Favorite?")
                        .setMessage("Are Sure you want to delete this Teemo? Ops!!! I mean this Match.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().getContentResolver()
                                        .delete(
                                                Contract.FavoriteEntry.CONTENT_URI,
                                                FavoriteProvider.sIdSelection,
                                                new String[]{GAMEID.toString()});
                                Toast.makeText(getActivity(), "Teemo REKT!", Toast.LENGTH_LONG).show();

                                getActivity().finish();
                /*End the display page and reload activity favorite*/
                                startActivity(new Intent(getActivity(), FavoriteActivity.class));
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })

                        .show();

                break;
        }

    }
}
