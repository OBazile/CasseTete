package com.example.bazile.p8cassetete;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

public class PlayActivity extends Activity {

        CasseTeteView jeu;
        TextView Temps,lose;
        Button Retour;
        Typeface font;
        Chronometer tempsStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Temps = (TextView) findViewById(R.id.temps);
        lose = (TextView) findViewById(R.id.Lose);
        font = Typeface.createFromAsset(getAssets(), "fonts/police2.ttf");

        Retour = (Button) findViewById(R.id.playRetour);
        tempsStart = (Chronometer) findViewById(R.id.chronometer);

        lose.setTypeface(font, Typeface.NORMAL);
        Temps.setTypeface(font, Typeface.NORMAL);
        Temps.setText("Temps");
        lose.setVisibility(View.INVISIBLE);
        Retour.setTypeface(font);
        Retour.setVisibility(View.INVISIBLE);

        tempsStart.setTypeface(font);
        tempsStart.setBase(SystemClock.elapsedRealtime());
        tempsStart.start();

        tempsStart.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (chronometer.getText().toString().equalsIgnoreCase("00:10")) {

                    tempsStart.stop();
                    lose.setVisibility(View.VISIBLE);
                }
            }
        });



        jeu = (CasseTeteView) findViewById(R.id.Cassetete);
        jeu.setVisibility(View.VISIBLE);

    }
}
