package com.example.smashinfo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smashinfo.R;
import com.example.smashinfo.data.DataCard;
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
    private final static DatabaseReference CARDREF = FirebaseDatabase.getInstance().getReference().child("cartes");
    public static final String JOINER = "joiner";
    public static final String HOSTER = "hoster";
    public static final String ROLE = "role";
    public static final String DECK_NAME = "deckName";
    private Button buttonDeconnexion, createGame, loadGame, startGame, kickButton, validerVolume, tutoriel, annuler;
    private EditText pseudo;
    private DatabaseReference refGeneral, refPartie, refUser;
    private String message, partieKey;
    private TextView hosterName, joinerName;
    private ImageButton combat, deck, pageAccueil, lootBox, parametres;
    private ConstraintLayout accueilLayout, lootLayout, deckLayout, lobbyLayout, setPartieLayout, parametresLayout;
    private FirebaseUser user;
    private SeekBar seekBarMusique, seekBarEffet;
    private ValueEventListener createdPartie;
    private int step;
    private CheckBox hostercheck, joinercheck;
    private ValueEventListener joinedPartie;
    private AlertDialog alertDialog;
    private ColorDrawable SELECTED_BUTTON_BACKGROUND = new ColorDrawable();
    private Drawable background;
    private DataCard dataCard;
    private Spinner choixDeck;
    private DatabaseReference refDeck;
    public static int musique, effet;
    private String role;
    private String deckName;
    private ConstraintLayout.LayoutParams layoutParamsOpen, layoutParamsClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        SELECTED_BUTTON_BACKGROUND.setColor(Color.BLUE);

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
        annuler = findViewById(R.id.annulation);
        kickButton.setAlpha(0F);

        choixDeck = findViewById(R.id.choixDeck);

        background = getDrawable(R.drawable.default_button);

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
                        role = HOSTER;
                        partieKey = dataSnapshot.getKey();
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
                        }else {
                            kickButton.setAlpha(0f);
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
                partieKey = refPartie.getKey();
                refPartie.addValueEventListener(createdPartie);
                refPartie.setValue(partie);
                annuler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        annuler();
                    }
                });
                loadPartie();

            }
        });

        final ChildEventListener partieAdded = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Partie partie = dataSnapshot.getValue(Partie.class);
                if ((partie.joinerName.equals(JOINER_NAME)) && (!partie.hosterName.equals(pseudo.getText().toString()))) {
                    partie.joinerName = pseudo.getText().toString();
                    joinerName.setText(partie.joinerName);
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
                role = JOINER;
                hostercheck.setChecked(partie.hosterReady);
                if (partie.joinerName.equals(JOINER_NAME)) {
                    Toast.makeText(getApplicationContext(), "vous avez été expulsé de la partie", Toast.LENGTH_LONG).show();
                    refPartie.removeEventListener(this);
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

                annuler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refPartie.child("joinerName").setValue(JOINER_NAME);
                    }
                });
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
                doSave(view);
            }
        });

        tutoriel = findViewById(R.id.tutoriel);

        this.tutoriel.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                tuto();
            }
        });

        this.loadGameSetting();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        layoutParamsOpen = new ConstraintLayout.LayoutParams(accueilLayout.getWidth(), accueilLayout.getHeight());
        layoutParamsClose = new ConstraintLayout.LayoutParams(0, 0);
        //pageAccueil();
    }

    private void tuto(){
        Intent intent=new Intent(this, TutorielActivity.class);
        startActivity(intent);
    }
//TODO
    private void combat() {
        combat.setBackground(SELECTED_BUTTON_BACKGROUND);
        deck.setBackground(background);
        pageAccueil.setBackground(background);
        lootBox.setBackground(background);
        parametres.setBackground(background);
        pseudo.setClickable(true);
        createGame.setClickable(true);
        loadGame.setClickable(true);
        /*accueilLayout.setAlpha(0F);
        accueilLayout.setLayoutParams(layoutParamsClose);
        lootLayout.setAlpha(0F);
        lootLayout.setLayoutParams(layoutParamsClose);
        deckLayout.setAlpha(0F);
        deckLayout.setLayoutParams(layoutParamsClose);
        lobbyLayout.setAlpha(1F);
        lobbyLayout.setLayoutParams(layoutParamsOpen);
        setPartieLayout.setAlpha(0F);
        setPartieLayout.setLayoutParams(layoutParamsClose);
        parametresLayout.setAlpha(0F);
        parametresLayout.setLayoutParams(layoutParamsClose);*/
    }
//TODO
    private void deck() {
        combat.setBackground(background);
        deck.setBackground(SELECTED_BUTTON_BACKGROUND);
        pageAccueil.setBackground(background);
        lootBox.setBackground(background);
        parametres.setBackground(background);
        pseudo.setClickable(false);
        createGame.setClickable(false);
        loadGame.setClickable(false);
        accueilLayout.setAlpha(0F);
        accueilLayout.setLayoutParams(layoutParamsClose);
        lootLayout.setAlpha(0F);
        lootLayout.setLayoutParams(layoutParamsClose);
        deckLayout.setAlpha(1F);
        deckLayout.setLayoutParams(layoutParamsOpen);
        lobbyLayout.setAlpha(0F);
        lobbyLayout.setLayoutParams(layoutParamsClose);
        setPartieLayout.setAlpha(0F);
        setPartieLayout.setLayoutParams(layoutParamsClose);
        parametresLayout.setAlpha(0F);
        parametresLayout.setLayoutParams(layoutParamsClose);
    }

    private void pageAccueil() {
        combat.setBackground(background);
        deck.setBackground(background);
        pageAccueil.setBackground(SELECTED_BUTTON_BACKGROUND);
        lootBox.setBackground(background);
        parametres.setBackground(background);
        pseudo.setClickable(false);
        createGame.setClickable(false);
        loadGame.setClickable(false);
        accueilLayout.setAlpha(1F);
        accueilLayout.setLayoutParams(layoutParamsOpen);
        lootLayout.setAlpha(0F);
        lootLayout.setLayoutParams(layoutParamsClose);
        deckLayout.setAlpha(0F);
        deckLayout.setLayoutParams(layoutParamsClose);
        lobbyLayout.setAlpha(0F);
        lobbyLayout.setLayoutParams(layoutParamsClose);
        setPartieLayout.setAlpha(0F);
        setPartieLayout.setLayoutParams(layoutParamsClose);
        parametresLayout.setAlpha(0F);
        parametresLayout.setLayoutParams(layoutParamsClose);
    }

    private void lootBox() {
        combat.setBackground(background);
        deck.setBackground(background);
        pageAccueil.setBackground(background);
        lootBox.setBackground(SELECTED_BUTTON_BACKGROUND);
        parametres.setBackground(background);
        pseudo.setClickable(false);
        createGame.setClickable(false);
        loadGame.setClickable(false);
        accueilLayout.setAlpha(0F);
        accueilLayout.setLayoutParams(layoutParamsClose);
        lootLayout.setAlpha(1F);
        lootLayout.setLayoutParams(layoutParamsOpen);
        deckLayout.setAlpha(0F);
        deckLayout.setLayoutParams(layoutParamsClose);
        lobbyLayout.setAlpha(0F);
        lobbyLayout.setLayoutParams(layoutParamsClose);
        setPartieLayout.setAlpha(0F);
        setPartieLayout.setLayoutParams(layoutParamsClose);
        parametresLayout.setAlpha(0F);
        parametresLayout.setLayoutParams(layoutParamsClose);
    }

    private void parametres() {
        combat.setBackground(background);
        deck.setBackground(background);
        pageAccueil.setBackground(background);
        lootBox.setBackground(background);
        parametres.setBackground(SELECTED_BUTTON_BACKGROUND);
        pseudo.setClickable(false);
        createGame.setClickable(false);
        loadGame.setClickable(false);
        accueilLayout.setAlpha(0F);
        accueilLayout.setLayoutParams(layoutParamsClose);
        lootLayout.setAlpha(0F);
        lootLayout.setLayoutParams(layoutParamsClose);
        deckLayout.setAlpha(0F);
        deckLayout.setLayoutParams(layoutParamsClose);
        lobbyLayout.setAlpha(0F);
        lobbyLayout.setLayoutParams(layoutParamsClose);
        setPartieLayout.setAlpha(0F);
        setPartieLayout.setLayoutParams(layoutParamsClose);
        parametresLayout.setAlpha(1F);
        parametresLayout.setLayoutParams(layoutParamsOpen);
    }
//TODO
    private void loadPartie() {
        pseudo.setClickable(false);
        createGame.setClickable(false);
        loadGame.setClickable(false);
        accueilLayout.setAlpha(0F);
        accueilLayout.setLayoutParams(layoutParamsClose);
        lootLayout.setAlpha(0F);
        lootLayout.setLayoutParams(layoutParamsClose);
        deckLayout.setAlpha(0F);
        deckLayout.setLayoutParams(layoutParamsClose);
        lobbyLayout.setAlpha(0F);
        lobbyLayout.setLayoutParams(layoutParamsClose);
        setPartieLayout.setAlpha(1F);
        setPartieLayout.setLayoutParams(layoutParamsOpen);
        parametresLayout.setAlpha(0F);
        parametresLayout.setLayoutParams(layoutParamsClose);


        joinercheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                refPartie.child("joinerReady").setValue(b);
                deckName = choixDeck.getSelectedItem().toString();
            }
        });

        hostercheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                refPartie.child("hosterReady").setValue(b);
                deckName = choixDeck.getSelectedItem().toString();
            }
        });

    }

    public void annuler() {
        combat();
        refPartie.child("joinerName").setValue(JOINER_NAME);
        if (refPartie!=null) {
            refPartie.removeEventListener(createdPartie);
            refPartie.removeValue();
            step = 0;
        }
    }

    public void startPartie() {
        refPartie.removeEventListener(createdPartie);
        refPartie.removeEventListener(joinedPartie);

        Intent myIntent = new Intent(MainMenuActivity.this, FieldActivity.class);
        myIntent.putExtra(PARTIE_KEY, partieKey);
        myIntent.putExtra(ROLE, role);
        myIntent.putExtra(DECK_NAME, deckName);
        startActivity(myIntent);
        Toast.makeText(getApplicationContext(), "lancement de la partie", Toast.LENGTH_SHORT).show();
    }

    private void quitApp() {
        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
    }

    private void loadGameSetting()  {
        SharedPreferences sharedPreferences= this.getSharedPreferences("gameSetting", Context.MODE_PRIVATE);

        if(sharedPreferences!= null) {

            int musique = sharedPreferences.getInt("musique",50);
            int effet = sharedPreferences.getInt("effet",50);


            this.seekBarMusique.setProgress(musique);
            this.seekBarEffet.setProgress(effet);

        } else {
            Toast.makeText(this,"Use the default game setting",Toast.LENGTH_LONG).show();
        }

    }

    public void doSave(View view)  {
        // The created file can only be accessed by the calling application
        // (or all applications sharing the same user ID).
        SharedPreferences sharedPreferences= this.getSharedPreferences("gameSetting", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("musique", this.seekBarMusique.getProgress());
        editor.putInt("effet", this.seekBarEffet.getProgress());

        // Save.
        editor.apply();

        Toast.makeText(this,"Paramètres sauvegardés",Toast.LENGTH_LONG).show();

        musique = seekBarMusique.getProgress();
        effet = seekBarEffet.getProgress();
    }

    @Override
    protected void onStart() {
        super.onStart();
        refUser.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //Toast.makeText(getApplicationContext(), dataSnapshot.toString(), Toast.LENGTH_LONG).show();
                if (dataSnapshot.getKey().equals("decks")) {
                    ArrayAdapter<CharSequence> list = new ArrayAdapter<CharSequence>(getApplicationContext(), R.layout.simple_list_item_1);
                    for (DataSnapshot dataDeck : dataSnapshot.getChildren()) {
                        list.add(dataDeck.getKey());
                    }
                    choixDeck.setAdapter(list);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
