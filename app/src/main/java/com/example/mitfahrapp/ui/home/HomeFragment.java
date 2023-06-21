package com.example.mitfahrapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mitfahrapp.DBHelper;
import com.example.mitfahrapp.LoginActivity;
import com.example.mitfahrapp.MainActivity;
import com.example.mitfahrapp.R;
import com.example.mitfahrapp.SessionManager;
import com.example.mitfahrapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    EditText starten, ende;
    Button hinzufügen;
    DBHelper DB;
    SessionManager sessionManager;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        hinzufügen = binding.button3;
        starten = binding.textView13;
        ende = binding.textView15;
        DB = new DBHelper(getActivity());
        sessionManager = new SessionManager(getActivity());


        String username = sessionManager.getUsername();

        hinzufügen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start = starten.getText().toString();
                String ziel = ende.getText().toString();

                if(start.equals("") || ziel.equals("")) {
                    Toast.makeText(getActivity(), "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
                } else {
                    boolean updatesuccess = DB.updateStartAndZiel(username, start, ziel);
                    if(updatesuccess){
                        Toast.makeText(getActivity(), "Mitfahrgegenheit erstellt", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Fehler beim Aktualisieren von Start und Ziel", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}