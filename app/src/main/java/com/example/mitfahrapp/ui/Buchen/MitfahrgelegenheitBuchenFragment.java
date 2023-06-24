package com.example.mitfahrapp.ui.Buchen;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mitfahrapp.DBHelper;
import com.example.mitfahrapp.Mitfahrgelegenheit;
import com.example.mitfahrapp.MitfahrgelegenheitAdapter;
import com.example.mitfahrapp.R;
import com.example.mitfahrapp.SessionManager;

import java.util.ArrayList;
import java.util.List;


public class MitfahrgelegenheitBuchenFragment extends Fragment implements MitfahrgelegenheitAdapter.OnAddPassengerClickListener {
    private RecyclerView recyclerView;
    private MitfahrgelegenheitAdapter adapter;
    private List<Mitfahrgelegenheit> mitfahrgelegenheitList;
    private DBHelper dbHelper;
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mitfahrgelegenheit, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        mitfahrgelegenheitList = new ArrayList<>();
        adapter = new MitfahrgelegenheitAdapter(getActivity(), mitfahrgelegenheitList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        dbHelper = new DBHelper(getActivity());
        sessionManager = new SessionManager(getActivity());

        loadMitfahrgelegenheiten();

        return root;
    }

    private void loadMitfahrgelegenheiten() {
        mitfahrgelegenheitList.clear();
        Cursor cursor = dbHelper.getAllMitfahrgelegenheiten();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String start = cursor.getString(cursor.getColumnIndexOrThrow("start"));
                String ziel = cursor.getString(cursor.getColumnIndexOrThrow("ziel"));
                String mitfahrer = cursor.getString(cursor.getColumnIndexOrThrow("mitfahrer"));
                Mitfahrgelegenheit mitfahrgelegenheit = new Mitfahrgelegenheit(id, username, start, ziel, mitfahrer);
                mitfahrgelegenheitList.add(mitfahrgelegenheit);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAddPassengerClick(int mitfahrgelegenheitId) {
        String currentUser = sessionManager.getUsername();

        // Mitfahrer zur Mitfahrgelegenheit hinzufügen
        boolean success = dbHelper.addMitfahrer(mitfahrgelegenheitId, currentUser);

        if (success) {
            // Mitfahrer erfolgreich hinzugefügt
            Toast.makeText(getActivity(), "Fahrt gebucht", Toast.LENGTH_SHORT).show();
            loadMitfahrgelegenheiten(); // Aktualisiere die Liste der Mitfahrgelegenheiten
        } else {
            // Fehler beim Hinzufügen des Mitfahrers
            Toast.makeText(getActivity(), "Fehler beim buchen der Fahrt", Toast.LENGTH_SHORT).show();
        }
    }
}