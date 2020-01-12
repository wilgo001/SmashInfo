package com.example.smashinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smashinfo.R;
import com.example.smashinfo.data.CarteWithId;
import com.example.smashinfo.data.DataSmasheurCard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class tirageActivity extends AppCompatActivity {

    private String tir;
    private DatabaseReference db;
    private FirebaseUser user;
    private DatabaseReference cartesUser;
    private ArrayList<CarteWithId> normal;
    private ArrayList<CarteWithId> rareSuperLegendaire;
    private ArrayList<CarteWithId> normalRare;
    private ArrayList<CarteWithId> superLegendaire;
    private int i;
    private Button suivant;
    private TextView nom, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tirage);

        Intent intent = this.getIntent();
        tir = intent.getStringExtra(MainMenuActivity.TIRAGE_KEY);

        db = FirebaseDatabase.getInstance().getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();
        cartesUser = db.child("users").child(user.getUid()).child("card list");

        normal = new ArrayList<>();
        rareSuperLegendaire = new ArrayList<>();
        normalRare = new ArrayList<>();
        superLegendaire = new ArrayList<>();
        db = db.child("cartes");

        switch (tir) {
            case "normal":

                db.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        if (dataSnapshot.child("probabilite").getValue() == "N") {
                            normal.add(new CarteWithId(dataSnapshot.getKey(), dataSnapshot.getValue(DataSmasheurCard.class)));
                        } else {
                            rareSuperLegendaire.add(new CarteWithId(dataSnapshot.getKey(), dataSnapshot.getValue(DataSmasheurCard.class)));
                        }

                        if (normal.size() + rareSuperLegendaire.size() >= 61) {
                            i = 0;
                            tirage();
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

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // gestion de l'erreur
                }

                for (int i = 1; i > 10; i++) {
                    int index = (int) (Math.random() * normal.size());
                    if (i < 9) {
                        String nom = normal.get(index).getValue().getName();
                        String description = normal.get(index).getValue().getDescription();
                        String attaque = ((DataSmasheurCard) (normal.get(index).getValue())).getAttaque();
                        String defense = ((DataSmasheurCard) (normal.get(index).getValue())).getDefense();
                        cartesUser.child(normal.get(index).getKey()).setValue(normal.get(index).getValue());

                    } else {
                        String nom = rareSuperLegendaire.get(index).getValue().getName();
                        String description = rareSuperLegendaire.get(index).getValue().getDescription();
                        String attaque = ((DataSmasheurCard) (rareSuperLegendaire.get(index).getValue())).getAttaque();
                        String defense = ((DataSmasheurCard) (rareSuperLegendaire.get(index).getValue())).getDefense();
                        cartesUser.child(rareSuperLegendaire.get(index).getKey()).setValue(rareSuperLegendaire.get(index).getValue());
                    }
                }


                break;
            /*case "rare":
                db.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        if (dataSnapshot.child("probabilite").getValue() == "N") {
                            normalRare.add(dataSnapshot.getValue(DataSmasheurCard.class));
                        } else {
                            superLegendaire.add(dataSnapshot.getValue(DataSmasheurCard.class));
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

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // gestion de l'erreur
                }

                for (int i = 1; i > 10; i++) {
                    int index = (int) (Math.random() * normal.size());
                    if (i < 9) {
                        String nom = normalRare.get(index).getName();
                        String description = normalRare.get(index).getDescription();
                        String attaque = ((DataSmasheurCard) (normalRare.get(index))).getAttaque();
                        String defense = ((DataSmasheurCard) (normalRare.get(index))).getDefense();
                    } else {
                        String nom = superLegendaire.get(index).getName();
                        String description = superLegendaire.get(index).getDescription();
                        String attaque = ((DataSmasheurCard) (superLegendaire.get(index))).getAttaque();
                        String defense = ((DataSmasheurCard) (superLegendaire.get(index))).getDefense();
                    }
                }
                break;*/
        }

        suivant = (Button) findViewById(R.id.suivant);
        nom = (TextView) findViewById(R.id.nomCarte);
        description = (TextView) findViewById(R.id.descriptionCarte);

        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i < 10) {
                    tirageSuivant();
                } else {
                    retourMenu();
                }
            }
        });

    }


    private void retourMenu() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    private void tirageSuivant() {
        if (i < 9) {
            int index = (int) (Math.random() * normal.size());
            String nom = normal.get(index).getValue().getName();
            String description = normal.get(index).getValue().getDescription();
            String attaque = ((DataSmasheurCard) (normal.get(index).getValue())).getAttaque();
            String defense = ((DataSmasheurCard) (normal.get(index).getValue())).getDefense();
            cartesUser.child(normal.get(index).getKey()).setValue(normal.get(index).getValue());
            this.nom.setText(nom);
            this.description.setText("Description : "+description);
        } else {
            int index = (int) (Math.random() * normal.size());
            String nom = rareSuperLegendaire.get(index).getValue().getName();
            String description = rareSuperLegendaire.get(index).getValue().getDescription();
            String attaque = ((DataSmasheurCard) (rareSuperLegendaire.get(index).getValue())).getAttaque();
            String defense = ((DataSmasheurCard) (rareSuperLegendaire.get(index).getValue())).getDefense();
            cartesUser.child(rareSuperLegendaire.get(index).getKey()).setValue(rareSuperLegendaire.get(index).getValue());
            this.nom.setText(nom);
            this.description.setText("Description : "+description);
        }
        i++;
    }

    private void tirage() {
        int index = (int) (Math.random() * normal.size());
        String nom = normal.get(index).getValue().getName();
        String description = normal.get(index).getValue().getDescription();
        String attaque = ((DataSmasheurCard) (normal.get(index).getValue())).getAttaque();
        String defense = ((DataSmasheurCard) (normal.get(index).getValue())).getDefense();
        cartesUser.child(normal.get(index).getKey()).setValue(normal.get(index).getValue());
        this.nom.setText(nom);
        this.description.setText("Description : "+description);
        i++;
    }
}