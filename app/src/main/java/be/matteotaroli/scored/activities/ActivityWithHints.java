package be.matteotaroli.scored.activities;

import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by matt on 25/11/16.
 */

public class ActivityWithHints extends AppCompatActivity {
    public void showRectangularHint(View view, String title, String body, HintCase.OnClosedListener listener) {
        HintContentHolder contentHolder = getContentHolder(view, title, body);

        new HintCase(view.getRootView())
                .setTarget(view, new RectangularShape())
                .setShapeAnimators(new RevealRectangularShapeAnimator(), new UnrevealRectangularShapeAnimator())
                .setHintBlock(contentHolder)
                .setOnClosedListener(listener)
                .show();
    }

    public void showCircularHint(View view, String title, String body, HintCase.OnClosedListener listener) {
        HintContentHolder contentHolder = getContentHolder(view, title, body);

        new HintCase(view.getRootView())
                .setTarget(view, new CircularShape())
                .setShapeAnimators(new RevealCircleShapeAnimator(), new UnrevealCircleShapeAnimator())
                .setHintBlock(contentHolder)
                .setOnClosedListener(listener)
                .show();
    }

    private HintContentHolder getContentHolder(View view, String title, String body) {
        return new SimpleHintContentHolder.Builder(view.getContext())
                .setContentTitle(title)
                .setContentText(body)
                .build();
    }
}
