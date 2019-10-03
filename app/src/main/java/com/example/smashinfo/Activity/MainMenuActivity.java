package com.example.smashinfo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smashinfo.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainMenuActivity extends AppCompatActivity {

    private Button buttonDeconnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        buttonDeconnexion = (Button) findViewById(R.id.buttonDeconnexion);

        buttonDeconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                quitApp();
            }
        });

    }

    private void quitApp() {
        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
    }
}
