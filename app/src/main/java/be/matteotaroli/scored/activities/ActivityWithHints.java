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

import android.os.Handler;
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

import be.matteotaroli.scored.R;

/**
 * Simple abstract {@link AppCompatActivity} that offers methods to simply show hints in any {@link AppCompatActivity}.
 */

public abstract class ActivityWithHints extends AppCompatActivity {

    /**
     * Delay before showing the first hint.
     */
    private static final int HINT_DELAY = 500;

    /**
     * Starts the runnable after {@link #HINT_DELAY} ms.
     *
     * @param runnable Runnable containing the method to run. This method should contain the first hint to show.
     */
    public void startShowingHints(Runnable runnable) {
        new Handler().postDelayed(runnable, HINT_DELAY);
    }

    /**
     * Show a rectangular hint.
     *
     * @param view     The view target of the hint.
     * @param title    The title of the hint.
     * @param body     The body content of the hint.
     * @param listener The action to execute when the hint is closed by the user.
     */
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

    /**
     * Show a circular hint.
     *
     * @param view     The view target of the hint.
     * @param title    The title of the hint.
     * @param body     The body content of the hint.
     * @param listener The action to execute when the hint is closed by the user.
     */
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

    /**
     * Create the {@link HintContentHolder} containing the explanation of hints.
     *
     * @param view  The view target of the hint.
     * @param title The title of the hint.
     * @param body  The body content of the hint.
     * @return The {@link HintContentHolder} with the given content.
     */
    private HintContentHolder getContentHolder(View view, String title, String body) {
        return new SimpleHintContentHolder.Builder(view.getContext())
                .setContentTitle(title)
                .setContentText(body)
                .setTitleStyle(R.style.hint_title_style)
                .setContentStyle(R.style.hint_body_style)
                .setMarginByResourcesId(R.dimen.hint_margin, R.dimen.hint_margin, R.dimen.hint_margin, R.dimen.hint_margin)
                .build();
    }
}
