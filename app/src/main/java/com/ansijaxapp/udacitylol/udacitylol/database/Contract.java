package com.ansijaxapp.udacitylol.udacitylol.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Massimo on 25/05/15.
 */
public class Contract {


    public static final String CONTENT_AUTHORITY = "com.ansijaxapp.udacitylol.udacitylol";

    public static final String PATH_FAVORITE = "favorite";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final class FavoriteEntry implements BaseColumns {
        static final String FAVORITE_TABLE = "favorite";


        public static final String COLUMN_ID = "id";
        public static final String COLUMN_GOLD = "gold";
        public static final String COLUMN_KILL = "kill";
        public static final String COLUMN_DEATH = "death";
        public static final String COLUMN_ASSIST = "assist";
        public static final String COLUMN_LEVEL = "level";
        public static final String COLUMN_DAMAGE = "damage";
        public static final String COLUMN_CS = "cs";
        public static final String COLUMN_GAME_MODE = "game_mode";
        public static final String COLUMN_GAME_MODE_SUB = "game_mode_sub";
        public static final String COLUMN_CHAMPION = "champion_name";
        public static final String COLUMN_SUMMONER = "summoner";
        public static final String COLUMN_RESULT = "result";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        public static Uri buildItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getMatchFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildMatchWithId(String matchId) {
            return CONTENT_URI.buildUpon().appendPath(matchId).build();
        }
    }
}



