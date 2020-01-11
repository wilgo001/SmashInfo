package com.example.smashinfo.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;

import android.widget.TextView;
import android.widget.Toast;

import com.example.smashinfo.R;
import com.example.smashinfo.data.DataCard;
import com.example.smashinfo.data.DataSmasheurCard;
import com.example.smashinfo.data.DataUser;
import com.example.smashinfo.data.DeckGestion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    public static final String DECKS_LIST = "decksList";
    public static final String USERS = "users";
    private FirebaseAuth mAuth;

    EditText editTextPseudo;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextConfirmPassword;
    Button buttonSignIn;
    TextView textViewGoConnecter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        editTextPseudo = findViewById(R.id.editTextPseudo);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        textViewGoConnecter = findViewById(R.id.textViewGoInscrire);

        textViewGoConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogIn();
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok = checkNonEmpty(editTextEmail);
                ok = ok && checkNonEmpty(editTextPseudo);
                ok = ok && checkNonEmpty(editTextPassword);
                ok = ok && checkNonEmpty(editTextConfirmPassword);
                ok = ok && (editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString()));

                if(ok) {
                    creerCompte(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        /*FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
        }
*/
    }

    private void goToLogIn() {
        Log.i("info", "go to log in");
        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
    }

    private void creerCompte(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(editTextPseudo.getText().toString())
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                    }
                                }
                            });
                    updateUser(user);
                }else {
                    Toast.makeText(SignInActivity.this, "Echec de l'Authentification.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void updateUser(final FirebaseUser user) {
        if(user != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(USERS).child(user.getUid());
            DataUser dataUser = new DataUser(user.getEmail(), editTextPseudo.getText().toString(), "starter deck", 0, 0);
            databaseReference.setValue(dataUser);

            DatabaseReference deckRef = databaseReference.child("decks").child("starter deck");
            final DatabaseReference listRef = databaseReference.child("card list");
            DeckGestion.generateAutoDeck(deckRef);
            DeckGestion.moveDeckWithTitle(deckRef, listRef);
        }
        goTuto();

    }

    private void goTuto() {
        Intent myIntent = new Intent(this, TutorielActivity.class);
        startActivity(myIntent);
    }

    private void goMainMenu() {
        Intent myIntent = new Intent(this, MainMenuActivity.class);
        startActivity(myIntent);
    }


    boolean checkNonEmpty(EditText editText) {
        if(editText.getText().toString().equals("")){
            editText.setError("champ vide");
            return false;
        }
        return true;
    }

}
