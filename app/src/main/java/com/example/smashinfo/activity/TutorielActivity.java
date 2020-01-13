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

        addSlide(AppIntroFragment.newInstance("Le plateau de jeu","Le plateau est séparé en 2 parties :",R.drawable.plateau, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Plateau 1/5","Une partie de l'adverse avec ses cartes smasheur",R.drawable.plateau1, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Plateau 2/5","Votre partie avec vos cartes smasheur",R.drawable.plateau2, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Plateau 3/5","Les PV de votre adversaire et vos PV",R.drawable.plateau3, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Plateau 4/5","Votre \"phase\" de jeu ",R.drawable.plateau4, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Plateau 5/5","Vos différents tas de cartes",R.drawable.plateau5, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));

        addSlide(AppIntroFragment.newInstance("Les phases","Il y a un total de 6 phases :",R.drawable.phases, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Phases 1/5","La Draw phase : vous piochez une carte (4 cartes au début de la partie)",R.drawable.phases1, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Phases 2/5","La Main phase 1 : vous pouvez poser des cartes depuis votre main",R.drawable.phases2, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Phases 3/5","La Battle phase : vous pouvez attaquer avec vos smasheurs",R.drawable.phases3, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Phases 4/5","La Main phase 2 : vous pouvez de nouveau poser des cartes depuis votre main",R.drawable.phases4, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Phases 5/5","La End phase : vous donnez la main à votre adversaire",R.drawable.phases5, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));

        addSlide(AppIntroFragment.newInstance("Main phase 1/2","Vous pouvez poser vos \"smasheur\" en position d'attaque",R.drawable.attaque, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Main phase 2/2","ou bien en position de défense",R.drawable.defense, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));

        addSlide(AppIntroFragment.newInstance("Battle Phase","La Battle phase : vous pouvez attaquer avec vos smasheurs en position d'attaque",R.drawable.Bphase, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Battle Phase","Un smasheur = une attaque maximum par tour",R.drawable.Bphase1, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));

        addSlide(AppIntroFragment.newInstance("Battle Phase Att vs Att","Si un combat entre 2 \"smasheur\" en position d'attaque a lieu",R.drawable.BphaseAtt1, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Battle Phase Att vs Att","On compare l'\"Att\" des deux \"smasheur\"",R.drawable.BphaseAtt2, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Battle Phase Att vs Att","le smasheur avec la plus faible attaque est détruit et envoyé au cimetière",R.drawable.BphaseAtt3, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));

        addSlide(AppIntroFragment.newInstance("Battle Phase Att vs Def","Si un combat entre 1 \"smasheur\" en position d'attaque et un \"smasheur\" en position de défense a lieu",R.drawable.BphaseDef1, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Battle Phase Att vs Def","On compare l'\"Att\" et la \"Déf\" de chaque \"smasheur\"",R.drawable.BphaseDef2, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Battle Phase Att vs Def","Si le \"smasheur\" en position de Defense a une \"Déf\" supérieure à l'\"Att\" du deuxième \"smasheur\"",R.drawable.BphaseDef3, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Battle Phase Att vs Def","Aucun des 2 \"smasheurs\" n'est détruit",R.drawable.BphaseDef2, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Battle Phase Att vs Def","Sinon le \"smasheurs\" en position de défense est détruit",R.drawable.BphaseDef3, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));


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
