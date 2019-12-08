package com.example.smashinfo.game;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.smashinfo.R;
import com.example.smashinfo.activity.MainMenuActivity;
import com.google.firebase.database.FirebaseDatabase;

public class FieldActivity extends AppCompatActivity {

    public static String partieKey;
    public Player player1;
    public Player player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_field);

        Intent intent = this.getIntent();
        partieKey = intent.getStringExtra(MainMenuActivity.PARTIE_KEY);

        //Toast toast = Toast.makeText(getApplicationContext(), "key : " + partieKey, Toast.LENGTH_SHORT);
        //toast.show();
        hideNavigationBar();

        Spinner phaseJeu = (Spinner) findViewById(R.id.phaseJeu);

        ArrayAdapter<String> leAdaptater = new ArrayAdapter<String>(FieldActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.phase_jeu));
        leAdaptater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phaseJeu.setAdapter(leAdaptater);
    }


    private void hideNavigationBar() {
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }
}
