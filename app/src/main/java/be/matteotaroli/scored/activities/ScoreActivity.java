package be.matteotaroli.scored.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.matteotaroli.scored.Listeners.RecyclerItemClickListener;
import be.matteotaroli.scored.Listeners.RecyclerItemLongClickListener;
import be.matteotaroli.scored.R;
import be.matteotaroli.scored.adapters.CountAdapter;
import be.matteotaroli.scored.pojos.Player;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ScoreActivity extends AppCompatActivity implements RecyclerItemClickListener, RecyclerItemLongClickListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.top)
    View top;
    @BindView(R.id.bottom)
    View bottom;

    private RecyclerView.Adapter adapter;

    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            Intent i = getIntent();
            if (i == null) {
                finish();
            }

            players = i.getParcelableArrayListExtra(getString(R.string.extra_players));
        } else {
            players = savedInstanceState.getParcelableArrayList(getString(R.string.players_key));
        }


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CountAdapter(players, this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(getString(R.string.players_key), players);
    }

    @Override
    public void onClick(View v, int position) {
        Log.d("lol", "Clicked : " + players.toString());
        Player p = players.get(position);
        p.incrementScore();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLongClick(View v, int position) {
        Log.d("lol", "Clicked long : " + players.toString());

        Player p = players.get(position);
        p.decrementScore();
        adapter.notifyDataSetChanged();
    }
}
