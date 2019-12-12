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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smashinfo.R;
import com.example.smashinfo.data.Partie;
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
    private Button buttonDeconnexion, createGame, loadGame, startGame, kickButton, validerVolume, tutoriel;
    private EditText pseudo;
    private DatabaseReference refGeneral, refPartie, refUser;
    private String message, partieKey;
    private TextView hosterName, joinerName;
    private ImageButton combat, deck, pageAccueil, lootBox, parametres;
    private ConstraintLayout accueilLayout, lootLayout, deckLayout, lobbyLayout, setPartieLayout, parametresLayout;
    FirebaseUser user;
    private SeekBar seekBarMusique, seekBarEffet;
    private ValueEventListener createdPartie;
    private int step;
    private CheckBox hostercheck, joinercheck;
    private ValueEventListener joinedPartie;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        user = FirebaseAuth.getInstance().getCurrentUser();

        buttonDeconnexion = (Button) findViewById(R.id.deconnexion);
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
        parametresLayout = findViewById(R.id.parametresLayout);

        hostercheck = findViewById(R.id.checkBox2);
        joinercheck = findViewById(R.id.checkBox1);
        kickButton = findViewById(R.id.kickButton);
        hosterName = findViewById(R.id.hosterName);
        joinerName = findViewById(R.id.joinerName);
        kickButton.setAlpha(0F);

        step = 0;

        createdPartie = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Partie partie = dataSnapshot.getValue(Partie.class);
                if (partie.start) {
                    startPartie();
                }
                switch (step) {
                    case 0:
                        hosterName.setText(partie.hosterName);
                        joinercheck.setClickable(false);
                        step++;
                        loadPartie();
                        startGame.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (joinercheck.isChecked() && hostercheck.isChecked()) {
                                    refPartie.child("start").setValue(true);
                                }
                            }
                        });
                        break;
                    case 1:
                        joinerName.setText(partie.joinerName);
                        if (!partie.getJoinerName().equals(JOINER_NAME)) {
                            kickButton.setAlpha(1F);
                            joinerName.setText(partie.joinerName);
                            joinercheck.setChecked(partie.joinerReady);
                            kickButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    refPartie.child("joinerName").setValue(JOINER_NAME);
                                    kickButton.setAlpha(0f);
                                }
                            });
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
                    refPartie = refGeneral.child(PARTIES).child(partieKey);
                    refPartie.setValue(partie);
                    hostercheck.setClickable(false);
                    hosterName.setText(partie.hosterName);
                    refPartie.addValueEventListener(joinedPartie);
                    if (alertDialog!=null && alertDialog.isShowing()) {
                        alertDialog.dismiss();
                        refGeneral.removeEventListener(this);
                    }
                    loadPartie();
                }
                if ((partie.joinerName.equals(JOINER_NAME)) && (!partie.hosterName.equals(pseudo.getText().toString()))) {
                    partie.joinerName = pseudo.getText().toString();
                    partieKey = dataSnapshot.getKey();
                    refGeneral.child(PARTIES).child(partieKey).setValue(partie);
                    hostercheck.setClickable(false);
                    loadPartie();
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };

        joinedPartie = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Partie partie = dataSnapshot.getValue(Partie.class);
                if (partie.start) {
                    startPartie();
                }
                hostercheck.setChecked(partie.hosterReady);
                if (partie.joinerName.equals(JOINER_NAME)) {
                    Toast.makeText(getApplicationContext(), "vous avez été expulsé de la partie", Toast.LENGTH_LONG).show();
                    refGeneral.removeEventListener(this);
                    combat();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        loadGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = MESSAGE;
                alertDialog = new AlertDialog.Builder(MainMenuActivity.this).create();
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

        this.seekBarMusique = (SeekBar) findViewById(R.id.seekBarMusique);
        this.seekBarEffet = (SeekBar) findViewById(R.id.seekBarEffet);
        this.validerVolume = (Button) findViewById(R.id.valider);

        validerVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validerVolume();
            }
        });

        tutoriel = findViewById(R.id.tutoriel);

        this.tutoriel.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                tuto();
            }
        });
    }

    private void tuto(){
        Intent intent=new Intent(this, TutorielActivity.class);
        startActivity(intent);
    }

    private void combat() {
        accueilLayout.setAlpha(0F);
        lootLayout.setAlpha(0F);
        deckLayout.setAlpha(0F);
        lobbyLayout.setAlpha(1F);
        setPartieLayout.setAlpha(0F);
        parametresLayout.setAlpha(0F);
    }

    private void deck() {
        accueilLayout.setAlpha(0F);
        lootLayout.setAlpha(0F);
        deckLayout.setAlpha(1F);
        lobbyLayout.setAlpha(0F);
        setPartieLayout.setAlpha(0F);
        parametresLayout.setAlpha(0F);
    }

    private void pageAccueil() {
        accueilLayout.setAlpha(1F);
        lootLayout.setAlpha(0F);
        deckLayout.setAlpha(0F);
        lobbyLayout.setAlpha(0F);
        setPartieLayout.setAlpha(0F);
        parametresLayout.setAlpha(0F);
    }

    private void lootBox() {
        accueilLayout.setAlpha(0F);
        lootLayout.setAlpha(1F);
        deckLayout.setAlpha(0F);
        lobbyLayout.setAlpha(0F);
        setPartieLayout.setAlpha(0F);
        parametresLayout.setAlpha(0F);
    }

    private void parametres() {
        accueilLayout.setAlpha(0F);
        lootLayout.setAlpha(0F);
        deckLayout.setAlpha(0F);
        lobbyLayout.setAlpha(0F);
        setPartieLayout.setAlpha(0F);
        parametresLayout.setAlpha(1F);
    }

    private void loadPartie() {
        accueilLayout.setAlpha(0F);
        lootLayout.setAlpha(0F);
        deckLayout.setAlpha(0F);
        lobbyLayout.setAlpha(0F);
        setPartieLayout.setAlpha(1F);
        kickButton.setAlpha(0F);
        parametresLayout.setAlpha(0F);


        joinercheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                refPartie.child("joinerReady").setValue(b);
            }
        });

        hostercheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                refPartie.child("hosterReady").setValue(b);
            }
        });


        ValueEventListener createdPartie = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Partie partie = dataSnapshot.getValue(Partie.class);
                if (!partie.getJoinerName().equals(JOINER_NAME)) {
                    partie.setStart(true);
                    refGeneral.child(PARTIES).child(partieKey).setValue(partie);
                    loadPartie();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

    }

    public void annuler(View v) {
        combat();
        refPartie.child("joinerName").setValue(JOINER_NAME);
        if (refPartie!=null) {
            refPartie.removeEventListener(createdPartie);
            refPartie.removeValue();
            step = 0;

        }
    }

    public void startPartie() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(MainMenuActivity.this, FieldActivity.class);
                //myIntent.putExtra(PARTIE_KEY, partieKey);
                startActivity(myIntent);
            }
        });

        Toast.makeText(getApplicationContext(), "lancement de la partie", Toast.LENGTH_SHORT).show();
    }

    private void quitApp() {
        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
    }

    private void validerVolume() {
        int volumeMusique = seekBarMusique.getProgress();
        int voluleEffet = seekBarEffet.getProgress();
    }



}
