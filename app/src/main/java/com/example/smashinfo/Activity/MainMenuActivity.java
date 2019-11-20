package com.example.smashinfo.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smashinfo.R;
import com.example.smashinfo.data.Partie;
import com.example.smashinfo.game.FieldActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class MainMenuActivity extends AppCompatActivity {

    public static final String JOINER_NAME = "searching";
    public static final String PARTIES = "parties";
    public static final String MESSAGE = "Veuillez patientez, nous recherchons une partie\n";
    public static final String HOSTER_NAME = "hosterName";
    private Button buttonDeconnexion, createGame, loadGame;
    private EditText pseudo;
    private DatabaseReference mDatabase;
    private String message;
    private String partieKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        buttonDeconnexion = (Button) findViewById(R.id.buttonDeconnexion);
        createGame = (Button) findViewById(R.id.createGame);
        loadGame = (Button) findViewById(R.id.loadGame);
        pseudo = (EditText) findViewById(R.id.pseudo);
        mDatabase = FirebaseDatabase.getInstance().getReference();

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
                final Partie partie = new Partie(pseudo.getText().toString(), JOINER_NAME, "white", false);
                mDatabase.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        for(DataSnapshot data : dataSnapshot.getChildren()) {
                            if(data.child(HOSTER_NAME).getValue().equals(pseudo.getText().toString()))
                                partieKey = data.getKey();
                        }

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        for(DataSnapshot data : dataSnapshot.getChildren()) {
                            if (partieKey.equals(data.getKey())) {
                                Partie partie = data.getValue(Partie.class);
                                if(!partie.getJoinerName().equals(JOINER_NAME)) {
                                    partie.setStart(true);
                                    mDatabase.child(PARTIES).child(partieKey).setValue(partie);
                                    startPartie();
                                }
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
                mDatabase.child(PARTIES).push().setValue(partie);
                Toast toast = Toast.makeText(getApplicationContext(), "partie créée. en attente de joueur", Toast.LENGTH_LONG);
                toast.show();

            }
        });

        final ChildEventListener partieAdded = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Partie partie;
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    partie = data.getValue(Partie.class);
                    if ((partie.joinerName.equals(JOINER_NAME))&&(!partie.hosterName.equals(pseudo.getText().toString()))) {
                        partie.joinerName = pseudo.getText().toString();
                        Toast toast = Toast.makeText(getApplicationContext(), "key : " + data.getKey(), Toast.LENGTH_SHORT);
                        toast.show();
                        partieKey=data.getKey();
                        mDatabase.child(PARTIES).child(partieKey).setValue(partie);
                        return;
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if (partieKey.equals(data.getKey())) {
                        Partie partie = data.getValue(Partie.class);
                        if(partie.isStart())
                            startPartie();
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
                if (pseudo.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "complete pseudo please", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                message = MESSAGE;
                mDatabase.addChildEventListener(partieAdded);
                final AlertDialog alertDialog = new AlertDialog.Builder(MainMenuActivity.this).create();
                alertDialog.setTitle("Recherche en cours");
                alertDialog.setMessage(message);
                mDatabase.addChildEventListener(partieAdded);
                alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDatabase.removeEventListener(partieAdded);
                    }
                });

                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if(message.equals(MESSAGE + "..."))
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

    }

    private void startPartie() {
        Intent myIntent = new Intent(this, FieldActivity.class);
        startActivity(myIntent);
    }

    private void quitApp() {
        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
    }
}
