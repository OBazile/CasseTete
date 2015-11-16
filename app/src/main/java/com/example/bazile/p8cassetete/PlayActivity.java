package com.example.bazile.p8cassetete;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PlayActivity extends AppCompatActivity {

        CasseTeteView casseTeteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        casseTeteView = (CasseTeteView) findViewById(R.id.Cassetete);

        casseTeteView.setVisibility(View.VISIBLE);


    }
}
