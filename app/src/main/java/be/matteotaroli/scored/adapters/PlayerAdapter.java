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

package be.matteotaroli.scored.adapters;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

import be.matteotaroli.scored.Listeners.ColorPickerListener;
import be.matteotaroli.scored.Listeners.RecyclerRemoveItemListener;
import be.matteotaroli.scored.R;
import be.matteotaroli.scored.pojos.Player;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for the RecyclerView that contains {@link Player}.
 */
public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private List<Player> players;

    private final RecyclerRemoveItemListener removeListener;
    private final ColorPickerListener colorPickerListener;

    public PlayerAdapter(List<Player> players, RecyclerRemoveItemListener removeListener, ColorPickerListener colorPickerListener) {
        this.players = players;
        this.removeListener = removeListener;
        this.colorPickerListener = colorPickerListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Player p = players.get(position);
        holder.playerNameEt.setText(p.getName());

        holder.colorButton.getBackground().setColorFilter(p.getColor(), PorterDuff.Mode.SRC_ATOP);

        if (position == 0) {
            holder.removeBtn.setVisibility(View.INVISIBLE);
        } else {
            holder.removeBtn.setVisibility(View.VISIBLE);
        }
        if (position + 1 == getItemCount()) {
            holder.playerNameEt.requestFocus();
            setBottomMargin(holder.itemView, (int) (72 * Resources.getSystem().getDisplayMetrics().density));
        } else {
            setBottomMargin(holder.itemView, 0);
        }
    }

    private static void setBottomMargin(View view, int bottomMargin) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, bottomMargin);
            view.requestLayout();
        }
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.color_circle_btn)
        Button colorButton;
        @BindView(R.id.player_name_editText)
        EditText playerNameEt;
        @BindView(R.id.remove_btn)
        ImageButton removeBtn;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            addRemoveListener();
            addChangeColorListener();
            addTextChangedListener();
        }

        void addRemoveListener() {
            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeListener.onItemRemoved(view, getAdapterPosition());
                }
            });
        }

        void addChangeColorListener() {
            colorButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    colorPickerListener.onColorPickerOpen(view, getAdapterPosition(), players.get(getAdapterPosition()).getColor());
                }
            });
        }

        void addTextChangedListener() {
            playerNameEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    players.get(getAdapterPosition()).setName(editable.toString());
                }
            });
        }
    }
}
