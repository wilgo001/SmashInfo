package com.example.smashinfo.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeckGestion {

    public static final String SMASHEUR = "Smasheur";
    public static final String EFFET = "Effet";
    public static final String SUPER_SMASHEUR = "Super-Smasheur";
    private final static DatabaseReference CARDREF = FirebaseDatabase.getInstance().getReference().child("cartes");
    private static DataCard dataCard;

    public static void generateAutoDeck(DatabaseReference deckRef) {
        //TODO : faire un meilleur generateur de starterDeck automatique
        moveCardWithTitle(DataCardsNames.Loris_Ponroy, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Flavien_Robert, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Valentin_Rouxel, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Quentin_Couland, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Iza_Marfisi, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Clément_Laborie, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Gabriel_Edde, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Simon_Beaussier, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Antoine_Jonard, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Valentin_Hallet, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Alexandre_Baron, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Axel_Blanchard, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Alex_Leborgne, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Nathan_Lefort, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Léo_Malet, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Wilhelm_Salles, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Thomas_Lhermelin, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Clément_Boussard, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Maxime_Maurin, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Quentin_Fourier, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Nathan_Chambrin, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Grégoire_Bellon, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Rafaël_Doneau, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Corentin_Dizel, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Colin_Bouligand, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Corentin_Livain, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Louis_Euvrard, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Nicolas_Durand, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.William_Lechat, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Yanis_Saupin, SMASHEUR, deckRef);
        moveCardWithTitle(DataCardsNames.Alexandre_Clouet, SMASHEUR, deckRef);

    }

    public static void addCardToDeck(String idCarte, String refDeck) {

    }

    public static DataCard getCardWithId(final String id) {
        CARDREF.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.getKey().equals(id)) {
                    if (dataSnapshot.hasChild("attaque")) {
                        dataCard = dataSnapshot.getValue(DataSmasheurCard.class);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.getKey().equals(id)) {
                    if (dataSnapshot.hasChild("attaque")) {
                        dataCard = dataSnapshot.getValue(DataSmasheurCard.class);
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
        });
        return dataCard;
    }

    public static DataCard getCardWithTitle(final String title, final String typeCard) {
        CARDREF.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.child("nom").getValue().equals(title)) {
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
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.child("nom").getValue().equals(title)) {
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
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return dataCard;
    }

    public static String getStringName(final DataCardsNames title) {
        String titleString = title.toString();
        int index = titleString.indexOf('_');
        while(index != -1) {
            titleString = titleString.substring(0, index) + " " + titleString.substring(index+1);
            index = titleString.indexOf('_');
        }
        return titleString;
    }

    public static void moveCardWithTitle(final DataCardsNames title, final String typeCard, final DatabaseReference refDeck) {
        CARDREF.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.child("nom").getValue().equals(DeckGestion.getStringName(title))) {
                    refDeck.child(dataSnapshot.getKey()).setValue(dataSnapshot.getValue());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.child("nom").getValue().equals(DeckGestion.getStringName(title))) {
                    refDeck.child(dataSnapshot.getKey()).setValue(dataSnapshot.getValue());
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
        });
    }

    public static void moveDeckWithTitle(DatabaseReference refDeckEmetteur, final DatabaseReference refDeckRecepteur) {
        refDeckEmetteur.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                refDeckRecepteur.child(dataSnapshot.getKey()).setValue(dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                refDeckRecepteur.child(dataSnapshot.getKey()).setValue(dataSnapshot.getValue());
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
    }

}
