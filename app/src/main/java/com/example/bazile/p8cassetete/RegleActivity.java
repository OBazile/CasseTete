package com.example.bazile.p8cassetete;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

public class RegleActivity extends Activity {

    TextView RegleText;
    Button RegleButton;
    Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regle);
        RegleButton = (Button) findViewById(R.id.regleButton);
        RegleText = (TextView) findViewById(R.id.regleText);

        RegleText.setText("Regle Blaaa Blaaaaablaaa\nRgjgjgvkkkkbk\n");

        font = Typeface.createFromAsset(getAssets(), "fonts/police2.ttf");
        RegleText.setTypeface(font);
        RegleButton.setTypeface(font);

        RegleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRetour = new Intent(RegleActivity.this, StartActivity.class);
                startActivity(intentRetour);
            }
        });
    }
}
