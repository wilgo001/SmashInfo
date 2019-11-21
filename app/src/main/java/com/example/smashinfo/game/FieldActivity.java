package com.example.smashinfo.game;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.smashinfo.R;
import com.example.smashinfo.activity.MainMenuActivity;
import com.google.firebase.database.FirebaseDatabase;

public class FieldActivity extends AppCompatActivity {

    public static String partieKey;
    public Player player1;
    public Player player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_field);

        Intent intent = this.getIntent();
        partieKey = intent.getStringExtra(MainMenuActivity.PARTIE_KEY);

        //Toast toast = Toast.makeText(getApplicationContext(), "key : " + partieKey, Toast.LENGTH_SHORT);
        //toast.show();


    }
}
