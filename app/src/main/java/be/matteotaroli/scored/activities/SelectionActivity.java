package be.matteotaroli.scored.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import be.matteotaroli.scored.Listeners.RecyclerItemClickListener;
import be.matteotaroli.scored.Listeners.RecyclerItemLongClickListener;
import be.matteotaroli.scored.R;
import be.matteotaroli.scored.adapters.PlayerAdapter;
import be.matteotaroli.scored.pojos.Player;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectionActivity extends AppCompatActivity {

    private final static String TAG = "SelectionActivity";

    @BindView(R.id.start_btn)
    Button startBtn;
    @BindView(R.id.add_player_fab)
    FloatingActionButton addPlayerFab;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    private List<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        players = new ArrayList<>();
        adapter = new PlayerAdapter(players);
        recyclerView.setAdapter(adapter);

    }

    @OnClick(R.id.start_btn)
    public void Start() {
        Log.d(TAG, "Start counting");
        Intent i = new Intent(SelectionActivity.this, ScoreActivity.class);

        // Add list of players

        startActivity(i);
    }

    @OnClick(R.id.add_player_fab)
    public void AddNewPlayer() {
        players.add(new Player());
        adapter.notifyDataSetChanged();
        Log.d(TAG, "Add new player");
    }
}
