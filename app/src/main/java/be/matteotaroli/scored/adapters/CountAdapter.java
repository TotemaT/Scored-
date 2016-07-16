package be.matteotaroli.scored.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import be.matteotaroli.scored.Listeners.RecyclerItemClickListener;
import be.matteotaroli.scored.Listeners.RecyclerItemLongClickListener;
import be.matteotaroli.scored.R;
import be.matteotaroli.scored.pojos.Player;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matt on 16/07/16.
 */

public class CountAdapter extends RecyclerView.Adapter<CountAdapter.ViewHolder> {

    private static final String TAG = "CountAdapter";

    private List<Player> players;

    private RecyclerItemClickListener clickListener;
    private RecyclerItemLongClickListener longClickListener;

    public CountAdapter(List<Player> players, RecyclerItemClickListener clickListener, RecyclerItemLongClickListener longClickListener) {
        this.players = players;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Player p = players.get(position);
        holder.playerNameTv.setText(p.getName());
        holder.scoreTv.setText("" + p.getScore());
        holder.itemView.setBackgroundColor(p.getColor());
    }

    @Override
    public int getItemCount() {
        return players.size();
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
