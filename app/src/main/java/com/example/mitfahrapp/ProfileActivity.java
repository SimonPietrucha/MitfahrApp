package com.example.mitfahrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class ProfileActivity extends AppCompatActivity {
    TextView textViewUsername;
    Button button, ausloggbutton;
    DBHelper DB;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sessionManager = new SessionManager(getApplicationContext());
        button = (Button) findViewById(R.id.button2);
        ausloggbutton = (Button) findViewById(R.id.buttonLogout);
        textViewUsername = (TextView) findViewById(R.id.textView6);

        DB = new DBHelper(this);

        String username = sessionManager.getUsername();
        textViewUsername.setText(username);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        ausloggbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
                Toast.makeText(ProfileActivity.this, "Ausgeloggt!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(intent);
            }
        });

    }
    protected void onDestroy() {
        super.onDestroy();

        // Session Manager zurücksetzen
        sessionManager.logout();
    }

}