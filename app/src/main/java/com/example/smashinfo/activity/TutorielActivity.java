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
        addSlide(AppIntroFragment.newInstance("Nombre de joueurs","Ce jeu se joue à 2 joueurs, chacun son écran !",R.drawable.yg, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("But du jeu","Le but de ce jeu est de réduire les points de Vie(PV) de l'autre joueur à 0",R.drawable.yg, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Atteindre le but","Pour cela vous disposez de cartes \"smasheur\", qui possèdent différents éléments :",R.drawable.mamie, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Description carte 1/3","Un nom et une image",R.drawable.mamie1, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Description carte 2/3","Un nom d'équipe et une description",R.drawable.mamie2, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Description carte 3/3","Une attaque(=att) et une défense(=déf)",R.drawable.mamie3, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Votre paquet de cartes","Lors d'une partie, vous disposez de 30 cartes dans votre tas(=deck)",R.drawable.yg, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
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
