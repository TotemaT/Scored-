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

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import be.matteotaroli.scored.Listeners.RecyclerItemClickListener;
import be.matteotaroli.scored.Listeners.RecyclerItemLongClickListener;
import be.matteotaroli.scored.R;
import be.matteotaroli.scored.pojos.Player;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for the RecyclerView that contains the players' score.
 */
public class CountAdapter extends RecyclerView.Adapter<CountAdapter.ViewHolder> {

    private final RecyclerItemClickListener clickListener;
    private final RecyclerItemLongClickListener longClickListener;
    private List<Player> players;
    private int height;
    private int width;

    public CountAdapter(List<Player> players, RecyclerItemClickListener clickListener, RecyclerItemLongClickListener longClickListener) {
        this.players = players;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score, parent, false);
        ViewHolder vh = new ViewHolder(v);

        height = ((Activity) parent.getContext()).getWindowManager().getDefaultDisplay().getHeight();
        width = ((Activity) parent.getContext()).getWindowManager().getDefaultDisplay().getWidth();

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Player p = players.get(position);
        if (p.getName().isEmpty()) {
            holder.playerNameTv.setVisibility(View.GONE);
        } else {
            holder.playerNameTv.setText(p.getName());
        }
        holder.scoreTv.setText(String.format(Locale.US, "%d", p.getScore()));
        holder.itemView.setBackgroundColor(p.getColor());

        TableLayout.LayoutParams params = getLayoutParams(position);
        holder.itemView.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    private TableLayout.LayoutParams getLayoutParams(int position) {
        int itemCount = getItemCount();
        if (itemCount <= 2) {
            return new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } else if (itemCount % 2 == 0) {
            if (position == getItemCount() - 1 || position == getItemCount() - 2) {
                int heightUsed = (getItemCount() / 2 - 1) * (height / (getItemCount() / 2));
                return new TableLayout.LayoutParams(width / 2, height - heightUsed);
            } else {
                return new TableLayout.LayoutParams(width / 2, height / (getItemCount() / 2));
            }
        } else {
            if (position == getItemCount() - 1) {
                int heightUsed = (getItemCount() - 1) * (height / getItemCount());
                return new TableLayout.LayoutParams(width / 2, height - heightUsed);
            } else {
                return new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height / getItemCount());
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.player_name_textView)
        TextView playerNameTv;
        @BindView(R.id.score_textView)
        TextView scoreTv;

        ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            longClickListener.onLongClick(view, getAdapterPosition());
            return true;
        }
    }
}
