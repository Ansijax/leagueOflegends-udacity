package com.ansijaxapp.udacitylol.udacitylol.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.ansijaxapp.udacitylol.udacitylol.utils.JsonParser;
import com.ansijaxapp.udacitylol.udacitylol.utils.Utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Massimo on 24/05/15.
 */
public class Game implements Parcelable{

    public Game(){}

    private Long gold;
    private int kill;
    private int death;
    private int assists;
    private int level;
    private int ward;
    private Long damage;
    private Boolean win;
    private int mapSite;
    private int cs;
    private String gameMode;
    private String gameModeSub;
    private Bitmap champion;
    private String championName;
    private String summonerName;
    private Long matchID;

    public Long getMatchID() {
        return matchID;
    }

    public void setMatchID(Long matchID) {
        this.matchID = matchID;
    }



    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getWard() {
        return ward;
    }

    public void setWard(int ward) {
        this.ward = ward;
    }

    public Long getDamage() {
        return damage;
    }

    public void setDamage(Long damage) {
        this.damage = damage;
    }

    public Boolean getWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
    }

    public int getMapSite() {
        return mapSite;
    }

    public void setMapSite(int mapSite) {
        this.mapSite = mapSite;
    }

    public int getCs() {
        return cs;
    }

    public void setCs(int cs) {
        this.cs = cs;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getGameModeSub() {
        return gameModeSub;
    }

    public void setGameModeSub(String gameModeSub) {
        this.gameModeSub = gameModeSub;
    }

    public Bitmap getChampion() {
        return champion;
    }

    public void setChampion(int champion) {
        String imgName = getBitmapName(champion);
        this.champion = getBitmapFromUrl(imgName);
    }

    public void setChampionName(String championName){this.championName=championName;}

    public String getChampionName(){return this.championName;}

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getSummonerName() {
        return summonerName;
    }

    private String getBitmapName(Integer champid){
        final String BASE_URL= "https://global.api.pvp.net/api/lol/static-data/euw/v1.2/champion/";
        final String URL_PARAM="?champData=image&api_key=a8a396dd-919b-45cf-a7a4-0ccfec4c8a2a";
        String response;
        URL request = null;
        String imgName="Annie.png"; //default png
        try {
            request = new URL(Uri.parse(BASE_URL).buildUpon().appendEncodedPath(champid.toString()).appendEncodedPath(URL_PARAM).build().toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("GAME", "MalformedURLException",e);
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) request.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

        int status_code =urlConnection.getResponseCode();
        Log.d("GAME", "status_code" + status_code);
        if(status_code==200) {
            response = Utils.bufferToString(urlConnection.getInputStream());
            imgName = JsonParser.getImgName(response);
            setChampionName(imgName.substring(0,imgName.length()-4));
            Log.d("GAME", "Debug " + response);

        }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return imgName;
        }

    }


    private Bitmap getBitmapFromUrl(String imgName ){
        final String BASE_URL="http://ddragon.leagueoflegends.com/cdn/5.2.1/img/champion/";

        HttpURLConnection urlConnection = null;
        //String response;
        URL request = null;
        Bitmap champImg=null;

        try {
            request = new URL(Uri.parse(BASE_URL).buildUpon().appendEncodedPath(imgName).build().toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("GAME", "MalformedURLException",e);
        }

        try {
            urlConnection = (HttpURLConnection) request.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

           champImg = BitmapFactory.decodeStream(urlConnection.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return champImg;
        }

    }

    public Game(Parcel in) {
        gold = in.readByte() == 0x00 ? null : in.readLong();
        kill = in.readInt();
        death = in.readInt();
        assists = in.readInt();
        level = in.readInt();
        ward = in.readInt();
        damage = in.readByte() == 0x00 ? null : in.readLong();
        matchID = in.readByte() == 0x00 ? null : in.readLong();
        byte winVal = in.readByte();
        win = winVal == 0x02 ? null : winVal != 0x00;
        mapSite = in.readInt();
        cs = in.readInt();
        gameMode = in.readString();
        gameModeSub = in.readString();
        champion = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
        championName = in.readString();
        summonerName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (gold == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(gold);
        }
        dest.writeInt(kill);
        dest.writeInt(death);
        dest.writeInt(assists);
        dest.writeInt(level);
        dest.writeInt(ward);
        if (damage == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(damage);
        }
        if (matchID == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(matchID);
        }
        if (win == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (win ? 0x01 : 0x00));
        }
        dest.writeInt(mapSite);
        dest.writeInt(cs);
        dest.writeString(gameMode);
        dest.writeString(gameModeSub);
        dest.writeValue(champion);
        dest.writeString(championName);
        dest.writeString(summonerName);
    }


    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
