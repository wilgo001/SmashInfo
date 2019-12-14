package com.example.smashinfo.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smashinfo.R;
import com.example.smashinfo.game.Player;

public class FieldActivity extends AppCompatActivity {

    public static String partieKey;
    public Player player1;
    public Player player2;
    private Button mainButton, deckButton, extraDeckButton, cimetiereButton;
    private ImageButton A1, A2, A3, A4, B1, B2, B3, B4, C1, C2, C3, C4, D1, D2, D3, D4;
    private TextView pv, pvAdverse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);

        Intent intent = this.getIntent();
        partieKey = intent.getStringExtra(MainMenuActivity.PARTIE_KEY);

        Toast toast = Toast.makeText(getApplicationContext(), "key : " + partieKey, Toast.LENGTH_SHORT);
        toast.show();

        hideNavigationBar();

        mainButton = findViewById(R.id.main);
        this.mainButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                main();
            }
        });

        deckButton = findViewById(R.id.deck);
        this.deckButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                deck();
            }
        });

        extraDeckButton = findViewById(R.id.extraDeck);
        this.extraDeckButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                extraDeck();
            }
        });

        cimetiereButton = findViewById(R.id.cimetiere);
        this.cimetiereButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                cimetiere();
            }
        });


        Spinner phaseJeu = (Spinner) findViewById(R.id.phaseJeu);

        ArrayAdapter<String> leAdaptater = new ArrayAdapter<String>(FieldActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.phase_jeu));
        leAdaptater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phaseJeu.setAdapter(leAdaptater);


        phaseJeu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO:switch case avec le i, i c'est l'index dans le tableau des données du spinner
                switch (i) {
                    case 0:
                        ;
                        break;
                    case 1:
                        ;
                        break;
                    case 2:
                        ;
                        break;
                    case 3:
                        ;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void main() {

    }

    private void deck() {

    }

    private void extraDeck() {

    }

    private void cimetiere() {

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void hideNavigationBar() {
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

}
