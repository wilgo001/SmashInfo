package com.example.smashinfo.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smashinfo.R;
import com.example.smashinfo.data.Partie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenuActivity extends AppCompatActivity {

    public static final String JOINER_NAME = "searching";
    public static final String PARTIES = "parties";
    private Button buttonDeconnexion, createGame, loadGame;
    private EditText pseudo;
    private DatabaseReference mDatabase;

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
                Partie partie = new Partie(pseudo.getText().toString(), JOINER_NAME, "white");
                mDatabase.child(PARTIES).push().setValue(partie);
                Toast toast = Toast.makeText(getApplicationContext(), "partie créée. en attente de joueur", Toast.LENGTH_LONG);
                toast.show();

            }
        });

        final ChildEventListener partieAdded = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (pseudo.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "complete pseudo please", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Partie partie;
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    partie = data.getValue(Partie.class);
                    Log.d("debug1", data.getValue().toString());
                    Log.d("debug3", partie.toString());
                    if ((partie.joinerName.equals(JOINER_NAME))&&(!partie.hosterName.equals(pseudo.getText().toString()))) {
                        partie.joinerName = pseudo.getText().toString();
                        Toast toast = Toast.makeText(getApplicationContext(), "key : " + data.getKey(), Toast.LENGTH_SHORT);
                        toast.show();
                        mDatabase.child(PARTIES).child(data.getKey()).setValue(partie);
                        return;
                    }
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
        };

        loadGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.addChildEventListener(partieAdded);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("debugAlertDialog", "1.ca marche pas ?");
                        AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                        Log.d("debugAlertDialog", "2.ca marche pas ?");
                        alertDialog.setTitle("Recherche en cours");
                /*alertDialog.setMessage("Veuillez patientez, nous recherchons une partie");
                mDatabase.addChildEventListener(partieAdded);
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDatabase.removeEventListener(partieAdded);
                        //alertDialog.setCancelable(true);
                    }
                });
                Log.d("debugAlertDialog", "ca marche pas ?");*/
                        alertDialog.show();
                    }
                });


            }
        });

    }

    private void quitApp() {
        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
    }
}
