package com.ansijaxapp.udacitylol.udacitylol.utils;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.support.v4.widget.CursorAdapter;
import android.widget.TextView;

import com.ansijaxapp.udacitylol.udacitylol.FavoriteListFragment;
import com.ansijaxapp.udacitylol.udacitylol.R;
import com.ansijaxapp.udacitylol.udacitylol.database.Contract;

/**
 * Created by Massimo on 26/05/15.
 */
public class FavoriteAdapter extends CursorAdapter {



    public static class ViewHolder{
        public final TextView summonerName;
        public final TextView result;
        public final TextView championName;
        public final TextView gameType;
        public final TextView kda;

        public ViewHolder (View view){
            summonerName=(TextView) view.findViewById(R.id.summoner_name_text);
            result =(TextView) view.findViewById(R.id.game_result_text);
            championName= (TextView) view.findViewById(R.id.champion_text);
            gameType= (TextView) view.findViewById(R.id.match_type_text);
            kda= (TextView) view.findViewById(R.id.kda_text);


        }

    }
    public FavoriteAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext=context;

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.summonerName.setText(cursor.getString(FavoriteListFragment.COLUMN_SUMMONER));
        viewHolder.gameType.setText(cursor.getString(FavoriteListFragment.COLUMN_GAME_MODE_SUB));

        if(cursor.getInt(FavoriteListFragment.COLUMN_RESULT)==0){
            viewHolder.result.setText("VICTORY");
            view.setBackgroundResource(R.color.blu_light);
        }
        else{
            viewHolder.result.setText("DEFEAT");
            view.setBackgroundResource(R.color.red_light);
        }

        viewHolder.championName.setText(cursor.getString(FavoriteListFragment.COLUMN_CHAMPION));
        int kill=cursor.getInt(FavoriteListFragment.COLUMN_KILL);
        int assist =cursor.getInt(FavoriteListFragment.COLUMN_ASSIST);
        int death =cursor.getInt(FavoriteListFragment.COLUMN_DEATH);

        viewHolder.kda.setText("KDA: "+kill+"/"+death+"/"+assist);


    }
}
