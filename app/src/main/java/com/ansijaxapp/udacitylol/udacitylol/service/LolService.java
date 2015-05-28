package com.ansijaxapp.udacitylol.udacitylol.service;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.ansijaxapp.udacitylol.udacitylol.data.Game;
import com.ansijaxapp.udacitylol.udacitylol.utils.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Massimo on 20/05/15.
 */
public class LolService extends IntentService {

    private final String BASE_URL = "https://euw.api.pvp.net/api/lol/euw/";
    final String API_KEY ="?api_key=a8a396dd-919b-45cf-a7a4-0ccfec4c8a2a";
    private final String LOG_TAG = LolService.class.getSimpleName();
    private String mSummonerName=null;

    public LolService() {
        super("LOLService");
    }




    @Override
    protected void onHandleIntent(Intent intent) {

        HttpURLConnection urlConnection = null;
        Long summonerID=-1L;
        String response = null;
        String summonerTosearch =intent.getStringExtra("summonerName");
        ResultReceiver rec = intent.getParcelableExtra("receiverTag");

        rec.send(LolServiceReceiver.START_SERVICE,null);
                //new Uri("https://euw.api.pvp.net/api/lol/euw/v1.4/summoner/by-name/ansijax?api_key=a8a396dd-919b-45cf-a7a4-0ccfec4c8a2a");

        try {

            //Request summoner ID
            URL request = queryBuilderSummonerId(summonerTosearch);
            urlConnection = (HttpURLConnection) request.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            int status_code =urlConnection.getResponseCode();
            Log.d(LOG_TAG, "status_code" + status_code);
            if(status_code==200) {
                response = bufferToString(urlConnection.getInputStream());
                summonerID = getSummonerID(summonerTosearch, response);
                Log.d(LOG_TAG, "Debug " + response);

            }else if (status_code==404){
                rec.send(LolServiceReceiver.NO_MATCH,null);
                return;

            }
            else{
                rec.send(LolServiceReceiver.ERROR,null);
                handleError(bufferToString(urlConnection.getInputStream()));
                return;
            }
            //request match history
            Log.d(LOG_TAG, "summonerID " + summonerID);
            request = queryBuilderSummonerMatchHistory(summonerID);
            urlConnection = (HttpURLConnection) request.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            status_code =urlConnection.getResponseCode();
            if(status_code ==200) {
                response = bufferToString(urlConnection.getInputStream());


                try {
                    ArrayList<Game> matchHistory= JsonParser.jsonToGames(new JSONObject(response),mSummonerName);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("matchHistory",matchHistory);
                    rec.send(LolServiceReceiver.END_SERVICE,bundle);
                } catch (JSONException e) {
                    Log.e("SERVICE", "error creating Data",e);
                }

                Log.e(LOG_TAG, "history " + response);

            }else {
                rec.send(LolServiceReceiver.ERROR,null);
                handleError(bufferToString(urlConnection.getInputStream()));}

        } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);

      /*  } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();*/
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

        }
        return;
    }


    private String bufferToString(InputStream inputStream){
        BufferedReader reader = null;

        StringBuffer buffer = new StringBuffer();
        if (inputStream == null) {
            // Nothing to do.
            return null;
        }
        reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        try {
            while ((line = reader.readLine()) != null) {

                buffer.append(line );
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error ", e);
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }

        }

        if (buffer.length() == 0) {
            // Stream was empty.  No point in parsing.
            return null;
        }

        return buffer.toString();


    }


    private URL queryBuilderSummonerId(String summonerToSearch) throws MalformedURLException {
        final String queryName ="v1.4/summoner/by-name";
        Uri query = Uri.parse(BASE_URL).buildUpon().appendEncodedPath(queryName).appendEncodedPath(summonerToSearch + API_KEY).build();

        return new URL(query.toString());
    }

    private URL queryBuilderSummonerMatchHistory(Long id) throws MalformedURLException {
        final String queryName ="v1.3/game/by-summoner/";
        final String queryParam ="recent/";

        Uri query = Uri.parse(BASE_URL).buildUpon().appendEncodedPath(queryName).appendEncodedPath(id.toString()).appendEncodedPath(queryParam)
                .appendEncodedPath(API_KEY).build();

        return new URL(query.toString());
    }

    private String handleError(String responseMessage){
        final String STATUS="status";
        final String MESSAGE="message";
        final String DEFAULT_MESSAGE="Sorry something went Wrong";
        String errorMessage;

        try {
            JSONObject data = new JSONObject(responseMessage);
            return data.getJSONObject(STATUS).getString(MESSAGE);

        } catch (JSONException e) {
            e.printStackTrace();
            return DEFAULT_MESSAGE;
        }


    }

    private  Long getSummonerID(String summoner,String data){

        final String ID="id";
        final String NAME="name";
        Long summmonerID=null;
        try {
            JSONObject dataJson= new JSONObject(data);
            //Json callback is always lowercase
            Log.d("Debug","summonerName: "+ summoner.toLowerCase());

            //get correct Lower/Uppercase
            mSummonerName= dataJson.getJSONObject(summoner.toLowerCase()).getString(NAME);
            //get summoner ID
            summmonerID=dataJson.getJSONObject(summoner.toLowerCase()).getLong(ID);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Debug","json error: ",e);
        }finally {
            return summmonerID;
        }

    }
}

