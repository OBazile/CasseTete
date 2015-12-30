package com.example.bazile.p8cassetete;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends Activity implements View.OnClickListener {

    Button Jouer,Aide,About;
    TextView titre;
    Typeface font1;
    MediaPlayer sound;
    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        code = 5;
       sound = MediaPlayer.create(StartActivity.this, R.raw.sound);

        titre = (TextView) findViewById(R.id.start_title);
        Jouer = (Button)  findViewById(R.id.start_play);
        Aide = (Button)  findViewById(R.id.start_aide);
        About = (Button)  findViewById(R.id.start_about);

        sound.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (!mp.isPlaying())
                    mp.start();
                mp.setLooping(true);
            }
        });

        font1 = Typeface.createFromAsset(getAssets(), "fonts/police2.ttf");
        titre.setTypeface(font1);
        Jouer.setTypeface(font1);
        Aide.setTypeface(font1);
        About.setTypeface(font1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_play:
                Intent intent_play = new Intent(this,PlayActivity.class);
                startActivity(intent_play);
                sound.stop();
            break;

            case R.id.start_about :
                Intent intent_about = new Intent(this,AboutActivity.class);
                startActivityForResult(intent_about, code);
               sound.pause();
            break;

            case R.id.start_aide :
                Intent intent_aide = new Intent(this,RegleActivity.class);
                startActivity(intent_aide);
               sound.pause();
            break;
        }
    }
}
