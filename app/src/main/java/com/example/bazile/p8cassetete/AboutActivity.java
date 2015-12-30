package com.example.bazile.p8cassetete;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends Activity {

    TextView aboutText;
    Button aboutButton;
    Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        aboutText = (TextView) findViewById(R.id.aboutText);
        aboutButton = (Button) findViewById(R.id.aboutRetour);
        font = Typeface.createFromAsset(getAssets(), "fonts/police2.ttf");

        aboutText.setTypeface(font);
        aboutButton.setTypeface(font);

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finishActivity(5);
                Intent intentRetour = new Intent(AboutActivity.this,StartActivity.class);
                startActivity(intentRetour);
            }
        });
    }


}
