package com.example.bazile.p8cassetete;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PlayActivity extends Activity {

        CasseTeteView jeu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        jeu = (CasseTeteView) findViewById(R.id.Cassetete);


        jeu.setVisibility(View.VISIBLE);


    }
}
