package com.example.demineur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         //Intent fenetre_jeu;
         //fenetre_jeu = new Intent(MainActivity.this,GameWindow.class);
         //startActivity(fenetre_jeu);
        //
        Intent fenetre_score;
        fenetre_score= new Intent(MainActivity.this,ActivityScore.class);
        startActivity(fenetre_score);

    }
}