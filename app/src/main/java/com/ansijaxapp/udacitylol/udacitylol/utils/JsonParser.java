package com.ansijaxapp.udacitylol.udacitylol.utils;

import com.ansijaxapp.udacitylol.udacitylol.data.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Massimo on 24/05/15.
 */
public class JsonParser {

    //////////////////////GAME LABEL
    private final static String GAMES_LIST="games";
    private final static String STATS_LIST="stats";
    private final static String DAMAGE="totalDamageDealtToChampions";
    private final static String GOLD="goldEarned";
    private final static String WARD="wardPlaced";
    private final static String LEVEL="level";
    private final static String KILL="championsKilled";
    private final static String DEATH="numDeaths";
    private final static String ASSIST="assists";
    private final static String CS="minionsKilled";
    private final static String WIN="win";
    private final static String MAP_SITE="team";
    private final static String GAME_MODE="gameMode";
    private final static String GAME_MODE_SUB="subType";
    private final static String CHAMPION="championId";
    private final static String GAME_ID="gameId";


    ///////////////////////CHAMP LABEL
    private final static String IMG_NAME="full";
    private final static String IMG="image";


    public static ArrayList<Game> jsonToGames(JSONObject data, String summonerName) throws JSONException {

        JSONArray gamesArray = data.getJSONArray(GAMES_LIST);
        ArrayList<Game> gameList = new ArrayList<>(gamesArray.length());

        for (int cont =0; cont < gamesArray.length(); cont++){
            JSONObject jsonMatch = gamesArray.getJSONObject(cont);
            Game match = new Game();

            match.setSummonerName(summonerName);
            match.setGameMode(jsonMatch.getString(GAME_MODE));
            match.setGameModeSub(jsonMatch.getString(GAME_MODE_SUB));
            match.setChampion(jsonMatch.getInt(CHAMPION));
            match.setMatchID(jsonMatch.getLong(GAME_ID));

            JSONObject jsonMatchStats = jsonMatch.getJSONObject(STATS_LIST);


            if(jsonMatchStats.isNull(KILL))
                match.setKill(0);
            else
                match.setKill(jsonMatchStats.getInt(KILL));

            if(jsonMatchStats.isNull(ASSIST))
                match.setAssists(0);
            else match.setAssists(jsonMatchStats.getInt(ASSIST));

            if(jsonMatchStats.isNull(DEATH))
                match.setDeath(0);
            else
                match.setDeath(jsonMatchStats.getInt(DEATH));

            if(jsonMatchStats.isNull(CS))
                match.setCs(0);
            else
                match.setCs(jsonMatchStats.getInt(CS));

            match.setDamage(jsonMatchStats.getLong(DAMAGE));
            if (jsonMatchStats.isNull(WARD))
                match.setWard(0);
            else
                match.setWard(jsonMatchStats.getInt(WARD));
            match.setWin(jsonMatchStats.getBoolean(WIN));
            match.setMapSite(jsonMatchStats.getInt(MAP_SITE));
            match.setLevel(jsonMatchStats.getInt(LEVEL));
            match.setGold(jsonMatchStats.getLong(GOLD));

            gameList.add(match);
        }

        return gameList;
    }

    public static String getImgName(String data){

        String imgName=null;
        try {
            JSONObject jsonChamp = new JSONObject(data);

            imgName= jsonChamp.getJSONObject(IMG).getString(IMG_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            return imgName;

        }

    }


}
