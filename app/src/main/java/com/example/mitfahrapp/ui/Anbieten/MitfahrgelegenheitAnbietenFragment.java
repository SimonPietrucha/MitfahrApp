package com.example.mitfahrapp.ui.Anbieten;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mitfahrapp.DBHelper;
import com.example.mitfahrapp.MainActivity;
import com.example.mitfahrapp.SessionManager;
import com.example.mitfahrapp.databinding.FragmentAnbietenBinding;

public class MitfahrgelegenheitAnbietenFragment extends Fragment {

    EditText starten, ende;
    Button hinzufügen;
    DBHelper DB;
    SessionManager sessionManager;

    private FragmentAnbietenBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAnbietenBinding.inflate(inflater, container, false);
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
                    boolean updatesuccess = DB.insertMitfahrgelegenheit(username, start, ziel);
                    if(updatesuccess){
                        Toast.makeText(getActivity(), "Mitfahrgelegenheit erstellt", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Mitfahrgelegenheit konnte nicht erstellt werden", Toast.LENGTH_SHORT).show();
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