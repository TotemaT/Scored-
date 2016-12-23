package be.matteotaroli.scored.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import be.matteotaroli.scored.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by matt on 23/12/16.
 */

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
        setTitle(R.string.about);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.butterknife)
    void goToButterKnife() {
        goToWebSite(getString(R.string.butterknife_url));
    }

    @OnClick(R.id.hintcase)
    void goToHintcase() {
        goToWebSite(getString(R.string.hintcase_url));
    }

    @OnClick(R.id.spectrum)
    void goToSpectrum() {
        goToWebSite(getString(R.string.spectrum_url));
    }

    void goToWebSite(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
