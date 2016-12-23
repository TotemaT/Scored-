package be.matteotaroli.scored.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import be.matteotaroli.scored.R;
import butterknife.BindView;
import butterknife.ButterKnife;

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

    void goToWebSite(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void goToButterknife(View view) {
        goToWebSite(getString(R.string.butterknife_url));
    }

    public void goToHintcase(View view) {
        goToWebSite(getString(R.string.hintcase_url));
    }

    public void goToSpectrum(View view) {
        goToWebSite(getString(R.string.spectrum_url));
    }

    public void goToApache(View view) {
        goToWebSite(getString(R.string.apache_url));
    }

    public void goToMit(View view) {
        goToWebSite(getString(R.string.mit_url));
    }
}
