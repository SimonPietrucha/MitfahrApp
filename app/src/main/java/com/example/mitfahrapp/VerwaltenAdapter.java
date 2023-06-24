package com.example.mitfahrapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VerwaltenAdapter extends RecyclerView.Adapter<VerwaltenAdapter.ViewHolder> {
    private Context context;
    private List<Mitfahrgelegenheit> mitfahrgelegenheitList;
    private OnDeleteMitfahrgelegenheitClickListener deleteMitfahrgelegenheitListener;

    public interface OnDeleteMitfahrgelegenheitClickListener {
        void onDeleteMitfahrgelegenheitClick(int mitfahrgelegenheitId);
    }

    public VerwaltenAdapter(Context context, List<Mitfahrgelegenheit> mitfahrgelegenheitList, OnDeleteMitfahrgelegenheitClickListener deleteMitfahrgelegenheitListener) {
        this.context = context;
        this.mitfahrgelegenheitList = mitfahrgelegenheitList;
        this.deleteMitfahrgelegenheitListener = deleteMitfahrgelegenheitListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_verwalten, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mitfahrgelegenheit mitfahrgelegenheit = mitfahrgelegenheitList.get(position);
        holder.textViewUsername.setText(mitfahrgelegenheit.getUsername());
        holder.textViewStart.setText(mitfahrgelegenheit.getStart());
        holder.textViewZiel.setText(mitfahrgelegenheit.getZiel());
        String mitfahrer = mitfahrgelegenheit.getMitfahrer();
        if (mitfahrer != null && !mitfahrer.isEmpty()) {
            holder.textViewMitfahrer.setVisibility(View.VISIBLE);
            holder.textViewMitfahrer.setText("Mitfahrer: " + mitfahrer);
        } else {
            holder.textViewMitfahrer.setVisibility(View.GONE);
        }

        holder.buttonDelete.setVisibility(View.VISIBLE);
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMitfahrgelegenheitListener.onDeleteMitfahrgelegenheitClick(mitfahrgelegenheit.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mitfahrgelegenheitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewUsername, textViewStart, textViewZiel, textViewMitfahrer;
        public Button buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            textViewStart = itemView.findViewById(R.id.textViewStart);
            textViewZiel = itemView.findViewById(R.id.textViewZiel);
            textViewMitfahrer = itemView.findViewById(R.id.textViewMitfahrer);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
