/*
Scored! allows to keep tracks of each player's score during a game.
Copyright (C) 2016  Matteo Taroli

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package be.matteotaroli.scored.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import be.matteotaroli.scored.Listeners.RecyclerItemClickListener;
import be.matteotaroli.scored.Listeners.RecyclerItemLongClickListener;
import be.matteotaroli.scored.R;
import be.matteotaroli.scored.adapters.CountAdapter;
import be.matteotaroli.scored.pojos.Player;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity that allows the user to manage players' score.
 */
public class ScoreActivity extends ActivityWithHints implements RecyclerItemClickListener, RecyclerItemLongClickListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;

    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            Intent i = getIntent();
            if (i != null)
                players = i.getParcelableArrayListExtra(getString(R.string.extra_players));
            else finish();

        } else {
            players = savedInstanceState.getParcelableArrayList(getString(R.string.players_key));
        }


        recyclerView.setHasFixedSize(true);

        if (players.size() % 2 == 0) {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        adapter = new CountAdapter(players, this, this);
        recyclerView.setAdapter(adapter);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        showHints();
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

    private void showHints() {
/*        String firstTimeKey = getResources().getString(R.string.pref_first_time_score_activity);
        if (!getPreferences(MODE_PRIVATE).getBoolean(firstTimeKey, true)) {
            return;
        }
        getPreferences(MODE_PRIVATE).edit().putBoolean(firstTimeKey, false).apply();*/
        startShowingHints(new Runnable() {
            @Override
            public void run() {
                showPlayerTileHint();
            }
        });
    }

    private void showPlayerTileHint() {
        Resources res = getResources();
        View view = recyclerView.getChildAt(0);
        String title = res.getString(R.string.hint_player_tile_title),
                body = res.getString(R.string.hint_player_tile_body);

        showRectangularHint(view, title, body, null);
    }

}
