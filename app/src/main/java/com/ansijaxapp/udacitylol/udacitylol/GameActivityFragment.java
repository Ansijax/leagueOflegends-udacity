package com.ansijaxapp.udacitylol.udacitylol;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ansijaxapp.udacitylol.udacitylol.data.Game;
import com.ansijaxapp.udacitylol.udacitylol.service.LolService;
import com.ansijaxapp.udacitylol.udacitylol.service.LolServiceReceiver;
import com.ansijaxapp.udacitylol.udacitylol.utils.GamesAdapter;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class GameActivityFragment extends Fragment implements LolServiceReceiver.Receiver,AdapterView.OnItemClickListener{


    ArrayList<Game> gameHistory;
    ProgressDialog progressDialog;
    ListView mListView;
    LolServiceReceiver mReceiver;
    boolean mTwoPane;
    private static final String TAG_DEBUG= GameActivityFragment.class.getSimpleName();

    public GameActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true); //TODO oncreateoptionmenu
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView= inflater.inflate(R.layout.fragment_main, container, false);
        mListView = (ListView)rootView.findViewById(R.id.list_view_games);
        mTwoPane=getArguments().getBoolean("mTwoPane");
        if(savedInstanceState==null) {
            Intent intent = new Intent(getActivity(), LolService.class);
            intent.putExtra("summonerName", getArguments().getString("summonerName"));
            mReceiver = new LolServiceReceiver(new Handler());
            mReceiver.setReceiver(this);
            intent.putExtra("receiverTag", mReceiver);
            getActivity().startService(intent);
        }

        return rootView;
    }




    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

        switch (resultCode) {
            case LolServiceReceiver.START_SERVICE :
                progressDialog = ProgressDialog.show(getActivity(),null,"Searching summoner");
                progressDialog.setCancelable(true);
                progressDialog.show();

                break;

            case LolServiceReceiver.END_SERVICE :
                progressDialog.dismiss();
                gameHistory = resultData.getParcelableArrayList("matchHistory");
                GamesAdapter adapter = new GamesAdapter(getActivity(), R.layout.fragment_main, gameHistory);
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(this);
                break;
            case LolServiceReceiver.NO_MATCH:
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "sorry no match found!!! Ranked disabled", Toast.LENGTH_SHORT).show();
                break;
            case LolServiceReceiver.ERROR:
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "sorry something goes wrong!!! FIX EUW PLS", Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (mTwoPane) {
            view.getFocusables(position);
            view.setSelected(true);

            FrameLayout frameLT= (FrameLayout)  getActivity().findViewById(R.id.datail_activity_game);
            frameLT.setVisibility(View.VISIBLE);

            MatchDetailFragment rightFragment=new MatchDetailFragment();
            Bundle rightBundle = new Bundle();
            rightBundle.putParcelable("match",gameHistory.get(position));
            rightFragment.setArguments(rightBundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.datail_activity_game, rightFragment, "TAG")
                    .commit();

        } else {

            Intent intent = new Intent(getActivity(), GameDetailActivity.class);
            Game singleMatch = gameHistory.get(position);
            intent.putExtra("match", singleMatch);
            startActivity(intent);
        }
    }
}
