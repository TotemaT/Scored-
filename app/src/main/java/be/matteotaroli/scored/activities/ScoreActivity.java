package be.matteotaroli.scored.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import be.matteotaroli.scored.R;
import be.matteotaroli.scored.pojos.Player;

public class ScoreActivity extends AppCompatActivity {

    private List<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent i = getIntent();
        if (i == null) {
            finish();
        }

        players = i.getParcelableArrayListExtra(getString(R.string.extra_players));

        ((TextView)findViewById(R.id.test)).setText(players.toString());
    }
}
