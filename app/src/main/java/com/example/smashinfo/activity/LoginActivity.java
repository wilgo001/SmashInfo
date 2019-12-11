package com.example.smashinfo.activity;

import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smashinfo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    TextView textViewGoInscrire, titre, indication;
    Button buttonLogIn;
    ConstraintLayout principal;

    RelativeLayout intro;
    ImageView logoIUT, logoAST, background, punchRed, punchBlue, explosion;
    TextView titreAST;
    TextView titreIUT;
    Animation fadeIn, fadeOut, leftToRight, rightToLeft, zoomIn, upToDown, downToUp;
    private AnimationSet animation;

    Runnable run;
    Handler handler;

    MediaPlayer introMusic;

    FirebaseAuth mAuth;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewGoInscrire = (TextView) findViewById(R.id.textViewGoInscrire);
        buttonLogIn = (Button) findViewById(R.id.buttonLogin);
        principal = (ConstraintLayout) findViewById(R.id.principalLayout);
        background = (ImageView) findViewById(R.id.background);

        mAuth = FirebaseAuth.getInstance();

        textViewGoInscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignIn();
            }
        });

        introMusic = MediaPlayer.create(this.getApplicationContext(), R.raw.intro_music);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ok = checkNonEmpty(editTextEmail);
                ok = ok && checkNonEmpty(editTextPassword);
                if(ok)
                    LogIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        intro = (RelativeLayout) findViewById(R.id.intro);

        logoAST = new ImageView(this);
        logoAST.setBackgroundResource(R.drawable.gorilla);
        titreAST = new TextView(this);
        titreAST.setText("A . S . T");
        titreAST.setTextSize(32f);
        titreAST.setGravity(TextView.TEXT_ALIGNMENT_CENTER);

        logoIUT = new ImageView(this);
        logoIUT.setBackgroundResource(R.drawable.logo_iut);
        titreIUT = new TextView(this);
        titreIUT.setText("IUT de Laval");
        titreIUT.setTextSize(32f);
        titreIUT.setGravity(TextView.TEXT_ALIGNMENT_CENTER);

        punchRed = new ImageView(this);
        punchRed.setBackgroundResource(R.drawable.boxing_red);

        punchBlue = new ImageView(this);
        punchBlue.setBackgroundResource(R.drawable.boxing_blue);

        explosion = new ImageView(this);
        explosion.setBackgroundResource(R.drawable.explosion);

        titre = new TextView(this);
        titre.setText("SMASH\nINFO");
        titre.setTextSize(72f);
        titre.setGravity(TextView.TEXT_ALIGNMENT_CENTER);

        indication = new TextView(this);
        indication.setText("cliquez pour continuer");
        indication.setTextSize(18f);
        titre.setGravity(TextView.TEXT_ALIGNMENT_CENTER);

    }

    private void goToLogIn() {
        intro.removeAllViews();
        principal.removeView(intro);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.v("debug", "coucou");
        //background.setLayoutParams(new ConstraintLayout.LayoutParams(intro.getWidth(), intro.getHeight()));
        int logoSize = intro.getWidth()/4;
        int punchSize = intro.getWidth()/5;
        int explosionSize = intro.getWidth()/3;
        logoAST.setLayoutParams(new ConstraintLayout.LayoutParams(logoSize, logoSize));
        intro.addView(logoAST);
        intro.addView(titreAST);
        logoAST.setX(intro.getWidth()/2-logoSize/2);
        logoAST.setY(intro.getHeight()/2-logoSize/2);
        titreAST.setX(intro.getWidth()/2-logoSize/2);
        titreAST.setY(intro.getHeight()/2+logoSize);

        Toast.makeText(LoginActivity.this, "AST set", Toast.LENGTH_SHORT).show();
        Log.v("debug", "AST set");

        //create the logo IUT
        logoIUT.setLayoutParams(new RelativeLayout.LayoutParams(logoSize, logoSize));
        intro.addView(logoIUT);
        intro.addView(titreIUT);
        logoIUT.setX(intro.getWidth()/2-logoSize/2);
        logoIUT.setY(intro.getHeight()/2-logoSize/2);
        titreIUT.setX(intro.getWidth()/2-logoSize/2);
        titreIUT.setY(intro.getHeight()/2+logoSize);
        logoIUT.setAlpha(0.0f);
        titreIUT.setAlpha(0.0f);

        explosion.setLayoutParams(new RelativeLayout.LayoutParams(explosionSize, explosionSize));
        intro.addView(explosion);
        explosion.setX(explosionSize);
        explosion.setY(intro.getHeight()/2-explosionSize/2);
        explosion.setAlpha(0.0f);

        punchRed.setLayoutParams(new RelativeLayout.LayoutParams(punchSize, punchSize));
        intro.addView(punchRed);
        punchRed.setX(intro.getWidth()/2+intro.getWidth()/20);
        punchRed.setY(intro.getHeight()/2-punchSize/2);
        punchRed.setAlpha(0.0f);

        punchBlue.setLayoutParams(new RelativeLayout.LayoutParams(punchSize, punchSize));
        intro.addView(punchBlue);
        punchBlue.setX(intro.getWidth()/2-punchSize-intro.getWidth()/20);
        punchBlue.setY(intro.getHeight()/2-punchSize/2);
        punchBlue.setAlpha(0.0f);

        intro.addView(titre);
        titre.setX(intro.getWidth()/2-350);
        titre.setY(intro.getHeight()/10);
        titre.setAlpha(0.0f);

        intro.addView(indication);
        indication.setX(intro.getWidth()/2-150);
        indication.setY(intro.getHeight()*4/5);
        indication.setAlpha(0.0f);


        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        leftToRight = AnimationUtils.loadAnimation(this, R.anim.lefttoright);
        rightToLeft = AnimationUtils.loadAnimation(this, R.anim.righttoleft);
        zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        upToDown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downToUp = AnimationUtils.loadAnimation(this, R.anim.downtoup);


        //change to false
        animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);

        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showIUT();
            }
        });

        logoAST.startAnimation(animation);
        titreAST.startAnimation(animation);
        handler = new Handler();
        run = new Runnable() {
            @Override
            public void run() {
                showIUT();
            }
        };
        handler.postDelayed(run, 8000);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUser(currentUser);
    }

    private void showIUT() {
        logoAST.setAlpha(0.0f);
        titreAST.setAlpha(0.0f);
        logoIUT.setAlpha(1.0f);
        titreIUT.setAlpha(1.0f);
        logoIUT.startAnimation(animation);
        titreIUT.startAnimation(animation);
        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTitle();
            }
        });
        run = new Runnable() {
            @Override
            public void run() {
                showTitle();
            }
        };
        handler.postDelayed(run, 8000);
    }

    private void showTitle() {
        logoIUT.setAlpha(0.0f);
        titreIUT.setAlpha(0.0f);
        punchBlue.setAlpha(1.0f);
        punchRed.setAlpha(1.0f);
        punchRed.startAnimation(rightToLeft);
        punchBlue.startAnimation(leftToRight);
        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogIn();
            }
        });
        run = new Runnable() {
            @Override
            public void run() {
                if (explosion.getAlpha()==0.0f)
                    showExplosion();
            }
        };
        handler.postDelayed(run, 1000);

    }

    private void showExplosion() {
        //explosion
        explosion.setAlpha(1.0f);
        explosion.startAnimation(zoomIn);

        titre.setAlpha(1.0f);
        titre.startAnimation(upToDown);
        indication.setAlpha(1.0f);
        indication.startAnimation(downToUp);

        introMusic.setVolume(0.5f, 0.5f);
        introMusic.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        introMusic.stop();
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i("information", "in login");
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUser(currentUser);
    }


    private void LogIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUser(user);
                }else{
                    Toast.makeText(LoginActivity.this, "Echec de connexion.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean checkNonEmpty(EditText editText) {
        if(editText.getText().toString().equals("")){
            editText.setError("champ vide");
            return false;
        }
        return true;
    }

    private void goToSignIn() {
        Log.i("info", "go to sign in");
        Intent myIntent = new Intent(this , SignInActivity.class);
        startActivity(myIntent);
    }

    private void updateUser(FirebaseUser user) {
        if(user != null) {
            Intent myIntent = new Intent(this, MainMenuActivity.class);
            startActivity(myIntent);
        }
    }
}
