package com.example.smashinfo.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.smashinfo.R;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class tirageActivity extends com.github.paolorotolo.appintro.AppIntro {

    private String tir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_app_intro);

        Intent intent = this.getIntent();
        tir = intent.getStringExtra(MainMenuActivity.TIRAGE_KEY);

        switch (tir) {
            case "normal":

                break;
            case "rare":

                break;
        }
    }

    @Override
    public void onDonePressed(Fragment currentfragment) {
        super.onDonePressed(currentfragment);
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);

    }

    @Override
    public void onSkipPressed(Fragment currentfragment) {
        super.onSkipPressed(currentfragment);
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);

    }
}