package be.matteotaroli.scored.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.joanfuentes.hintcase.HintCase;
import com.joanfuentes.hintcase.RectangularShape;
import com.joanfuentes.hintcaseassets.hintcontentholders.HintContentHolder;
import com.joanfuentes.hintcaseassets.hintcontentholders.SimpleHintContentHolder;
import com.joanfuentes.hintcaseassets.shapeanimators.RevealCircleShapeAnimator;
import com.joanfuentes.hintcaseassets.shapeanimators.RevealRectangularShapeAnimator;
import com.joanfuentes.hintcaseassets.shapeanimators.UnrevealCircleShapeAnimator;
import com.joanfuentes.hintcaseassets.shapeanimators.UnrevealRectangularShapeAnimator;
import com.joanfuentes.hintcaseassets.shapes.CircularShape;

import be.matteotaroli.scored.R;

/**
 * Created by matt on 25/11/16.
 */

public class ActivityWithHints extends AppCompatActivity {

    private static final int HINT_DELAY = 500;

    public void startShowingHints(Runnable runnable) {
        new Handler().postDelayed(runnable, HINT_DELAY);
    }

    public void showRectangularHint(View view, String title, String body, HintCase.OnClosedListener listener) {
        HintContentHolder contentHolder = getContentHolder(view, title, body);

        new HintCase(view.getRootView())
                .setTarget(view, new RectangularShape())
                .setShapeAnimators(new RevealRectangularShapeAnimator(), new UnrevealRectangularShapeAnimator())
                .setHintBlock(contentHolder)
                .setOnClosedListener(listener)
                .setBackgroundColorByResourceId(R.color.hint_background)
                .show();
    }

    public void showCircularHint(View view, String title, String body, HintCase.OnClosedListener listener) {
        HintContentHolder contentHolder = getContentHolder(view, title, body);

        new HintCase(view.getRootView())
                .setTarget(view, new CircularShape())
                .setShapeAnimators(new RevealCircleShapeAnimator(), new UnrevealCircleShapeAnimator())
                .setHintBlock(contentHolder)
                .setOnClosedListener(listener)
                .setBackgroundColorByResourceId(R.color.hint_background)
                .show();
    }

    private HintContentHolder getContentHolder(View view, String title, String body) {
        return new SimpleHintContentHolder.Builder(view.getContext())
                .setContentTitle(title)
                .setContentText(body)
                .setTitleStyle(R.style.hint_title_style)
                .setContentStyle(R.style.hint_body_style)
                .setMarginByResourcesId(R.dimen.hint_margin,R.dimen.hint_margin,R.dimen.hint_margin,R.dimen.hint_margin)
                .build();
    }
}
