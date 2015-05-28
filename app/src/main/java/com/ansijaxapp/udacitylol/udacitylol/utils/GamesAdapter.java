package com.ansijaxapp.udacitylol.udacitylol.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ansijaxapp.udacitylol.udacitylol.R;
import com.ansijaxapp.udacitylol.udacitylol.data.Game;

import java.util.List;

/**
 * Created by Massimo on 24/05/15.
 */
public class GamesAdapter extends ArrayAdapter<Game> {
    Context context;
    public GamesAdapter(Context context, int resource, List<Game> item) {
        super(context, resource, item);
        this.context =context;
    }


    public static class ViewHolder{

        ImageView championIcon;
        TextView result;
        TextView kda;
        TextView gameMode;
        public ViewHolder(View view){
            championIcon= (ImageView) view.findViewById(R.id.champion_icon);
            result = (TextView) view.findViewById(R.id.result);
            gameMode = (TextView) view.findViewById(R.id.gameMode);
            kda = (TextView) view.findViewById(R.id.kda);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent){

        Game match = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.listitem_games, null);
        ViewHolder holder = new ViewHolder(convertView);
        holder.championIcon.setImageBitmap(match.getChampion());
        holder.gameMode.setText(match.getGameModeSub());
        holder.kda.setText("K/D/A:"+match.getKill()+"/"+match.getDeath()+"/"+match.getAssists());

       if(match.getWin()){
            holder.result.setText("Victory");

        }else {
            holder.result.setText("Defeat");

        }
        return convertView;
    }
}
