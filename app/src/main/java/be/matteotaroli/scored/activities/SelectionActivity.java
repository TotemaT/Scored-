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

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.joanfuentes.hintcase.HintCase;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.util.ArrayList;

import be.matteotaroli.scored.Listeners.ColorPickerListener;
import be.matteotaroli.scored.Listeners.RecyclerRemoveItemListener;
import be.matteotaroli.scored.R;
import be.matteotaroli.scored.adapters.PlayerAdapter;
import be.matteotaroli.scored.pojos.Player;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity that allows the user to manage players.
 */
public class SelectionActivity extends ActivityWithHints implements ColorPickerListener, RecyclerRemoveItemListener {

    private final static String TAG = "SelectionActivity";

    @BindView(R.id.start_btn)
    Button startBtn;
    @BindView(R.id.add_player_fab)
    FloatingActionButton addPlayerFab;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private ArrayList<Player> players;
    private int[] colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        ButterKnife.bind(this);

        colors = getResources().getIntArray(R.array.palette);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (savedInstanceState != null) {
            players = savedInstanceState.getParcelableArrayList(getString(R.string.players_key));
        } else {
            players = new ArrayList<>(8);
            players.add(new Player(colors[0]));
        }
        adapter = new PlayerAdapter(players, this, this);
        recyclerView.setAdapter(adapter);
        showHints();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(getString(R.string.players_key), players);
    }

    @OnClick(R.id.start_btn)
    void Start() {
        Log.d(TAG, "Launching ScoreActivity");
        if (players.size() == 0) {
            Toast.makeText(this, R.string.minimum_players_toast, Toast.LENGTH_SHORT).show();
            return;
        }

        hideSoftKeyboard();

        Intent i = new Intent(SelectionActivity.this, ScoreActivity.class);
        i.putParcelableArrayListExtra(getString(R.string.extra_players), players);
        startActivity(i);
    }

    @OnClick(R.id.add_player_fab)
    void AddNewPlayer() {
        if (players.size() == 8) {
            Toast.makeText(this, R.string.maximum_players_toast, Toast.LENGTH_SHORT).show();
        } else {
            Player p = new Player(colors[players.size()]);
            players.add(p);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onColorPickerOpen(final View v, final int position, int color) {

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

    private void showHints() {
/*        String firstTimeKey = getResources().getString(R.string.pref_first_time_selection_activity);
        if (!getPreferences(MODE_PRIVATE).getBoolean(firstTimeKey, true)) {
            return;
        }
        getPreferences(MODE_PRIVATE).edit().putBoolean(firstTimeKey, false).apply();*/

        startShowingHints(new Runnable() {
            @Override
            public void run() {
                showPlayerHint();
            }
        });
    }

    private void showPlayerHint() {
        Resources res = getResources();
        View view = recyclerView.getChildAt(0);
        String title = res.getString(R.string.hint_player_title),
                body = res.getString(R.string.hint_player_body);
        HintCase.OnClosedListener listener = new HintCase.OnClosedListener() {
            @Override
            public void onClosed() {
                showAddPlayerHint();
            }
        };
        showRectangularHint(view, title, body, listener);
    }

    private void showAddPlayerHint() {
        Resources res = getResources();
        View view = addPlayerFab;
        String title = res.getString(R.string.hint_add_player_title),
                body = res.getString(R.string.hint_add_player_body);
        HintCase.OnClosedListener listener = new HintCase.OnClosedListener() {
            @Override
            public void onClosed() {
                showStartHint();
            }
        };
        showCircularHint(view, title, body, listener);
    }

    private void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void showStartHint() {
        Resources res = getResources();
        View view = startBtn;
        String title = res.getString(R.string.hint_start_title),
                body = res.getString(R.string.hint_start_body);
        showCircularHint(view, title, body, null);
    }

}
