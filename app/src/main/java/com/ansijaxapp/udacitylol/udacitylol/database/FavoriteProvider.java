package com.ansijaxapp.udacitylol.udacitylol.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;


import com.ansijaxapp.udacitylol.udacitylol.database.Contract.FavoriteEntry;

/**
 * Created by Massimo on 26/05/15.
 */
public class FavoriteProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FavoriteDbHelper mOpenHelper;
    private static final SQLiteQueryBuilder matchQueryBuilder;
    private static final int MATCH= 100;
    private static final int MATCH_ID= 101;


    static{
        matchQueryBuilder = new SQLiteQueryBuilder();
        matchQueryBuilder.setTables(FavoriteEntry.FAVORITE_TABLE);
    }

    public static final String sIdSelection= FavoriteEntry.FAVORITE_TABLE + "." +FavoriteEntry.COLUMN_ID + " = ? ";

    @Override
    public boolean onCreate() {
        mOpenHelper = new FavoriteDbHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case MATCH: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        FavoriteEntry.FAVORITE_TABLE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case MATCH_ID: {
                String id= FavoriteEntry.getMatchFromUri(uri);
                String selectionQuery =sIdSelection;
                selectionArgs= new String[] {id};
                retCursor=  matchQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                        projection,
                        selectionQuery,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
                }
                //TODO

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int matchUri = sUriMatcher.match(uri);
        Uri returnUri;

        switch (matchUri){
            case MATCH:
                long _id = db.insert(FavoriteEntry.FAVORITE_TABLE, null, values);
                if ( _id > 0 )
                    returnUri = FavoriteEntry.buildItemUri(_id);

                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;



        }




    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case MATCH:
                rowsDeleted = db.delete(
                        FavoriteEntry.FAVORITE_TABLE, selection, selectionArgs);
                break;
            case MATCH_ID:

                rowsDeleted = db.delete(
                        FavoriteEntry.FAVORITE_TABLE, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contract.CONTENT_AUTHORITY;
        matcher.addURI(authority, Contract.PATH_FAVORITE, MATCH);
        matcher.addURI(authority, Contract.PATH_FAVORITE + "/#", MATCH_ID);

        return matcher;
    }
}
