package com.example.smashinfo.activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.smashinfo.data.DeckGestion;
import com.example.smashinfo.game.Player;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FieldActivity extends AppCompatActivity {

    public static String partieKey;
    public String role, nomHoster, nomJoiner;
    public Player hoster;
    public Player joiner;
    private Button mainButton, deckButton, extraDeckButton, cimetiereButton;
    public ImageButton A1, A2, A3, A4, B1, B2, B3, B4, C1, C2, C3, C4, D1, D2, D3, D4;
    private TextView pv, pvAdverse;
    private DatabaseReference refGeneral = FirebaseDatabase.getInstance().getReference(), refPartie;
    private FirebaseUser user;
    private int phase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);

        Intent intent = this.getIntent();
        partieKey = intent.getStringExtra(MainMenuActivity.PARTIE_KEY);
        role = intent.getStringExtra(MainMenuActivity.ROLE);
        String deckName = intent.getStringExtra(MainMenuActivity.DECK_NAME);
        Toast.makeText(getApplicationContext(), partieKey, Toast.LENGTH_LONG);
        user = FirebaseAuth.getInstance().getCurrentUser();

        refPartie = refGeneral.child(MainMenuActivity.PARTIES).child(partieKey);
        DatabaseReference refDeck = refPartie.child(role);
        DatabaseReference refUserDeck = refGeneral.child(SignInActivity.USERS).child(user.getUid()).child("decks").child(deckName);
        DeckGestion.moveDeckWithTitle(refUserDeck, refDeck);

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

        phase = 0;


        phaseJeu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO:switch case avec le i, i c'est l'index dans le tableau des données du spinner / changer le phase en fontion de la phase entre 1 et 4
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

        refPartie.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                switch(phase) {
                    case 0:
                        if (dataSnapshot.getKey().equals("hosterName"))
                            nomHoster = dataSnapshot.getValue(String.class);
                        if (dataSnapshot.getKey().equals("joinerName"))
                            nomJoiner = dataSnapshot.getValue(String.class);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        hoster = new Player(nomHoster, 500, this);
        joiner = new Player(nomJoiner, 500, this);
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
