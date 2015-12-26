package com.example.bazile.p8cassetete;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;

public class PlayActivity extends Activity {

        CasseTeteView jeu;
        TextView text_temps,lose,level;
        Button Retour,Suivant;
        Typeface font;
        Chronometer tempsStart;
        MediaPlayer ffsound;
        Intent intentSuiv;
        int score = 0;
        Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        intentSuiv = new Intent(this,PlayActivity.class);
        score = intentSuiv.getIntExtra("Score",score);
        intentSuiv = getIntent();

        /*TextView */
        text_temps = (TextView) findViewById(R.id.temps);
        lose = (TextView) findViewById(R.id.Lose);
        level = (TextView) findViewById(R.id.level);
        font = Typeface.createFromAsset(getAssets(), "fonts/police2.ttf");

        /* style des text view */
        lose.setTypeface(font, Typeface.NORMAL);
        lose.setVisibility(View.INVISIBLE);

        level.setTypeface(font, Typeface.NORMAL);

        text_temps.setTypeface(font, Typeface.NORMAL);
        text_temps.setText("Temps");

        /* Music */
        ffsound = MediaPlayer.create(PlayActivity.this, R.raw.sound);

        /* Button et chronometre */
        Suivant  = (Button) findViewById(R.id.Suivant);
        Retour = (Button) findViewById(R.id.Retour);
        tempsStart = (Chronometer) findViewById(R.id.chronometer);

        Suivant.setTypeface(font);
        Suivant.setVisibility(View.INVISIBLE);
        Retour.setTypeface(font);
        Retour.setVisibility(View.INVISIBLE);

        tempsStart.setTypeface(font);
        tempsStart.setBase(SystemClock.elapsedRealtime());
        tempsStart.start();


        /** ... Gestion du temps ... **/
        tempsStart.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (chronometer.getText().toString().equalsIgnoreCase("01:00")) {
                    tempsStart.setTextColor(Color.parseColor("#FFEB3B"));
                    text_temps.setTextColor(Color.parseColor("#FFEB3B"));
                }

                if (chronometer.getText().toString().equalsIgnoreCase("01:40")) {
                    tempsStart.stop();
                    tempsStart.setTextColor(Color.parseColor("#F44336"));
                    text_temps.setTextColor(Color.parseColor("#F44336"));
                    lose.setVisibility(View.VISIBLE);
                    jeu.bloquer = false;
                    jeu.setVisibility(View.INVISIBLE);
                    Retour.setVisibility(View.VISIBLE);
                    Retour.setTextColor(Color.parseColor("#F44336"));
                }

                if (jeu.YouWin()) {
                    lose.setTextColor(Color.parseColor("#00E5FF"));
                    lose.setText("You Win");
                    lose.setVisibility(View.VISIBLE);
                    tempsStart.setTextColor(Color.parseColor("#00E5FF"));
                    text_temps.setTextColor(Color.parseColor("#00E5FF"));
                    tempsStart.stop();
                    jeu.bloquer = false;
                    score = score+1;
                    Retour.setVisibility(View.VISIBLE);
                    Suivant.setVisibility(View.VISIBLE);
                }
            }
        });

        /** ... Music ... **/
        ffsound.start();
        ffsound.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

            jeu = (CasseTeteView) findViewById(R.id.Cassetete);
            jeu.setStart(5, 5); //random

            jeu.setVisibility(View.VISIBLE);
        level.setText("Level:" + score);

    }

    /** ... Touche ... **/
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.Retour:
                Intent intentRetour = new Intent(this,StartActivity.class);
                startActivity(intentRetour);
                ffsound.stop();
                break;
            case R.id.Suivant:
                Intent intentSuiv = new Intent(this,PlayActivity.class);
                intentSuiv.putExtra("Score",score);
                startActivity(intentSuiv);
                ffsound.stop();
                break;
        }

    }
}
