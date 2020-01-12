package com.example.smashinfo.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.smashinfo.R;
import com.example.smashinfo.data.DataCard;
import com.example.smashinfo.data.DataSmasheurCard;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class tirageActivity extends com.github.paolorotolo.appintro.AppIntro {

    private String tir;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_app_intro);

        Intent intent = this.getIntent();
        tir = intent.getStringExtra(MainMenuActivity.TIRAGE_KEY);

        db = FirebaseDatabase.getInstance().getReference();

        final ArrayList<DataCard> normal = new ArrayList<DataCard>();
        final ArrayList<DataCard> rareSuperLegendaire = new ArrayList<DataCard>();
        final ArrayList<DataCard> normalRare = new ArrayList<DataCard>();
        final ArrayList<DataCard> superLegendaire = new ArrayList<DataCard>();
        db = db.child("cartes");

        switch (tir) {
            case "normal":

                db.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        if (dataSnapshot.child("probabilite").getValue() == "N") {
                            normal.add(dataSnapshot.getValue(DataSmasheurCard.class));
                        } else {
                            rareSuperLegendaire.add(dataSnapshot.getValue(DataSmasheurCard.class));
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
                        String nom = normal.get(index).getName();
                        String description = normal.get(index).getDescription();
                        String attaque = ((DataSmasheurCard) (normal.get(index))).getAttaque();
                        String defense = ((DataSmasheurCard) (normal.get(index))).getDefense();
                        addSlide(AppIntroFragment.newInstance(nom, description, R.drawable.yg, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
                    } else {
                        String nom = rareSuperLegendaire.get(index).getName();
                        String description = rareSuperLegendaire.get(index).getDescription();
                        String attaque = ((DataSmasheurCard) (rareSuperLegendaire.get(index))).getAttaque();
                        String defense = ((DataSmasheurCard) (rareSuperLegendaire.get(index))).getDefense();
                        addSlide(AppIntroFragment.newInstance(nom, description, R.drawable.yg, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
                    }
                }


                break;
            case "rare":
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
                        addSlide(AppIntroFragment.newInstance(nom, description, R.drawable.yg, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
                    } else {
                        String nom = superLegendaire.get(index).getName();
                        String description = superLegendaire.get(index).getDescription();
                        String attaque = ((DataSmasheurCard) (superLegendaire.get(index))).getAttaque();
                        String defense = ((DataSmasheurCard) (superLegendaire.get(index))).getDefense();
                        addSlide(AppIntroFragment.newInstance(nom, description, R.drawable.yg, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
                    }
                }
                break;
        }
    }

    @Override
    public void onDonePressed(Fragment currentfragment) {
        super.onDonePressed(currentfragment);
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);

    }

    @Override
    public void onSkipPressed(Fragment currentfragment) {
        super.onSkipPressed(currentfragment);
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);

    }
}