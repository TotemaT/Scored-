package be.matteotaroli.scored.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
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

    private String name;
    private int score;
    private int color;

    private TextView scoreTextView;
    private TextView nameTextView;

    public ScoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ScoreView,
                0, 0);
        try {
            name = a.getString(R.styleable.ScoreView_name);
            score = a.getInt(R.styleable.ScoreView_score, 0);
            color = a.getColor(R.styleable.ScoreView_color, Color.GREEN);
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
        this.setBackgroundColor(color);

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


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
            scoreTextView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        } else {
            scoreTextView.setGravity(Gravity.CENTER);
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        scoreTextView.setLayoutParams(params);
    }

    private void initializeNameTextView() {
        nameTextView = new TextView(getContext());
        setName(name);

        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.scoreview_name_size));
        nameTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
            nameTextView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        } else {
            nameTextView.setGravity(Gravity.CENTER);
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.score_text_view);
        nameTextView.setLayoutParams(params);
    }

    private RelativeLayout initializeContainer() {
        RelativeLayout container = new RelativeLayout(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        container.setLayoutParams(params);
        return container;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        scoreTextView.setText(String.valueOf(score));
        invalidate();
        requestLayout();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if (name != null && !name.isEmpty()) {
            nameTextView.setVisibility(VISIBLE);
        } else {
            nameTextView.setVisibility(GONE);
        }
        nameTextView.setText(name);
        invalidate();
        requestLayout();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
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
}
