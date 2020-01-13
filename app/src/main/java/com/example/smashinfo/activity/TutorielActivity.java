package com.example.smashinfo.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.smashinfo.R;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class TutorielActivity extends com.github.paolorotolo.appintro.AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_app_intro);

        addSlide(AppIntroFragment.newInstance("Smash Info","Bienvenue dans Smash Info !",R.drawable.yg, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Nombre de joueurs","Ce jeu se joue a 2 joueurs, chacun son écran !",R.drawable.yg, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("But du jeu","Le but de ce jeu est de réduire les points de Vie(PV) de l'autre joueur à 0",R.drawable.yg, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
    }

    @Override
    public void onDonePressed(Fragment currentfragment){
        super.onDonePressed(currentfragment);
        Intent intent=new Intent(this,MainMenuActivity.class);
        startActivity(intent);

    }

    @Override
    public void onSkipPressed(Fragment currentfragment){
        super.onSkipPressed(currentfragment);
        Intent intent=new Intent(this,MainMenuActivity.class);
        startActivity(intent);

    }
}
