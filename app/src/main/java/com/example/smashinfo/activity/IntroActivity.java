package com.example.smashinfo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.smashinfo.R;

public class IntroActivity extends AppCompatActivity {

    RelativeLayout intro;
    ImageView logoIUT;
    ImageView logoAST;
    TextView titreAST;
    TextView titreIUT;
    Animation fadeIn;
    Animation fadeOut;
    private AnimationSet animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        intro = (RelativeLayout) findViewById(R.id.intro);

        logoAST = new ImageView(this);
        logoAST.setBackgroundResource(R.drawable.gorilla);
        titreAST = new TextView(this);
        titreAST.setText("A . S . T");

        logoIUT = new ImageView(this);
        logoIUT.setBackgroundResource(R.drawable.logo_iut);
        titreIUT = new TextView(this);
        titreIUT.setText("IUT de Laval");

        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showIUT();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        logoAST.setLayoutParams(new ConstraintLayout.LayoutParams(200, 200));
        intro.addView(logoAST);
        intro.addView(titreAST);
        logoAST.setX((intro.getWidth()/2)-100);
        logoAST.setY((intro.getHeight()/2)-200);
        titreAST.setX((intro.getWidth()/2)-100);
        titreAST.setY((intro.getWidth()/2)+200);

        //create the logo IUT
        logoIUT.setLayoutParams(new ConstraintLayout.LayoutParams(200, 200));
        intro.addView(logoIUT);
        intro.addView(titreIUT);
        logoIUT.setX((intro.getWidth()/2)-100);
        logoIUT.setY((intro.getHeight()/2)-200);
        titreIUT.setX((intro.getWidth()/2)-100);
        titreIUT.setY((intro.getWidth()/2)+200);
        logoIUT.setAlpha((float) 0.0);
        titreIUT.setAlpha((float) 0.0);

        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        //change to false
        animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);

        logoAST.startAnimation(animation);
        titreAST.startAnimation(animation);
        Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                showIUT();
            }
        };
        handler.postDelayed(run, 8000);
    }

    private void showIUT() {

        logoAST.setAlpha((float) 0.0);
        titreAST.setAlpha((float) 0.0);
        logoIUT.setAlpha((float) 1.0);
        titreIUT.setAlpha((float) 1.0);
        logoIUT.startAnimation(animation);
        titreIUT.startAnimation(animation);

        Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                goToLogIn();
            }
        };
        handler.postDelayed(run, 8000);

    }

    private void goToLogIn() {
        Log.i("info", "go to sign in");
        Intent myIntent = new Intent(this, SignInActivity.class);
        this.startActivity(myIntent);
        Log.i("info", "intent done");
    }
}
