package com.example.smashinfo.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.smashinfo.R;
import com.example.smashinfo.data.CarteWithId;
import com.example.smashinfo.data.DataCard;
import com.example.smashinfo.data.DataSmasheurCard;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class tirageActivity extends com.github.paolorotolo.appintro.AppIntro {

    private String tir;
    private DatabaseReference db;
    private FirebaseUser user;
    private DatabaseReference cartesUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_app_intro);

        Intent intent = this.getIntent();
        tir = intent.getStringExtra(MainMenuActivity.TIRAGE_KEY);

        db = FirebaseDatabase.getInstance().getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();
        cartesUser = db.child("users").child(user.getUid()).child("card list");

        final ArrayList<CarteWithId> normal = new ArrayList<>();
        final ArrayList<CarteWithId> rareSuperLegendaire = new ArrayList<>();
        final ArrayList<CarteWithId> normalRare = new ArrayList<>();
        final ArrayList<CarteWithId> superLegendaire = new ArrayList<>();
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
                        addSlide(AppIntroFragment.newInstance(nom, description, R.drawable.yg, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
                        cartesUser.child(normal.get(index).getKey()).setValue(normal.get(index).getValue());
                    } else {
                        String nom = rareSuperLegendaire.get(index).getValue().getName();
                        String description = rareSuperLegendaire.get(index).getValue().getDescription();
                        String attaque = ((DataSmasheurCard) (rareSuperLegendaire.get(index).getValue())).getAttaque();
                        String defense = ((DataSmasheurCard) (rareSuperLegendaire.get(index).getValue())).getDefense();
                        addSlide(AppIntroFragment.newInstance(nom, description, R.drawable.yg, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
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
                        addSlide(AppIntroFragment.newInstance(nom, description, R.drawable.yg, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));

                    } else {
                        String nom = superLegendaire.get(index).getName();
                        String description = superLegendaire.get(index).getDescription();
                        String attaque = ((DataSmasheurCard) (superLegendaire.get(index))).getAttaque();
                        String defense = ((DataSmasheurCard) (superLegendaire.get(index))).getDefense();
                        addSlide(AppIntroFragment.newInstance(nom, description, R.drawable.yg, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
                    }
                }
                break;*/
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