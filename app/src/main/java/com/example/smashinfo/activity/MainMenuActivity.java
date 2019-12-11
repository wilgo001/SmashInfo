package com.example.smashinfo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smashinfo.R;
import com.example.smashinfo.data.Partie;
import com.example.smashinfo.game.FieldActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class
MainMenuActivity extends AppCompatActivity {

    public static final String JOINER_NAME = "searching";
    public static final String PARTIES = "parties";
    public static final String MESSAGE = "Veuillez patientez, nous recherchons une partie\n";
    public static final String HOSTER_NAME = "hosterName";
    public static final String PARTIE_KEY = "com.example.smashinfo.PARTIE_KEY";
    private Button buttonDeconnexion, createGame, loadGame, startGame, kickButton;
    private EditText pseudo;
    private DatabaseReference refGeneral, refPartie, refUser;
    private String message, partieKey;
    private TextView hosterName;
    private ImageButton combat, deck, pageAccueil, lootBox, parametres;
    private ConstraintLayout accueilLayout, lootLayout, deckLayout, lobbyLayout, setPartieLayout;
    FirebaseUser user;
    private ValueEventListener createdPartie;
    private int step;
    private CheckBox hostercheck, joinercheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        user = FirebaseAuth.getInstance().getCurrentUser();

        buttonDeconnexion = (Button) findViewById(R.id.buttonDeconnexion);
        createGame = (Button) findViewById(R.id.createGame);
        loadGame = (Button) findViewById(R.id.loadGame);
        pseudo = (EditText) findViewById(R.id.pseudo);
        startGame = findViewById(R.id.startPartie);

        refGeneral = FirebaseDatabase.getInstance().getReference();
        refUser = refGeneral.child(SignInActivity.USERS).child(user.getUid());

        combat = (ImageButton) findViewById(R.id.combat);
        deck = (ImageButton) findViewById(R.id.deck);
        pageAccueil = (ImageButton) findViewById(R.id.pageAccueil);
        lootBox = (ImageButton) findViewById(R.id.lootBox);
        parametres = (ImageButton) findViewById(R.id.parametres);

        hosterName = findViewById(R.id.hosterName);

        accueilLayout = findViewById(R.id.accueilLayout);
        lootLayout = findViewById(R.id.lootLayout);
        deckLayout = findViewById(R.id.deckLayout);
        lobbyLayout = findViewById(R.id.lobbylayout);
        setPartieLayout = findViewById(R.id.setpartieLayout);

        hostercheck = findViewById(R.id.checkBox2);
        joinercheck = findViewById(R.id.checkBox1);
        kickButton = findViewById(R.id.kickButton);
        hosterName = findViewById(R.id.hosterName);
        kickButton.setAlpha(0F);

        step = 0;

        createdPartie = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Partie partie = dataSnapshot.getValue(Partie.class);
                switch (step) {
                    case 0:
                        hosterName.setText(partie.hosterName);
                        joinercheck.setClickable(false);
                        step++;
                        loadPartie();
                        break;
                    case 1:
                        if (!partie.getJoinerName().equals(JOINER_NAME)) {
                            kickButton.setAlpha(1F);
                            joinercheck.setChecked(partie.joinerReady);
                            kickButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    partie.joinerName = JOINER_NAME;
                                }
                            });
                            partie.hosterReady = hostercheck.isChecked();
                        }else {
                            combat();
                            if (refPartie!=null) {
                                refPartie.removeEventListener(createdPartie);
                                refPartie.removeValue();
                                step = 0;

                            }
                        }
                        break;
                }
                refPartie.setValue(partie);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        buttonDeconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                quitApp();
            }
        });

        createGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pseudo.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "complete pseudo please", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Partie partie = new Partie(pseudo.getText().toString(), JOINER_NAME, "white", false, false, false);
                refPartie = refGeneral.child(PARTIES).push();
                refPartie.addValueEventListener(createdPartie);
                refPartie.setValue(partie);
                loadPartie();

            }
        });

        final ChildEventListener partieAdded = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Partie partie = dataSnapshot.getValue(Partie.class);
                    if ((partie.joinerName.equals(JOINER_NAME)) && (!partie.hosterName.equals(pseudo.getText().toString()))) {
                        partie.joinerName = pseudo.getText().toString();
                        partieKey = dataSnapshot.getKey();
                        refGeneral.child(PARTIES).child(partieKey).setValue(partie);
                        hostercheck.setClickable(false);
                        loadPartie();
                    }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (partieKey.equals(data.getKey())) {
                        Partie partie = data.getValue(Partie.class);
                        hostercheck.setChecked(partie.hosterReady);
                        if (partie.joinerName.equals(JOINER_NAME)) {
                            Toast.makeText(getApplicationContext(), "vous avez été expulsé de la partie", Toast.LENGTH_LONG).show();
                            refGeneral.removeEventListener(this);
                            combat();
                        }
                        partie.hosterReady = hostercheck.isChecked();
                        refGeneral.child(PARTIES).child(partieKey).setValue(partie);
                    }
                }
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
        };

        loadGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = MESSAGE;
                final AlertDialog alertDialog = new AlertDialog.Builder(MainMenuActivity.this).create();
                alertDialog.setTitle("Recherche en cours");
                alertDialog.setMessage(message);
                refGeneral.child(PARTIES).addChildEventListener(partieAdded);
                alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        refGeneral.removeEventListener(partieAdded);
                        step = 0;
                    }
                });

                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (message.equals(MESSAGE + "..."))
                                    message = MESSAGE;
                                else
                                    message = message + ".";
                                alertDialog.setMessage(message);
                            }
                        });
                    }
                };
                alertDialog.show();
                timer.schedule(timerTask, 0, 1000);
            }
        });


        buttonDeconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                quitApp();
            }
        });


        combat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                combat();
            }
        });

        deck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deck();
            }
        });

        pageAccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageAccueil();
            }
        });

        lootBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lootBox();
            }
        });

        parametres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parametres();
            }
        });

        combat();
    }

    private void combat(){
        accueilLayout.setAlpha(0F);
        lootLayout.setAlpha(0F);
        deckLayout.setAlpha(0F);
        lobbyLayout.setAlpha(1F);
        setPartieLayout.setAlpha(0F);
    }

    private void deck(){
        accueilLayout.setAlpha(0F);
        lootLayout.setAlpha(0F);
        deckLayout.setAlpha(1F);
        lobbyLayout.setAlpha(0F);
        setPartieLayout.setAlpha(0F);
    }

    private void pageAccueil(){
        accueilLayout.setAlpha(1F);
        lootLayout.setAlpha(0F);
        deckLayout.setAlpha(0F);
        lobbyLayout.setAlpha(0F);
        setPartieLayout.setAlpha(0F);
    }

    private void lootBox(){
        accueilLayout.setAlpha(0F);
        lootLayout.setAlpha(1F);
        deckLayout.setAlpha(0F);
        lobbyLayout.setAlpha(0F);
        setPartieLayout.setAlpha(0F);
    }

    private void parametres(){
        //TODO:changer d'activity
    }

    private void loadPartie() {
        accueilLayout.setAlpha(0F);
        lootLayout.setAlpha(0F);
        deckLayout.setAlpha(0F);
        lobbyLayout.setAlpha(0F);
        setPartieLayout.setAlpha(1F);
        kickButton.setAlpha(0F);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPartie();
            }
        });
    }

    public void annuler(View v) {
        combat();
        if (refPartie!=null) {
            refPartie.removeEventListener(createdPartie);
            refPartie.removeValue();
            step = 0;

        }
    }

    public void startPartie() {
        Intent myIntent = new Intent(this, FieldActivity.class);
        myIntent.putExtra(PARTIE_KEY, partieKey);
        startActivity(myIntent);
    }

    private void quitApp() {
        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
    }

}
