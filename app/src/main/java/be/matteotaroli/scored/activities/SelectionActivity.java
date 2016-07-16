package be.matteotaroli.scored.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.thebluealliance.spectrum.SpectrumDialog;

import java.util.ArrayList;

import be.matteotaroli.scored.Listeners.ColorPickedListener;
import be.matteotaroli.scored.Listeners.RecyclerRemoveItemListener;
import be.matteotaroli.scored.R;
import be.matteotaroli.scored.adapters.PlayerAdapter;
import be.matteotaroli.scored.pojos.Player;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectionActivity extends AppCompatActivity implements ColorPickedListener, RecyclerRemoveItemListener {

    private final static String TAG = "SelectionActivity";

    @BindView(R.id.start_btn)
    Button startBtn;
    @BindView(R.id.add_player_fab)
    FloatingActionButton addPlayerFab;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (savedInstanceState != null) {
            players = savedInstanceState.getParcelableArrayList(getString(R.string.players_key));
        } else {
            players = new ArrayList<>(8);
        }
        adapter = new PlayerAdapter(players, this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(getString(R.string.players_key), players);
    }

    @OnClick(R.id.start_btn)
    public void Start() {
        Log.d(TAG, "Start counting");
        Intent i = new Intent(SelectionActivity.this, ScoreActivity.class);

        i.putParcelableArrayListExtra(getString(R.string.extra_players), players);

        startActivity(i);
    }

    @OnClick(R.id.add_player_fab)
    public void AddNewPlayer() {
        if (players.size() == 8) {
            //show dialog " max 8 players "
        } else {
            players.add(new Player());
            adapter.notifyDataSetChanged();
        }
        Log.d(TAG, "Add new player");

        Log.d(TAG, "Player list : " + players.toString());
    }

    @Override
    public void onColorPicked(final View v, final int position, int color) {

        Log.d(TAG, "v = " + v.getClass().getSimpleName());

        new SpectrumDialog.Builder(this)
                .setTitle("Choose a color")
                .setColors(R.array.palette)
                .setSelectedColor(color)
                .setDismissOnColorSelected(true)
                .setFixedColumnCount(4)
                .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                        if (positiveResult) {
                            players.get(position).setColor(color);
                            v.getBackground().setColorFilter(color, PorterDuff.Mode.SRC);
                        }
                    }
                })
                .build().show(getSupportFragmentManager(), "color_dialog");
    }

    @Override
    public void onItemRemoved(View v, int position) {
        players.remove(position);
        adapter.notifyDataSetChanged();
    }
}
