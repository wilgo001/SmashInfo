package com.example.smashinfo.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smashinfo.activity.SignInActivity;
import com.example.smashinfo.game.Card;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeckGestion {

    public static final String SMASHEUR = "Smasheur";
    public static final String EFFET = "Effet";
    public static final String SUPER_SMASHEUR = "Super-Smasheur";
    private DataCard[] deck;
    private DatabaseReference deckRef;
    private final static DatabaseReference CARDREF = FirebaseDatabase.getInstance().getReference().child("cartes");
    private static DataSmasheurCard dataCard;

    public DeckGestion(DatabaseReference deckRef) {
        this(deckRef, new DataCard[30]);
        generateAutoDeck();
    }

    public DeckGestion(DatabaseReference deckRef, DataCard[] deck) {
        this.deckRef = deckRef;
        this.deck = deck;
    }

    private void generateAutoDeck() {
        //TODO : faire un meilleur generateur de starterDeck automatique
        deckRef.child("name").setValue("starter deck");
        deckRef.child("card").setValue(getCardWithId("-LvQ7FVfEcyIvTAOB9Ue", SMASHEUR));
        for (int i = 0; i < deck.length; i++) {
            //deck[i] = getCardWithId("-LvQ7FVfEcyIvTAOB9Ue", SMASHEUR);
            //deckRef.child(""+i).setValue(deck[i]);
        }
    }
    public static DataCard getCardWithId(final String id, final String typeCard) {
        CARDREF.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.println(Log.DEBUG, "debug", "je marche pas");

                dataCard = dataSnapshot.child(id).getValue(DataSmasheurCard.class);
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
        CARDREF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.println(Log.DEBUG, "debug", "je marche pa s");

                dataCard = dataSnapshot.child(id).getValue(DataSmasheurCard.class);
                /*
                switch (typeCard) {
                    case SMASHEUR:
                        dataCard = dataSnapshot.child(id).getValue(DataSmasheurCard.class);
                        break;
                    case EFFET:
                        dataCard = dataSnapshot.child(id).getValue(DataEffectCard.class);
                        break;
                    case SUPER_SMASHEUR:
                        dataCard = dataSnapshot.child(id).getValue(DataSuperSmasheurCard.class);
                        break;
                    default:
                        dataCard = null;
                        break;
                }
                */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return dataCard;
    }

    /*
    public static DataCard getCardWithTitle(final String title, final String typeCard) {
        CARDREF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("nom").getValue().equals(title)) {
                        dataSnapshot = data;
                    }
                    switch (typeCard) {
                        case SMASHEUR:
                            dataCard = dataSnapshot.getValue(DataSmasheurCard.class);
                            break;
                        case EFFET:
                            dataCard = dataSnapshot.getValue(DataEffectCard.class);
                            break;
                        case SUPER_SMASHEUR:
                            dataCard = dataSnapshot.getValue(DataSuperSmasheurCard.class);
                            break;
                        default:
                            dataCard = null;
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return dataCard;
    }
*/
}
