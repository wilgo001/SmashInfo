package com.example.smashinfo.activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smashinfo.R;
import com.example.smashinfo.data.DataCard;
import com.example.smashinfo.data.DataSmasheurCard;
import com.example.smashinfo.data.DeckGestion;
import com.example.smashinfo.game.Card;
import com.example.smashinfo.game.CardSmasheur;
import com.example.smashinfo.game.Deck;
import com.example.smashinfo.game.Player;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
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
    public DatabaseReference refGeneral = FirebaseDatabase.getInstance().getReference(), refPartie;
    private FirebaseUser user;
    public int phase, turn;
    public List<ImageView> handImages;
    private ConstraintLayout.LayoutParams layoutParamsHand, layoutParamsClose;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference().child("cartes");
    private Uri file;
    private int index;
    private Bitmap bitmap;

    private ConstraintLayout show1, gridHand;
    private TextView titre, archetype, descriptionCarte, attaque, defense;
    private Button action1, action2, annuler;
    private ImageView imageCarte;
    boolean myTurn, invoque, vide;
    private String ennmiPv;
    private int nbEnnemi;
    private CardSmasheur ec1, ec3;
    private CardSmasheur ec2, ec4;
    private Player adverser;

    public int lpJoueur, lpAdverse;


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

        show1 = findViewById(R.id.show1);
        titre = findViewById(R.id.titreCarte);
        archetype = findViewById(R.id.archetype);
        descriptionCarte = findViewById(R.id.descriptionCarte);
        attaque = findViewById(R.id.attaque);
        defense = findViewById(R.id.defense);
        action1 = findViewById(R.id.action1);
        action2 = findViewById(R.id.action2);
        annuler = findViewById(R.id.annuler);
        imageCarte = findViewById(R.id.imagecarte);

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show1.setLayoutParams(layoutParamsClose);
            }
        });

        nbEnnemi = 0;

        lpJoueur = 500;
        lpAdverse = 500;


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

        C1 = findViewById(R.id.C1);
        C2 = findViewById(R.id.C2);
        C3 = findViewById(R.id.C3);
        C4 = findViewById(R.id.C4);

        B1 = findViewById(R.id.B1);
        B2 = findViewById(R.id.B2);
        B3 = findViewById(R.id.B3);
        B4 = findViewById(R.id.B4);

        pv = findViewById(R.id.pv);
        pvAdverse = findViewById(R.id.pvAdverse);

        layoutParamsClose = new ConstraintLayout.LayoutParams(0, 0);

        Intent intent = this.getIntent();
        partieKey = intent.getStringExtra(MainMenuActivity.PARTIE_KEY);
        role = intent.getStringExtra(MainMenuActivity.ROLE);
        final String deckName = intent.getStringExtra(MainMenuActivity.DECK_NAME);
        Toast.makeText(getApplicationContext(), partieKey, Toast.LENGTH_LONG);
        user = FirebaseAuth.getInstance().getCurrentUser();

        refPartie = refGeneral.child(MainMenuActivity.PARTIES).child(partieKey);
        DatabaseReference refDeck = refPartie.child(role);
        DatabaseReference refUserDeck = refGeneral.child(SignInActivity.USERS).child(user.getUid()).child("decks").child(deckName);
        DeckGestion.moveDeckWithTitle(refUserDeck, refDeck);

        hideNavigationBar();

        refPartie.child("hosterPv").setValue(lpJoueur);
        refPartie.child("joinerPv").setValue(lpAdverse);

        mainButton = findViewById(R.id.main);
        this.mainButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                mainButton.setClickable(false);
                gridHand.setLayoutParams(layoutParamsHand);
                try {

                    main();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        this.retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainButton.setClickable(true);
                gridHand.setLayoutParams(layoutParamsClose);
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

        phase = 0;
        turn = 0;

        cimetiereButton = findViewById(R.id.cimetiere);
        this.cimetiereButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (phase == 1) {
                    phase ++;
                    cimetiere();
                }else if (phase == 2) {
                    phase ++;
                    cimetiere();
                }else if (phase == 3) {
                    refPartie.child("turn").setValue(role);
                }
            }
        });

        hoster = new Player(MainMenuActivity.HOSTER, 500, this);
        joiner = new Player(MainMenuActivity.JOINER, 500, this);




        refPartie.child("turn").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(), dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                if (dataSnapshot.getValue() == null) {

                }else {
                    if (dataSnapshot.getValue().equals(role)) {
                        mainButton.setClickable(false);
                        extraDeckButton.setClickable(false);
                        cimetiereButton.setClickable(false);
                    }else {
                        mainButton.setClickable(true);
                        extraDeckButton.setClickable(true);
                        cimetiereButton.setClickable(true);
                        phase = 0;
                        cimetiere();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        if(role.equals(MainMenuActivity.HOSTER)) {
            refPartie.child(MainMenuActivity.HOSTER).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.hasChild("attaque")) {
                        hoster.getDeck().addCarte(dataSnapshot.getValue(DataSmasheurCard.class), dataSnapshot.getKey(), ""+dataSnapshot.child("id").getValue());
                    }
                    if (hoster.getDeck().getNbCards() >= 29) {
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
            refPartie.child(MainMenuActivity.JOINER).child("B1").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.getValue() == null) {

                        } else {
                            ec1 = Deck.convert(data.getValue(DataSmasheurCard.class), data.getKey(), FieldActivity.this);
                            try {
                                afficherImage(B1, ec1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            nbEnnemi++;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            refPartie.child(MainMenuActivity.JOINER).child("B2").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.getValue() == null) {

                        } else {
                            ec2 = Deck.convert(data.getValue(DataSmasheurCard.class), data.getKey(), FieldActivity.this);
                            try {
                                afficherImage(B2, ec2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            nbEnnemi++;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            refPartie.child(MainMenuActivity.JOINER).child("B3").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.getValue() == null) {

                        } else {
                            ec3 = Deck.convert(data.getValue(DataSmasheurCard.class), data.getKey(), FieldActivity.this);
                            try {
                                afficherImage(B3, ec3);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            nbEnnemi++;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            refPartie.child(MainMenuActivity.JOINER).child("B4").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.getValue() == null) {

                        } else {
                            ec4 = Deck.convert(data.getValue(DataSmasheurCard.class), data.getKey(), FieldActivity.this);
                            try {
                                afficherImage(B4, ec4);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            nbEnnemi++;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            player = hoster;
            adverser = joiner;
            player.setPv(pv);
            joiner.setPv(pvAdverse);
            ennmiPv = "joinerPv";
        }
        if(role.equals(MainMenuActivity.JOINER)) {
            refPartie.child(MainMenuActivity.JOINER).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.hasChild("attaque")) {
                        joiner.getDeck().addCarte(dataSnapshot.getValue(DataSmasheurCard.class), dataSnapshot.getKey(), dataSnapshot.child("id").getValue().toString());
                    }
                    if (joiner.getDeck().getNbCards() >= 29) {
                        refPartie.child("turn").setValue(role);
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
            refPartie.child(MainMenuActivity.HOSTER).child("B1").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.getValue() == null) {

                        } else {
                            ec1 = Deck.convert(data.getValue(DataSmasheurCard.class), data.getKey(), FieldActivity.this);
                            try {
                                afficherImage(B1, ec1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            nbEnnemi++;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            refPartie.child(MainMenuActivity.HOSTER).child("B2").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.getValue() == null) {

                        } else {
                            ec2 = Deck.convert(data.getValue(DataSmasheurCard.class), data.getKey(), FieldActivity.this);
                            try {
                                afficherImage(B2, ec2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            nbEnnemi++;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            refPartie.child(MainMenuActivity.HOSTER).child("B3").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.getValue() == null) {

                        } else {
                            ec3 = Deck.convert(data.getValue(DataSmasheurCard.class), data.getKey(), FieldActivity.this);
                            try {
                                afficherImage(B3, ec3);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            nbEnnemi++;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            refPartie.child(MainMenuActivity.HOSTER).child("B4").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.getValue() == null) {

                        } else {
                            ec4 = Deck.convert(data.getValue(DataSmasheurCard.class), data.getKey(), FieldActivity.this);
                            try {
                                afficherImage(B4, ec4);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            nbEnnemi++;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            player = joiner;
            player.setPv(pv);
            adverser = hoster;
            hoster.setPv(pvAdverse);
            ennmiPv = "hosterPv";
        }
        refPartie.child("hosterPv").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {

                }else {
                    if (role.equals(MainMenuActivity.HOSTER)) {
                        pv.setText("pv : " + dataSnapshot.getValue());
                        if (Integer.parseInt(dataSnapshot.getValue().toString()) <= 0) {
                            Toast.makeText(getApplicationContext(), "Défaite", Toast.LENGTH_LONG).show();
                            goMainMenu();
                        }
                    }else {
                        pvAdverse.setText("pv averse : " + dataSnapshot.getValue());
                        if (Integer.parseInt(dataSnapshot.getValue().toString()) <= 0) {
                            Toast.makeText(getApplicationContext(), "Victoire", Toast.LENGTH_LONG).show();
                            goMainMenu();
                        }
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        refPartie.child("joinerPv").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {

                }else {
                    if (role.equals(MainMenuActivity.JOINER)) {
                        pv.setText(pv.getText().toString() + " " + dataSnapshot.getValue());
                        if (Integer.parseInt(dataSnapshot.getValue().toString()) <= 0) {
                            Toast.makeText(getApplicationContext(), "Défaite", Toast.LENGTH_LONG).show();
                            goMainMenu();
                        }
                    }else {
                        pvAdverse.setText(pvAdverse.getText().toString() + " " + dataSnapshot.getValue());
                        if (Integer.parseInt(dataSnapshot.getValue().toString()) <= 0) {
                            Toast.makeText(getApplicationContext(), "Victoire", Toast.LENGTH_LONG).show();
                            goMainMenu();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void goMainMenu() {
        Intent myIntent = new Intent(FieldActivity.this, MainMenuActivity.class);
        startActivity(myIntent);
    }

    private void battlePhase() {

    }

    private void mainPhase() {

    }

    private void draw() {
        player.getHand().add(player.getDeck().getTopCard());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        layoutParamsHand = new ConstraintLayout.LayoutParams(show1.getWidth(), show1.getHeight());
        gridHand.setLayoutParams(layoutParamsClose);
        show1.setLayoutParams(layoutParamsClose);
    }

    private void main() throws IOException {
        Toast.makeText(this, "main : " + player.getHand().size(), Toast.LENGTH_SHORT).show();
        if (player.getHand().size() > 0) {
            afficherImage(M1, player.getHand().get(0));
            addCardListener(M1, (CardSmasheur) player.getHand().get(0), "hand");
        }
        if (player.getHand().size() > 1) {
            afficherImage(M2, player.getHand().get(1));
            addCardListener(M2, (CardSmasheur) player.getHand().get(1), "hand");
        }
        if (player.getHand().size() > 2) {
            afficherImage(M3, player.getHand().get(2));
            addCardListener(M3, (CardSmasheur) player.getHand().get(2), "hand");
        }
        if (player.getHand().size() >3) {
            afficherImage(M4, player.getHand().get(3));
            addCardListener(M4, (CardSmasheur) player.getHand().get(3), "hand");
        }
        if (player.getHand().size() > 4) {
            afficherImage(M5, player.getHand().get(4));
            addCardListener(M5, (CardSmasheur) player.getHand().get(4), "hand");
        }
        if (player.getHand().size() > 5) {
            afficherImage(M6, player.getHand().get(5));
            addCardListener(M6, (CardSmasheur) player.getHand().get(5), "hand");
        }
        if (player.getHand().size() > 6) {
            afficherImage(M7, player.getHand().get(6));
            addCardListener(M7, (CardSmasheur) player.getHand().get(6), "hand");
        }
        if (player.getHand().size() >7) {
            afficherImage(M8, player.getHand().get(7));
            addCardListener(M8, (CardSmasheur) player.getHand().get(7), "hand");
        }
        if (player.getHand().size() > 8) {
            afficherImage(M9, player.getHand().get(8));
            addCardListener(M9, (CardSmasheur) player.getHand().get(8), "hand");
        }
        if (player.getHand().size() > 9) {
            Toast.makeText(this, "mange tes morts", Toast.LENGTH_SHORT).show();
        }
    }

    public void afficherImage(final ImageView view, Card card) throws IOException {
        final StorageReference imageRef = card.getImageRef();
        final File localFile = File.createTempFile(card.getCardKey(), "jpeg");
        imageRef.getFile(localFile).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getApplicationContext(), "echec de l'upload", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), imageRef.getPath(), Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                view.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 256, 256, false));
            }
        });
    }

    public void afficherImageWithTaille(final ImageView view, Card card, final int width, final int height) throws IOException {
        StorageReference imageRef = card.getImageRef();
        final File localFile = File.createTempFile(card.getCardKey(), "jpeg");
        imageRef.getFile(localFile).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "echec de l'upload", Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                view.setImageBitmap(Bitmap.createScaledBitmap(bitmap, width, height, false));
            }
        });
    }

    public void addCardListener(final ImageView listener, final CardSmasheur card, final String position) {
        listener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show1.setLayoutParams(layoutParamsHand);
                titre.setText(card.getName());
                archetype.setText(card.getGroupe1());
                descriptionCarte.setText(card.getDescription());
                attaque.setText(Integer.toString(card.getValAttack()));
                defense.setText(Integer.toString(card.getValDefense()));
                try {
                    afficherImage(imageCarte, card);
                }catch(IOException e) {
                    e.printStackTrace();
                }
                if (phase == 1 || phase == 3) {
                    if (position.equals("hand")) {
                        action1.setAlpha(1f);
                        action1.setText("poser en attaque");
                        action1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                poserSmasheur("attaque", card);
                                show1.setLayoutParams(layoutParamsClose);
                                gridHand.setLayoutParams(layoutParamsClose);
                            }
                        });
                        action2.setAlpha(1f);
                        action2.setText("poser en defense");
                        action2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                poserSmasheur("defense", card);
                                show1.setLayoutParams(layoutParamsClose);
                                gridHand.setLayoutParams(layoutParamsClose);
                            }
                        });
                    }
                    if (position.equals("attaque")) {
                        action1.setAlpha(1f);
                        action1.setText("passer en défense");
                        action1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switchPos("defense", card);
                                show1.setLayoutParams(layoutParamsClose);
                                gridHand.setLayoutParams(layoutParamsClose);
                            }
                        });
                        action2.setAlpha(0f);
                    }
                    if (position.equals("défense")) {
                        action1.setAlpha(1f);
                        action1.equals("passer en attaque");
                        action1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switchPos("attaque", card);
                                show1.setLayoutParams(layoutParamsClose);
                                gridHand.setLayoutParams(layoutParamsClose);
                            }
                        });
                        action2.setAlpha(0f);
                    }
                }else if (phase == 2) {
                    if (position.equals("attaque")) {
                        action1.setAlpha(1f);
                        action2.setAlpha(0f);
                        action1.setText("attaquer");
                        action1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                attaquer(card, listener);
                                show1.setLayoutParams(layoutParamsClose);
                                gridHand.setLayoutParams(layoutParamsClose);
                            }
                        });
                    }else {
                        action1.setAlpha(0f);
                        action2.setAlpha(0f);
                    }
                }
            }
        });
    }

    private void attaquer(final CardSmasheur card, final ImageView imageCarte) {
        if (nbEnnemi == 0) {
            lpAdverse -= card.getValAttack();
            if (role.equals(MainMenuActivity.HOSTER)) {
                refPartie.child("joinerPv").setValue(lpAdverse);
            }else {
                refPartie.child("hosterPv").setValue(lpAdverse);
            }
        }
        if (ec1 != null) {
            B1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int val = card.getValAttack() - ec1.getValAttack();
                    if (val < 0) {
                        B1.setImageResource(R.drawable.square);
                        ec1 = null;
                        nbEnnemi--;
                        lpAdverse += val;
                        if (role.equals(MainMenuActivity.HOSTER)) {
                            refPartie.child("joinerPv").setValue(lpAdverse);
                        }else {
                            refPartie.child("hosterPv").setValue(lpAdverse);
                        }
                    }else if (val > 0){
                        imageCarte.setImageResource(R.drawable.square);
                        imageCarte.setOnClickListener(null);
                        lpJoueur -= val;
                        if (role.equals(MainMenuActivity.JOINER)) {
                            refPartie.child("joinerPv").setValue(lpJoueur);
                        }else {
                            refPartie.child("hosterPv").setValue(lpJoueur);
                        }
                    }
                }
            });
        }
        if (ec2 != null) {
            B2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int val = card.getValAttack() - ec2.getValAttack();
                    if (val < 0) {
                        B2.setImageResource(R.drawable.square);
                        ec2 = null;
                        nbEnnemi--;
                        lpAdverse += val;
                        if (role.equals(MainMenuActivity.HOSTER)) {
                            refPartie.child("joinerPv").setValue(lpAdverse);
                        }else {
                            refPartie.child("hosterPv").setValue(lpAdverse);
                        }
                    }else if (val > 0){
                        imageCarte.setImageResource(R.drawable.square);
                        imageCarte.setOnClickListener(null);
                        lpJoueur -= val;
                        if (role.equals(MainMenuActivity.JOINER)) {
                            refPartie.child("joinerPv").setValue(lpJoueur);
                        }else {
                            refPartie.child("hosterPv").setValue(lpJoueur);
                        }
                    }
                }
            });
        }
        if (ec4 != null) {
            B4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int val = card.getValAttack() - ec4.getValAttack();
                    if (val < 0) {
                        B4.setImageResource(R.drawable.square);
                        ec4 = null;
                        nbEnnemi--;
                        lpAdverse += val;
                        if (role.equals(MainMenuActivity.HOSTER)) {
                            refPartie.child("joinerPv").setValue(lpAdverse);
                        }else {
                            refPartie.child("hosterPv").setValue(lpAdverse);
                        }
                    }else if (val > 0){
                        imageCarte.setImageResource(R.drawable.square);
                        imageCarte.setOnClickListener(null);
                        lpJoueur -= val;
                        if (role.equals(MainMenuActivity.JOINER)) {
                            refPartie.child("joinerPv").setValue(lpJoueur);
                        }else {
                            refPartie.child("hosterPv").setValue(lpJoueur);
                        }
                    }
                }
            });
        }
        if (ec3 != null) {
            B3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int val = card.getValAttack() - ec3.getValAttack();
                    if (val < 0) {
                        B3.setImageResource(R.drawable.square);
                        ec3 = null;
                        nbEnnemi--;
                        lpAdverse += val;
                        if (role.equals(MainMenuActivity.HOSTER)) {
                            refPartie.child("joinerPv").setValue(lpAdverse);
                        }else {
                            refPartie.child("hosterPv").setValue(lpAdverse);
                        }
                    }else if (val > 0){
                        imageCarte.setImageResource(R.drawable.square);
                        imageCarte.setOnClickListener(null);
                        lpJoueur -= val;
                        if (role.equals(MainMenuActivity.JOINER)) {
                            refPartie.child("joinerPv").setValue(lpJoueur);
                        }else {
                            refPartie.child("hosterPv").setValue(lpJoueur);
                        }
                    }
                }
            });
        }
    }

    private void switchPos(String defense, CardSmasheur card) {

    }

    private void poserSmasheur(final String attaque, final CardSmasheur card) {
        if (!invoque) {
            C1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    invoque = true;
                    try {
                        afficherImageWithTaille(C1, card, 64, 64);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    refPartie.child(role).child("B1").child(card.getCardKey()).setValue(card.replaceData());
                    C2.setOnClickListener(null);
                    C3.setOnClickListener(null);
                    C4.setOnClickListener(null);
                    addCardListener(C1, card, attaque);
                }
            });
            C2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    invoque = true;
                    try {
                        afficherImageWithTaille(C2, card, 64, 64);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    refPartie.child(role).child("B2").child(card.getCardKey()).setValue(card.replaceData());
                    C1.setOnClickListener(null);
                    C3.setOnClickListener(null);
                    C4.setOnClickListener(null);
                    addCardListener(C2, card, attaque);
                }
            });
            C3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    invoque = true;
                    try {
                        afficherImageWithTaille(C3, card, 64, 64);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    refPartie.child(role).child("B3").child(card.getCardKey()).setValue(card.replaceData());
                    C2.setOnClickListener(null);
                    C1.setOnClickListener(null);
                    C4.setOnClickListener(null);
                    addCardListener(C3, card, attaque);
                }
            });
            C4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    invoque = true;
                    try {
                        afficherImageWithTaille(C4, card, 64, 64);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    refPartie.child(role).child("B4").child(card.getCardKey()).setValue(card.replaceData());
                    C2.setOnClickListener(null);
                    C3.setOnClickListener(null);
                    C1.setOnClickListener(null);
                    addCardListener(C4, card, attaque);
                }
            });
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
        if (phase == 0) {
            Toast.makeText(this, "draw phase", Toast.LENGTH_SHORT).show();
            draw();
            phase = 1;
            invoque = false;
        }
        if (phase == 1) {
            Toast.makeText(this, "main phase 1", Toast.LENGTH_SHORT).show();
            mainPhase();
        }
        if (phase == 2) {
            Toast.makeText(this, "battle phase", Toast.LENGTH_SHORT).show();
            battlePhase();
        }
        if (phase == 3) {
            Toast.makeText(this, "main phase 2", Toast.LENGTH_SHORT).show();
            mainPhase();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void hideNavigationBar() {
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

}
