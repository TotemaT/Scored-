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
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

import be.matteotaroli.scored.R;
import be.matteotaroli.scored.pojos.Player;
import be.matteotaroli.scored.views.ScoreView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity that allows the user to manage players' score.
 */
public class ScoreActivity extends ActivityWithHints {

    private static final String TAG = "ScoreActivity";
    @BindView(R.id.grid)
    GridLayout grid;

    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Intent i = getIntent();
            if (i != null)
                players = i.getParcelableArrayListExtra(getString(R.string.extra_players));
            else finish();

        } else {
            players = savedInstanceState.getParcelableArrayList(getString(R.string.players_key));
        }
        setLayout();
        showHints();
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void setLayout() {
        setContentView(getResources().getIdentifier("activity_score_" + players.size(), "layout", getPackageName()));
        ButterKnife.bind(this);

        for (int i = 0; i < players.size(); i++) {
            ((ScoreView) grid.getChildAt(i)).setPlayer(players.get(i));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(getString(R.string.players_key), players);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        players = savedInstanceState.getParcelableArrayList(getString(R.string.players_key));
    }

    private void showHints() {
        String firstTimeKey = getResources().getString(R.string.pref_first_time_score_activity);
        if (!getPreferences(MODE_PRIVATE).getBoolean(firstTimeKey, true)) {
            return;
        }
        getPreferences(MODE_PRIVATE).edit().putBoolean(firstTimeKey, false).apply();
        startShowingHints(new Runnable() {
            @Override
            public void run() {
                showPlayerTileHint();
            }
        });
    }

    private void showPlayerTileHint() {
        Resources res = getResources();
        View view = grid.getChildAt(0);
        String title = res.getString(R.string.hint_player_tile_title),
                body = res.getString(R.string.hint_player_tile_body);
        showRectangularHint(view, title, body, null);
    }

}
