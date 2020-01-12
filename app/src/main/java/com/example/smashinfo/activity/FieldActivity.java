package com.example.smashinfo.activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smashinfo.R;
import com.example.smashinfo.data.DataCard;
import com.example.smashinfo.data.DataSmasheurCard;
import com.example.smashinfo.data.DeckGestion;
import com.example.smashinfo.game.Card;
import com.example.smashinfo.game.Player;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FieldActivity extends AppCompatActivity {

    public static String partieKey;
    public String role, nomHoster, nomJoiner;
    public Player hoster, joiner, player;
    private Button mainButton, deckButton, extraDeckButton, cimetiereButton;
    public ImageButton A1, A2, A3, A4, B1, B2, B3, B4, C1, C2, C3, C4, D1, D2, D3, D4;
    public ImageView M1, M2, M3, M4, M5, M6, M7, M8, M9, retour;
    private TextView pv, pvAdverse;
    private DatabaseReference refGeneral = FirebaseDatabase.getInstance().getReference(), refPartie;
    private FirebaseUser user;
    public int phase, turn;
    public GridLayout gridHand;
    public List<ImageView> handImages;
    private ConstraintLayout.LayoutParams layoutParamsRetour, layoutParamsClose;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference().child("cartes");
    private Uri file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);

        gridHand = findViewById(R.id.gridHand);

        M1 = findViewById(R.id.M1);
        M2 = findViewById(R.id.M2);
        M3 = findViewById(R.id.M3);
        M4 = findViewById(R.id.M4);
        M5 = findViewById(R.id.M5);
        M6 = findViewById(R.id.M6);
        M7 = findViewById(R.id.M7);
        M8 = findViewById(R.id.M8);
        M9 = findViewById(R.id.M9);
        retour = findViewById(R.id.retour);

        handImages = new ArrayList<>();

        handImages.add(M1);
        handImages.add(M2);
        handImages.add(M3);
        handImages.add(M4);
        handImages.add(M5);
        handImages.add(M6);
        handImages.add(M7);
        handImages.add(M8);
        handImages.add(M9);

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
                try {
                    main();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        turn = 0;

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


        hoster = new Player(MainMenuActivity.HOSTER, 500, this);
        joiner = new Player(MainMenuActivity.JOINER, 500, this);
        if(role.equals(MainMenuActivity.HOSTER)) {
            refPartie.child(MainMenuActivity.HOSTER).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.hasChild("attaque")) {
                        hoster.getDeck().addCarte(dataSnapshot.getValue(DataSmasheurCard.class), dataSnapshot.getKey(), dataSnapshot.child("id").getValue().toString());
                    }
                    if (hoster.getDeck().getNbCards() == 29) {
                        hoster.makeHand();
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

            player = hoster;
        }
        if(role.equals(MainMenuActivity.JOINER)) {
            refPartie.child(MainMenuActivity.JOINER).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.hasChild("attaque")) {
                        joiner.getDeck().addCarte(dataSnapshot.getValue(DataSmasheurCard.class), dataSnapshot.getKey(), dataSnapshot.child("id").getValue().toString());
                    }
                    if (joiner.getDeck().getNbCards() == 29) {
                        joiner.makeHand();
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

            player = joiner;
        }
    }
/*
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        layoutParamsRetour = new ConstraintLayout.LayoutParams(retour.getWidth(), retour.getHeight());
        layoutParamsClose = new ConstraintLayout.LayoutParams(0, 0);
        retour.setLayoutParams(layoutParamsClose);
    }
*/
    private void main() throws IOException {
        Toast.makeText(this, "main : " + player.getHand().size(), Toast.LENGTH_SHORT).show();
        //retour.setLayoutParams(layoutParamsRetour);
        for (int i = 0; i < player.getHand().size(); i++) {
            Card card = player.getHand().get(i);
            StorageReference imageRef = card.getImageRef();
            File localFile = File.createTempFile(card.getCardKey(), "jpeg");
            imageRef.getFile(localFile).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "echec de l'upload", Toast.LENGTH_SHORT).show();

                }
            });

            //Toast.makeText(this, file.toString(), Toast.LENGTH_SHORT).show();
            handImages.get(i).setImageURI(Uri.fromFile(localFile));
            handImages.get(i).setImageResource(R.drawable.square);
            retour.setImageURI(Uri.fromFile(localFile));
        }
    }

    private void deck() {
        if (role.equals(MainMenuActivity.HOSTER)) {
            int val = hoster.getDeck().getNbCards();
            Toast.makeText(this, "nb cartes : " + val, Toast.LENGTH_SHORT).show();
        }
        if (role.equals(MainMenuActivity.JOINER)) {
            int val = joiner.getDeck().getNbCards();
            Toast.makeText(this, "nb cartes : " + val, Toast.LENGTH_SHORT).show();
        }
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
