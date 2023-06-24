package com.example.mitfahrapp.ui.Verwalten;

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
import com.example.mitfahrapp.R;
import com.example.mitfahrapp.SessionManager;
import com.example.mitfahrapp.VerwaltenAdapter;

import java.util.ArrayList;
import java.util.List;

public class VerwaltenFragment extends Fragment implements VerwaltenAdapter.OnDeleteMitfahrgelegenheitClickListener {

    private RecyclerView recyclerView;
    private VerwaltenAdapter adapter;
    private List<Mitfahrgelegenheit> mitfahrgelegenheitList;
    private DBHelper dbHelper;
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_verwalten, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        mitfahrgelegenheitList = new ArrayList<>();
        adapter = new VerwaltenAdapter(getActivity(), mitfahrgelegenheitList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        dbHelper = new DBHelper(getActivity());
        sessionManager = new SessionManager(getActivity());

        loadMitfahrgelegenheiten();

        return root;
    }

    private void loadMitfahrgelegenheiten() {
        mitfahrgelegenheitList.clear();
        String currentUser = sessionManager.getUsername();
            Cursor cursor = dbHelper.getMitfahrgelegenheitenByUsername(currentUser);
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
    public void onDeleteMitfahrgelegenheitClick(int mitfahrgelegenheitId) {
        boolean success = dbHelper.deleteMitfahrgelegenheit(mitfahrgelegenheitId);

        if (success) {
            Toast.makeText(getActivity(), "Mitfahrgelegenheit gelöscht", Toast.LENGTH_SHORT).show();
            loadMitfahrgelegenheiten();
        } else {
            Toast.makeText(getActivity(), "Fehler beim Löschen der Mitfahrgelegenheit", Toast.LENGTH_SHORT).show();
        }
    }
}