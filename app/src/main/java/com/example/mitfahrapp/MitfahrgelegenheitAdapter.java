package com.example.mitfahrapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MitfahrgelegenheitAdapter extends RecyclerView.Adapter<MitfahrgelegenheitAdapter.ViewHolder> {
    private Context context;
    private List<Mitfahrgelegenheit> mitfahrgelegenheitList;

    public MitfahrgelegenheitAdapter(Context context, List<Mitfahrgelegenheit> mitfahrgelegenheitList) {
        this.context = context;
        this.mitfahrgelegenheitList = mitfahrgelegenheitList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mitfahrgelegenheit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mitfahrgelegenheit mitfahrgelegenheit = mitfahrgelegenheitList.get(position);
        holder.textViewUsername.setText(mitfahrgelegenheit.getUsername());
        holder.textViewStart.setText(mitfahrgelegenheit.getStart());
        holder.textViewZiel.setText(mitfahrgelegenheit.getZiel());
    }

    @Override
    public int getItemCount() {
        return mitfahrgelegenheitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewUsername, textViewStart, textViewZiel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            textViewStart = itemView.findViewById(R.id.textViewStart);
            textViewZiel = itemView.findViewById(R.id.textViewZiel);
        }
    }
}