package be.matteotaroli.scored.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import be.matteotaroli.scored.BuildConfig;
import be.matteotaroli.scored.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity that shows the libraries used in this app and allows to send a feedback.
 */

public class AboutActivity extends AppCompatActivity {
    private static final String MAIL_TITLE = "Scored feedback";
    private static final String[] EMAIL = {"scored.feedback@gmail.com"};

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.feedback_body_wrapper)
    TextInputLayout feedbackBodyWrapper;
    @BindView(R.id.feedback_body)
    EditText feedbackBodyTv;
    @BindView(R.id.feedback_radio_btn_group)
    RadioGroup feedbackRadioGroup;

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

    public void sendFeedback(View view) {
        if (feedbackBodyTv.getText().toString().trim().equals("")) {
            feedbackBodyWrapper.setError("Enter your feedback");
        } else {
            /* Intent to send email or sending it directly??????? */
            String feedback = feedbackBodyTv.getText().toString();
            String type;

            switch (feedbackRadioGroup.getCheckedRadioButtonId()) {
                case R.id.feedback_radio_btn:
                    type = "#feedback";
                    break;
                case R.id.improvement_radio_btn:
                    type = "#improvement";
                    break;
                case R.id.bug_radio_btn:
                    type = "#bug";
                    break;
                default:
                    type = "#other";
                    break;
            }

            String versionName = "#v" + BuildConfig.VERSION_NAME;
            String androidVersion = "#SDK" + Build.VERSION.SDK_INT;
            String title = MAIL_TITLE + " " + type + " " + versionName + " " + androidVersion;

            Intent intent = new Intent(Intent.ACTION_SEND);

            intent.setType("text/html");
            intent.putExtra(Intent.EXTRA_EMAIL, EMAIL);
            intent.putExtra(Intent.EXTRA_SUBJECT, title);
            intent.putExtra(Intent.EXTRA_TEXT, feedback);

            startActivity(Intent.createChooser(intent, "Send Email"));

        }
    }
}
