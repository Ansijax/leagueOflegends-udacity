package com.ansijaxapp.udacitylol.udacitylol.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ansijaxapp.udacitylol.udacitylol.database.Contract.FavoriteEntry;

/**
 * Created by Massimo on 25/05/15.
 */
public class FavoriteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME= "udacitylol.db";
    private static final int DATABASE_VERSION = 1;


    public FavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavoriteEntry.FAVORITE_TABLE + " (" +
                FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE," +
                FavoriteEntry.COLUMN_CS + " INTEGER NOT NULL, " +
                FavoriteEntry.COLUMN_KILL + " INTEGER NOT NULL, " +
                FavoriteEntry.COLUMN_ASSIST + " INTEGER NOT NULL, " +
                FavoriteEntry.COLUMN_DEATH + " INTEGER NOT NULL, " +
                FavoriteEntry.COLUMN_LEVEL + " INTEGER NOT NULL, " +
                FavoriteEntry.COLUMN_CHAMPION+ " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_SUMMONER+ " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_GAME_MODE+ " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_GAME_MODE_SUB+ " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_DAMAGE+ " LONG NOT NULL, " +
                FavoriteEntry.COLUMN_GOLD+ " LONG NOT NULL, " +
                FavoriteEntry.COLUMN_RESULT+ " LONG NOT NULL, " +
                FavoriteEntry.COLUMN_ID+ " LONG NOT NULL UNIQUE" +
                ");";

            db.execSQL(SQL_CREATE_FAVORITE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + FavoriteEntry.FAVORITE_TABLE);
            onCreate(db);
        }

}
