package com.ansijaxapp.udacitylol.udacitylol;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.ansijaxapp.udacitylol.udacitylol.database.Contract.FavoriteEntry;
import com.ansijaxapp.udacitylol.udacitylol.utils.FavoriteAdapter;


public class FavoriteListFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor>  {



    private FavoriteAdapter mAdapter;
    private ListView mListView;

    public static final int _ID=0;
    public static final int COLUMN_ID=1;
    public static final int COLUMN_KILL=2;
    public static final int COLUMN_DEATH=3;
    public static final int COLUMN_ASSIST=4;
    public static final int COLUMN_SUMMONER=5;
    public static final int COLUMN_CHAMPION=6;
    public static final int COLUMN_RESULT=7;
    public static final int COLUMN_GAME_MODE_SUB=8;
    private int mPosition = ListView.INVALID_POSITION;

    private final String[] FAVORITE_COLUMNS= {
            FavoriteEntry._ID,
            FavoriteEntry.COLUMN_ID,
            FavoriteEntry.COLUMN_KILL,
            FavoriteEntry.COLUMN_DEATH,
            FavoriteEntry.COLUMN_ASSIST,
            FavoriteEntry.COLUMN_SUMMONER,
            FavoriteEntry.COLUMN_CHAMPION,
            FavoriteEntry.COLUMN_RESULT,
            FavoriteEntry.COLUMN_GAME_MODE_SUB

    };

    public FavoriteListFragment() {
        // Required empty public constructor
    }

    public interface Callback {
        public void onItemSelected(Long gameID);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true); //TODO oncreateoptionmenu
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAdapter= new FavoriteAdapter(getActivity(),null,0);
        View rootView = inflater.inflate(R.layout.fragment_favovrite, container, false);
        mListView = (ListView) rootView.findViewById(R.id.list_view_favorite);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                 /*highlight the selected list item */
                view.getFocusables(position);
                view.setSelected(true);

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    ((Callback) getActivity())
                            .onItemSelected(cursor.getLong(COLUMN_ID));

                    mPosition = position;
                }
            }
        });


        return rootView;

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String sortOrder = FavoriteEntry.COLUMN_ID + " ASC";
        Uri gameUri = FavoriteEntry.CONTENT_URI;
        return new CursorLoader(getActivity(),
                gameUri,
                FAVORITE_COLUMNS,
                null,
                null,
                sortOrder);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        if (mPosition != ListView.INVALID_POSITION) {
            mListView.smoothScrollToPosition(mPosition);
        }


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(0, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);

    }




}
