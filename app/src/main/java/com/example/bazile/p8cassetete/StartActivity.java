package com.example.bazile.p8cassetete;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends Activity implements View.OnClickListener {



    Button Jouer,Aide,About;
    TextView titre;
    Typeface font,font1,font2;
    String Play,Credit,Regle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        titre = (TextView) findViewById(R.id.start_title);
        Jouer = (Button)  findViewById(R.id.start_play);
        Aide = (Button)  findViewById(R.id.start_aide);
        About = (Button)  findViewById(R.id.start_about);


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

            break;

            case R.id.start_about :
                Intent intent_about = new Intent(this,AboutActivity.class);
                startActivity(intent_about);


            break;

            case R.id.start_aide :
                Intent intent_aide = new Intent(this,RegleActivity.class);
                startActivity(intent_aide);

            break;


        }

    }
}
