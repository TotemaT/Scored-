package be.matteotaroli.scored.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import be.matteotaroli.scored.R;
import be.matteotaroli.scored.pojos.Player;

/**
 * Created by matt on 3/12/16.
 */

public class ScoreView extends FrameLayout implements View.OnClickListener, View.OnLongClickListener {

    private static final String NAME_KEY = "name_key";
    private static final String SCORE_KEY = "score_key";
    private static final String COLOUR_KEY = "colour_key";
    private static final String SUPER_STATE_KEY = "super_state_key";
    private static final String TAG = "ScoreView";

    private String name;
    private int score;
    private int color;

    private TextView scoreTextView;
    public TextView nameTextView;

    public ScoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ScoreView,
                0, 0);
        try {
            name = a.getString(R.styleable.ScoreView_name);
            score = a.getInt(R.styleable.ScoreView_score, 0);
            color = a.getColor(R.styleable.ScoreView_color, ContextCompat.getColor(context, R.color.blue_grey));
        } finally {
            a.recycle();
        }

        initialize();
    }

    public ScoreView(Context context, Player player) {
        super(context);
        name = player.getName();
        score = player.getScore();
        color = player.getColor();
        initialize();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SUPER_STATE_KEY, super.onSaveInstanceState());
        bundle.putString(NAME_KEY, name);
        bundle.putInt(SCORE_KEY, score);
        bundle.putInt(COLOUR_KEY, color);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            setName(bundle.getString(NAME_KEY));
            setScore(bundle.getInt(SCORE_KEY));
            setColor(bundle.getInt(COLOUR_KEY));
            state = bundle.getParcelable(SUPER_STATE_KEY);
        }
        super.onRestoreInstanceState(state);
    }

    private void initialize() {
        this.setClickable(true);
        this.setOnClickListener(this);
        this.setOnLongClickListener(this);
        this.setColor(color);
        this.setLayoutParams(new ViewGroup.LayoutParams(0, 0));

        RelativeLayout container = initializeContainer();
        initializeScoreTextView();
        initializeNameTextView();

        container.addView(scoreTextView);
        container.addView(nameTextView);

        this.addView(container);
    }

    private void initializeScoreTextView() {
        scoreTextView = new TextView(getContext());
        scoreTextView.setId(R.id.score_text_view);
        setScore(score);
        scoreTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        scoreTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.scoreview_score_size));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        scoreTextView.setLayoutParams(params);
    }

    private void initializeNameTextView() {
        nameTextView = new TextView(getContext());
        setName(name);

        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.scoreview_name_size));
        nameTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.score_text_view);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        nameTextView.setLayoutParams(params);
    }

    private RelativeLayout initializeContainer() {
        RelativeLayout container = new RelativeLayout(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.setMargins(0, 0, 0, 0);
        container.setLayoutParams(params);
        return container;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        // Log.d(TAG, "Set Score : " + score);
        this.score = score;
        scoreTextView.setText(String.valueOf(score));
        scoreTextView.invalidate();
        scoreTextView.requestLayout();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        // Log.d(TAG, "Set Name : " + name);
        this.name = name;
        if (name != null && !name.isEmpty()) {
            nameTextView.setVisibility(VISIBLE);
        } else {
            nameTextView.setVisibility(GONE);
        }
        nameTextView.setText(name);
        nameTextView.invalidate();
        nameTextView.requestLayout();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        // Log.d(TAG, "Set Color : " + color);
        this.color = color;
        this.setBackgroundColor(color);
        invalidate();
        requestLayout();
    }

    @Override
    public void onClick(View view) {
        setScore(getScore() + 1);
    }

    @Override
    public boolean onLongClick(View view) {
        setScore(getScore() - 1);
        return true;
    }

    public void setPlayer(Player player) {
        setName(player.getName());
        setScore(player.getScore());
        setColor(player.getColor());
    }
}
