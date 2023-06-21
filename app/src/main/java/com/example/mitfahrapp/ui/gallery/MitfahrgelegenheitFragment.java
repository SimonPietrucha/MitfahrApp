package com.example.mitfahrapp.ui.gallery;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mitfahrapp.DBHelper;
import com.example.mitfahrapp.Mitfahrgelegenheit;
import com.example.mitfahrapp.MitfahrgelegenheitAdapter;
import com.example.mitfahrapp.R;

import java.util.ArrayList;
import java.util.List;

public class MitfahrgelegenheitFragment extends Fragment {
        private RecyclerView recyclerView;
        private MitfahrgelegenheitAdapter adapter;
        private List<Mitfahrgelegenheit> mitfahrgelegenheitList;
        private DBHelper dbHelper;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_mitfahrgelegenheit, container, false);

            recyclerView = root.findViewById(R.id.recyclerView);
            mitfahrgelegenheitList = new ArrayList<>();
            adapter = new MitfahrgelegenheitAdapter(getActivity(), mitfahrgelegenheitList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);

            dbHelper = new DBHelper(getActivity());
            loadMitfahrgelegenheiten();

            return root;
        }

    private void loadMitfahrgelegenheiten() {
        mitfahrgelegenheitList.clear();
        Cursor cursor = dbHelper.getAllMitfahrgelegenheiten();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID")); // Anpassung der Spaltennamen
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String start = cursor.getString(cursor.getColumnIndexOrThrow("start"));
                String ziel = cursor.getString(cursor.getColumnIndexOrThrow("ziel"));
                Mitfahrgelegenheit mitfahrgelegenheit = new Mitfahrgelegenheit(id, username, start, ziel);
                mitfahrgelegenheitList.add(mitfahrgelegenheit);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }


}